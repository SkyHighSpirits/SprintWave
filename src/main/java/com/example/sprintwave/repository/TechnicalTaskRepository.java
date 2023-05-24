package com.example.sprintwave.repository;

import com.example.sprintwave.model.TechnicalTask;
import com.example.sprintwave.utility.ConnectionManager;
import com.example.sprintwave.utility.DataHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Repository
public class TechnicalTaskRepository {

    @Value("${spring.datasource.url}")
    String DB_HOSTNAME;
    @Value("${spring.datasource.username}")
    String DB_USERNAME;
    @Value("${spring.datasource.password}")
    String DB_PASSWORD;


    public ArrayList<TechnicalTask> getAllTechnicalTasksFromUserstoryID(int id)
    {
        ArrayList<TechnicalTask> technicalTasks = new ArrayList<>();
        try
        {
            Connection connection = ConnectionManager.getConnection(DB_HOSTNAME, DB_USERNAME, DB_PASSWORD);
            final String SQL_QUERY = "SELECT * FROM technicaltasks WHERE userstory_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_QUERY);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next())
            {
                TechnicalTask technicalTask = new TechnicalTask();
                technicalTask.setId(resultSet.getInt(1));
                technicalTask.setUserstory_id(resultSet.getInt(2));
                technicalTask.setName(resultSet.getString(3));
                technicalTask.setDescription(resultSet.getString(4));
                technicalTask.setReleased(resultSet.getBoolean(5));
                technicalTask.setPoints(resultSet.getInt(6));
                technicalTask.setStatus(DataHandler.convertIntToStatus(resultSet.getInt(7)));
                technicalTask.setSprint_id(resultSet.getInt(8));
                technicalTasks.add(technicalTask);
            }
        }
        catch(SQLException e)
        {
            System.out.println("Could not query userstories from database");
            e.printStackTrace();
        }
        return technicalTasks;
    }

