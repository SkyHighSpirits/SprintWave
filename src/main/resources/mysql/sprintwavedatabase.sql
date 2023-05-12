DROP DATABASE IF EXISTS sprintwavedatabase;
CREATE DATABASE sprintwavedatabase;
USE sprintwavedatabase;

DROP TABLE IF EXISTS workspaces;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS projects;


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
                      permission_level VARCHAR(255) NOT NULL,
                      workspace_id INT NOT NULL,
                      FOREIGN KEY(workspace_id) REFERENCES workspaces(workspace_id)
);


CREATE TABLE projects(
                         project_id INT UNIQUE NOT NULL AUTO_INCREMENT PRIMARY KEY,
                         project_name VARCHAR(255) NOT NULL,
                         project_description VARCHAR(255) NOT NULL,
                         project_owner VARCHAR(225) NOT NULL,
                         project_status BOOLEAN NOT NULL DEFAULT TRUE,
                         project_deadline DATE NOT NULL,
                         workspace_id INT NOT NULL,
                         FOREIGN KEY(workspace_id) REFERENCES workspaces(workspace_id)
  
CREATE TABLE epics(
                         project_id INT NOT NULL,
                         epic_id INT UNIQUE NOT NULL AUTO_INCREMENT PRIMARY KEY ,
                         epic_name VARCHAR(255) NOT NULL,
                         epic_description VARCHAR(255) NOT NULL,
                         FOREIGN KEY(project_id) REFERENCES projects(project_id)

);


INSERT into workspaces (workspace_name)
VALUES ('KEA');

INSERT INTO users (email, user_password, firstname, lastname, permission_level, workspace_id)
VALUES ('steffen@localhost.com', 'e10adc3949ba59abbe56e057f20f883e', 'Steffen', 'Andersen', 'ADMINISTRATOR',1);


INSERT INTO projects (project_id, project_name, project_description, project_owner, project_status, project_deadline, workspace_id)
VALUES(1,'testProject','fjollefjol','tommy', true, '2023-05-30',1);

INSERT INTO epics (project_id, epic_name, epic_description)
VALUES(1, 'EP003', 'Deploy feature Epics');

INSERT INTO epics (project_id, epic_name, epic_description)
VALUES(1, 'EP004', 'Deploy feature projects');

