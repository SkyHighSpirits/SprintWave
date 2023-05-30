package com.example.sprintwave.controller;

import com.example.sprintwave.model.*;
import com.example.sprintwave.repository.*;

import com.example.sprintwave.utility.Calculations;
import com.example.sprintwave.utility.DataHandler;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import com.example.sprintwave.utility.PasswordHashing;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.HTML;
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

    //A Getmapping for frontpage
    @GetMapping("/")
    public String getHomepage()
    {
        return "frontpage";
    }

    //A Getmapping for signup page
    @GetMapping("/signup")
    public String getSignupPage()
    {
        return "signup";
    }

    //A Get mapping that shows all the requirements related to a specific projectID
    @GetMapping("/requirements/{project_id}")
    public String requirement(@PathVariable("project_id") int project_id, Model model) {
        ArrayList<Requirement> requirementList = requirementRepository.getAllRequirementByProjectID(project_id);
        model.addAttribute("requirements", requirementList);
        return "requirements";
    }

    //A getmapping that leads to the page where you create a requirement
    @GetMapping("/showcreaterequirement")
    public String showCreateRequirement() {
        return "requirementcreate";
    }

    //A Postmapping to that takes the input from the user, creates an object with this information, shoots it to the database, and returns to an overview of the requirements based on a projectID
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
        requirement.setFuncNonFunc(DataHandler.convertFuncNonFuncStringToBoolean(funcNonFuncChoice));
        requirementRepository.createRequirement(requirement);
        return "redirect:/requirements/" + project_id;
    }

    //A getmapping that uses the requirementsId, to shoots the current information into a model to show on the update page
    @GetMapping("/updaterequirement/{requirement_id}")
    public String showUpdateRequirement(@PathVariable("requirement_id") int requirement_id, Model model) {
        Requirement foundRequirement = requirementRepository.findRequirementByID(requirement_id);
        model.addAttribute("requirement", foundRequirement);
        return "requirementupdate";
    }

    //A Postmapping to that takes the input from the user, creates an object with this information, shoots it to the database, and returns to an overview of the requirements based on a projectID
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
        updateRequirement.setFuncNonFunc(DataHandler.convertFuncNonFuncStringToBoolean(funcNonFuncChoice));
        requirementRepository.updateRequirement(updateRequirement);
        return "redirect:/requirements/" + project_id;
    }

    //A getmapping that uses the requirementId, that finds the requirement and deletes it with deleteRequirement, and return to overview of the requirements based on the projectId
    @GetMapping("/deleterequirement/{requirement_id}/{project_id}")
    public String deleteRequirement(@PathVariable("requirement_id") int requirement_id,
                                    @PathVariable("project_id") int project_id) {
        requirementRepository.deleteRequirement(requirement_id);
        return "redirect:/requirements/" + project_id;

    }

    //A getmapping that uses projectId to request all the epics related that, and puts it into a model to show on the page
    @GetMapping("/epics/{project_id}")
    public String epic(@PathVariable("project_id") int project_id, Model model) {
        ArrayList<Epic> epicList = epicRepository.getAllEpicByProjectID(project_id);
        model.addAttribute("epics", epicList);
        return "epics";
    }

    //A getmapping that shows the page where you can create an epic
    @GetMapping("/showcreateepic")
    public String showCreateEpic() {
        return "epiccreate";
    }

    //A postmapping that request the user input, puts it into an epic object, shoots it to the databse and returns to the overview of epics based on a projectId
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

    //A getmapping that finds an epic based on a epicId, puts it into a model that can be shown on the update epic page
    @GetMapping("/updateepic/{epic_id}")
    public String showEpicUpdate(@PathVariable("epic_id") int updateID, Model model) {
        Epic updateEpic = epicRepository.findEpicByID(updateID);
        model.addAttribute("epic", updateEpic);
        return "epicupdate";
    }

    //A postmapping that request user input, puts it into an epic object, shoots it to the database, and return the overview of epics based on a projectId
    @PostMapping("/updateepic")
    public String updateEpic(@RequestParam("project_id") int project_id,
                             @RequestParam("epic_id") int epic_id,
                             @RequestParam("epic_name") String epic_name,
                             @RequestParam("epic_description") String epic_description) {
        Epic updateEpic = new Epic(project_id, epic_id, epic_name, epic_description);
        epicRepository.updateEpic(updateEpic);

        return "redirect:/epics/" + project_id;
    }

    //A getmapping that deletes an epic based on a epicId from pathvariable, and return the epics overview based on a projectId
    @GetMapping("/deleteepic/{epic_id}/{project_id}")
    public String deleteEpic(@PathVariable("epic_id") int epic_id, @PathVariable("project_id") int project_id) {
        epicRepository.deleteEpic(epic_id);

        return "redirect:/epics/" + project_id;
    }

    //A postmapping the request some user input, creates a workspace and a user. A session will also be created with the user,
    // which we use to return to appfrontpage based on a workspaceID from the session, which was just created.
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

    //A postmapping that request user input and check if the entered emails and passwords hashed version matches with the database
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

    //A getmapping that showss the login page
    @GetMapping("/login")
    public String getLoginPage()
    {
        return "login";
    }

    //A getmapping that shows the overview page
    @GetMapping("/overview/{projectID}")
    public String overview(@PathVariable("projectID") int projectID, HttpSession session, Model model) {
        Project currentproject = projectRepository.findProjectByID(projectID);
        currentproject.setProjectID(projectID);
        model.addAttribute("currentproject",currentproject);
        session.setAttribute("currentproject",currentproject);
        return "overview";
    }

    //A getmapping that shows the create project page
    @GetMapping("/createproject")
    public String getCreateproject() {return "createproject";}

    //A postmapping that request user input and creates a project object with that information. Return project page based on a workspaceId
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

    //A getmapping that finds a project by a projectId and puts it into a model, to be able to show it on the page
    @GetMapping("/updateproject/{projectID}")
    public String showUpdateProject(@PathVariable("projectID") int updateID, Model model, HttpSession session){
        Project updateProject = projectRepository.findProjectByID(updateID);
        model.addAttribute("project", updateProject);
        session.setAttribute("currentproject", updateProject);
        return "/modifyproject";
    }

    //A postmapping that request user input and shoots the updated version of the project to the database. Return appfrontpage based on a workspaceId
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
    //A getmapping that shows all projects based on a workspaceId, puts the objects into an arrayList and into a model that show the projects on the page
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

    //A getmapping that deletes a userstory based on a userstoryId, and returns backlogs page based on a projectId
    @GetMapping("/deleteuserstory/{userstory_id}")
    public String deleteUserstory(@PathVariable("userstory_id") int userstory_id) {
        int backlogProjectId = userstoriesRepository.getSpecificUserstoryByID(userstory_id).getProject_id();
        userstoriesRepository.deleteUserstory(userstory_id);

        return "redirect:/backlog/" + backlogProjectId;
    }

    //A postmapping that request user input to create a technicalTask object and shoot it to the database. Return backlog based on a projectID
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

    //A postmapping that request user input to update the specific technicalTask, shoots it to the database and returns the backlog based on a projectId
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
        TechnicalTask technicalTask = new TechnicalTask(technicaltask_id, userstory_id, technicaltask_name, technicaltask_description, technicaltask_released, technicaltask_points, DataHandler.convertStringToStatus(technicaltask_status), sprint_id);
        technicalTask.setSprint_id(sprint_id);
        technicalTaskRepository.updateTechnicalTask(technicalTask);

        return "redirect:/backlog/" + ((Project) session.getAttribute("currentproject")).getProjectID();
    }

    //A getmapping that deletes a technicalTask based on a technicalTaskId from pathvariable and return to backlog based on a projectId
    @GetMapping("/deletetask/{technicaltask_id}")
    public String deleteTechnicalTask(@PathVariable("technicaltask_id") int task_id, HttpSession session) {
        technicalTaskRepository.deleteTechnicalTask(task_id);
        return "redirect:/backlog/" + ((Project) session.getAttribute("currentproject")).getProjectID();
    }

    //A getmapping that shows the page to create a userstory
    @GetMapping("/showcreateuserstory")
    public String showCreateUserstory() {
        return "backlogcreateuserstory";
    }

    @PostMapping("/createuserstory")
    public String createUserStory(@RequestParam(value = "project_id") int project_id,
                                  @RequestParam(value= "userstory_name") String userstory_name,
                                  @RequestParam(value="userstory_description") String userstory_description,
                                  @RequestParam(value="userstory_points") int userstory_points,
                                  @RequestParam(value="sprint_id") int sprint_id, HttpSession session) {
        Userstory userstory = new Userstory();
        userstory.setProject_id(project_id);
        userstory.setName(userstory_name);
        userstory.setDescription(userstory_description);
        userstory.setPoints(DataHandler.limitPoints(userstory_points));
        userstory.setStatus(Status.sprintbacklog);
        userstory.setReleased(false);
        userstory.setSprint_id(sprint_id);

        System.out.println("Sprint id: " + sprint_id);

        //create an empty sprint 1 if the sprint does not exist allready
        Project currentProject = (Project) session.getAttribute("currentproject");
        if(sprintRepository.getSprintByIDAndProjectID(1, currentProject.getProjectID()) == null)
        {
            System.out.println("There are no sprints with this id within this project");
            Sprint defaultSprint = new Sprint(1, "SP001", currentProject.getProjectID());
            sprintRepository.createSprint(defaultSprint);
        }

        userstoriesRepository.createNewUserstory(userstory);
        return "redirect:/backlog/" + currentProject.getProjectID();
    }

    //A postmapping that request user input and creates an updated userstory object to shoots to the database, and return the backlog page based on a projectId
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
        Userstory userstory = new TechnicalTask(userstory_id, project_id, userstory_name, userstory_description, userstory_released, userstory_points, DataHandler.convertStringToStatus(userstory_status), sprint_id);
        userstory.setSprint_id(sprint_id);
        System.out.println("Sprint id: " + sprint_id);
        userstoriesRepository.updateUserstory(userstory);

        return "redirect:/backlog/" + ((Project) session.getAttribute("currentproject")).getProjectID();
    }


    @GetMapping("/updateuserstory/{userstory_id}")
    public String showUpdateUserstory(@PathVariable("userstory_id") int userstory_id, Model model) {
        model.addAttribute("userstory", userstoriesRepository.getSpecificUserstoryByID(userstory_id));

        return "backlogupdateuserstory";
    }

    /* END OF USERSTORY MAPPINGS BY NICOLAI */

    /*
    @PostMapping("/createsprint")
    public String createDefaultSprintIfNotExists(@RequestParam("spint_id") int newSprintID, @RequestParam("spint_name") String newSprintName, HttpSession session)
    {
        Project currentProject = (Project) session.getAttribute("currentproject");
        Sprint newSprint = new Sprint(newSprintID, newSprintName, currentProject.getProjectID());
        sprintRepository.createSprint(newSprint);
        return "redirect:/backlog/" + currentProject.getProjectID();
    }
    */


    /* START OF SPRINT BACKLOG MAPPINGS BY NICOLAI */

    @GetMapping("/sprints")
    public String showSprints(Model model, HttpSession session)
    {
        //TODO: Get the project id of the current opened project - TEST
        Project currentProject = (Project) session.getAttribute("currentproject");
        int project_id = currentProject.getProjectID();

        //TODO: Define and initialize arrays for models - DONE
        ArrayList<Sprint> sprintsInProject;

        ArrayList<TechnicalTask> technicaltasksInProject = new ArrayList<>();

        ArrayList<Userstory> userstoriesInProject = new ArrayList<>();

        //TODO: Find all sprints for a given project and add to a model - DONE
        sprintsInProject = sprintRepository.getAllSprintsByProjectID(project_id);
        model.addAttribute("sprintsinproject", sprintsInProject);

        //TODO: Find all userstories for a given project and add to a model - TEST
        userstoriesInProject = userstoriesRepository.getAllUserstoriesFromProjectID(project_id);
        model.addAttribute("userstoriesinproject",userstoriesInProject);
        System.out.println(userstoriesInProject);

        //TODO: Get an send an object of Datahandler and Calculator
        DataHandler datahandler = new DataHandler();
        model.addAttribute("datahandler", datahandler);
        Calculations calculations = new Calculations();
        model.addAttribute("calculator", calculations);


        //TODO: Find all technicalTasks for a given project and add to a model - DONE
        for(Userstory userstory: userstoriesInProject)
        {

            //TODO: If a userstory is released, then all technicaltasks are released - Done
            if(userstory.getStatus() == Status.done)
            {
                for(TechnicalTask technicalTask: technicalTaskRepository.getAllTechnicalTasksFromUserstoryID(userstory.getId()))
                {

                    technicalTask.setStatus(Status.done);
                    technicalTask.setReleased(true);

                    technicalTaskRepository.updateTechnicalTaskReleasedAndStatus(technicalTask);
                }
            }
            //TODO: If all technicaltasks are done, userstory is released - TEST
            boolean userstoryIsReleased = true;
            if(!technicalTaskRepository.getAllTechnicalTasksFromUserstoryID(userstory.getId()).isEmpty())
            {
                for(TechnicalTask technicalTask: technicalTaskRepository.getAllTechnicalTasksFromUserstoryID(userstory.getId()))
                {
                    if(technicalTask.getStatus() != Status.done)
                    {
                        userstoryIsReleased = false;
                    }
                    if((technicalTaskRepository.getAllTechnicalTasksFromUserstoryID(userstory.getId()).size() < 1))
                    {
                        userstoryIsReleased = false;
                    }
                }
                userstory.setReleased(userstoryIsReleased);
                if(userstoryIsReleased)
                {
                    userstory.setStatus(Status.done);
                }
            }
            else if(technicalTaskRepository.getAllTechnicalTasksFromUserstoryID(userstory.getId()).isEmpty() && userstory.getStatus() == Status.sprintbacklog)
            {
                userstory.setStatus(Status.sprintbacklog);
                userstory.setReleased(false);
            }

            userstoriesRepository.updateUserstory(userstory);

            //As technicaltask belong to a userstory within the current project ID, we put all technicaltasks for the userstory into the arraylist
            technicaltasksInProject.addAll(technicalTaskRepository.getAllTechnicalTasksFromUserstoryID(userstory.getId()));
        }

        //DONE
        model.addAttribute("technicaltasksinproject", technicaltasksInProject);

        return "sprints";
    }

    @PostMapping("/moveuserstoryleft")
    public String moveUserstoryLeft(@RequestParam("userstoryid") int userstoryID)
    {
        //TODO: Make functionality of moving tasks or userstories left on board
        Userstory movingUserstory = userstoriesRepository.getSpecificUserstoryByID(userstoryID);
        int status = DataHandler.convertStatusToInt(movingUserstory.getStatus());
        if(status == 1)
        {
            //Do nothing
        }
        else if(status == 4)
        {
            status--;
            for(TechnicalTask technicalTask: technicalTaskRepository.getAllTechnicalTasksFromUserstoryID(userstoryID))
            {
                technicalTask.setReleased(false);
                technicalTask.setStatus(DataHandler.convertIntToStatus(status));
                technicalTaskRepository.updateTechnicalTaskReleasedAndStatus(technicalTask);
            }
        }
        else
        {
            status--;
        }
        userstoriesRepository.updateUserstoryWithIntStatus(movingUserstory, status);

        return "redirect:/sprints";
    }

    @PostMapping("/moveuserstoryright")
    public String moveUserstoryRight(@RequestParam("userstoryid") int userstoryID)
    {
        //TODO: Make functionality of moving tasks or userstories right on board
        Userstory movingUserstory = userstoriesRepository.getSpecificUserstoryByID(userstoryID);
        int status = DataHandler.convertStatusToInt(movingUserstory.getStatus());
        if(status == 4)
        {
            //Do nothing
        }
        else
        {
            status++;
        }
        userstoriesRepository.updateUserstoryWithIntStatus(movingUserstory, status);
        return "redirect:/sprints";
    }

    @PostMapping("/movetechnicaltaskleft")
    public String moveTechnicaltaskLeft(@RequestParam("technicaltaskid") int TechnicaltaskID)
    {
        //TODO: Make functionality of moving tasks or userstories left on board
        TechnicalTask movingTechnicalTask = technicalTaskRepository.getSpecificTechnicalTaskFromID(TechnicaltaskID);
        int status = DataHandler.convertStatusToInt(movingTechnicalTask.getStatus());
        if(status == 1)
        {
            //Do nothing
        }
        else if(status == 4)
        {
            status--;
            movingTechnicalTask.setReleased(false);
            Userstory parentUserstory = userstoriesRepository.getSpecificUserstoryByID(movingTechnicalTask.getUserstory_id());
            parentUserstory.setReleased(false);
            userstoriesRepository.updateUserstoryWithIntStatus(parentUserstory, status);
        }
        else
        {
            status--;
        }
        technicalTaskRepository.updateTechnicalTaskUsingIntStatus(movingTechnicalTask, status);
        return "redirect:/sprints";
    }

    @PostMapping("/movetechnicaltaskright")
    public String moveTechnicaltaskRight(@RequestParam("technicaltaskid") int TechnicaltaskID)
    {
        //TODO: Make functionality of moving tasks or userstories right on board
        TechnicalTask movingTechnicalTask = technicalTaskRepository.getSpecificTechnicalTaskFromID(TechnicaltaskID);
        int status = DataHandler.convertStatusToInt(movingTechnicalTask.getStatus());
        if(status == 4)
        {
            //Do nothing
        }
        else
        {
            status++;
        }
        technicalTaskRepository.updateTechnicalTaskUsingIntStatus(movingTechnicalTask, status);
        return "redirect:/sprints";
    }

    @PostMapping("/createsprint")
    public String createSprint(@RequestParam("sprint_name") String sprintName,
                               HttpSession session)
    {
        Sprint sprint = new Sprint();
        Project currentproject = (Project) session.getAttribute("currentproject");
        // Auto increment logic pr project id for sprints
        int sprintID = sprintRepository.getMaxSprintIDFromProjectID(currentproject.getProjectID());
        sprintID++;
        sprint.setSprintId(sprintID);
        sprint.setSprintName(sprintName);
        sprint.setProjectId(currentproject.getProjectID());
        sprintRepository.createSprint(sprint);
        return "redirect:/sprints";
    }

    @GetMapping("/showcreatesprint")
    public String showCreateSprint(Model model)
    {
        Calculations calculator = new Calculations();
        model.addAttribute("calculator", calculator);
        return "createsprint";
    }

    @GetMapping("/updatesprint/{sprint_id}")
    public String showUpdateSprint(@PathVariable("sprint_id") int sprintID, HttpSession session, Model model) {
        Project currentproject = (Project) session.getAttribute("currentproject");
        Sprint foundSprint = sprintRepository.getSprintByIDAndProjectID(sprintID,currentproject.getProjectID());
        model.addAttribute("sprint", foundSprint);
        return "updatesprint";
    }

    @PostMapping("/updatesprint")
    public String updateSprint(@RequestParam("sprint_name") String sprintName,
                               @RequestParam("sprint_id") int sprintID,
                               HttpSession session) {
        Sprint updateSprint = new Sprint();
        Project currentproject = (Project) session.getAttribute("currentproject");
        updateSprint.setProjectId(currentproject.getProjectID());
        updateSprint.setSprintName(sprintName);
        updateSprint.setSprintId(sprintID);
        sprintRepository.updateSprint(updateSprint);
        return "redirect:/sprints";
    }

    /* END OF SPRINT BACKLOG MAPPINGS BY NICOLAI */





}