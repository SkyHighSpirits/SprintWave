package com.example.sprintwave.utility;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DataHandlerTest {

    @Test
    void limitPointstester() {
        assertEquals(1, DataHandler.limitPoints(-100));
    }
}