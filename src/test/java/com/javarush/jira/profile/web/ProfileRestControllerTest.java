package com.javarush.jira.profile.web;

import com.javarush.jira.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProfileRestControllerTest extends AbstractControllerTest {

    /**
     * Так как метод get() класса ProfileRestController состоит только из реализации super.get(),
     * то будет целеобразно реализовать unit тестирование именно в родительском классе
     */
    @Test
    void stubMethodGet() {
    }

    /**
     * Так как метод update() класса ProfileRestController состоит только из реализации super.update(),
     * то будет целеобразно реализовать unit тестирование именно в родительском классе
     */
    @Test
    void stubMethodUpdate() {
    }
}