package com.example.sprintwave.repository;


import com.example.sprintwave.model.Epic;
import com.example.sprintwave.utility.ConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class EpicRepository {

    @Value("$spring.datasource.url")
    String DB_HOSTNAME;

    @Value("$spring.datasource.username")
    String DB_USERNAME;

    @Value("$spring.datasource.password")
    String DB_PASSWORD;

    public void createEpic(Epic newEpic) {
        try {
            //Opret forbindelse til database
            Connection connection = ConnectionManager.getConnection(DB_HOSTNAME, DB_USERNAME, DB_PASSWORD);
            //SQL statement
            String SQL_QUERY = "INSERT INTO epics(project_id, epic_name, epic_description) VALUES (?, ?, ?)";
            //prepared statement
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_QUERY);
            //set parameters
            preparedStatement.setInt(1, newEpic.getProject_id());
            preparedStatement.setString(2, newEpic.getEpic_name());
            preparedStatement.setString(3, newEpic.getEpic_description());
            //execute query
            preparedStatement.executeUpdate();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            System.out.println("Did not create the epic");
        }
    }

    public void updateEpic(Epic updateEpic){
        try {
            //Oprette forbindelse til database
            Connection connection = ConnectionManager.getConnection(DB_HOSTNAME, DB_USERNAME, DB_PASSWORD);
            //SQL statement
            String SQL_QUERY = "UPDATE epics SET epic_name = ?, epic_description = ? WHERE epic_id = ?";
            //PreparedStatement
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_QUERY);
            //set parameters
            preparedStatement.setString(1, updateEpic.getEpic_name());
            preparedStatement.setString(2, updateEpic.getEpic_description());
            preparedStatement.setInt(3, updateEpic.getEpic_id());
            //ExecuteStatement
            preparedStatement.executeUpdate();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            System.out.println("Did not update the epic");
        }
    }

    public void deleteEpic(int deleteEpicID){
        try {
            //Oprette forbindelse til database
            Connection connection = ConnectionManager.getConnection(DB_HOSTNAME, DB_USERNAME, DB_PASSWORD);
            //SQL statement
            String SQL_QUERY = "DELETE FROM epics WHERE epic_id = ?";
            //PreparedStatement
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_QUERY);
            //set parameters
            preparedStatement.setInt(1, deleteEpicID);
            //execute statement
            preparedStatement.executeQuery();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            System.out.println("Did not delete the epic");
        }
    }

    public Epic findEpicByID(int id){
        Epic foundEpic = new Epic();
        foundEpic.setEpic_id(id);
        try {
            //Oprette forbindelse til database
            Connection connection = ConnectionManager.getConnection(DB_HOSTNAME, DB_USERNAME, DB_PASSWORD);
            //SQL statement
            String SQL_QUERY = "SELECT * FROM epics WHERE epic_id = ?";
            //PreparedStatement
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_QUERY);
            //set parameters
            preparedStatement.setInt(1, id);
            //execute statement
            ResultSet resultSet = preparedStatement.executeQuery();
            //Få et epic ud fra databasen
            foundEpic.setProject_id(resultSet.getInt(1));
            foundEpic.setEpic_name(resultSet.getString(3));
            foundEpic.setEpic_description(resultSet.getString(4));
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            System.out.println("No epic was found by that id");
        }
        return foundEpic;
    }

    public ArrayList<Epic> getAllEpicByProjectID(int projectID){
        ArrayList<Epic> epicList = new ArrayList<>();
        try {
            //Oprette forbindelse til database
            Connection connection = ConnectionManager.getConnection(DB_HOSTNAME, DB_USERNAME, DB_PASSWORD);
            //SQL statement
            String SQL_QUERY = "SELECT * FROM epics WHERE project_id = ?";
            //Prepared statement
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_QUERY);
            //set parameters
            preparedStatement.setInt(1, projectID);
            //execute statement
            ResultSet resultSet = preparedStatement.executeQuery();
            //set resultset
            Epic foundEpic = new Epic();
            while(resultSet.next()) {
                foundEpic.setProject_id(projectID);
                foundEpic.setEpic_id(resultSet.getInt(2));
                foundEpic.setEpic_name(resultSet.getString(3));
                foundEpic.setEpic_description(resultSet.getString(4));
                epicList.add(foundEpic);
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            System.out.println("Something went wrong when getting all the epics");
        }

        return epicList;
    }
}
