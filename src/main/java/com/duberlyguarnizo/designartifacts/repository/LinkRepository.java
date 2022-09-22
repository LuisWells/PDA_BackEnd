package com.duberlyguarnizo.designartifacts.repository;

import com.duberlyguarnizo.designartifacts.model.Graph;
import com.duberlyguarnizo.designartifacts.model.Link;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LinkRepository extends JpaRepository<Link, Long> {
    List<Link> findByGraph(Graph graph);
}
