package com.duberlyguarnizo.designartifacts.controller;

import com.duberlyguarnizo.designartifacts.model.GraphDefinition;
import com.duberlyguarnizo.designartifacts.repository.GraphRepository;
import org.springframework.stereotype.Controller;

import java.util.List;


@Controller

public class GraphController {
    private final GraphRepository graphRepository;

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

    public List<GraphDefinition> getGraphsByCreationUserName(Long adminId) {
        return graphRepository.findByCreationAdmin_AdminId(adminId);
    }


    public List<GraphDefinition> getGraphsByUpdateUserName(Long adminId) {
        return graphRepository.findByUpdateAdmin_AdminId(adminId);

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
