package com.example.sprintwave.model;

public class TechnicalTask extends Userstory{


    private int userstoryId;

    public TechnicalTask(int id, int userstoryId, String name, String description, boolean released, int points, Status status, int sprint_id) {
        super(id, 0, name, description, released, points, status, sprint_id);
        this.userstoryId = userstoryId;
    }

    public TechnicalTask()
    {

    }

    public int getUserstoryId() {
        return userstoryId;
    }

    public void setUserstoryId(int userstoryId) {
        this.userstoryId = userstoryId;
    }


}
