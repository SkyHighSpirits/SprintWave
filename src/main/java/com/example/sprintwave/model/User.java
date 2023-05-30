package com.example.sprintwave.model;

public class User {
    private int userId;
    private String email;
    private String userPassword;
    private String firstName;
    private String lastName;
    private int workspaceId;
    PermissionLevel permissionLevel;

    public User(int userId, String email, String userPassword, String firstName, String lastName, PermissionLevel permissionLevel, int workspaceId)
    {
        this.userId = userId;
        this.email = email;
        this.userPassword = userPassword;
        this.firstName = firstName;
        this.lastName = lastName;
        this.permissionLevel = permissionLevel;
        this.workspaceId = workspaceId;
    }

    public User()
    {
    }


    public int getUserId()
    {
        return userId;
    }

    public void setUserId(int userId)
    {
        this.userId = userId;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getUserPassword()
    {
        return userPassword;
    }

    public void setUserPassword(String userPassword)
    {
        this.userPassword = userPassword;
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

    public int getWorkspaceId()
    {
        return workspaceId;
    }

    public void setWorkspaceId(int workspaceId)
    {
        this.workspaceId = workspaceId;
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
                "userId=" + userId +
                ", email='" + email + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", workspaceId=" + workspaceId +
                ", permessionLevel=" + permissionLevel +
                '}';
    }
}
