package com.javarush.jira.profile;

import com.javarush.jira.common.util.validation.ValidationUtil;
import com.javarush.jira.profile.internal.Profile;
import com.javarush.jira.profile.internal.ProfileMapper;
import com.javarush.jira.profile.internal.ProfileRepository;
import com.javarush.jira.profile.internal.ProfileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProfileService {
    private final ProfileRepository repository;

    private final ProfileMapper mapper;

    public ProfileTo get(long id) {
        log.info("get {}", id);
        return mapper.toTo(repository.getOrCreate(id));
    }

    public void update(ProfileTo profileTo, long id) {
        log.info("update {}, user {}", profileTo, id);
        ValidationUtil.assureIdConsistent(profileTo, id);
        ValidationUtil.assureIdConsistent(profileTo.getContacts(), id);
        ProfileUtil.checkContactsExist(profileTo.getContacts());
        Profile profile = mapper.updateFromTo(repository.getOrCreate(profileTo.id()), profileTo);
        repository.save(profile);
    }
}
