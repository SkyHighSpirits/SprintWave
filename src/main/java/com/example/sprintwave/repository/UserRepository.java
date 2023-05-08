package com.example.sprintwave.repository;

import com.example.sprintwave.model.PermessionLevel;
import com.example.sprintwave.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;



@Repository
public class UserRepository {

    @Value("${spring.datasource.url}")
    String DB_HOSTNAME;
    @Value("${spring.datasource.username}")
    String DB_USERNAME;
    @Value("${spring.datasource.password}")
    String DB_PASSWORD;
    
    public List<User> getAllUsers(){
        List <User> userList = new ArrayList<>();
        
        try{
            // Connect to database
            Connection connection = DriverManager.getConnection(DB_HOSTNAME, DB_USERNAME, DB_PASSWORD);
            Statement statement = connection.createStatement();
            
            // Prepare statement
            final String SQL_QUERY = "SELECT * FROM users";
            ResultSet resultSet = statement.executeQuery(SQL_QUERY);
            
            // Get data from database
            while(resultSet.next()){
                int user_id = resultSet.getInt(1);
                String email = resultSet.getString(2);
                String user_password = resultSet.getString(3);
                String firstName = resultSet.getString(4);
                String lastName = resultSet.getString(5);
                int workspace_id = resultSet.getInt(6);
                
                User user = new User(user_id, email, user_password, firstName, lastName, workspace_id);
                
                
            }
            
        } catch(SQLException e){
            System.out.println("Error: Could not connect to database and getAllUsers.");
            e.printStackTrace();
        }
        
        
        return userList;
    }

}
