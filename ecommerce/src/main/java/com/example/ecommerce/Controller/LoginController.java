package com.example.ecommerce.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {


    @GetMapping("/login")
    public String loginForm(){
        return "loginForm";
    }

    @GetMapping("/")
    public String indexPage(){
        return "index";
    }
}
