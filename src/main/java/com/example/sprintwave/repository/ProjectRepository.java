package com.example.sprintwave.repository;
import com.example.sprintwave.model.Project;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.time.LocalDate;
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
                    final String CREATE_QUERY="INSERT INTO projects" + "(project_name, project_owner, project_deadline, project_description)"  + "VALUES (?,?,?,?)";// INDSÆT HER
            //prepare QUERY
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE_QUERY);
            preparedStatement.setString(1, newProject.getProjectName());
            preparedStatement.setString(2, newProject.getProjectOwner());
            preparedStatement.setDate(3, java.sql.Date.valueOf(newProject.getDeadline()));
            preparedStatement.setString(4, newProject.getProjectDescription());

            //EXECUTE QUERY
            preparedStatement.executeQuery();
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
            final String SQL_QUERY ="SELECT * FROM projects";
            ResultSet resultSet = statement.executeQuery(SQL_QUERY);
            while(resultSet.next()){
                int projectID = resultSet.getInt(1);
                String projectName = resultSet.getString(2);
                String projectOwner = resultSet.getString(3);
                boolean projectStatus = resultSet.getBoolean(4);
                java.sql.Date deadlineDate = resultSet.getDate(5);
                LocalDate deadline = deadlineDate.toLocalDate(); // skal testes!!!
                String projectDescription = resultSet.getString(6);
                Project project = new Project(projectID, projectName, projectOwner, projectStatus, deadline, projectDescription);
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
