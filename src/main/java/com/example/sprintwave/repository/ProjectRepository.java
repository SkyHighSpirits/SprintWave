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
                    final String CREATE_QUERY="INSERT INTO projects" + "(project_name, project_owner, project_deadline, project_description, workspace_id)"  + "VALUES (?,?,?,?,?)";
            //prepare QUERY
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE_QUERY);
            preparedStatement.setString(1, newProject.getProjectName());
            preparedStatement.setString(2, newProject.getProjectOwner());
            preparedStatement.setDate(3, java.sql.Date.valueOf(newProject.getDeadline()));
            preparedStatement.setString(4, newProject.getProjectDescription());
            preparedStatement.setInt(5, newProject.getWorkspaceID());

            //EXECUTE QUERY
            preparedStatement.executeUpdate();
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

    public Project findProjectById(int id){
        Project foundProject = new Project();
        foundProject.setProjectID(id);
        try{
            Connection connection = DriverManager.getConnection(DB_HOSTNAME,DB_USERNAME,DB_PASSWORD);
            String SQL_QUERY = "SELECT * FROM projects WHERE workspace_id =?";
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_QUERY);
            preparedStatement.setInt(1,id);
            ResultSet resultset = preparedStatement.executeQuery();

            foundProject.setProjectID(resultset.getInt(1));
            foundProject.setProjectName(resultset.getString(2));
            foundProject.setProjectDescription(resultset.getString(3));
            foundProject.setProjectOwner(resultset.getString(4));
            foundProject.setProjectStatus(resultset.getBoolean(5));
            foundProject.setDeadline(resultset.getDate(6).toLocalDate());
            //toLocalDate tilføjet for at kunne omskrive java.SQL.date fra databasen til LocalDate objet. da vi bruger localdate some dato attribut

        }catch(SQLException e){
            e.printStackTrace();
            System.out.println("could not find project by id");

        }
        return foundProject;
    }

}
