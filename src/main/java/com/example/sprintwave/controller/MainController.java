package com.example.sprintwave.controller;

import com.example.sprintwave.model.*;
import com.example.sprintwave.repository.*;

import com.example.sprintwave.utility.UserDataHandler;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import com.example.sprintwave.utility.PasswordHashing;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
@Controller
public class MainController {

    ProjectRepository projectRepository;
    WorkspaceRepository workspaceRepository;
    UserRepository userRepository;
    EpicRepository epicRepository;
    RequirementRepository requirementRepository;

    TechnicalTaskRepository technicalTaskRepository;

    UserstoriesRepository userstoriesRepository;


    public MainController(UserRepository userRepository, WorkspaceRepository workspaceRepository, ProjectRepository projectRepository, EpicRepository epicRepository, RequirementRepository requirementRepository, UserstoriesRepository userstoriesRepository, TechnicalTaskRepository technicalTaskRepository)
    {
        this.userRepository = userRepository;
        this.workspaceRepository = workspaceRepository;
        this.projectRepository = projectRepository;
        this.epicRepository = epicRepository;
        this.requirementRepository = requirementRepository;
        this.userstoriesRepository = userstoriesRepository;
        this.technicalTaskRepository = technicalTaskRepository;
    }

    @ModelAttribute("currentuser")
    public User getCurrentUser(HttpServletRequest request)
    {
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("currentuser");
        return currentUser;
    }

    @ModelAttribute("currentproject")
    public Project getCurrentProject(HttpServletRequest request)
    {
        HttpSession session = request.getSession();
        Project currentProject = (Project) session.getAttribute("currentproject");
        return currentProject;
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

    @GetMapping("/requirements/{project_id}")
    public String requirement(@PathVariable("project_id") int project_id, Model model) {
        ArrayList<Requirement> requirementList = requirementRepository.getAllRequirementByProjectID(project_id);
        model.addAttribute("requirements", requirementList);
        return "requirements";
    }

    @GetMapping("/showcreaterequirement")
    public String showCreateRequirement() {
        return "requirementcreate";
    }
    @PostMapping("/createrequirement")
    public String createRequirement(@RequestParam("project_id") int project_id,
                                    @RequestParam("requirement_name") String requirement_name,
                                    @RequestParam("requirement_description") String requirement_description,
                                    @RequestParam("requirement_actor") String requirement_actor,
                                    @RequestParam("funcNonFunc") boolean funcNonFunc) {
        Requirement requirement = new Requirement();
        requirement.setProject_id(project_id);
        requirement.setRequirement_name(requirement_name);
        requirement.setRequirement_description(requirement_description);
        requirement.setRequirement_actor(requirement_actor);
        requirement.setFuncNonFunc(funcNonFunc);
        requirementRepository.createRequirement(requirement);
        return "redirect:/requirements/" + project_id;
    }

    @GetMapping("/updaterequirement/{requirement_id}")
    public String showUpdateRequirement(@PathVariable("requirement_id") int requirement_id, Model model) {
        Requirement foundRequirement = requirementRepository.findRequirementByID(requirement_id);
        model.addAttribute("requirement", foundRequirement);
        return "requirementupdate";
    }

    @PostMapping("/updaterequirement")
    public String updateRequirement(@RequestParam("project_id") int project_id,
                                    @RequestParam("requirement_id") int requirement_id,
                                    @RequestParam("requirement_name") String requirement_name,
                                    @RequestParam("requirement_description") String requirement_description,
                                    @RequestParam("requirement_actor") String requirement_actor,
                                    @RequestParam("funcNonFunc") boolean funcNonfunc) {
        Requirement updateRequirement = new Requirement(project_id, requirement_id, requirement_name, requirement_description, requirement_actor, funcNonfunc);
        requirementRepository.updateRequirement(updateRequirement);
        return "redirect:/requirements/" + project_id;
    }

    @GetMapping("/deleterequirement/{requirement_id}/{project_id}")
    public String deleteRequirement(@PathVariable("requirement_id") int requirement_id,
                                    @PathVariable("project_id") int project_id) {
        requirementRepository.deleteRequirement(requirement_id);
        return "redirect:/requirements/" + project_id;

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
                             @RequestParam("epic_name") String epic_name,
                             @RequestParam("epic_description") String epic_description) {
        Epic epic = new Epic();
        epic.setProject_id(project_id);
        epic.setEpic_name(epic_name);
        epic.setEpic_description(epic_description);
        epicRepository.createEpic(epic);
        return "redirect:/epics/" + project_id;
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

        return "redirect:/epics/" + project_id;
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
                                @RequestParam("password") String password,
                                Model model,
                                HttpSession session)
    {
        Workspace workspace = new Workspace();
        workspace.setName(workspaceName);
        workspaceRepository.addWorkspace(workspace);
        User user = UserDataHandler.populateUserWithInformation(email, password, firstName, lastName, workspaceName, workspaceRepository);
        userRepository.createUser(user);

        model.addAttribute("currentuser",user);
        session.setAttribute("currentuser", user);
        User currentuser = (User) session.getAttribute("currentuser");
        return "redirect:/appfrontpage/" + currentuser.getWorkspace_id();
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
            if(UserDataHandler.checkUserInformationMatch(checkUser, enteredPassword, enteredEmail))
            {
                model.addAttribute("currentuser",checkUser);
                session.setAttribute("currentuser", checkUser);
                User currentuser = (User) session.getAttribute("currentuser");

                return "redirect:/appfrontpage/" + currentuser.getWorkspace_id();
            }
        }
        return "login";
    }

