package com.example.ecommerce.Controller;

import ch.qos.logback.core.model.Model;
import com.example.ecommerce.dto.AdminDto;
import com.example.ecommerce.model.Admin;
import com.example.ecommerce.service.impl.AdminServiceImpl;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

    @Autowired
    private AdminServiceImpl adminService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String loginForm(){
        return "loginForm";
    }

    @GetMapping("/")
    public String indexPage(){
        return "index";
    }

    @GetMapping("/forgetpassword")
    public String forgetPassWord(Model model){
        return "forgetPassword";
    }

    @GetMapping("/register")
    public String register(org.springframework.ui.Model model){
        model.addAttribute("adminDto", new AdminDto());
        return "register";
    }

    @PostMapping("/register-new")
    public String addNewAsdmin(@Valid @ModelAttribute AdminDto adminDto,
                               BindingResult result ,
                               org.springframework.ui.Model model,
                               HttpSession session){

        try {

            session.removeAttribute("message");
            if (result.hasErrors()){
                model.addAttribute("adminDto", adminDto);
                result.toString();
                return "register";
            }
           String username = adminDto.getUsername();
            Admin admin = adminService.findByUsername(username);

            if (admin != null){
                model.addAttribute("adminDto", adminDto);
                System.out.println("Admin not null");
                session.setAttribute("message", "Your email has been register");
                return "register";
            }

            if (adminDto.getPassword().equals(adminDto.getRepeatePassword())){

                adminDto.setPassword(passwordEncoder.encode(adminDto.getPassword()));
                adminService.save(adminDto);
                System.out.println("Register success");
                model.addAttribute("adminDto", adminDto);
                session.setAttribute("message", "Register successfully");
                return "register";

            }else {
                model.addAttribute("adminDto", adminDto);
                session.setAttribute("message" , "Your password is not same");
                System.out.println("Password is not same");
                return "register";
            }

        }catch (Exception e){
            e.printStackTrace();
            session.setAttribute("message" , "Exception");
        }


        return "register";
    }
}
