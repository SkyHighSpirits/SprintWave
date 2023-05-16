package com.example.sprintwave.model;

public class Userstory {

    private int id;
    private int project_id;
    private String name;
    private String description;
    private boolean released;

    public Userstory(int id, int project_id, String name, String description, boolean released) {
        this.id = id;
        this.project_id = project_id;
        this.name = name;
        this.description = description;
        this.released = released;
    }

    public Userstory()
    {

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

    @Override
    public String toString() {
        return "Userstory{" +
                "id=" + id +
                ", project_id=" + project_id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", released=" + released +
                '}';
    }

}
