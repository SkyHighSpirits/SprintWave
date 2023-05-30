package com.example.sprintwave.controller;

import com.example.sprintwave.model.*;
import com.example.sprintwave.repository.*;

import com.example.sprintwave.utility.Calculations;
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
    @GetMapping("/requirements/{projectid}")
    public String requirement(@PathVariable("projectid") int projectId, Model model) {
        ArrayList<Requirement> requirementList = requirementRepository.getAllRequirementByProjectID(projectId);
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
    public String createRequirement(@RequestParam("projectId") int projectId,
                                    @RequestParam("requirementName") String requirementName,
                                    @RequestParam("requirementDescription") String requirementDescription,
                                    @RequestParam("requirementActor") String requirementActor,
                                    @RequestParam("funcNonFuncChoice") String funcNonFuncChoice) {
        Requirement requirement = new Requirement();
        requirement.setProjectId(projectId);
        requirement.setRequirementName(requirementName);
        requirement.setRequirementDescription(requirementDescription);
        requirement.setRequirementActor(requirementActor);
        requirement.setFuncNonFunc(DataHandler.convertFuncNonFuncStringToBoolean(funcNonFuncChoice));
        requirementRepository.createRequirement(requirement);
        return "redirect:/requirements/" + projectId;
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
    public String updateRequirement(@RequestParam("projectId") int projectId,
                                    @RequestParam("requirementId") int requirementId,
                                    @RequestParam("requirementName") String requirementName,
                                    @RequestParam("requirementDescription") String requirementDescription,
                                    @RequestParam("requirementActor") String requirementActor,
                                    @RequestParam("funcNonFuncChoice") String funcNonFuncChoice) {
        Requirement updateRequirement = new Requirement();
        updateRequirement.setProjectId(projectId);
        updateRequirement.setRequirementId(requirementId);
        updateRequirement.setRequirementName(requirementName);
        updateRequirement.setRequirementDescription(requirementDescription);
        updateRequirement.setRequirementActor(requirementActor);
        updateRequirement.setFuncNonFunc(DataHandler.convertFuncNonFuncStringToBoolean(funcNonFuncChoice));
        requirementRepository.updateRequirement(updateRequirement);
        return "redirect:/requirements/" + projectId;
    }

    //A getmapping that uses the requirementId, that finds the requirement and deletes it with deleteRequirement, and return to overview of the requirements based on the projectId
    @GetMapping("/deleterequirement/{requirementId}/{projectId}")
    public String deleteRequirement(@PathVariable("requirementId") int requirementId,
                                    @PathVariable("projectId") int projectId) {
        requirementRepository.deleteRequirement(requirementId);
        return "redirect:/requirements/" + projectId;

    }

    //A getmapping that uses projectId to request all the epics related that, and puts it into a model to show on the page
    @GetMapping("/epics/{projectId}")
    public String epic(@PathVariable("projectId") int projectId, Model model) {
        ArrayList<Epic> epicList = epicRepository.getAllEpicByProjectID(projectId);
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
    public String createEpic(@RequestParam("projectId") int projectId,
                             @RequestParam("epicName") String epicName,
                             @RequestParam("epicDescription") String epicDescription) {
        Epic epic = new Epic();
        epic.setProjectId(projectId);
        epic.setEpicName(epicName);
        epic.setEpicDescription(epicDescription);
        epicRepository.createEpic(epic);
        return "redirect:/epics/" + projectId;
    }

    //A getmapping that finds an epic based on a epicId, puts it into a model that can be shown on the update epic page
    @GetMapping("/updateepic/{epicId}")
    public String showEpicUpdate(@PathVariable("epicId") int updateID, Model model) {
        Epic updateEpic = epicRepository.findEpicByID(updateID);
        model.addAttribute("epic", updateEpic);
        return "epicupdate";
    }

    //A postmapping that request user input, puts it into an epic object, shoots it to the database, and return the overview of epics based on a projectId
    @PostMapping("/updateepic")
    public String updateEpic(@RequestParam("projectId") int projectId,
                             @RequestParam("epicId") int epicId,
                             @RequestParam("epicName") String epicName,
                             @RequestParam("epicDescription") String epicDescription) {
        Epic updateEpic = new Epic(projectId, epicId, epicName, epicDescription);
        epicRepository.updateEpic(updateEpic);

        return "redirect:/epics/" + projectId;
    }

    //A getmapping that deletes an epic based on a epicId from pathvariable, and return the epics overview based on a projectId
    @GetMapping("/deleteepic/{epicId}/{projectId}")
    public String deleteEpic(@PathVariable("epicId") int epicId, @PathVariable("projectId") int projectId) {
        epicRepository.deleteEpic(epicId);

        return "redirect:/epics/" + projectId;
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
        return "redirect:/appfrontpage/" + currentuser.getWorkspaceId();
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

                return "redirect:/appfrontpage/" + currentuser.getWorkspaceId();
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
    public String getAppFrontpage(@PathVariable("workspace_id") int workspaceId, Model model){
       ArrayList projectList = (ArrayList)projectRepository.findProjectByWorkspaceId(workspaceId); //TODO Skal opdateres til getAllProjectsByWorkspaceID
       model.addAttribute("projects", projectList);
        return "appfrontpage";
    }
    
    /* END OF PROJECT MAPPINGS BY STEFFEN */

    /* START OF USERSTORY MAPPINGS BY NICOLAI */
    @GetMapping("/backlog/{projectId}")
    public String showBacklogPage(@PathVariable("projectId") int projectId, Model model)
    {

        ArrayList<Userstory> relevantUserstories = userstoriesRepository.getAllUserstoriesFromProjectID(projectId);
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
    @GetMapping("/deleteuserstory/{userstoryId}")
    public String deleteUserstory(@PathVariable("userstoryId") int userstoryId) {
        int backlogProjectId = userstoriesRepository.getSpecificUserstoryByID(userstoryId).getProjectId();
        userstoriesRepository.deleteUserstory(userstoryId);

        return "redirect:/backlog/" + backlogProjectId;
    }

    //A postmapping that request user input to create a technicalTask object and shoot it to the database. Return backlog based on a projectID

    //A getmapping that shows the page to create a userstory
    @GetMapping("/showcreateuserstory")
    public String showCreateUserstory() {
        return "backlogcreateuserstory";
    }

    @PostMapping("/createuserstory")
    public String createUserStory(@RequestParam("projectId") int projectId,
                                  @RequestParam("userstoryName") String userstoryName,
                                  @RequestParam("userstoryDescription") String userstoryDescription,
                                  @RequestParam("userstoryPoints") int userstoryPoints,
                                  @RequestParam("sprintId") int sprintId, HttpSession session) {
        Userstory userstory = new Userstory();
        userstory.setProjectId(projectId);
        userstory.setName(userstoryName);
        userstory.setDescription(userstoryDescription);
        userstory.setPoints(DataHandler.limitPoints(userstoryPoints));
        userstory.setStatus(Status.sprintbacklog);
        userstory.setReleased(false);
        userstory.setSprintId(sprintId);

        System.out.println("Sprint id: " + sprintId);

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
    public String updateUserstory(@RequestParam("userstoryId") int userstoryId,
                                  @RequestParam("projectId") int projectId,
                                  @RequestParam("userstoryName") String userstoryName,
                                  @RequestParam("userstoryDescription") String userstoryDescription,
                                  @RequestParam("userstoryReleased") Boolean userstoryReleased,
                                  @RequestParam("userstoryPoints") int userstoryPoints,
                                  @RequestParam("userstoryStatus") String userstoryStatus,
                                  @RequestParam("sprintId") int sprintId,
                                  HttpSession session)
    {
        Userstory userstory = new TechnicalTask(userstoryId, projectId, userstoryName, userstoryDescription, userstoryReleased, userstoryPoints, DataHandler.convertStringToStatus(userstoryStatus), sprintId);
        userstory.setSprintId(sprintId);
        System.out.println("Sprint id: " + sprintId);
        userstoriesRepository.updateUserstory(userstory);

        return "redirect:/backlog/" + ((Project) session.getAttribute("currentproject")).getProjectID();
    }

    @GetMapping("/updateuserstory/{userstoryId}")
    public String showUpdateUserstory(@PathVariable("userstoryId") int userstoryId, Model model) {
        model.addAttribute("userstory", userstoriesRepository.getSpecificUserstoryByID(userstoryId));

        return "backlogupdateuserstory";
    }

    @PostMapping("/createtask")
    public String createTask(@RequestParam("userstoryId") int userstoryId,
                             @RequestParam("technicaltaskName") String technicaltaskName,
                             @RequestParam("technicaltaskPoints") int technicaltaskPoints,
                             @RequestParam("technicaltaskDescription") String technicaltaskDescription,
                             @RequestParam("technicaltaskStatus") String technicaltaskStatus,
                             @RequestParam("sprintId") int sprintId, HttpSession session) {
        TechnicalTask technicalTask = new TechnicalTask();
        technicalTask.setUserstoryId(userstoryId);
        technicalTask.setName(technicaltaskName);
        technicalTask.setDescription(technicaltaskDescription);
        technicalTask.setReleased(false);
        technicalTask.setPoints(DataHandler.limitPoints(technicaltaskPoints));
        technicalTask.setStatus(DataHandler.convertStringToStatus(technicaltaskStatus));
        technicalTask.setSprintId(sprintId);
        technicalTaskRepository.createNewTecnicalTask(technicalTask);
        return "redirect:/backlog/" + ((Project) session.getAttribute("currentproject")).getProjectID();
    }

    @GetMapping("/showcreatetask/{userstoryId}")
    public String showCreateTask(@PathVariable("userstoryId") int userstoryId, Model model) {
        model.addAttribute("parentuserstory", userstoriesRepository.getSpecificUserstoryByID(userstoryId));

        return "backlogcreatetask";
    }

    @GetMapping("/updatetask/{technicaltaskId}")
    public String showUpdateTask(@PathVariable("technicaltaskId") int taskId, Model model) {
        model.addAttribute("technicaltask", technicalTaskRepository.getSpecificTechnicalTaskFromID(taskId));

        return "backlogupdatetask";
    }

    @PostMapping("/updatetask")
    public String updateTechnicalTask(@RequestParam("technicaltaskId") int technicaltaskId,
                                      @RequestParam("userstoryId") int userstoryId,
                                      @RequestParam("technicaltaskName") String technicaltaskName,
                                      @RequestParam("technicaltaskDescription") String technicaltaskDescription,
                                      @RequestParam("technicaltaskReleased") Boolean technicaltaskReleased,
                                      @RequestParam("technicaltaskPoints") int technicaltaskPoints,
                                      @RequestParam("technicaltaskStatus") String technicaltaskStatus,
                                      @RequestParam("sprintId") int sprintId, HttpSession session)
    {
        TechnicalTask technicalTask = new TechnicalTask(technicaltaskId, userstoryId, technicaltaskName, technicaltaskDescription, technicaltaskReleased, technicaltaskPoints, DataHandler.convertStringToStatus(technicaltaskStatus), sprintId);
        technicalTask.setSprintId(sprintId);
        technicalTaskRepository.updateTechnicalTask(technicalTask);

        return "redirect:/backlog/" + ((Project) session.getAttribute("currentproject")).getProjectID();
    }

    @GetMapping("/deletetask/{technicaltaskId}")
    public String deleteTechnicalTask(@PathVariable("technicaltaskId") int taskId, HttpSession session) {
        technicalTaskRepository.deleteTechnicalTask(taskId);
        return "redirect:/backlog/" + ((Project) session.getAttribute("currentproject")).getProjectID();
    }

    /* END OF USERSTORY MAPPINGS BY NICOLAI */

    /* START OF SPRINT BACKLOG MAPPINGS BY NICOLAI */

    @GetMapping("/sprints")
    public String showSprints(Model model, HttpSession session)
    {
        //Get the project id of the current opened project
        Project currentProject = (Project) session.getAttribute("currentproject");
        int project_id = currentProject.getProjectID();

        //Define and initialize arrays for models
        ArrayList<Sprint> sprintsInProject;

        ArrayList<TechnicalTask> technicaltasksInProject = new ArrayList<>();

        ArrayList<Userstory> userstoriesInProject = new ArrayList<>();

        //Find all sprints for a given project and add to a model
        sprintsInProject = sprintRepository.getAllSprintsByProjectID(project_id);
        model.addAttribute("sprintsinproject", sprintsInProject);

        //Find all userstories for a given project and add to a model
        userstoriesInProject = userstoriesRepository.getAllUserstoriesFromProjectID(project_id);
        model.addAttribute("userstoriesinproject",userstoriesInProject);
        System.out.println(userstoriesInProject);

        //Get and send an object of Datahandler and Calculator
        DataHandler datahandler = new DataHandler();
        model.addAttribute("datahandler", datahandler);
        Calculations calculations = new Calculations();
        model.addAttribute("calculator", calculations);


        //find all technicalTasks for a given project and add to a model
        for(Userstory userstory: userstoriesInProject)
        {

            //If a userstory is released, then all technicaltasks are released
            if(userstory.getStatus() == Status.done)
            {
                for(TechnicalTask technicalTask: technicalTaskRepository.getAllTechnicalTasksFromUserstoryID(userstory.getId()))
                {

                    technicalTask.setStatus(Status.done);
                    technicalTask.setReleased(true);

                    technicalTaskRepository.updateTechnicalTaskReleasedAndStatus(technicalTask);
                }
            }
            //If all technicaltasks are done, userstory is released
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
        //Functionality of moving tasks or userstories left on board
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
        //Functionality of moving tasks or userstories right on board
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
        //functionality of moving tasks or userstories left on board
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
            Userstory parentUserstory = userstoriesRepository.getSpecificUserstoryByID(movingTechnicalTask.getUserstoryId());
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
        //functionality of moving tasks or userstories right on board
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

    @GetMapping("/updatesprint/{sprintId}")
    public String showUpdateSprint(@PathVariable("sprintId") int sprintID, HttpSession session, Model model) {
        Project currentproject = (Project) session.getAttribute("currentproject");
        Sprint foundSprint = sprintRepository.getSprintByIDAndProjectID(sprintID,currentproject.getProjectID());
        model.addAttribute("sprint", foundSprint);
        return "updatesprint";
    }

    @PostMapping("/updatesprint")
    public String updateSprint(@RequestParam("sprintName") String sprintName,
                               @RequestParam("sprintId") int sprintID,
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