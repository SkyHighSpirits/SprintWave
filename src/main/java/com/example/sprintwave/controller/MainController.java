package com.example.sprintwave.controller;

import com.example.sprintwave.model.PermissionLevel;
import com.example.sprintwave.model.User;
import com.example.sprintwave.model.Workspace;
import com.example.sprintwave.repository.UserRepository;
import com.example.sprintwave.repository.WorkspaceRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import com.example.sprintwave.utility.PasswordHashing;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
@Controller
public class MainController {


    WorkspaceRepository workspaceRepository;
    UserRepository userRepository;

    public MainController(UserRepository userRepository, WorkspaceRepository workspaceRepository)
    {
        this.userRepository = userRepository;
        this.workspaceRepository = workspaceRepository;
    }

    @ModelAttribute("currentuser")
    public User getCurrentUser(HttpServletRequest request)
    {
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("currentuser");
        return currentUser;
    }

    @GetMapping("/")
    public String getHomepage()
    {
        return "frontpage";
    }

    @GetMapping("/signup")
    public String getSignupPage()
    {
        return "signup";
    }

    @PostMapping("/createaccount")
    public String createAccount(@RequestParam("workspacename") String workspaceName,
                                @RequestParam("firstname") String firstName,
                                @RequestParam("lastname") String lastName,
                                @RequestParam("email") String email,
                                @RequestParam("password") String password)
    {
        Workspace workspace = new Workspace();
        workspace.setName(workspaceName);
        workspaceRepository.addWorkspace(workspace);


        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        PasswordHashing passwordHashing = new PasswordHashing();
        String hashedpassword = passwordHashing.doHashing(password);
        user.setUser_password(hashedpassword);
        user.setPermessionLevel(PermissionLevel.ADMINISTRATOR);
        Workspace withIDWorkspace = workspaceRepository.getLastEntryWorkspace();
        user.setWorkspace_id(withIDWorkspace.getID());
        userRepository.createUser(user);
        return "redirect:/workspace";
    }
    
    // Login User
    @PostMapping("/loginuser")
    public String loginUser(@RequestParam("email") String enteredEmail,
                            @RequestParam("password") String enteredPassword,
                            Model model,
                            HttpSession session){
        //User user = new User();
        //model.addAttribute("user", user);
        PasswordHashing passwordHashing = new PasswordHashing();
        enteredPassword = passwordHashing.doHashing(enteredPassword);

        for(User checkUser: userRepository.getAllUsers()){
                // TO DO: Create CHECK for user.
            String checkEmail = checkUser.getEmail();
            String checkPassword = checkUser.getUser_password();
            System.out.println(checkUser);
            System.out.println(enteredPassword);
            if(checkEmail.equals(enteredEmail) && checkPassword.equals(enteredPassword))
            {
                model.addAttribute("currentuser",checkUser);
                session.setAttribute("currentuser", checkUser);
                User currentuser = (User) session.getAttribute("currentuser");

                return "redirect:/frontpage";
            }
        }
        
        return "login";
        
    }

    @GetMapping("/login")
    public String getLoginPage()
    {
        return "login";
    }
}
