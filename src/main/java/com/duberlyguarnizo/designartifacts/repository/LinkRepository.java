package com.duberlyguarnizo.designartifacts.repository;

import com.duberlyguarnizo.designartifacts.model.GraphLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LinkRepository extends JpaRepository<GraphLink, Long> {
    List<GraphLink> findByLinkId(Long id);
    GraphLink findByPath(String path);
}
