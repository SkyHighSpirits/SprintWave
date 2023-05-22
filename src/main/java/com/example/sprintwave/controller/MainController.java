package com.example.sprintwave.controller;

import com.example.sprintwave.model.*;
import com.example.sprintwave.repository.*;

import com.example.sprintwave.utility.DataHandler;
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
    RequirementRepository requirementRepository;

    TechnicalTaskRepository technicalTaskRepository;

    UserstoriesRepository userstoriesRepository;

    SprintRepository sprintRepository;


    public MainController(UserRepository userRepository, WorkspaceRepository workspaceRepository, ProjectRepository projectRepository, EpicRepository epicRepository, RequirementRepository requirementRepository, UserstoriesRepository userstoriesRepository, TechnicalTaskRepository technicalTaskRepository, SprintRepository sprintRepository)
    {
        this.userRepository = userRepository;
        this.workspaceRepository = workspaceRepository;
        this.projectRepository = projectRepository;
        this.epicRepository = epicRepository;
        this.requirementRepository = requirementRepository;
        this.userstoriesRepository = userstoriesRepository;
        this.technicalTaskRepository = technicalTaskRepository;
        this.sprintRepository = sprintRepository;
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
                                    @RequestParam("funcNonFuncChoice") String funcNonFuncChoice) {
        Requirement requirement = new Requirement();
        requirement.setProject_id(project_id);
        requirement.setRequirement_name(requirement_name);
        requirement.setRequirement_description(requirement_description);
        requirement.setRequirement_actor(requirement_actor);
        requirement.setFuncNonFuncChoice(funcNonFuncChoice);
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
                                    @RequestParam("funcNonFuncChoice") String funcNonFuncChoice) {
        Requirement updateRequirement = new Requirement();
        updateRequirement.setProject_id(project_id);
        updateRequirement.setRequirement_id(requirement_id);
        updateRequirement.setRequirement_name(requirement_name);
        updateRequirement.setRequirement_description(requirement_description);
        updateRequirement.setRequirement_actor(requirement_actor);
        updateRequirement.setFuncNonFuncChoice(funcNonFuncChoice);
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
        User user = DataHandler.populateUserWithInformation(email, password, firstName, lastName, workspaceName, workspaceRepository);
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
            if(DataHandler.checkUserInformationMatch(checkUser, enteredPassword, enteredEmail))
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
        Project currentproject = projectRepository.findProjectByID(projectID);
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

    @GetMapping("/updateproject/{projectID}")
    public String showUpdateProject(@PathVariable("projectID") int updateID, Model model, HttpSession session){
        Project updateProject = projectRepository.findProjectByID(updateID);
        model.addAttribute("project", updateProject);
        session.setAttribute("currentproject", updateProject);
        return "/modifyproject";
    }


    @PostMapping("/updateproject")
    public String updateProject(@RequestParam("project_id") int projectID,
                                @RequestParam("projectname") String projectName,
                                @RequestParam("projectdescription") String projectDescription,
                                @RequestParam("projectowner") String projectOwner,
                                @RequestParam("projectstatus") boolean projectStatus,
                                @RequestParam("projectdeadline") LocalDate deadline,
                                @RequestParam("workspaceID") int workspaceID) {
        Project updateProject = new Project(projectID, projectName, projectDescription, projectOwner, projectStatus, deadline, workspaceID);
        projectRepository.updateProject(updateProject);


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
        ArrayList<Userstory> notreleasedUserstories = new ArrayList<>();
        ArrayList<Userstory> notreleasedTechnicalTasks = new ArrayList<>();
        ArrayList<Userstory> releasedUserstories = new ArrayList<>();
        ArrayList<TechnicalTask> releasedTechnicalTasks = new ArrayList<>();

        ArrayList<TechnicalTask> temporarytasks = new ArrayList<>();

        for(Userstory userstory: relevantUserstories)
        {
            temporarytasks.addAll(technicalTaskRepository.getAllTechnicalTasksFromUserstoryID(userstory.getId()));
            if(!userstory.isReleased())
            {
                notreleasedUserstories.add(userstory);
            }
            else
            {
                releasedUserstories.add(userstory);
            }
        }
        for(TechnicalTask technicalTask: temporarytasks)
        {
            if(!technicalTask.isReleased())
            {
                notreleasedTechnicalTasks.add(technicalTask);
            }
            else
            {
                releasedTechnicalTasks.add(technicalTask);
            }

        }

        model.addAttribute("notreleaseduserstories", notreleasedUserstories);
        model.addAttribute("notreleasedtechnicaltasks", notreleasedTechnicalTasks);
        model.addAttribute("releaseduserstories", releasedUserstories);
        model.addAttribute("releasedtechnicaltasks", releasedTechnicalTasks);

        return "/backlog";
    }

    @GetMapping("/deleteuserstory/{userstory_id}")
    public String deleteUserstory(@PathVariable("userstory_id") int userstory_id) {
        int backlogProjectId = userstoriesRepository.getSpecificUserstoryByID(userstory_id).getProject_id();
        userstoriesRepository.deleteUserstory(userstory_id);

        return "redirect:/backlog/" + backlogProjectId;
    }

    @PostMapping("/createtask")
    public String createTask(@RequestParam("userstory_id") int userstory_id,
                             @RequestParam("technicaltask_name") String technicaltask_name,
                             @RequestParam("technicaltask_points") int technicaltask_points,
                             @RequestParam("technicaltask_description") String technicaltask_description,
                             @RequestParam("technicaltask_status") String technicaltask_status,
                             @RequestParam("sprint_id") int sprint_id, HttpSession session) {
        TechnicalTask technicalTask = new TechnicalTask();
        technicalTask.setUserstory_id(userstory_id);
        technicalTask.setName(technicaltask_name);
        technicalTask.setDescription(technicaltask_description);
        technicalTask.setReleased(false);
        technicalTask.setPoints(DataHandler.limitPoints(technicaltask_points));
        technicalTask.setStatus(DataHandler.convertStringToStatus(technicaltask_status));
        technicalTask.setSprint_id(sprint_id);
        technicalTaskRepository.createNewTecnicalTask(technicalTask);
        return "redirect:/backlog/" + ((Project) session.getAttribute("currentproject")).getProjectID();
    }

    @GetMapping("/showcreatetask/{userstory_id}")
    public String showCreateTask(@PathVariable("userstory_id") int userstory_id, Model model) {
        model.addAttribute("parentuserstory", userstoriesRepository.getSpecificUserstoryByID(userstory_id));

        return "backlogcreatetask";
    }

    @GetMapping("/updatetask/{technicaltask_id}")
    public String showUpdateTask(@PathVariable("technicaltask_id") int task_id, Model model) {
        model.addAttribute("technicaltask", technicalTaskRepository.getSpecificTechnicalTaskFromID(task_id));

        return "backlogupdatetask";
    }

    @PostMapping("/updatetask")
    public String updateTechnicalTask(@RequestParam("technicaltask_id") int technicaltask_id,
                             @RequestParam("userstory_id") int userstory_id,
                             @RequestParam("technicaltask_name") String technicaltask_name,
                             @RequestParam("technicaltask_description") String technicaltask_description,
                             @RequestParam("technicaltask_released") Boolean technicaltask_released,
                             @RequestParam("technicaltask_points") int technicaltask_points,
                             @RequestParam("technicaltask_status") String technicaltask_status,
                             @RequestParam("sprint_id") int sprint_id, HttpSession session)
    {
        TechnicalTask technicalTask = new TechnicalTask(technicaltask_id, userstory_id, technicaltask_name, technicaltask_description, technicaltask_released, technicaltask_points, DataHandler.convertStringToStatus(technicaltask_status));
        technicalTask.setSprint_id(sprint_id);
        technicalTaskRepository.updateTechnicalTask(technicalTask);

        return "redirect:/backlog/" + ((Project) session.getAttribute("currentproject")).getProjectID();
    }

    @GetMapping("/deletetask/{technicaltask_id}")
    public String deleteTechnicalTask(@PathVariable("technicaltask_id") int task_id, HttpSession session) {
        technicalTaskRepository.deleteTechnicalTask(task_id);
        return "redirect:/backlog/" + ((Project) session.getAttribute("currentproject")).getProjectID();
    }

    @GetMapping("/showcreateuserstory")
    public String showCreateUserstory() {
        return "backlogcreateuserstory";
    }

    @PostMapping("/createuserstory")
    public String createUserStory(@RequestParam("project_id") int project_id,
                                  @RequestParam("userstory_name") String userstory_name,
                                  @RequestParam("userstory_description") String userstory_description,
                                  @RequestParam("userstory_points") int userstory_points,
                                  @RequestParam("sprint_id") int sprint_id, HttpSession session) {
        Userstory userstory = new Userstory();
        userstory.setProject_id(project_id);
        userstory.setName(userstory_name);
        userstory.setDescription(userstory_description);
        userstory.setPoints(DataHandler.limitPoints(userstory_points));
        userstory.setStatus(Status.sprintbacklog);
        userstory.setReleased(false);
        userstory.setSprint_id(sprint_id);

        //create an empty sprint if the sprint does not exist allready
        Project currentProject = (Project) session.getAttribute("currentproject");
        if(sprintRepository.getSprintByIDAndProjectID(1, currentProject.getProjectID()) != null)
        {
            Sprint defaultSprint = new Sprint(1, "SP001", currentProject.getProjectID());
            sprintRepository.createSprint(defaultSprint);
        }

        userstoriesRepository.createNewUserstory(userstory);
        return "redirect:/backlog/" + currentProject.getProjectID();
    }

    @PostMapping("/updateuserstory")
    public String updateUserstory(@RequestParam("userstory_id") int userstory_id,
                                  @RequestParam("project_id") int project_id,
                                  @RequestParam("userstory_name") String userstory_name,
                                  @RequestParam("userstory_description") String userstory_description,
                                  @RequestParam("userstory_released") Boolean userstory_released,
                                  @RequestParam("userstory_points") int userstory_points,
                                  @RequestParam("userstory_status") String userstory_status,
                                  @RequestParam("sprint_id") int sprint_id,
                                  HttpSession session)
    {
        Userstory userstory = new TechnicalTask(userstory_id, project_id, userstory_name, userstory_description, userstory_released, userstory_points, DataHandler.convertStringToStatus(userstory_status));
        userstory.setSprint_id(sprint_id);
        userstoriesRepository.updateUserstory(userstory);

        return "redirect:/backlog/" + ((Project) session.getAttribute("currentproject")).getProjectID();
    }

    @GetMapping("/updateuserstory/{userstory_id}")
    public String showUpdateUserstory(@PathVariable("userstory_id") int userstory_id, Model model) {
        model.addAttribute("userstory", userstoriesRepository.getSpecificUserstoryByID(userstory_id));

        return "backlogupdateuserstory";
    }


    @PostMapping("/createsprint")
    public String createDefaultSprintIfNotExists(@RequestParam("spint_id") int newSprintID, @RequestParam("spint_name") String newSprintName, HttpSession session)
    {
        Project currentProject = (Project) session.getAttribute("currentproject");
        Sprint newSprint = new Sprint(newSprintID, newSprintName, currentProject.getProjectID());
        sprintRepository.createSprint(newSprint);
        return "redirect:/backlog/" + currentProject.getProjectID();
    }

     /* END OF USERSTORY MAPPINGS BY NICOLAI */
}