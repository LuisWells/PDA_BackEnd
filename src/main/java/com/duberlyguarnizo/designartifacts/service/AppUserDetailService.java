package com.duberlyguarnizo.designartifacts.service;

import com.duberlyguarnizo.designartifacts.config.AdminUserDetail;
import com.duberlyguarnizo.designartifacts.model.Admin;
import com.duberlyguarnizo.designartifacts.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AppUserDetailService implements UserDetailsService {
    private final AdminRepository adminRepository;


    @Autowired
    public AppUserDetailService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin user = adminRepository.findFirstByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException(
                    String.format("User with email %s not found!", username)
            );
        }
        return new AdminUserDetail(user);
    }

}
