package com.example.sprintwave.model;

public class Epic {
    private int projectId;
    private int epicId;
    private String epicName;
    private String epicDescription;

    public Epic() {

    }

    public Epic(int projectId, int epicId, String epicName, String epicDescription) {
        this.projectId = projectId;
        this.epicId = epicId;
        this.epicName = epicName;
        this.epicDescription = epicDescription;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public int getEpicId() {
        return epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }

    public String getEpicName() {
        return epicName;
    }

    public void setEpicName(String epicName) {
        this.epicName = epicName;
    }

    public String getEpicDescription() {
        return epicDescription;
    }

    public void setEpicDescription(String epicDescription) {
        this.epicDescription = epicDescription;
    }

    @Override
    public String toString() {
        return "Epic{" +
                "projectId=" + projectId +
                ", epicId=" + epicId +
                ", epicName='" + epicName + '\'' +
                ", epicDescription='" + epicDescription + '\'' +
                '}';
    }
}
