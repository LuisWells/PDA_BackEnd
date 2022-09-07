package com.duberlyguarnizo.designartifacts.repository;

import com.duberlyguarnizo.designartifacts.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
    List<Admin> findByIsActive(boolean isActive);

    List<Admin> findByName(String name);
}
