package com.javarush.jira.bugtracking;

import com.javarush.jira.bugtracking.internal.model.UserBelong;
import com.javarush.jira.bugtracking.internal.repository.UserBelongRepository;
import com.javarush.jira.common.HasId;
import com.javarush.jira.common.Subscribable;
import com.javarush.jira.common.error.NotFoundException;
import com.javarush.jira.login.Role;
import com.javarush.jira.login.User;
import com.javarush.jira.login.internal.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class UserBelongService {

    @Autowired
    protected UserBelongRepository userBelongRepository;
    @Autowired
    protected UserRepository userRepository;

    public void subscribe(Long userId, Subscribable object){
        List<UserBelong> allUserBelongs = userBelongRepository.getAllByUserId(userId);
        List<Long> userAssignedObjectsIds = allUserBelongs.stream()
                .map(UserBelong::getObjectId)
                .toList();

        User existed = userRepository.getExisted(userId);

        if (userAssignedObjectsIds.size() == 0 || !userAssignedObjectsIds.contains(object.getId())){
            String userType = getUserType(existed);

            UserBelong userBelong = new UserBelong();
            userBelong.setObjectId(object.getId());
            userBelong.setObjectType(object.getObjectType());
            userBelong.setUserId(userId);
            userBelong.setUserTypeCode(userType);
            userBelong.setStartpoint(LocalDateTime.now());
            userBelong.setEndpoint(null);
            userBelongRepository.save(userBelong);
        }
    }

    private String getUserType(User user){
        Optional<Role> maxRole = user.getRoles().stream()
                .filter(Objects::nonNull)
                .max(Comparator.comparing(Enum::ordinal));
        if (maxRole.isPresent()) {
            return maxRole.get().name().toLowerCase();
        } else {
            log.error("No Role found for user id=%s".formatted(user.id()));
            throw new NotFoundException("No Role found for user id=%s".formatted(user.id()));
        }
    }

}
