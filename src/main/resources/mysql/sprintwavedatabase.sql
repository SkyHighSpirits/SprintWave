
/* Create and use database */
DROP DATABASE IF EXISTS sprintwavedatabase;
CREATE DATABASE sprintwavedatabase;
USE sprintwavedatabase;

/* Delete database and tables if they exist */
DROP TABLE IF EXISTS workspaces;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS projects;
DROP TABLE IF EXISTS epics;
DROP TABLE IF EXISTS requirements;
DROP TABLE IF EXISTS technicaltasks;
DROP TABLE IF EXISTS userstories;

/* Create tables to database: Workspaces, Users, Projects, Epics. */
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

CREATE TABLE requirements(
                             project_id INT NOT NULL,
                             requirement_id INT UNIQUE NOT NULL AUTO_INCREMENT PRIMARY KEY ,
                             requirement_name VARCHAR(255) NOT NULL,
                             requirement_description VARCHAR(255) NOT NULL,
                             requirement_actor VARCHAR(255) NOT NULL,
                             funcNonFuncChoice VARCHAR(255) NOT NULL,
                             funcNonFunc BOOLEAN,
                             FOREIGN KEY(project_id) REFERENCES projects(project_id)

);

CREATE TABLE sprints(
                        sprint_id INT NOT NULL UNIQUE AUTO_INCREMENT PRIMARY KEY,
                        sprint_name VARCHAR(255) NOT NULL,
                        project_id INT NOT NULL,
                        FOREIGN KEY(project_id) REFERENCES projects(project_id) ON DELETE CASCADE
);

CREATE TABLE userstories(
                            userstory_id INT NOT NULL UNIQUE AUTO_INCREMENT PRIMARY KEY,
                            project_id INT NOT NULL,
                            userstory_name VARCHAR(255) NOT NULL,
                            userstory_description VARCHAR(255) NOT NULL,
                            userstory_released BOOLEAN NOT NULL,
                            userstory_points INT NOT NULL,
                            userstory_status INT NOT NULL,
                            sprint_id INT,
                            FOREIGN KEY(project_id) REFERENCES projects(project_id),
                            FOREIGN KEY(sprint_id) REFERENCES sprints(sprint_id)
);

CREATE TABLE technicaltasks(
                               technicaltask_id INT NOT NULL UNIQUE AUTO_INCREMENT PRIMARY KEY,
                               userstory_id INT NOT NULL,
                               technicaltask_name VARCHAR(255) NOT NULL,
                               technicaltask_description VARCHAR(255) NOT NULL,
                               technicaltask_released BOOLEAN NOT NULL,
                               technicaltask_points INT NOT NULL,
                               technicaltask_status INT NOT NULL,
                               sprint_id INT,
                               FOREIGN KEY(userstory_id) REFERENCES userstories(userstory_id) ON DELETE CASCADE,
                               FOREIGN KEY(sprint_id) REFERENCES sprints(sprint_id)
);





/* Insert temporary data into database. */
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

INSERT INTO sprints
(sprint_name, project_id)
VALUES
    ('SP001', 1);

INSERT INTO userstories
(project_id, userstory_name, userstory_description, userstory_released, userstory_points, userstory_status, sprint_id)
VALUES
    (1, 'US001', 'As a drunkard I want to be able to buy beer, so that I can stay drunk', false, 64, 1, 1),
    (1, 'US002', 'As a cyclist I want to be able to maintain my bike, so that I can keep using it', true, 51, 1, 1),
    (1, 'US003', 'As a beaver I want to be able to chew wood, so that I can create my dam', false, 100, 1, 1);

INSERT INTO technicaltasks
(userstory_id, technicaltask_name, technicaltask_description, technicaltask_released, technicaltask_points, technicaltask_status, sprint_id)
VALUES
    (1, 'TEC001','Earn Money', false, 32, 1, 1),
    (1, 'TEC002','Buy beer', false, 32, 1, 1),
    (2, 'TEC003','Buy WD40', true, 30, 1, 1),
    (2, 'TEC004','Use WD40 on rusty parts of the bike', true, 21, 1, 1),
    (3, 'TEC005','Eat Well', false, 25, 1, 1),
    (3, 'TEC006','Sharpen Teeth', false, 25, 1, 1),
    (3, 'TEC007','Chew Wood', false, 50, 1, 1);