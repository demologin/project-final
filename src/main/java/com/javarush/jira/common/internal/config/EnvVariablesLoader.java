package com.javarush.jira.common.internal.config;

import io.github.cdimascio.dotenv.Dotenv;

//temporarily class - development purposes (will delete while adding Dockerfile)
public class EnvVariablesLoader {
    public static void loadEnvVariables() {
        Dotenv dotenv = Dotenv.configure().load();
        String username = dotenv.get("DATASOURCE_USERNAME");
        String password = dotenv.get("DATASOURCE_PASSWORD");


        String githubClientId = dotenv.get("OAUTH2_REGISTRATION_GITHUB_CLIENT_ID");
        String githubClientSecret = dotenv.get("OAUTH2_REGISTRATION_GITHUB_CLIENT_SECRET");

        String googleClientId = dotenv.get("OAUTH2_REGISTRATION_GOOGLE_CLIENT_ID");
        String googleClientSecret = dotenv.get("OAUTH2_REGISTRATION_GOOGLE_CLIENT_SECRET");

        String gitlabClientId = dotenv.get("OAUTH2_REGISTRATION_GITLAB_CLIENT_ID");
        String gitlabClientSecret = dotenv.get("OAUTH2_REGISTRATION_GITLAB_CLIENT_SECRET");

        String mailPropertiesSmtpStarttlsEnable = dotenv.get("MAIL_PROPERTIES_SMTP_STARTTLS_ENABLE");
        String springMailPropertiesSmtpAuth = dotenv.get("MAIL_PROPERTIES_SMTP_AUTH");

        String springMailPropertiesHost = dotenv.get("MAIL_PROPERTIES_HOST");
        String springMailPropertiesUsername = dotenv.get("MAIL_PROPERTIES_USERNAME");
        String springMailPropertiesPassword = dotenv.get("MAIL_PROPERTIES_PASSWORD");
        String springMailPropertiesPort = dotenv.get("MAIL_PROPERTIES_PORT");



        System.setProperty("DATASOURCE_USERNAME", username);
        System.setProperty("DATASOURCE_PASSWORD", password);
        System.setProperty("OAUTH2_REGISTRATION_GITHUB_CLIENT_ID", githubClientId);
        System.setProperty("OAUTH2_REGISTRATION_GITHUB_CLIENT_SECRET", githubClientSecret);
        System.setProperty("OAUTH2_REGISTRATION_GOOGLE_CLIENT_ID", googleClientId);
        System.setProperty("OAUTH2_REGISTRATION_GOOGLE_CLIENT_SECRET", googleClientSecret);

        System.setProperty("OAUTH2_REGISTRATION_GITLAB_CLIENT_ID", gitlabClientId);
        System.setProperty("OAUTH2_REGISTRATION_GITLAB_CLIENT_SECRET",gitlabClientSecret );

        System.setProperty("MAIL_PROPERTIES_SMTP_STARTTLS_ENABLE", mailPropertiesSmtpStarttlsEnable);
        System.setProperty("MAIL_PROPERTIES_SMTP_AUTH", springMailPropertiesSmtpAuth);
        System.setProperty("MAIL_PROPERTIES_HOST", springMailPropertiesHost);
        System.setProperty("MAIL_PROPERTIES_USERNAME", springMailPropertiesUsername);
        System.setProperty("MAIL_PROPERTIES_PASSWORD", springMailPropertiesPassword);
        System.setProperty("MAIL_PROPERTIES_PORT", springMailPropertiesPort);

    }
}
