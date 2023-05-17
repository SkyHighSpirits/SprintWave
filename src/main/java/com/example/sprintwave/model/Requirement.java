package com.example.sprintwave.model;

public class Requirement {
    private int project_id;
    private int requirement_id;
    private String requirement_name;
    private String requirement_description;
    private String requirement_actor;
    private boolean funcNonFunc = false;


    public Requirement(int project_id, int requirement_id, String requirement_name, String requirement_description, String requirement_actor, boolean funcNonFunc) {
        this.project_id = project_id;
        this.requirement_id = requirement_id;
        this.requirement_name = requirement_name;
        this.requirement_description = requirement_description;
        this.requirement_actor = requirement_actor;
        this.funcNonFunc = funcNonFunc;

    }

    public Requirement() {
    }

    public int getProject_id() {
        return project_id;
    }

    public void setProject_id(int project_id) {
        this.project_id = project_id;
    }

    public int getRequirement_id() {
        return requirement_id;
    }

    public void setRequirement_id(int requirement_id) {
        this.requirement_id = requirement_id;
    }

    public String getRequirement_name() {
        return requirement_name;
    }

    public void setRequirement_name(String requirement_name) {
        this.requirement_name = requirement_name;
    }

    public String getRequirement_description() {
        return requirement_description;
    }

    public void setRequirement_description(String requirement_description) {
        this.requirement_description = requirement_description;
    }

    public String getRequirement_actor() {
        return requirement_actor;
    }

    public void setRequirement_actor(String requirement_actor) {
        this.requirement_actor = requirement_actor;
    }

    public boolean isFuncNonFunc() {
        return funcNonFunc;
    }

    public void setFuncNonFunc(boolean funcNonFunc) {
        this.funcNonFunc = funcNonFunc;
    }

    @Override
    public String toString() {
        return "Requirement{" +
                "project_id=" + project_id +
                ", requirement_id=" + requirement_id +
                ", requirement_name='" + requirement_name + '\'' +
                ", requirement_description='" + requirement_description + '\'' +
                ", requirement_actor='" + requirement_actor + '\'' +
                ", funcNonFunc=" + funcNonFunc +
                '}';
    }
}


