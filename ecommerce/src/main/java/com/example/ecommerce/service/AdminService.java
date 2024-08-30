package com.example.ecommerce.service;

import com.example.ecommerce.dto.AdminDto;
import com.example.ecommerce.model.Admin;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AdminService extends UserDetailsService {

    Admin findByUsername(String username);

    Admin save(AdminDto adminDto);

}
