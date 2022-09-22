package com.duberlyguarnizo.designartifacts.controller;

import com.duberlyguarnizo.designartifacts.model.Admin;
import com.duberlyguarnizo.designartifacts.repository.AdminRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
    Marker apiMarker = MarkerFactory.getMarker("PDA-API");
    @Autowired
    private AdminRepository adminRepository;

    @GetMapping("/all")
    public List<Admin> getAllAdmins() {
        logger.warn("Requested all admins. Note: this is usually a bad idea! Try getting a paged request.");
        return adminRepository.findAll();
    }

    @GetMapping("/id/{id}")
    public Admin getAdminById(@PathVariable("id") Long id) {
        logger.info(apiMarker, "Returning admin with id: {}", id);
        return adminRepository.findById(id).orElse(null);
    }

    @GetMapping("/active")
    public List<Admin> getActiveAdmins() {
        logger.info(apiMarker, "Returning active admins");
        return adminRepository.findByIsActiveTrue();
    }

    @GetMapping("/inactive")
    public List<Admin> getInactiveAdmins() {
        logger.info(apiMarker, "Returning inactive admins");
        return adminRepository.findByIsActiveFalse();
    }

    @GetMapping("/name/{name}")
    public List<Admin> getAdminsByName(@PathVariable("name") String name) {
        logger.info(apiMarker, "Returning admins with name: {}", name);

        return adminRepository.findByNameContaining(name);
    }
}
