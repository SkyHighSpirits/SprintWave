package com.example.sprintwave.repository;


import com.example.sprintwave.model.Epic;
import com.example.sprintwave.utility.ConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
            String SQL_QUERY = "INSERT INTO epics VALUES (?, ?, ?, ?)";
            //prepared statement
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_QUERY);
            //set parameters
            preparedStatement.setInt(1, newEpic.getProject_id());
            preparedStatement.setInt(2, newEpic.getEpic_id());
            preparedStatement.setString(3, newEpic.getEpic_name());
            preparedStatement.setString(4, newEpic.getEpic_description());
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
            //ExecuteStatement
            preparedStatement.executeUpdate();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            System.out.println("Did not update the epic");
        }
    }

    public void deleteEpic(Epic deleteEpic){
        try {
            //Oprette forbindelse til database
            Connection connection = ConnectionManager.getConnection(DB_HOSTNAME, DB_USERNAME, DB_PASSWORD);
            //SQL statement
            String SQL_QUERY = "DELETE FROM epics WHERE epic_id = ?";
            //PreparedStatement
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_QUERY);
            //set parameters
            preparedStatement.setInt(1, deleteEpic.getEpic_id());
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
        //Oprette forbindelse til database
        Connection connection = ConnectionManager.getConnection(DB_HOSTNAME, DB_USERNAME, DB_PASSWORD);
        //SQL statement
        //PreparedStatement
        //set parameters
        //execute statement
        //Få et epic ud fra databasen
    }

    public void getAllEpicByProjectID(){

    }
}
