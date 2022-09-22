package com.duberlyguarnizo.designartifacts.repository;


import com.duberlyguarnizo.designartifacts.model.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VisitRepository extends JpaRepository<Visit, Long> {
    List<Visit> findByWasAdminTrue();

    List<Visit> findByWasAdminFalse();

    List<Visit> findByHadInteractionsTrue();

}
