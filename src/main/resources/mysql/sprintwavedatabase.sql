DROP DATABASE IF EXISTS sprintwavedatabase;
CREATE DATABASE sprintwavedatabase;
USE sprintwavedatabase;

DROP TABLE IF EXISTS workspaces;
DROP TABLE IF EXISTS users;

CREATE TABLE workspaces(
	workspace_id INT UNIQUE NOT NULL AUTO_INCREMENT PRIMARY KEY,
    workspace_name VARCHAR(255) NOT NULL
);

CREATE TABLE users(
	user_id INT UNIQUE NOT NULL AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    user_password VARCHAR(255) NOT NULL,
    firstname VARCHAR(255) NOT NULL,
    lastname VARCHAR(255) NOT NULL,
	workspace_id INT NOT NULL,
    FOREIGN KEY(workspace_id) REFERENCES workspaces(workspace_id)
);

CREATE TABLE epics(
    project_id INT NOT NULL,
    epic_id INT UNIQUE NOT NULL AUTO_INCREMENT PRIMARY KEY ,
    epic_name VARCHAR(255) NOT NULL,
    epic_description VARCHAR(255) NOT NULL,
    FOREIGN KEY(project_id) REFERENCES projects(project_id)
);

