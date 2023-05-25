package com.example.sprintwave.utility;

import com.example.sprintwave.model.TechnicalTask;

import java.util.ArrayList;
import java.util.Random;

public class Calculations {

    public int calculateSumOfPointsOnStatus(ArrayList<TechnicalTask> TechnicalTasksInSpecificStatus)
    {
        int sum = 0;
        for (TechnicalTask technicalTask: TechnicalTasksInSpecificStatus)
        {
            sum = sum + technicalTask.getPoints();
        }
        return sum;
    }
    
    public int createRandomNumber(){
        
        double answer = Math.random() * 100000000;
        
        return (int) answer;
        
    }


}
