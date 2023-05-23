package com.example.sprintwave.utility;

import com.example.sprintwave.model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DataHandlerTest {

    @Test
    void limitPointstester() {
        assertEquals(1, DataHandler.limitPoints(-100));
    }

    @Test
    void checkUserInformationMatchtester() {
        User user = new User();
        user.setEmail("localhost@localhost.com");
        user.setUser_password("123456");
        String enteredEmail = "localhost@localhost.com";
        String enteredPassword = "123456";

        assertTrue(DataHandler.checkUserInformationMatch(user, enteredEmail, enteredPassword));
    }

    @Test
    void checkUserInformationMatchtesternumbertwo() {
        User user = new User();
        user.setEmail("localhost@localhost.com");
        user.setUser_password("123456");
        String enteredEmail = "bobo";
        String enteredPassword = "bibi";

        assertFalse(DataHandler.checkUserInformationMatch(user, enteredEmail, enteredPassword));
    }
}