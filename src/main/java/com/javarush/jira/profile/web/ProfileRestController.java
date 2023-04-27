package com.javarush.jira.profile.web;

import com.javarush.jira.login.AuthUser;
import com.javarush.jira.profile.ProfileService;
import com.javarush.jira.profile.ProfileTo;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = ProfileRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class ProfileRestController {
    //TODO добавил сервис для контроллера, поскольку он обращался напрямую в репозиторий
    private final ProfileService service;
    public static final String REST_URL = "/api/profile";

    @GetMapping
    public ProfileTo get(@AuthenticationPrincipal AuthUser authUser) {
        return service.get(authUser.id());
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody ProfileTo profileTo, @AuthenticationPrincipal AuthUser authUser) {
        service.update(profileTo, authUser.id());
    }


}

