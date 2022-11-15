package com.duberlyguarnizo.designartifacts.controller;

import com.duberlyguarnizo.designartifacts.config.PdaPasswordEncoder;
import com.duberlyguarnizo.designartifacts.model.Admin;
import com.duberlyguarnizo.designartifacts.repository.AdminRepository;
import lombok.extern.java.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
@Log
@RequestMapping("/admin")
public class AdminController {
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
    private final AdminRepository adminRepository;
    private final PdaPasswordEncoder pdaPasswordEncoder;

    @Autowired
    public AdminController(AdminRepository adminRepository, PdaPasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.pdaPasswordEncoder = passwordEncoder;
    }

    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }


    public Admin getAdminById(Long id) {
        return adminRepository.findById(id).orElse(null);
    }


    public List<Admin> getActiveAdmins() {
        return adminRepository.findByActiveIsTrue();
    }


    public List<Admin> getInactiveAdmins() {
        return adminRepository.findByActiveIsFalse();
    }


    public List<Admin> getAdminsByName(String name) {
        return adminRepository.findByNameContaining(name);
    }

    @PostMapping(path = "/create")
    public void createAdmin(@RequestBody Admin admin) {
        admin.setPasswordHash(pdaPasswordEncoder.bCryptPasswordEncoder().encode(admin.getPasswordHash()));
        adminRepository.save(admin);
        logger.info("Admin created: {}", admin.getName());
    }


    public Admin updateAdmin(Long adminId, Admin admin) {
        if (adminRepository.findById(adminId).orElse(null) == null) {
            return null;
        }
        return adminRepository.save(admin);
    }
    //TODO: implement admin change password


    public void deleteAdmin(Long id) {
        if (adminRepository.findById(id).orElse(null) != null) {
            adminRepository.deleteById(id);
        }
    }
}
