package com.duberlyguarnizo.designartifacts.controller;

import com.duberlyguarnizo.designartifacts.model.Admin;
import com.duberlyguarnizo.designartifacts.repository.AdminRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
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
    public List<Admin> getAdminByIsActive() {
        return adminRepository.findByIsActive(true);
    }
}
