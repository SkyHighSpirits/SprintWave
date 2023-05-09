package com.example.sprintwave.model;

public class User {
    private int user_id;
    private String email;
    private String user_password;
    private String firstName;
    private String lastName;
    private int workspace_id;
    PermissionLevel permissionLevel;

    public User(int user_id, String email, String user_password, String firstName, String lastName, int workspace_id, PermissionLevel permissionLevel)
    {
        this.user_id = user_id;
        this.email = email;
        this.user_password = user_password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.workspace_id = workspace_id;
        this.permissionLevel = permissionLevel;
    }

    public User()
    {
    }


    public int getUser_id()
    {
        return user_id;
    }

    public void setUser_id(int user_id)
    {
        this.user_id = user_id;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getUser_password()
    {
        return user_password;
    }

    public void setUser_password(String user_password)
    {
        this.user_password = user_password;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public int getWorkspace_id()
    {
        return workspace_id;
    }

    public void setWorkspace_id(int workspace_id)
    {
        this.workspace_id = workspace_id;
    }

    public PermissionLevel getPermessionLevel()
    {
        return permissionLevel;
    }

    public void setPermessionLevel(PermissionLevel permissionLevel)
    {
        this.permissionLevel = permissionLevel;
    }

    @Override
    public String toString()
    {
        return "User{" +
                "user_id=" + user_id +
                ", email='" + email + '\'' +
                ", user_password='" + user_password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", workspace_id=" + workspace_id +
                ", permessionLevel=" + permissionLevel +
                '}';
    }
}
