package com.javarush.jira.bugtracking;

import com.javarush.jira.bugtracking.internal.mapper.UserBelongMapper;
import com.javarush.jira.bugtracking.internal.repository.UserBelongRepository;
import com.javarush.jira.bugtracking.to.UserBelongTo;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.javarush.jira.common.util.validation.ValidationUtil.checkNew;
//TODO notifications for watching objects functionality
@RestController
@RequestMapping(value = UserBelongRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Slf4j
public class UserBelongRestController {
    static final String REST_URL = "/api/user_belong";

    private UserBelongRepository repository;
    private UserBelongMapper mapper;
    private UserBelongService service;

    @GetMapping("/byUserId/{userId}")
    public List<UserBelongTo> getUserBelongByUser(@PathVariable long userId){
        return service.getToByUserId(userId);
    }

    @GetMapping("/byObjectId/{objectId}")
    public List<UserBelongTo> getUserBelongByObject(@PathVariable long objectId){
        return service.getToByObjectId(objectId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void create(@Valid @RequestBody UserBelongTo userBelongTo) {
        log.debug("create {}", userBelongTo);
        checkNew(userBelongTo);
        repository.save(mapper.toEntity(userBelongTo));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        log.debug("delete by id {}", id);
        repository.deleteExisted(id);
    }
}
