package com.duberlyguarnizo.designartifacts.controller;

import com.duberlyguarnizo.designartifacts.model.Graph;
import com.duberlyguarnizo.designartifacts.repository.GraphRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("api/graph")
public class GraphController {
    @Autowired
    GraphRepository graphRepository;

    public Graph getGraphById(Long id) {
        return graphRepository.findById(id).orElse(null);
    }

    public Graph getGraphByName(String name) {
        return graphRepository.findByName(name);
    }

    public List<Graph> getGraphsByCreationUserName(Long adminId) {
        return graphRepository.findByCreationAdmin_AdminId(adminId);
    }

    public List<Graph> getGraphsByUpdateUserName(Long adminId) {
        return graphRepository.findByUpdateAdmin_AdminId(adminId);
    }
}
