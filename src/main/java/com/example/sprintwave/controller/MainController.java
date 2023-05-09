package com.example.sprintwave.controller;

import com.example.sprintwave.model.Project;
import com.example.sprintwave.model.User;
import com.example.sprintwave.model.Workspace;
import com.example.sprintwave.repository.ProjectRepository;
import com.example.sprintwave.repository.UserRepository;
import com.example.sprintwave.repository.WorkspaceRepository;

import com.example.sprintwave.utility.UserDataHandler;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import com.example.sprintwave.utility.PasswordHashing;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
@Controller
public class MainController {

    ProjectRepository projectRepository;
    WorkspaceRepository workspaceRepository;
    UserRepository userRepository;

    public MainController(UserRepository userRepository, WorkspaceRepository workspaceRepository, ProjectRepository projectRepository)
    {
        this.userRepository = userRepository;
        this.workspaceRepository = workspaceRepository;
        this.projectRepository = projectRepository;
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
        User user = UserDataHandler.populateUserWithInformation(email, password, firstName, lastName, workspaceName, workspaceRepository);
        userRepository.createUser(user);
        return "redirect:/workspace";
    }

    // Login User
    @PostMapping("/loginuser")
    public String loginUser(@RequestParam("email") String enteredEmail,
                            @RequestParam("password") String enteredPassword,
                            Model model,
                            HttpSession session){
        PasswordHashing passwordHashing = new PasswordHashing();
        enteredPassword = passwordHashing.doHashing(enteredPassword);


        for(User checkUser: userRepository.getAllUsers()){
                // TO DO: Create CHECK for user.
            if(UserDataHandler.checkUserInformationMatch(checkUser, enteredPassword, enteredEmail))
            {
                model.addAttribute("currentuser",checkUser);
                session.setAttribute("currentuser", checkUser);
                User currentuser = (User) session.getAttribute("currentuser");

                return "redirect:/";
            }
        }
        return "login";

    }

    @GetMapping("/login")
    public String getLoginPage()
    {
        return "login";
    }

    @GetMapping("/overview")
    public String overview() {
        return "overview";
    }

    @PostMapping("/createProject")
    public String createProject(@RequestParam("projectname") String projectName)
    {
        Project project = new Project();
        project.setProjectName(projectName);
        projectRepository.createProject(project);
        return "redirect:/";
    }


}