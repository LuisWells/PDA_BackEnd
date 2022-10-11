package com.duberlyguarnizo.designartifacts.controller;

import com.duberlyguarnizo.designartifacts.model.Graph;
import com.duberlyguarnizo.designartifacts.repository.GraphRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("api/graph")
public class GraphController {
    @Autowired
    GraphRepository graphRepository;

    @GetMapping("/id/{id}")
    public Graph getGraphById(@PathVariable("id") Long id) {
        return graphRepository.findById(id).orElse(null);
    }

    @GetMapping("/name/{name}")
    public Graph getGraphByName(@PathVariable("name") String name) {
        return graphRepository.findByName(name);
    }

    @GetMapping("/username/{username}")
    public List<Graph> getGraphsByCreationUserName(@PathVariable("username") Long adminId) {
        return graphRepository.findByCreationAdmin_AdminId(adminId);
    }

    @GetMapping("/admin/{adminId}")
    public List<Graph> getGraphsByUpdateUserName(@PathVariable("adminId") Long adminId) {
        return graphRepository.findByUpdateAdmin_AdminId(adminId);
    }
    @PostMapping("/create")
    public Graph createGraph(@RequestBody Graph graph) {
        return graphRepository.save(graph);
    }

}
