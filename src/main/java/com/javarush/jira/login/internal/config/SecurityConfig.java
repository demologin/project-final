package com.javarush.jira.login.internal.config;

import com.javarush.jira.login.AuthUser;
import com.javarush.jira.login.Role;
import com.javarush.jira.login.internal.UserRepository;
import com.javarush.jira.login.internal.jwtauth.JwtAuthenticationFilter;
import com.javarush.jira.login.internal.jwtauth.JwtService;
import com.javarush.jira.login.internal.jwtauth.TokenRepository;
import com.javarush.jira.login.internal.sociallogin.CustomOAuth2UserService;
import com.javarush.jira.login.internal.sociallogin.CustomTokenResponseConverter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.endpoint.DefaultAuthorizationCodeTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.http.OAuth2ErrorResponseErrorHandler;
import org.springframework.security.oauth2.core.http.converter.OAuth2AccessTokenResponseHttpMessageConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@Slf4j
@AllArgsConstructor
//https://stackoverflow.com/questions/72493425/548473
public class SecurityConfig {
    public static final PasswordEncoder PASSWORD_ENCODER = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    private final UserRepository userRepository;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    private final LogoutHandler logoutHandler;
    private final JwtService jwtService;
    private final TokenRepository tokenRepository;

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(jwtService, userDetailsService(), tokenRepository);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PASSWORD_ENCODER;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return email -> {
            log.debug("Authenticating '{}'", email);
            return new AuthUser(userRepository.getExistedByEmail(email));
        };
    }

    @Bean
    @Order(1)
    public SecurityFilterChain apiFilterChain(HttpSecurity http) throws Exception {
        http.securityMatcher("/api/**").authorizeHttpRequests()
                .requestMatchers("/api/v1/auth/**").permitAll()
                .requestMatchers("/api/admin/**").hasRole(Role.ADMIN.name())
                .requestMatchers(HttpMethod.POST, "/api/users").anonymous()
                .requestMatchers("/api/**").authenticated()
                .and().httpBasic()
                .authenticationEntryPoint(restAuthenticationEntryPoint)
                .and()
                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.NEVER)
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .logout()
                .logoutUrl("/api/v1/auth/logout")
                .addLogoutHandler(logoutHandler)
                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
                .and().csrf().disable();
        return http.build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                .requestMatchers("/view/unauth/**", "/ui/register/**", "/ui/password").anonymous()
                .requestMatchers("/", "/doc", "/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**", "/static/**").permitAll()
                .requestMatchers("/ui/admin/**", "/view/admin/**").hasRole(Role.ADMIN.name())
                .anyRequest().authenticated()
                .and().formLogin().permitAll()
                .loginPage("/view/login")
                .defaultSuccessUrl("/", true)
                .and().oauth2Login()
                .loginPage("/view/login")
                .defaultSuccessUrl("/", true)
                .tokenEndpoint()
                .accessTokenResponseClient(accessTokenResponseClient())
                .and()
                .userInfoEndpoint()
                .userService(customOAuth2UserService)
                .and().and().logout()
                .logoutUrl("/ui/logout")
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .deleteCookies("JSESSIONID")
                .and().csrf().disable();
        return http.build();
    }

    @Bean
    public OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> accessTokenResponseClient() {
        DefaultAuthorizationCodeTokenResponseClient accessTokenResponseClient =
                new DefaultAuthorizationCodeTokenResponseClient();
        OAuth2AccessTokenResponseHttpMessageConverter tokenResponseHttpMessageConverter =
                new OAuth2AccessTokenResponseHttpMessageConverter();
        tokenResponseHttpMessageConverter.setAccessTokenResponseConverter(new CustomTokenResponseConverter());

        RestTemplate restTemplate = new RestTemplate(
                Arrays.asList(
                        new FormHttpMessageConverter(), tokenResponseHttpMessageConverter
                )
        );
        restTemplate.setErrorHandler(new OAuth2ErrorResponseErrorHandler());
        accessTokenResponseClient.setRestOperations(restTemplate);
        return accessTokenResponseClient;
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
