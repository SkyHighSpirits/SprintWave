package com.example.sprintwave.repository;

import com.example.sprintwave.model.TechnicalTask;
import com.example.sprintwave.model.Userstory;
import com.example.sprintwave.utility.ConnectionManager;
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
        ArrayList<TechnicalTask> tecnicaltasks = new ArrayList<>();
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
                tecnicaltasks.add(technicalTask);
            }
        }
        catch(SQLException e)
        {
            System.out.println("Could not query userstories from database");
            e.printStackTrace();
        }
        return tecnicaltasks;
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
            final String SQL_CREATE = "INSERT INTO userstories" +
                    "(userstory_id,userstory_name,userstory_description, userstory_released)" +
                    "VALUES (?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_CREATE);
            preparedStatement.setInt(1, technicalTask.getUserstory_id());
            preparedStatement.setString(2, technicalTask.getName());
            preparedStatement.setString(3, technicalTask.getDescription());
            preparedStatement.setBoolean(4, technicalTask.isReleased());
            preparedStatement.executeUpdate();
        }
        catch (SQLException e)
        {
            System.out.println("Could not create new technicalTask in database");
            e.printStackTrace();
        }
    }

}
