package com.example.sprintwave.model;

public class TechnicalTask extends Userstory{


    private int userstory_id;

    public TechnicalTask(int id, int userstory_id, String name, String description, boolean released, int points, Status status, int sprint_id) {
        super(id, 0, name, description, released, points, status, sprint_id);
        this.userstory_id = userstory_id;
    }

    public TechnicalTask()
    {

    }

    public int getUserstory_id() {
        return userstory_id;
    }

    public void setUserstory_id(int userstory_id) {
        this.userstory_id = userstory_id;
    }


}
