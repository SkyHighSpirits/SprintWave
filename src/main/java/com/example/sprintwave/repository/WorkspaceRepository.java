package com.example.sprintwave.repository;

import com.example.sprintwave.model.Workspace;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

@Repository
public class WorkspaceRepository {

    @Value("${spring.datasource.url}")
    String DB_HOSTNAME;
    @Value("${spring.datasource.username}")
    String DB_USERNAME;
    @Value("${spring.datasource.password}")
    String DB_PASSWORD;



    ArrayList<Workspace> workspaces = new ArrayList<Workspace>();

    public ArrayList<Workspace> getAllWorkspaces()
    {

    }
    public void addWorkspace(Workspace newWorkspace){
        try{
            // Connect to database and create CREATE_QUERY
            Connection connection = DriverManager.getConnection(DB_HOSTNAME, DB_USERNAME, DB_PASSWORD);
            final String CREATE_QUERY ="INSERT INTO workspaces" +
                    "(workspace_name)" +
                    "VALUES (?)";

            // Prepare CREATE QUERY
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE_QUERY);
            preparedStatement.setString(1, newWorkspace.getName());

            // Execute CREATE QUERY
            preparedStatement.executeUpdate();
        } catch(SQLException e){
            System.out.println("Error: Could not add Account to database and addAccount");
            e.printStackTrace();
        }

    }
}
