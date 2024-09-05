package com.example.ecommerce.service.impl;

import com.example.ecommerce.dto.AdminDto;
import com.example.ecommerce.model.Admin;
import com.example.ecommerce.repo.AdminRepository;
import com.example.ecommerce.repo.RoleRepository;
import com.example.ecommerce.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;

@Service
public class AdminServiceImpl implements AdminService {



    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private RoleRepository roleRepository;


    @Override
    public Admin findByUsername(String username) {
        return adminRepository.findByUsername(username);
    }

    @Override
    public Admin save(AdminDto adminDto) {
        Admin admin = new Admin();
        admin.setFirstName(adminDto.getFirstName());
        admin.setLastName(adminDto.getLastName());
        admin.setUsername(adminDto.getUsername());
        admin.setPassword(adminDto.getPassword());
        admin.setRoles(Arrays.asList(roleRepository.findByName("ADMIN")));
        return adminRepository.save(admin);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = adminRepository.findByUsername(username);  // Fetch admin details from the repository
        if (admin == null) {
            throw new UsernameNotFoundException("Admin not found with username: " + username);
        }

        // Create a UserDetails object using the User class provided by Spring Security
        return new User(admin.getUsername(), admin.getPassword(), Collections.singleton(() -> "ADMIN"));  // Assign "ADMIN" role
    }

}
