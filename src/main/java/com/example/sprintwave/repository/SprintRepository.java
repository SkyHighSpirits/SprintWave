package com.example.sprintwave.repository;

import com.example.sprintwave.model.Epic;
import com.example.sprintwave.model.Sprint;
import com.example.sprintwave.utility.ConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Repository
public class SprintRepository {

    @Value("${spring.datasource.url}")
    String DB_HOSTNAME;
    @Value("${spring.datasource.username}")
    String DB_USERNAME;
    @Value("${spring.datasource.password}")
    String DB_PASSWORD;
    public void createSprint(Sprint newSprint) {
        try {
            //Opret forbindelse til database
            Connection connection = ConnectionManager.getConnection(DB_HOSTNAME, DB_USERNAME, DB_PASSWORD);
            //SQL statement
            String SQL_QUERY = "INSERT INTO sprints(sprint_id, sprint_name, project_id) VALUES (?, ?, ?)";
            //prepared statement
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_QUERY);
            //set parameters
            preparedStatement.setInt(1, newSprint.getSprintId());
            preparedStatement.setString(2, newSprint.getSprintName());
            preparedStatement.setInt(3, newSprint.getProjectId());
            //execute query
            preparedStatement.executeUpdate();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            System.out.println("Did not create the Sprint");
        }
    }

    public Sprint getSprintByIDAndProjectID(int id, int project_id){
        Sprint foundSprint = new Sprint();
        foundSprint.setSprintId(id);
        foundSprint.setProjectId(project_id);
        try {
            //Oprette forbindelse til database
            Connection connection = ConnectionManager.getConnection(DB_HOSTNAME, DB_USERNAME, DB_PASSWORD);
            //SQL statement
            String SQL_QUERY = "SELECT * FROM sprints WHERE sprint_id = ? && project_id = ?";
            //PreparedStatement
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_QUERY);
            //set parameters
            preparedStatement.setInt(1, id);
            preparedStatement.setInt(2, project_id);
            //execute statement
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next())
            {
                //Få et epic ud fra databasen
                foundSprint.setSprintId(resultSet.getInt(1));
                foundSprint.setSprintName(resultSet.getString(2));
                foundSprint.setProjectId(resultSet.getInt(3));
            }
            else
            {
                foundSprint = null;
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            System.out.println("No epic was found by that id");
            return null;
        }
        return foundSprint;
    }

    public ArrayList<Sprint> getAllSprintsByProjectID(int projectID){
        ArrayList<Sprint> sprintList = new ArrayList<>();
        try {
            //Oprette forbindelse til database
            Connection connection = ConnectionManager.getConnection(DB_HOSTNAME, DB_USERNAME, DB_PASSWORD);
            //SQL statement
            String SQL_QUERY = "SELECT * FROM sprints WHERE project_id = ?";
            //Prepared statement
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_QUERY);
            //set parameters
            preparedStatement.setInt(1, projectID);
            //execute statement
            ResultSet resultSet = preparedStatement.executeQuery();
            //set resultset

            while(resultSet.next()) {
                Sprint foundSprint = new Sprint();
                foundSprint.setSprintId(resultSet.getInt(1));
                foundSprint.setSprintName(resultSet.getString(2));
                foundSprint.setProjectId(resultSet.getInt(3));
                sprintList.add(foundSprint);
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            System.out.println("Could not query database for sprints");
        }

        return sprintList;
    }
}
