package com.example.sprintwave.utility;

import com.example.sprintwave.model.PermissionLevel;
import com.example.sprintwave.model.User;
import com.example.sprintwave.model.Workspace;
import com.example.sprintwave.repository.WorkspaceRepository;

public class UserDataHandler {

    public static boolean checkUserInformationMatch(User checkUser, String enteredPassword, String enteredEmail)
    {
            // TO DO: Create CHECK for user.
            String checkEmail = checkUser.getEmail();
            String checkPassword = checkUser.getUser_password();
            System.out.println(checkUser);
            System.out.println(enteredPassword);
            if(checkEmail.equals(enteredEmail) && checkPassword.equals(enteredPassword))
            {
                return true;
            }
            else
            {
                return false;
            }
    }

    public static User populateUserWithInformation(String email, String password, String firstname, String lastname, String workspacename, WorkspaceRepository workspaceRepository)
    {
        User user = new User();
        user.setFirstName(firstname);
        user.setLastName(lastname);
        user.setEmail(email);
        PasswordHashing passwordHashing = new PasswordHashing();
        String hashedpassword = passwordHashing.doHashing(password);
        user.setUser_password(hashedpassword);
        user.setPermessionLevel(PermissionLevel.ADMINISTRATOR);
        Workspace withIDWorkspace = workspaceRepository.getLastEntryWorkspace();
        user.setWorkspace_id(withIDWorkspace.getID());
        return user;
    }
}
