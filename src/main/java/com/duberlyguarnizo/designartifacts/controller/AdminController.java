package com.duberlyguarnizo.designartifacts.controller;

import com.duberlyguarnizo.designartifacts.model.Admin;
import com.duberlyguarnizo.designartifacts.repository.AdminRepository;
import lombok.extern.java.Log;
import org.springframework.stereotype.Controller;

import java.util.List;


@Controller
@Log
public class AdminController {
    private final AdminRepository adminRepository;

    public AdminController(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
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


    public Admin createAdmin(Admin admin) {
        return adminRepository.save(admin);
    }


    public Admin updateAdmin(Long adminId, Admin admin) {
        if (adminRepository.findById(adminId).orElse(null) == null) {
            return null;
        }
        return adminRepository.save(admin);
    }


    public void deleteAdmin(Long id) {
        if (adminRepository.findById(id).orElse(null) != null) {
            adminRepository.deleteById(id);
        }
    }
}