    public ArrayList<TechnicalTask> getAllTechnicalTasks()
    {
        ArrayList<TechnicalTask> technicaltasks = new ArrayList<>();
        try
        {
            Connection connection = ConnectionManager.getConnection(DB_HOSTNAME, DB_USERNAME, DB_PASSWORD);
            final String SQL_QUERY = "SELECT * FROM technicaltasks";
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_QUERY);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next())
            {
                TechnicalTask technicalTask = new TechnicalTask();
                technicalTask.setId(resultSet.getInt(1));
                technicalTask.setUserstory_id(resultSet.getInt(2));
                technicalTask.setName(resultSet.getString(3));
                technicalTask.setDescription(resultSet.getString(4));
                technicalTask.setReleased(resultSet.getBoolean(5));
                technicalTask.setPoints(resultSet.getInt(6));
                technicalTask.setStatus(DataHandler.convertIntToStatus(resultSet.getInt(7)));
                technicalTask.setSprint_id(resultSet.getInt(8));
                technicaltasks.add(technicalTask);
            }
        }
        catch(SQLException e)
        {
            System.out.println("Could not query userstories from database");
            e.printStackTrace();
        }
        return technicaltasks;
    }

    public void createNewTecnicalTask(TechnicalTask technicalTask)
    {
        try {
            Connection connection = ConnectionManager.getConnection(DB_HOSTNAME, DB_USERNAME, DB_PASSWORD);
            final String SQL_CREATE = "INSERT INTO technicaltasks" +
                    "(userstory_id, technicaltask_name, technicaltask_description, technicaltask_released, technicaltask_points, technicaltask_status, sprint_id)" +
                    "VALUES (?,?,?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_CREATE);
            preparedStatement.setInt(1, technicalTask.getUserstory_id());
            preparedStatement.setString(2, technicalTask.getName());
            preparedStatement.setString(3, technicalTask.getDescription());
            preparedStatement.setBoolean(4, technicalTask.isReleased());
            preparedStatement.setInt(5, technicalTask.getPoints());
            preparedStatement.setInt(6, DataHandler.convertStatusToInt(technicalTask.getStatus()));
            preparedStatement.setInt(7, technicalTask.getSprint_id());
            preparedStatement.executeUpdate();
        }
        catch (SQLException e)
        {
            System.out.println("Could not create new technicalTask in database");
            e.printStackTrace();
        }
    }

    public TechnicalTask getSpecificTechnicalTaskFromID(int task_id)
    {
        TechnicalTask technicalTask = new TechnicalTask();
        try
        {
            Connection connection = ConnectionManager.getConnection(DB_HOSTNAME, DB_USERNAME, DB_PASSWORD);
            final String SQL_QUERY = "SELECT * FROM technicaltasks WHERE technicaltask_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_QUERY);
            preparedStatement.setInt(1, task_id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next())
            {
                technicalTask.setId(resultSet.getInt(1));
                technicalTask.setUserstory_id(resultSet.getInt(2));
                technicalTask.setName(resultSet.getString(3));
                technicalTask.setDescription(resultSet.getString(4));
                technicalTask.setReleased(resultSet.getBoolean(5));
                technicalTask.setPoints(resultSet.getInt(6));
                technicalTask.setStatus(DataHandler.convertIntToStatus(resultSet.getInt(7)));
                technicalTask.setSprint_id(resultSet.getInt(8));
            }
        }
        catch(SQLException e)
        {
            System.out.println("Could not query technicaltask from database");
            e.printStackTrace();
        }
        return technicalTask;
    }

    public void updateTechnicalTask(TechnicalTask technicalTask)
    {
        try {
            //Oprette forbindelse til database
            Connection connection = ConnectionManager.getConnection(DB_HOSTNAME, DB_USERNAME, DB_PASSWORD);
            //SQL statement
            String SQL_QUERY = "UPDATE technicaltasks SET userstory_id = ? ,technicaltask_name = ?, technicaltask_description = ?, technicaltask_released = ?, technicaltask_points = ?, technicaltask_status = ?, sprint_id = ? WHERE technicaltask_id = ?";
            //PreparedStatement
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_QUERY);
            //set parameters
            preparedStatement.setInt(1, technicalTask.getUserstory_id());
            preparedStatement.setString(2, technicalTask.getName());
            preparedStatement.setString(3, technicalTask.getDescription());
            preparedStatement.setBoolean(4, technicalTask.isReleased());
            preparedStatement.setInt(5, technicalTask.getPoints());
            preparedStatement.setInt(6, DataHandler.convertStatusToInt(technicalTask.getStatus()));
            preparedStatement.setInt(7, technicalTask.getId());
            System.out.println("Sprint id: " + technicalTask.getSprint_id());
            preparedStatement.setInt(8, technicalTask.getSprint_id());
            //ExecuteStatement
            preparedStatement.executeUpdate();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            System.out.println("Did not update the epic");
        }
    }

    public void updateTechnicalTaskUsingIntStatus(TechnicalTask technicalTask, int status)
    {
        try {
            //Oprette forbindelse til database
            Connection connection = ConnectionManager.getConnection(DB_HOSTNAME, DB_USERNAME, DB_PASSWORD);
            //SQL statement
            String SQL_QUERY = "UPDATE technicaltasks SET technicaltask_status = ? WHERE technicaltask_id = ?";
            //PreparedStatement
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_QUERY);
            //set parameters
            preparedStatement.setInt(1, status);
            preparedStatement.setInt(2, technicalTask.getId());
            System.out.println("Sprint id: " + technicalTask.getSprint_id());
            //ExecuteStatement
            preparedStatement.executeUpdate();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            System.out.println("Did not update the technicaltask");
        }
    }

    public void deleteTechnicalTask(int deleteTaskId){
        try {
            //Oprette forbindelse til database
            Connection connection = ConnectionManager.getConnection(DB_HOSTNAME, DB_USERNAME, DB_PASSWORD);
            //SQL statement
            String SQL_QUERY = "DELETE FROM technicaltasks WHERE technicaltask_id = ?";
            //PreparedStatement
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_QUERY);
            //set parameters
            preparedStatement.setInt(1, deleteTaskId);
            //execute statement
            preparedStatement.execute();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            System.out.println("Did not delete the Task");
        }
    }

}
