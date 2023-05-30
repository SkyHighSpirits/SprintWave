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

@Repository
public class EpicRepository {

    @Value("${spring.datasource.url}")
    String DB_HOSTNAME;
    @Value("${spring.datasource.username}")
    String DB_USERNAME;
    @Value("${spring.datasource.password}")
    String DB_PASSWORD;

    //Takes an object parameter, and creates a preparedstatement to shoot to the database
    public void createEpic(Epic newEpic) {
        try {
            //Opret forbindelse til database
            Connection connection = ConnectionManager.getConnection(DB_HOSTNAME, DB_USERNAME, DB_PASSWORD);
            //SQL statement
            String SQL_QUERY = "INSERT INTO epics(project_id, epic_name, epic_description) VALUES (?, ?, ?)";
            //prepared statement
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_QUERY);
            //set parameters
            preparedStatement.setInt(1, newEpic.getProjectId());
            preparedStatement.setString(2, newEpic.getEpicName());
            preparedStatement.setString(3, newEpic.getEpicDescription());
            //execute query
            preparedStatement.executeUpdate();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            System.out.println("Did not create the epic");
        }
    }

    //Takes an updated epic object and shoots it to the database as a preparedstatement
    public void updateEpic(Epic updateEpic){
        try {
            //Oprette forbindelse til database
            Connection connection = ConnectionManager.getConnection(DB_HOSTNAME, DB_USERNAME, DB_PASSWORD);
            //SQL statement
            String SQL_QUERY = "UPDATE epics SET epic_name = ?, epic_description = ? WHERE epic_id = ?";
            //PreparedStatement
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_QUERY);
            //set parameters
            preparedStatement.setString(1, updateEpic.getEpicName());
            preparedStatement.setString(2, updateEpic.getEpicDescription());
            preparedStatement.setInt(3, updateEpic.getEpicId());
            //ExecuteStatement
            preparedStatement.executeUpdate();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            System.out.println("Did not update the epic");
        }
    }

    //Takes an integer as parameter and deletes an object in the database based on this integer
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
            preparedStatement.execute();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            System.out.println("Did not delete the epic");
        }
    }

    //Take a integer parameter, and finds the epic object that has this id and creates a new object to return
    public Epic findEpicByID(int id){
        Epic foundEpic = new Epic();
        foundEpic.setEpicId(id);
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

            if(resultSet.next())
            {
                //Få et epic ud fra databasen
                foundEpic.setProjectId(resultSet.getInt(1));
                foundEpic.setEpicId(resultSet.getInt(2));
                foundEpic.setEpicName(resultSet.getString(3));
                foundEpic.setEpicDescription(resultSet.getString(4));
            }

        }
        catch(SQLException e)
        {
            e.printStackTrace();
            System.out.println("No epic was found by that id");
        }
        return foundEpic;
    }

    //Finds all epic based on a project id and create epic object to put into an arraylist and return that list
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

            while(resultSet.next()) {
                Epic foundEpic = new Epic();
                foundEpic.setProjectId(projectID);
                foundEpic.setEpicId(resultSet.getInt(2));
                foundEpic.setEpicName(resultSet.getString(3));
                foundEpic.setEpicDescription(resultSet.getString(4));
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
