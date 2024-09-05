package com.example.ecommerce.Controller;

import com.example.ecommerce.dto.AdminDto;
import com.example.ecommerce.model.Admin;
import com.example.ecommerce.service.impl.AdminServiceImpl;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    @Autowired
    private AdminServiceImpl adminService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String loginForm() {
        return "loginForm";
    }

    @GetMapping("/admin/index")
    public String adminHomePage() {
        return "index";  // This should correspond to the actual HTML or Thymeleaf template
    }


    @GetMapping("/")
    public String homePage(Model model) {
        model.addAttribute("title", "Home Page");
        Authentication authentication  = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null || authentication instanceof AnonymousAuthenticationToken) {

            return "redirect:/login";
        }
        return "index";
    }

    @GetMapping("/forgetpassword")
    public String forgetPassWord(Model model) {
        return "forgetPassword";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("title", "Register");
        model.addAttribute("adminDto", new AdminDto());
        return "register";
    }

    @PostMapping("/register-new")
    public String addNewAdmin(@Valid @ModelAttribute("adminDto") AdminDto adminDto,
                              BindingResult result,
                              Model model,
                              HttpSession session) {

        if (result.hasErrors()) {
            model.addAttribute("adminDto", adminDto);
            return "register";
        }

        String username = adminDto.getUsername();
        Admin admin = adminService.findByUsername(username);

        if (admin != null) {
            model.addAttribute("adminDto", adminDto);
            session.setAttribute("message", "Your email has been registered");
            return "register";
        }

        if (!adminDto.getPassword().equals(adminDto.getRepeatePassword())) {
            model.addAttribute("adminDto", adminDto);
            session.setAttribute("message", "Your passwords do not match");
            return "register";
        }

        adminDto.setPassword(passwordEncoder.encode(adminDto.getPassword()));
        adminService.save(adminDto);
        session.setAttribute("message", "Registered successfully");
        return "redirect:/login"; // Redirect to login page after successful registration
    }

            @GetMapping("/index1")
            String newIndex(){
                return "html/index1";
            }
}
