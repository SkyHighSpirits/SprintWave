package com.example.sprintwave.model;

public class Epic {
    private int project_id;
    private int epic_id;
    private String epic_name;
    private String epic_description;

    public Epic() {

    }

    public Epic(int project_id, int epic_id, String epic_name, String epic_description) {
        this.project_id = project_id;
        this.epic_id = epic_id;
        this.epic_name = epic_name;
        this.epic_description = epic_description;
    }

    public int getProject_id() {
        return project_id;
    }

    public void setProject_id(int project_id) {
        this.project_id = project_id;
    }

    public int getEpic_id() {
        return epic_id;
    }

    public void setEpic_id(int epic_id) {
        this.epic_id = epic_id;
    }

    public String getEpic_name() {
        return epic_name;
    }

    public void setEpic_name(String epic_name) {
        this.epic_name = epic_name;
    }

    public String getEpic_description() {
        return epic_description;
    }

    public void setEpic_description(String epic_description) {
        this.epic_description = epic_description;
    }

    @Override
    public String toString() {
        return "Epic{" +
                "project_id=" + project_id +
                ", epic_id=" + epic_id +
                ", epic_name='" + epic_name + '\'' +
                ", epic_description='" + epic_description + '\'' +
                '}';
    }
}
