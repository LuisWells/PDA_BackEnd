package com.duberlyguarnizo.designartifacts.controller;

import com.duberlyguarnizo.designartifacts.config.PdaPasswordEncoder;
import com.duberlyguarnizo.designartifacts.model.Admin;
import com.duberlyguarnizo.designartifacts.repository.AdminRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
@Log
@RequestMapping("/admin")
public class AdminController {
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

    @PostMapping(path = "/create", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String createAdmin(@RequestBody Admin admin) {
        admin.setPasswordHash(pdaPasswordEncoder.bCryptPasswordEncoder().encode(admin.getPasswordHash()));
        System.out.println(admin.getPasswordHash());
        adminRepository.save(admin);
        return "redirect:/admin/users";
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
