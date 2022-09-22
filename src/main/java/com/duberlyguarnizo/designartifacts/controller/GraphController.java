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
        return graphRepository.findById(id).get();
    }

    public Graph getGraphByName(String name) {
        return graphRepository.getGraphByName(name);
    }

    public List<Graph> getGraphsByCreationUserName(String name) {
        return graphRepository.findGraphsByCreationAdminName(name);
    }

    public List<Graph> getGraphsByUpdateUserName(String name) {
        return graphRepository.getGraphsByUpdateAdmin_Name(name);
    }
}
