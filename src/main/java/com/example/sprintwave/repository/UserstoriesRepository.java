package com.example.sprintwave.repository;

import com.example.sprintwave.model.Userstory;
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
public class UserstoriesRepository {

    @Value("${spring.datasource.url}")
    String DB_HOSTNAME;
    @Value("${spring.datasource.username}")
    String DB_USERNAME;
    @Value("${spring.datasource.password}")
    String DB_PASSWORD;

    public ArrayList<Userstory> getAllUserstoriesFromProjectID(int id)
    {
        ArrayList<Userstory> userstories = new ArrayList<>();
        try
        {
            Connection connection = ConnectionManager.getConnection(DB_HOSTNAME, DB_USERNAME, DB_PASSWORD);
            final String SQL_QUERY = "SELECT * FROM userstories WHERE project_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_QUERY);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next())
            {
                Userstory userstory = new Userstory();
                userstory.setId(resultSet.getInt(1));
                userstory.setProjectId(resultSet.getInt(2));
                userstory.setName(resultSet.getString(3));
                userstory.setDescription(resultSet.getString(4));
                userstory.setReleased(resultSet.getBoolean(5));
                userstory.setPoints(resultSet.getInt(6));
                userstory.setStatus(DataHandler.convertIntToStatus(resultSet.getInt(7)));
                userstory.setSprintId(resultSet.getInt(8));
                userstories.add(userstory);
            }
        }
        catch(SQLException e)
        {
            System.out.println("Could not query userstories from database");
            e.printStackTrace();
        }
        return userstories;
    }

    public void createNewUserstory(Userstory userstory)
    {
        try {
            Connection connection = ConnectionManager.getConnection(DB_HOSTNAME, DB_USERNAME, DB_PASSWORD);
            final String SQL_CREATE = "INSERT INTO userstories" +
                    "(project_id,userstory_name,userstory_description, userstory_released, userstory_points, userstory_status, sprint_id)" +
                    "VALUES (?,?,?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_CREATE);
            preparedStatement.setInt(1, userstory.getProjectId());
            preparedStatement.setString(2, userstory.getName());
            preparedStatement.setString(3, userstory.getDescription());
            preparedStatement.setBoolean(4, userstory.isReleased());
            preparedStatement.setInt(5, userstory.getPoints());
            preparedStatement.setInt(6, DataHandler.convertStatusToInt(userstory.getStatus()));
            preparedStatement.setInt(7, userstory.getSprintId());
            preparedStatement.executeUpdate();
        }
        catch (SQLException e)
        {
            System.out.println("Could not create new userstory in database");
            e.printStackTrace();
        }
    }

    public Userstory getSpecificUserstoryByID(int ID)
    {
        Userstory userstory = new Userstory();
        try
        {
            Connection connection = ConnectionManager.getConnection(DB_HOSTNAME, DB_USERNAME, DB_PASSWORD);
            final String SQL_QUERY = "SELECT * FROM userstories WHERE userstory_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_QUERY);
            preparedStatement.setInt(1, ID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next())
            {
                userstory.setId(resultSet.getInt(1));
                userstory.setProjectId(resultSet.getInt(2));
                userstory.setName(resultSet.getString(3));
                userstory.setDescription(resultSet.getString(4));
                userstory.setReleased(resultSet.getBoolean(5));
                userstory.setPoints(resultSet.getInt(6));
                userstory.setStatus(DataHandler.convertIntToStatus(resultSet.getInt(7)));
                userstory.setSprintId(resultSet.getInt(8));
            }
        }
        catch(SQLException e)
        {
            System.out.println("Could not query userstories from database");
            e.printStackTrace();
        }
        return userstory;

    }

    public void updateUserstory(Userstory userstory)
    {
        try {
            //Oprette forbindelse til database
            Connection connection = ConnectionManager.getConnection(DB_HOSTNAME, DB_USERNAME, DB_PASSWORD);
            //SQL statement
            String SQL_QUERY = "UPDATE userstories SET userstory_name = ?, userstory_description = ?, userstory_released = ?, userstory_points = ?, userstory_status = ?, sprint_id = ? WHERE userstory_id = ?";
            //PreparedStatement
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_QUERY);
            //set parameters
            preparedStatement.setString(1, userstory.getName());
            preparedStatement.setString(2, userstory.getDescription());
            preparedStatement.setBoolean(3, userstory.isReleased());
            preparedStatement.setInt(4, userstory.getPoints());
            preparedStatement.setInt(5, DataHandler.convertStatusToInt(userstory.getStatus()));
            preparedStatement.setInt(6, userstory.getSprintId());
            preparedStatement.setInt(7, userstory.getId());

            //ExecuteStatement
            preparedStatement.executeUpdate();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            System.out.println("Did not update the userstory");
        }
    }

    public void updateUserstoryWithIntStatus(Userstory userstory, int status)
    {
        try {
            //Oprette forbindelse til database
            Connection connection = ConnectionManager.getConnection(DB_HOSTNAME, DB_USERNAME, DB_PASSWORD);
            //SQL statement
            String SQL_QUERY = "UPDATE userstories SET userstory_name = ?, userstory_description = ?, userstory_released = ?, userstory_points = ?, userstory_status = ?, sprint_id = ? WHERE userstory_id = ?";
            //PreparedStatement
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_QUERY);
            //set parameters
            preparedStatement.setString(1, userstory.getName());
            preparedStatement.setString(2, userstory.getDescription());
            preparedStatement.setBoolean(3, userstory.isReleased());
            preparedStatement.setInt(4, userstory.getPoints());
            preparedStatement.setInt(5, status);
            preparedStatement.setInt(6, userstory.getSprintId());
            preparedStatement.setInt(7, userstory.getId());

            //ExecuteStatement
            preparedStatement.executeUpdate();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            System.out.println("Did not update the userstory");
        }
    }

    public void deleteUserstory(int deleteUserstoryID){
        try {
            //Oprette forbindelse til database
            Connection connection = ConnectionManager.getConnection(DB_HOSTNAME, DB_USERNAME, DB_PASSWORD);
            //SQL statement
            String SQL_QUERY = "DELETE FROM userstories WHERE userstory_id = ?";
            //PreparedStatement
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_QUERY);
            //set parameters
            preparedStatement.setInt(1, deleteUserstoryID);
            //execute statement
            preparedStatement.execute();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            System.out.println("Did not delete the Userstory");
        }
    }

}
