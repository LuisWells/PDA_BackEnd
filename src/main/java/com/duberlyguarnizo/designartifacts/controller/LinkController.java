package com.duberlyguarnizo.designartifacts.controller;

import com.duberlyguarnizo.designartifacts.model.GraphLink;
import com.duberlyguarnizo.designartifacts.repository.LinkRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("api/link")
public class LinkController {
    private final LinkRepository linkRepository;

    public LinkController(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    @GetMapping("/all")
    public List<GraphLink> getAllLinks() {
        return linkRepository.findAll();
    }

    @GetMapping("/id/{id}")
    public GraphLink getLinkById(@PathVariable("id") Long id) {
        return linkRepository.findById(id).orElse(null);
    }

    @GetMapping("/path/{path}")
    public GraphLink getLinkByPath(@PathVariable("path") String path) {
        return linkRepository.findByPath(path);
    }
    @PostMapping("/create")
    public GraphLink createLink(@RequestBody GraphLink graphLink) {
        return linkRepository.save(graphLink);
    }

}
