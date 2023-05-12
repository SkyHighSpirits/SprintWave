package com.example.sprintwave.controller;

import com.example.sprintwave.model.Epic;
import com.example.sprintwave.model.Project;
import com.example.sprintwave.model.User;
import com.example.sprintwave.model.Workspace;
import com.example.sprintwave.repository.EpicRepository;
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

import java.util.ArrayList;

import java.time.LocalDate;

@ControllerAdvice
@Controller
public class MainController {

    ProjectRepository projectRepository;
    WorkspaceRepository workspaceRepository;
    UserRepository userRepository;
    EpicRepository epicRepository;

    public MainController(UserRepository userRepository, WorkspaceRepository workspaceRepository, ProjectRepository projectRepository, EpicRepository epicRepository)
    {
        this.userRepository = userRepository;
        this.workspaceRepository = workspaceRepository;
        this.projectRepository = projectRepository;
        this.epicRepository = epicRepository;
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

    @GetMapping("/epics/{project_id}")
    public String epic(@PathVariable("project_id") int project_id, Model model) {
        ArrayList<Epic> epicList = epicRepository.getAllEpicByProjectID(project_id);
        model.addAttribute("epics", epicList);
        return "epics";
    }

    @GetMapping("/showcreateepic")
    public String showCreateEpic() {
        return "epiccreate";
    }

    @PostMapping("/createepic")
    public String createEpic(@RequestParam("project_id") int project_id,
                             @RequestParam("epic_id") int epic_id,
                             @RequestParam("epic_name") String epic_name,
                             @RequestParam("epic_description") String epic_description) {
        Epic epic = new Epic();
        epic.setProject_id(project_id);
        epic.setEpic_id(epic_id);
        epic.setEpic_name(epic_name);
        epic.setEpic_description(epic_description);
        epicRepository.createEpic(epic);
        return "redirect:/epics";
    }

    @GetMapping("/updateepic/{epic_id}")
    public String showEpicUpdate(@PathVariable("epic_id") int updateID, Model model) {
        Epic updateEpic = epicRepository.findEpicByID(updateID);
        model.addAttribute("epic", updateEpic);
        return "epicupdate";
    }

    @PostMapping("/updateepic")
    public String updateEpic(@RequestParam("project_id") int project_id,
                             @RequestParam("epic_id") int epic_id,
                             @RequestParam("epic_name") String epic_name,
                             @RequestParam("epic_description") String epic_description) {
        Epic updateEpic = new Epic(project_id, epic_id, epic_name, epic_description);
        epicRepository.updateEpic(updateEpic);

        return "redirect:/epics";
    }

    @GetMapping("/deleteepic/{epic_id}/{project_id}")
    public String deleteEpic(@PathVariable("epic_id") int epic_id, @PathVariable("project_id") int project_id) {
        epicRepository.deleteEpic(epic_id);

        return "redirect:/epics/" + project_id;
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
                            HttpSession session) {
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


}