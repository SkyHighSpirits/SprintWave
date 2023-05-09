package com.example.sprintwave.repository;

import com.example.sprintwave.model.Workspace;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class WorkspaceRepository {

    @Value("${spring.datasource.url}")
    String DB_HOSTNAME;
    @Value("${spring.datasource.username}")
    String DB_USERNAME;
    @Value("${spring.datasource.password}")
    String DB_PASSWORD;


    public List<Workspace> getAllWorkspaces()
    {
        List<Workspace> workspaces = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection(DB_HOSTNAME, DB_USERNAME, DB_PASSWORD);
            Statement statement = connection.createStatement();
            final String SQL_QUERY = "SELECT * FROM workspaces";
            ResultSet resultSet = statement.executeQuery(SQL_QUERY);
            while (resultSet.next()){
                int workspaceID = resultSet.getInt(1);
                String workspaceName = resultSet.getString(2);
                Workspace workspace = new Workspace(workspaceID, workspaceName);
                workspaces.add(workspace);
                System.out.println(workspace);
            }

        }
        catch(SQLException e)
        {
            System.out.println("Could not query database");
            e.printStackTrace();
        }
        return workspaces;
    }

    public Workspace getLastEntryWorkspace()
    {
        Workspace workspace = new Workspace();
        try {
            Connection connection = DriverManager.getConnection(DB_HOSTNAME, DB_USERNAME, DB_PASSWORD);
            Statement statement = connection.createStatement();
            final String SQL_QUERY = "SELECT * FROM workspaces ORDER BY workspace_id DESC LIMIT 1";
            ResultSet resultSet = statement.executeQuery(SQL_QUERY);
            while (resultSet.next()){
                int workspaceID = resultSet.getInt(1);
                String workspaceName = resultSet.getString(2);
                workspace.setID(workspaceID);
                workspace.setName(workspaceName);
                System.out.println(workspace);
            }

        }
        catch(SQLException e)
        {
            System.out.println("Could not query database");
            e.printStackTrace();
        }
        return workspace;
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
