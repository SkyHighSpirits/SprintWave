package com.example.sprintwave.model;

public class Requirement {
    private int projectId;
    private int requirementId;
    private String requirementName;
    private String requirementDescription;
    private String requirementActor;
    private boolean funcNonFunc = false;


    public Requirement(int projectId, int requirementId, String requirementName, String requirementDescription, String requirementActor, boolean funcNonFunc) {
        this.projectId = projectId;
        this.requirementId = requirementId;
        this.requirementName = requirementName;
        this.requirementDescription = requirementDescription;
        this.requirementActor = requirementActor;
        this.funcNonFunc = funcNonFunc;

    }

    public Requirement() {
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public int getRequirementId() {
        return requirementId;
    }

    public void setRequirementId(int requirementId) {
        this.requirementId = requirementId;
    }

    public String getRequirementName() {
        return requirementName;
    }

    public void setRequirementName(String requirementName) {
        this.requirementName = requirementName;
    }

    public String getRequirementDescription() {
        return requirementDescription;
    }

    public void setRequirementDescription(String requirementDescription) {
        this.requirementDescription = requirementDescription;
    }

    public String getRequirementActor() {
        return requirementActor;
    }

    public void setRequirementActor(String requirementActor) {
        this.requirementActor = requirementActor;
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
                "projectId=" + projectId +
                ", requirement_id=" + requirementId +
                ", requirementName='" + requirementName + '\'' +
                ", requirement_description='" + requirementDescription + '\'' +
                ", requirementActor='" + requirementActor + '\'' +
                ", funcNonFunc=" + funcNonFunc +
                '}';
    }
}


