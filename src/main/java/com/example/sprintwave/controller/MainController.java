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
import org.apache.coyote.Request;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
        return "redirect:/appfrontpage";
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

    @GetMapping("/createproject")
    public String getCreateproject() {return "createproject";}

    @PostMapping("/postcreateproject")
    public String createProject(@RequestParam("projectname") String projectName,
                                @RequestParam("projectowner") String projectOwner,
                                @RequestParam("projectdescription") String projectDescription,
                                @RequestParam("deadline") LocalDate deadline)
    {
        Project project = new Project();
        project.setProjectName(projectName);
        project.setProjectOwner(projectOwner);
        project.setProjectDescription(projectDescription);
        project.setDeadline(deadline);
        project.setWorkspaceID(1); //todo skal rettes så dette sker automatisk.
        projectRepository.createProject(project);
        return "redirect:/appfrontpage";
    }
    
    
    /* START OF PROJECT MAPPINGS BY STEFFEN */
    @GetMapping("/appfrontpage/{workspace_id}")
    public String getAppFrontpage(@PathVariable("workspace_id") int workspace_id, Model model){
       // ArrayList projectList = (ArrayList)projectRepository.getAllProjects(); //TODO Skal opdateres til getAllProjectsByWorkspaceID
       // model.addAttribute("projects", projectList);
        return "appfrontpage";
    }
    
    /* END OF PROJECT MAPPINGS BY STEFFEN */
    


}