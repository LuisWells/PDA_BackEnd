package com.duberlyguarnizo.designartifacts.controller;

import com.duberlyguarnizo.designartifacts.model.GraphDefinition;
import com.duberlyguarnizo.designartifacts.repository.GraphRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("api/graph")
public class GraphController {
    final
    GraphRepository graphRepository;

    public GraphController(GraphRepository graphRepository) {
        this.graphRepository = graphRepository;
    }

    @GetMapping("/all")
    public List<GraphDefinition> getAllGraphs() {
        return graphRepository.findAll();
    }

    @GetMapping("/id/{id}")
    public GraphDefinition getGraphById(@PathVariable("id") Long id) {
        return graphRepository.findById(id).orElse(null);
    }

    @GetMapping("/name/{name}")
    public GraphDefinition getGraphByName(@PathVariable("name") String name) {
        return graphRepository.findByName(name);
    }

    @GetMapping("/username/{username}")
    public List<GraphDefinition> getGraphsByCreationUserName(@PathVariable("username") Long adminId) {
        return graphRepository.findByCreationAdmin_AdminId(adminId);
    }

    @GetMapping("/admin/{adminId}")
    public List<GraphDefinition> getGraphsByUpdateUserName(@PathVariable("adminId") Long adminId) {
        return graphRepository.findByUpdateAdmin_AdminId(adminId);
    }

    @PostMapping("/create")
    public GraphDefinition createGraph(@RequestBody GraphDefinition graphDefinition) {
        return graphRepository.save(graphDefinition);
    }

}
