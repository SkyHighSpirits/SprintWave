package com.example.sprintwave.repository;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
public class EpicRepository {

    @Value("$spring.datasource.url")
    String DB_HOSTNAME;

    @Value("$spring.datasource.username")
    String DB_USERNAME;

    @Value("$spring.datasource.password")
    String DB_PASSWORD;

    public void createEpic() {

        //Opret forbindelse til database
        //SQL statement
        //prepared statement
        //set parameters
        //execute query
    }
}
