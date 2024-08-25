package com.example.ecommerce.service;

import com.example.ecommerce.dto.AdminDto;
import com.example.ecommerce.model.Admin;

public interface AdminService {

    Admin findByUsername(String username);

    Admin save(AdminDto adminDto);

}
