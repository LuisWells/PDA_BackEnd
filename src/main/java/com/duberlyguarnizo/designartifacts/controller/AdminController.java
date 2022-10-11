package com.duberlyguarnizo.designartifacts.controller;

import com.duberlyguarnizo.designartifacts.model.Admin;
import com.duberlyguarnizo.designartifacts.repository.AdminRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/admin")
@Log
public class AdminController {
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
    public List<Admin> getActiveAdmins() {
        return adminRepository.findByActiveIsTrue();
    }

    @GetMapping("/inactive")
    public List<Admin> getInactiveAdmins() {
        return adminRepository.findByActiveIsFalse();
    }

    @GetMapping("/name/{name}")
    public List<Admin> getAdminsByName(@PathVariable("name") String name) {
        return adminRepository.findByNameContaining(name);
    }
    @PostMapping("/create")
    public Admin createAdmin(@RequestBody Admin admin) {
        return adminRepository.save(admin);
    }
}
