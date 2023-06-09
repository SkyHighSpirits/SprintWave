package com.example.sprintwave.model;

public class Sprint {

    private int sprintId;
    private int projectId;
    private String sprintName;

    public Sprint(int sprintId, String sprintName, int projectId) {
        this.sprintId = sprintId;
        this.sprintName = sprintName;
        this.projectId = projectId;
    }

    public Sprint()
    {

    }

    public int getSprintId() {
        return sprintId;
    }

    public void setSprintId(int sprintId) {
        this.sprintId = sprintId;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public String getSprintName() {
        return sprintName;
    }

    public void setSprintName(String sprintName) {
        this.sprintName = sprintName;
    }
}
