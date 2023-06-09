package com.example.sprintwave.utility;

import com.example.sprintwave.model.*;
import com.example.sprintwave.repository.WorkspaceRepository;

import java.util.ArrayList;

//Class responsible for handeling data sorting, conversion and object creation
//NOTE sprint 4, put all sorting and object creation from mainController in this class instead
public class DataHandler {


    // Method for checking if user login values are correct
    public static boolean checkUserInformationMatch(User checkUser, String enteredPassword, String enteredEmail)
    {
            // TO DO: Create CHECK for user.
            String checkEmail = checkUser.getEmail();
            String checkPassword = checkUser.getUserPassword();
            System.out.println(checkUser);
            System.out.println(enteredPassword);
            if(checkEmail.equals(enteredEmail) && checkPassword.equals(enteredPassword))
            {
                return true;
            }
            else
            {
                return false;
            }
    }

    //Method to populate a user object with information and returning it
    //Note, sprint 4, should be done the same way for all model objects created in MainController
    public static User populateUserWithInformation(String email, String password, String firstname, String lastname, String workspacename, WorkspaceRepository workspaceRepository)
    {
        User user = new User();
        user.setFirstName(firstname);
        user.setLastName(lastname);
        user.setEmail(email);
        PasswordHashing passwordHashing = new PasswordHashing();
        String hashedpassword = passwordHashing.doHashing(password);
        user.setUserPassword(hashedpassword);
        user.setPermessionLevel(PermissionLevel.ADMINISTRATOR);
        Workspace withIDWorkspace = workspaceRepository.getLastEntryWorkspace();
        user.setWorkspaceId(withIDWorkspace.getID());
        return user;
    }

    //Puts a limit on how many storypoints a userstory or technicaltask can have
    public static int limitPoints(int points)
    {
        if(points > 100)
        {
            return 100;
        }
        else if(points >= 1 && points <= 100)
        {
            return points;
        }
        else
        {
            return 1;
        }
    }

    //Converts an int status representation to a Status
    public static Status convertIntToStatus(int status_index) {
        Status status = null;
        switch (status_index) {
            case 1:
                status = Status.sprintbacklog;
                break;
            case 2:
                status = Status.doing;
                break;
            case 3:
                status = Status.testing;
                break;
            case 4:
                status = Status.done;
                break;
            default:
                System.out.println("Could not convert number to status. Check if status entered is over 4 or below 1.");
        }
        return status;
    }

    //Method for deviding technicaltask arraylists into arrayslists only containing technicaltasks with a specific status
    public ArrayList<TechnicalTask> divideArrayIntoStatusSpecificArrays(ArrayList<TechnicalTask> allTechnicalTasks, String status, Sprint sprint)
    {
        // Note: If we have the time, make this method more efficient and shorter
        ArrayList<TechnicalTask> newListOfTasksForSpecificStatus = new ArrayList<>();
        for (TechnicalTask technicalTask: allTechnicalTasks)
        {
            switch (status)
            {
                case "sprintbacklog":
                {
                    if(technicalTask.getStatus() == Status.sprintbacklog && technicalTask.getSprintId() == sprint.getSprintId())
                    {
                        newListOfTasksForSpecificStatus.add(technicalTask);
                    }
                    break;
                }
                case "doing":
                {
                    if(technicalTask.getStatus() == Status.doing && technicalTask.getSprintId() == sprint.getSprintId())
                    {
                        newListOfTasksForSpecificStatus.add(technicalTask);
                    }
                    break;
                }
                case "testing":
                {
                    if(technicalTask.getStatus() == Status.testing && technicalTask.getSprintId() == sprint.getSprintId())
                    {
                        newListOfTasksForSpecificStatus.add(technicalTask);
                    }
                    break;
                }
                case "done":
                {
                    if(technicalTask.getStatus() == Status.done && technicalTask.getSprintId() == sprint.getSprintId())
                    {
                        newListOfTasksForSpecificStatus.add(technicalTask);
                    }
                    break;
                }
                default:
                {
                    System.out.println("Could not divide array correctly, check if status is provided");
                }
            }
        }
        return newListOfTasksForSpecificStatus;
    }

    //Method for converting status to an int representation of status (1-4)
    public static int convertStatusToInt(Status status)
    {
        int statusIndex = 0;
        switch (status)
        {
            case sprintbacklog:
            {
                statusIndex = 1;
                break;
            }
            case doing:
            {
                statusIndex = 2;
                break;
            }
            case testing:
            {
                statusIndex = 3;
                break;
            }
            case done:
            {
                statusIndex = 4;
                break;
            }
            default:
            {
                System.out.println("could not convert status enum to int, check if status is null");
            }
        }
        return statusIndex;
    }

    //Method for converting String representation of status to a status.
    public static Status convertStringToStatus(String Stringstatus)
    {
        Status status = null;
        switch (Stringstatus)
        {
            case "sprintbacklog":
            {
                status = Status.sprintbacklog;
                break;
            }
            case "doing":
            {
                status = Status.doing;
                break;
            }
            case "testing":
            {
                status = Status.testing;
                break;
            }
            case "done":
            {
                status = Status.done;
                break;
            }
            default:
            {
                System.out.println("could not convert status enum to int, check if status is null");
            }
        }
        return status;
    }

    //Method for converting boolean funcNonFunc to a String representation of funcNonFunc
    public static String convertFuncNonFuncBooleanToString(boolean funcNonFunc)
    {
        String returnValue = "";
        if(funcNonFunc)
        {
            returnValue = "Functional";
        }
        if(!funcNonFunc)
        {
            returnValue = "Non-functional";
        }
        return returnValue;
    }

    //Method for converting String Representation of funcNonFunc to boolean
    public static boolean convertFuncNonFuncStringToBoolean(String funcNonFuncChoice)
    {
        boolean returnValue = false;
        if(funcNonFuncChoice.equals("Functional"))
        {
            returnValue = true;
        }
        if(funcNonFuncChoice.equals("Non-functional"))
        {
            returnValue = false;
        }

        return returnValue;
    }

}
