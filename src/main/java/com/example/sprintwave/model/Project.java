package com.example.sprintwave.model;
import java.time.LocalDate;

public class Project
{
    int projectID;
    String projectName;
    String projectOwner;
    boolean projectStatus;
    LocalDate deadline;
    String projectDescription;
    int workspaceID;




    public Project(int projectID, String projectName,String projectDescription, String projectOwner, boolean projectStatus, LocalDate deadline, int workspaceID){
        this.projectID =projectID;
        this.projectName = projectName;
        this.projectOwner = projectOwner;
        this.projectStatus = projectStatus;
        this.deadline = deadline;
        this.projectDescription = projectDescription;
        this.workspaceID = workspaceID;

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

    public void setProjectOwner(String projectOwner)
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

    public LocalDate getDeadline()
    {
        return deadline;
    }

    public void setDeadline(LocalDate deadline)
    {
        this.deadline = deadline;
    }

    public String getProjectDescription()
    {
        return projectDescription;
    }

    public void setProjectDescription(String projectDescription)
    {
        this.projectDescription = projectDescription;
    }

    public int getWorkspaceID()
    {
        return workspaceID;
    }

    public void setWorkspaceID(int workspaceID)
    {
        this.workspaceID = workspaceID;
    }

    @Override
    public String toString()
    {
        return "Project{" +
                "projectID=" + projectID +
                ", projectName='" + projectName + '\'' +
                ", projectOwner='" + projectOwner + '\'' +
                ", projectStatus=" + projectStatus +
                ", deadline=" + deadline +
                ", projectDescription='" + projectDescription + '\'' +
                ", workspaceID=" + workspaceID +
                '}';
    }
}
