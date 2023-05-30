
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
DROP TABLE IF EXISTS sprints;
DROP TABLE IF EXISTS userstories;
DROP TABLE IF EXISTS technicaltasks;

/* Create tables to database: Workspaces, Users, Projects, Epics, Requirements etc... */
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
                             FOREIGN KEY(project_id) REFERENCES projects(project_id)

);

CREATE TABLE sprints (
                         sprint_id INT NOT NULL,
                         sprint_name VARCHAR(255) NOT NULL,
                         project_id INT NOT NULL,
                         PRIMARY KEY (sprint_id, project_id),
                         FOREIGN KEY (project_id) REFERENCES projects (project_id) ON DELETE CASCADE
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
    (2,'Migrate legacy database to cloud-based platform', 'Move existing databases to the cloud for better scalability and accessibility.','Steffen Grøn Andersen', false, '2023-05-30',1),
    (3,'Implement cybersecurity measures for client portal', 'Implement security measures to protect client data in online portals.','Steffen Grøn Andersen', false, '2023-05-30',1);


INSERT INTO epics
(project_id, epic_name, epic_description)
VALUES
    (1, 'EP001: User Management', 'Enable the creation, authentication, and management of user accounts with role-based access control for the CRM system.'),
    (1, 'EP002: Lead Management', 'Implement features to capture, track, and qualify leads, allowing the sales team to efficiently manage their pipeline and prioritize opportunities.'),
    (1, 'EP003: Reporting and Analytics', 'Develop comprehensive reporting and analytics capabilities, enabling the sales team to gain valuable insights and make data-driven decisions for improved performance.');

INSERT INTO requirements
(project_id, requirement_id, requirement_name, requirement_description, requirement_actor, funcNonFuncChoice)
VALUES
    (1, 1, 'FR001: Customer Data Management', 'The CRM system should allow the sales team to create, update, and store customer information, including contact details, purchase history, and interactions.', 'Actor: Sales team','Functional'),
    (1, 2, 'FR002: Sales Pipeline Tracking', 'The CRM system should provide a visual representation of the sales pipeline, allowing the sales team to track the progress of leads and opportunities through different stages, such as prospecting, qualification, negotiation, and closure.', 'Actor: Sales team', 'Functional'),
    (1, 3, 'NFR001: Performance','The CRM system should have a response time of less than 2 seconds for common operations, such as searching for customer records or updating contact information.','Actor: Sales team','Non-functional'),
    (1, 4, 'NFR002: Security','The CRM system should implement robust security measures to ensure the confidentiality and integrity of customer data, including user authentication, data encryption, and role-based access control.','Actor: Sales team','Non-functional');

INSERT INTO sprints
(sprint_id, sprint_name, project_id)
VALUES
    (1,'SP001', 1);

INSERT INTO userstories
(project_id, userstory_name, userstory_description, userstory_released, userstory_points, userstory_status, sprint_id)
VALUES
    (1, 'US001', 'As a salesperson, I want a user-friendly interface where I can enter and save customer details, such as name, contact information, and company information.', false, 64, 4, 1),
    (1, 'US002', 'As a salesperson, I want to have the ability to edit and update customer details whenever there are changes or new interactions.', true, 51, 2, 1),
    (1, 'US003', 'I want a clear and interactive view that shows the different stages of the sales pipeline, including the number of leads and opportunities at each stage.', false, 100, 1, 1),
    (1, 'US004', ' need the ability to search and filter the sales pipeline based on criteria such as customer name, deal value, or stage, to quickly locate and focus on relevant leads or opportunities.', false, 100, 1, 1);

INSERT INTO technicaltasks
(userstory_id, technicaltask_name, technicaltask_description, technicaltask_released, technicaltask_points, technicaltask_status, sprint_id)
VALUES
    (1, 'TEC001.1','Create a web form with appropriate fields to capture customer information, validate the input, and save it to the CRM database.', false, 32, 4, 1),
    (1, 'TEC001.2','Design and create a database schema that can store customer information efficiently, considering data normalization and relationships.', false, 32, 4, 1),
    (2, 'TEC002.1','Develop an interface that allows salespersons to select and modify existing customer information, validate the input, and update the CRM database accordingly.', true, 30, 2, 1),
    (2, 'TEC002.2','Implement server-side validation to ensure that the updated customer information follows the required format and handle any potential errors gracefully, providing helpful feedback to the user.', true, 21, 3, 1),
    (3, 'TEC003.1','Create a visually appealing and intuitive representation of the sales pipeline, using charts, graphs, or other suitable visual elements, allowing salespersons to easily track and understand the progression of leads and opportunities.', false, 25, 1, 1),
    (3, 'TEC003.2','Implement the necessary data retrieval and processing logic to fetch relevant data from the CRM database and display it accurately in the sales pipeline visualization, ensuring real-time updates when changes occur.', false, 25, 1, 1),
    (4, 'TEC004.1',' Develop a search feature that allows salespersons to enter keywords or specific criteria and retrieve matching leads or opportunities from the CRM system, presenting the results in a clear and organized manner.', false, 50, 1, 1),
    (4, 'TEC004.2','Create filter options within the sales pipeline view, enabling salespersons to refine the displayed leads or opportunities based on predefined or customizable filters, such as stage, deal value range, or time period.', false, 50, 1, 1);