package com.example.sprintwave.repository;

import com.example.sprintwave.model.Requirement;
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
            String SQL_QUERY = "INSERT INTO requirements(project_id, requirement_name, requirement_description, requirement_actor, funcNonFuncChoice) VALUES (?, ?, ?, ?, ?)";
            //prepared statement
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_QUERY);
            //set parameters
            preparedStatement.setInt(1, newRequirement.getProjectId());
            preparedStatement.setString(2, newRequirement.getRequirementName());
            preparedStatement.setString(3, newRequirement.getRequirementDescription());
            preparedStatement.setString(4, newRequirement.getRequirementActor());

            preparedStatement.setString(5, DataHandler.convertFuncNonFuncBooleanToString(newRequirement.isFuncNonFunc()));

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
            String SQL_QUERY = "UPDATE requirements SET requirement_name = ?, requirement_description = ?, requirement_actor = ?, funcNonFuncChoice = ? WHERE requirement_id = ?";
            //PreparedStatement
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_QUERY);
            //set parameters
            preparedStatement.setString(1, updateRequirement.getRequirementName());
            preparedStatement.setString(2, updateRequirement.getRequirementDescription());
            preparedStatement.setString(3, updateRequirement.getRequirementActor());
            preparedStatement.setString(4, DataHandler.convertFuncNonFuncBooleanToString(updateRequirement.isFuncNonFunc()));
            preparedStatement.setInt(5, updateRequirement.getRequirementId());
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
        foundRequirement.setRequirementId(id);
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
            if(resultSet.next()) {
                foundRequirement.setProjectId(resultSet.getInt(1));
                foundRequirement.setRequirementName(resultSet.getString(3));
                foundRequirement.setRequirementDescription(resultSet.getString(4));
                foundRequirement.setRequirementActor(resultSet.getString(5));
                foundRequirement.setFuncNonFunc(DataHandler.convertFuncNonFuncStringToBoolean(resultSet.getString(6)));
            }
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
                foundRequirement.setProjectId(projectID);
                foundRequirement.setRequirementId(resultSet.getInt(2));
                foundRequirement.setRequirementName(resultSet.getString(3));
                foundRequirement.setRequirementDescription(resultSet.getString(4));
                foundRequirement.setRequirementActor(resultSet.getString(5));
                foundRequirement.setFuncNonFunc(DataHandler.convertFuncNonFuncStringToBoolean(resultSet.getString(6)));
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