    @GetMapping("/login")
    public String getLoginPage()
    {
        return "login";
    }

    @GetMapping("/overview/{projectID}")
    public String overview(@PathVariable("projectID") int projectID, HttpSession session, Model model) {
        Project currentproject = new Project();
        currentproject.setProjectID(projectID);
        model.addAttribute("currentproject",currentproject);
        session.setAttribute("currentproject",currentproject);
        return "overview";
    }

    @GetMapping("/createproject")
    public String getCreateproject() {return "createproject";}

    @PostMapping("/postcreateproject")
    public String createProject(@RequestParam("projectname") String projectName,
                                @RequestParam("projectowner") String projectOwner,
                                @RequestParam("projectdescription") String projectDescription,
                                @RequestParam("deadline") LocalDate deadline,
                                @RequestParam("workspaceid") int workspaceID)
    {
        Project project = new Project();
        project.setProjectName(projectName);
        project.setProjectOwner(projectOwner);
        project.setProjectDescription(projectDescription);
        project.setDeadline(deadline);
        project.setWorkspaceID(workspaceID);
        projectRepository.createProject(project);
        return "redirect:/appfrontpage/" + workspaceID;
    }
    
    
    /* START OF PROJECT MAPPINGS BY STEFFEN */
    @GetMapping("/appfrontpage/{workspace_id}")
    public String getAppFrontpage(@PathVariable("workspace_id") int workspace_id, Model model){
       ArrayList projectList = (ArrayList)projectRepository.findProjectByWorkspaceId(workspace_id); //TODO Skal opdateres til getAllProjectsByWorkspaceID
       model.addAttribute("projects", projectList);
        return "appfrontpage";
    }
    
    /* END OF PROJECT MAPPINGS BY STEFFEN */

    /* START OF USERSTORY MAPPINGS BY NICOLAI */
    @GetMapping("/backlog/{project_id}")
    public String showBacklogPage(@PathVariable("project_id") int project_id, Model model)
    {
        ArrayList<Userstory> relevantUserstories = userstoriesRepository.getAllUserstoriesFromProjectID(project_id);
        ArrayList<TechnicalTask> relevantTechnicalTasks = new ArrayList<>();
        for(Userstory userstory: relevantUserstories)
        {
            ArrayList<TechnicalTask> temporarytasks = technicalTaskRepository.getAllTechnicalTasksFromUserstoryID(userstory.getId());
            for(TechnicalTask technicalTask: temporarytasks)
            {
                relevantTechnicalTasks.add(technicalTask);
            }
        }
        model.addAttribute("userstories", relevantUserstories);
        model.addAttribute("technicaltasks", relevantTechnicalTasks);

        return "/backlog";
    }

     /* END OF USERSTORY MAPPINGS BY NICOLAI */


}