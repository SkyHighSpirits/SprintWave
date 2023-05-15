package com.example.sprintwave.repository;

import com.example.sprintwave.model.Requirement;
import com.example.sprintwave.utility.ConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Repository
public class RequirementRepository {

    @Value("${spring.datasource.url}")
    String DB_HOSTNAME;

    @Value("${spring.datasource.username}")
    String DB_USERNAME;

    @Value("${spring.datasource.password}")
    String DB_PASSWORD;

    public void createRequirement(Requirement newRequirement) {
        try {
            //Opret forbindelse til database
            Connection connection = ConnectionManager.getConnection(DB_HOSTNAME, DB_USERNAME, DB_PASSWORD);
            //SQL statement
            String SQL_QUERY = "INSERT INTO requirements(project_id, requirement_name, requirement_description, requirement_actor) VALUES (?, ?, ?, ?)";
            //prepared statement
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_QUERY);
            //set parameters
            preparedStatement.setInt(1, newRequirement.getProject_id());
            preparedStatement.setString(2, newRequirement.getRequirement_name());
            preparedStatement.setString(3, newRequirement.getRequirement_description());
            preparedStatement.setString(4, newRequirement.getRequirement_actor());
            //execute query
            preparedStatement.executeUpdate();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            System.out.println("Did not create the requirement");
        }
    }

    public void updateRequirement(Requirement updateRequirement){
        try {
            //Oprette forbindelse til database
            Connection connection = ConnectionManager.getConnection(DB_HOSTNAME, DB_USERNAME, DB_PASSWORD);
            //SQL statement
            String SQL_QUERY = "UPDATE requirements SET requirement_name = ?, requirement_description = ?, requirement_actor = ? WHERE requirement_id = ?";
            //PreparedStatement
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_QUERY);
            //set parameters
            preparedStatement.setString(1, updateRequirement.getRequirement_name());
            preparedStatement.setString(2, updateRequirement.getRequirement_description());
            preparedStatement.setString(3, updateRequirement.getRequirement_actor());
            preparedStatement.setInt(4, updateRequirement.getRequirement_id());
            //ExecuteStatement
            preparedStatement.executeUpdate();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            System.out.println("Did not update the requirement");
        }
    }

    public void deleteRequirement(int deleteRequirementID){
        try {
            //Oprette forbindelse til database
            Connection connection = ConnectionManager.getConnection(DB_HOSTNAME, DB_USERNAME, DB_PASSWORD);
            //SQL statement
            String SQL_QUERY = "DELETE FROM requirements WHERE requirement_id = ?";
            //PreparedStatement
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_QUERY);
            //set parameters
            preparedStatement.setInt(1, deleteRequirementID);
            //execute statement
            preparedStatement.execute();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            System.out.println("Did not delete the requirement");
        }
    }

    public Requirement findRequirementByID(int id){
        Requirement foundRequirement = new Requirement();
        foundRequirement.setRequirement_id(id);
        try {
            //Oprette forbindelse til database
            Connection connection = ConnectionManager.getConnection(DB_HOSTNAME, DB_USERNAME, DB_PASSWORD);
            //SQL statement
            String SQL_QUERY = "SELECT * FROM requirements WHERE requirement_id = ?";
            //PreparedStatement
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_QUERY);
            //set parameters
            preparedStatement.setInt(1, id);
            //execute statement
            ResultSet resultSet = preparedStatement.executeQuery();
            //Få et epic ud fra databasen
            foundRequirement.setProject_id(resultSet.getInt(1));
            foundRequirement.setRequirement_name(resultSet.getString(3));
            foundRequirement.setRequirement_description(resultSet.getString(4));
            foundRequirement.setRequirement_actor(resultSet.getString(5));
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            System.out.println("No requirement was found by that id");
        }
        return foundRequirement;
    }

    public ArrayList<Requirement> getAllRequirementByProjectID(int projectID){
        ArrayList<Requirement> requirementList = new ArrayList<>();
        try {
            //Oprette forbindelse til database
            Connection connection = ConnectionManager.getConnection(DB_HOSTNAME, DB_USERNAME, DB_PASSWORD);
            //SQL statement
            String SQL_QUERY = "SELECT * FROM requirements WHERE project_id = ?";
            //Prepared statement
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_QUERY);
            //set parameters
            preparedStatement.setInt(1, projectID);
            //execute statement
            ResultSet resultSet = preparedStatement.executeQuery();
            //set resultset

            while(resultSet.next()) {
                Requirement foundRequirement = new Requirement();
                foundRequirement.setProject_id(projectID);
                foundRequirement.setRequirement_id(resultSet.getInt(2));
                foundRequirement.setRequirement_name(resultSet.getString(3));
                foundRequirement.setRequirement_description(resultSet.getString(4));
                foundRequirement.setRequirement_actor(resultSet.getString(5));
                requirementList.add(foundRequirement);
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            System.out.println("Something went wrong when getting all the requirements");
        }

        return requirementList;
    }

}
