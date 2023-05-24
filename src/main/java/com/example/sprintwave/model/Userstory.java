package com.example.sprintwave.model;

public class Userstory {

    private int id;
    private int project_id;
    private String name;
    private String description;
    private boolean released;
    private int points;
    private Status status;

    private int sprint_id;

    public Userstory()
    {

    }

    public Userstory(int id, int project_id, String name, String description, boolean released, int points, Status status, int sprint_id) {
        this.id = id;
        this.project_id = project_id;
        this.name = name;
        this.description = description;
        this.released = released;
        this.points = points;
        this.status = status;
        this.sprint_id = sprint_id;
    }

    public String getStatusAsString()
    {
        return getStatus().toString();
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

    public int getProject_id() {
        return project_id;
    }

    public void setProject_id(int project_id) {
        this.project_id = project_id;
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


    public int getSprint_id() {
        return sprint_id;
    }

    public void setSprint_id(int sprint_id) {
        this.sprint_id = sprint_id;
    }

    @Override
    public String toString() {
        return "Userstory{" +
                "id=" + id +
                ", project_id=" + project_id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", released=" + released +
                ", points=" + points +
                ", status=" + status +
                '}';
    }

}
