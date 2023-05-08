package com.example.sprintwave.controller;

import com.example.sprintwave.model.User;
import com.example.sprintwave.model.Workspace;
import com.example.sprintwave.repository.UserRepository;
import com.example.sprintwave.repository.WorkspaceRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {

    WorkspaceRepository workspaceRepository;
    UserRepository userRepository;

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


        return "redirect:/workspace";
    }
    
    // Login User
    @PostMapping("/loginuser")
    public String loginUser(@RequestParam() String checkEmail,
                            @RequestParam() String checkPassword,
                            Model model,
                            HttpSession session){
        User user = new User();
        model.addAttribute("user", user);
        
        for(User checkUser: userRepository.getAllUsers){
            
        }
        
        return "redirect:/workspace";
        
    }
}
