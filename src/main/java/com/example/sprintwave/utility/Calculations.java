package com.example.sprintwave.utility;

import com.example.sprintwave.model.TechnicalTask;

import java.util.ArrayList;

public class Calculations {

    //Method for calculation the sum of all points contained by technicaltasks for a specific row on the sprint board
    //takes a sorted arrayList with same status technicaltasks
    public int calculateSumOfPointsOnStatus(ArrayList<TechnicalTask> TechnicalTasksInSpecificStatus)
    {
        //Initialize return variable to 0
        int sum = 0;
        //Go through each technicaltask in the arraylist
        for (TechnicalTask technicalTask: TechnicalTasksInSpecificStatus)
        {
            //Assign the new sum to be the old sum plus the points in the technicaltask
            sum = sum + technicalTask.getPoints();
        }
        //Return the final value when all tehcnicaltasks has been looped through
        return sum;
    }


}
