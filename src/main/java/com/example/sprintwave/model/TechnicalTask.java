package com.example.sprintwave.model;

public class TechnicalTask extends Userstory{


    private int userstory_id;

    public TechnicalTask(int id, int userstory_id, String name, String description, boolean released) {
        super(id, 0, name, description, released);
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


    @Override
    public String toString() {
        return "TechnicalTask{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", userstory_id=" + userstory_id +
                '}';
    }
}
