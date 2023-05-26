package com.example.sprintwave.utility;

import com.example.sprintwave.model.TechnicalTask;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.sprintwave.utility.Calculations;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

class calculationsTest
{



    @Test
    public void calculateSumOfPointsOnStatustest ()
    {
        Calculations calculations = new Calculations();
        TechnicalTask technicalTask1 = new TechnicalTask();
        TechnicalTask technicalTask2 = new TechnicalTask();
        TechnicalTask technicalTask3 = new TechnicalTask();
        TechnicalTask technicalTask4 = new TechnicalTask();

        technicalTask1.setPoints(4);
        technicalTask2.setPoints(8);
        technicalTask3.setPoints(6);
        technicalTask4.setPoints(3);

        ArrayList<TechnicalTask> techtasks= new ArrayList<>();
        techtasks.add(technicalTask1);
        techtasks.add(technicalTask2);
        techtasks.add(technicalTask3);
        techtasks.add(technicalTask4);
        assertEquals(21, calculations.calculateSumOfPointsOnStatus(techtasks));
    }
}