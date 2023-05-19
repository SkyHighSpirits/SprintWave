package com.example.sprintwave.utility;

import com.example.sprintwave.model.Status;
import com.example.sprintwave.model.TechnicalTask;

import java.util.ArrayList;

public class calculations {

    public static int calculateSumOfPointsOnStatus(ArrayList<TechnicalTask> TechnicalTasksInSpecificStatus)
    {
        int sum = 0;
        for (TechnicalTask technicalTask: TechnicalTasksInSpecificStatus)
        {
            sum = sum + technicalTask.getPoints();
        }
        return sum;
    }


}
