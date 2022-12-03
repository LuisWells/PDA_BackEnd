package com.duberlyguarnizo.designartifacts.controller;

import com.duberlyguarnizo.designartifacts.model.GraphDefinition;
import com.duberlyguarnizo.designartifacts.repository.GraphRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;


@Controller

public class GraphController {
    private final GraphRepository graphRepository;

    @Autowired
    public GraphController(GraphRepository graphRepository) {
        this.graphRepository = graphRepository;
    }


    public List<GraphDefinition> getAllGraphs() {
        return graphRepository.findAll();
    }


    public GraphDefinition getGraphById(Long id) {
        return graphRepository.findById(id).orElse(null);
    }


    public List<GraphDefinition> getGraphByName(String name) {
        return graphRepository.findByName(name);//validate in frontend
    }

    public List<GraphDefinition> getGraphsByCreationUserName(String adminName) {
        return graphRepository.findByCreationAdmin(adminName);
    }


    public List<GraphDefinition> getGraphsByUpdateUserName(String adminName) {
        return graphRepository.findByUpdateAdmin(adminName);

    }


    public GraphDefinition createGraph(GraphDefinition graphDefinition) {
        return graphRepository.save(graphDefinition);
    }


    public GraphDefinition updateDefinition(Long definitionId, GraphDefinition definition) {
        if (graphRepository.findById(definitionId).orElse(null) != null) {
            return graphRepository.save(definition);
        }
        return null;
    }


    public void deleteDefinition(Long id) {
        if (graphRepository.findById(id).orElse(null) != null) {
            graphRepository.deleteById(id);
        }
    }

}
