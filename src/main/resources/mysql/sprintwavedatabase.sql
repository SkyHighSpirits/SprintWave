DROP DATABASE IF EXISTS sprintwavedatabase;
CREATE DATABASE sprintwavedatabase;
USE sprintwavedatabase;

DROP TABLE IF EXISTS workspaces;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS projects;
DROP TABLE IF EXISTS epics;

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
);

CREATE TABLE epics(
                      project_id INT NOT NULL,
                      epic_id INT UNIQUE NOT NULL AUTO_INCREMENT PRIMARY KEY ,
                      epic_name VARCHAR(255) NOT NULL,
                      epic_description VARCHAR(255) NOT NULL,
                      FOREIGN KEY(project_id) REFERENCES projects(project_id)

);


INSERT into workspaces
(workspace_name)
VALUES
    ('KEA');

INSERT INTO users
(email, user_password, firstname, lastname, permission_level, workspace_id)
VALUES
    ('steffen@localhost.com', 'e10adc3949ba59abbe56e057f20f883e', 'Steffen', 'Andersen', 'ADMINISTRATOR',1);


INSERT INTO projects
(project_id, project_name, project_description, project_owner, project_status, project_deadline, workspace_id)
VALUES
    (1,'Develop CRM system for sales team', 'A sales CRM software that helps manage customer relationships and sales pipelines','Steffen Grøn Andersen', true, '2023-05-30',1),
    (2,'Migrate legacy database to cloud-based platform', 'Move existing databases to the cloud for better scalability and accessibility.','Steffen Grøn Andersen', true, '2023-05-30',1),
    (3,'Implement cybersecurity measures for client portal', 'Implement security measures to protect client data in online portals.','Steffen Grøn Andersen', true, '2023-05-30',1),
    (4,'Enhance website user experience with responsive design', 'Optimize website for mobile devices and improve user experience.','Steffen Grøn Andersen', true, '2023-05-30',1),
    (5,'Build custom e-commerce platform with inventory management', 'Develop a custom platform for online sales, order processing, and inventory management.','Steffen Grøn Andersen', true, '2023-05-30',1);

INSERT INTO epics
(project_id, epic_name, epic_description)
VALUES
    (1, 'EP003', 'Deploy feature Epics'),
    (1, 'EP004', 'Deploy feature projects');