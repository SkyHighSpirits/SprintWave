package com.example.sprintwave.repository;
import com.example.sprintwave.model.Project;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProjectRepository
{

    @Value("${spring.datasource.url}")
    String DB_HOSTNAME;
    @Value("${spring.datasource.username}")
    String DB_USERNAME;
    @Value("${spring.datasource.password}")
    String DB_PASSWORD;

    public void createProject(Project newProject){
        try{
            Connection connection = DriverManager.getConnection(DB_HOSTNAME,DB_USERNAME,DB_PASSWORD);
                    final String CREATE_QUERY="";// INDSÆT HER

            PreparedStatement preparedStatement = connection.prepareStatement(CREATE_QUERY);
            preparedStatement.setString(1, newProject.getProjectName());



        }catch(SQLException e){
            System.out.println("Could not add Project");
            e.printStackTrace();
        }
    }

    public List<Project> getAllProjects()
    {
        List<Project> projects = new ArrayList<>();
        try{
            Connection connection = DriverManager.getConnection(DB_HOSTNAME,DB_USERNAME,DB_PASSWORD);
            Statement statement = connection.createStatement();
            final String SQL_QUERY ="";
            ResultSet resultSet = statement.executeQuery(SQL_QUERY);
            while(resultSet.next()){
                int projectID = resultSet.getInt(1);
                String projectName = resultSet.getString(2);
                String projectOwner = resultSet.getString(3);
                boolean projectStatus = resultSet.getBoolean(4); // ret dette til auto increment
                Project project = new Project(projectID, projectName, projectOwner, projectStatus);
                projects.add(project);
                System.out.println(project);
            }

        }catch(SQLException e){
            System.out.println("Could not add Query");
            e.printStackTrace();
    }
        return projects;
    }

}
