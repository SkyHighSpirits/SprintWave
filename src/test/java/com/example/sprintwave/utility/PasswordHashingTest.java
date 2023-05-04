package com.example.sprintwave.utility;

import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.*;

class PasswordHashingTest {

    @Test
    public void tryHashing() {
        PasswordHashing ph = new PasswordHashing();
        String result = ph.doHashing("Hello World!");
        assertEquals("ed076287532e86365e841e92bfc50d8c", result);
    }
}