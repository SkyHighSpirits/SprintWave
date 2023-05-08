package com.example.sprintwave.model;

public class Workspace {

    int ID;
    String name;

    public Workspace()
    {

    }

    public Workspace(int workspaceID, String workspaceName)
    {
        this.ID = workspaceID;
        this.name = workspaceName;
    }
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
