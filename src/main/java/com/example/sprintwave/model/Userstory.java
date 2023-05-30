package com.example.sprintwave.model;

public class Userstory {

    private int id;
    private int projectId;
    private String name;
    private String description;
    private boolean released;
    private int points;
    private Status status;

    private int sprintId;

    public Userstory()
    {

    }

    public Userstory(int id, int projectId, String name, String description, boolean released, int points, Status status, int sprintId) {
        this.id = id;
        this.projectId = projectId;
        this.name = name;
        this.description = description;
        this.released = released;
        this.points = points;
        this.status = status;
        this.sprintId = sprintId;
    }

    public boolean isReleased() {
        return released;
    }

    public void setReleased(boolean released) {
        this.released = released;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }


    public int getSprintId() {
        return sprintId;
    }

    public void setSprintId(int sprintId) {
        this.sprintId = sprintId;
    }

    @Override
    public String toString() {
        return "Userstory{" +
                "id=" + id +
                ", projectId=" + projectId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", released=" + released +
                ", points=" + points +
                ", status=" + status +
                '}';
    }

}
