package com.example.sprintwave.model;

public class Project
{
    int projectID;
    String projectName;
    String projectOwner;
    boolean projectStatus;


    public Project(int projectID, String projectName, String projectOwner, boolean projectStatus ){
        this.projectID =projectID;
        this.projectName = projectName;
        this.projectOwner = projectOwner;
        this.projectStatus = projectStatus;
    }
    public Project()
    {

    }

    public int getProjectID()
    {
        return projectID;
    }

    public void setProjectID(int projectID)
    {
        this.projectID = projectID;
    }

    public String getProjectName()
    {
        return projectName;
    }

    public void setProjectName(String projectName)
    {
        this.projectName = projectName;
    }

    public String getProjectOwner()
    {
        return projectOwner;
    }

    public void setProjectOwner(String ownership)
    {
        this.projectOwner = projectOwner;
    }

    public boolean isProjectStatus()
    {
        return projectStatus;
    }

    public void setProjectStatus(boolean projectStatus)
    {
        this.projectStatus = projectStatus;
    }
}
