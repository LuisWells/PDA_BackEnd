package com.duberlyguarnizo.designartifacts.controller;

import com.duberlyguarnizo.designartifacts.model.Admin;
import com.duberlyguarnizo.designartifacts.repository.AdminRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
    @Autowired
    private AdminRepository adminRepository;

    @GetMapping("/all")
    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    @GetMapping("/id/{id}")
    public Admin getAdminById(@PathVariable("id") Long id) {
        return adminRepository.findById(id).orElse(null);
    }

    @GetMapping("/active")
    public List<Admin> getAdminByIsActive(boolean isActive) {
        return adminRepository.findByIsActive(isActive);
    }
}
