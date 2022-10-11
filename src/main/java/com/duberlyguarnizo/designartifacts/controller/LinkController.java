package com.duberlyguarnizo.designartifacts.controller;

import com.duberlyguarnizo.designartifacts.model.Link;
import com.duberlyguarnizo.designartifacts.repository.LinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("api/link")
public class LinkController {
    @Autowired
    private LinkRepository linkRepository;

    @GetMapping("/all")
    public List<Link> getAllLinks() {
        return linkRepository.findAll();
    }

    @GetMapping("/id/{id}")
    public Link getLinkById(@PathVariable("id") Long id) {
        return linkRepository.findById(id).orElse(null);
    }

    @GetMapping("/path/{path}")
    public Link getLinkByPath(@PathVariable("path") String path) {
        return linkRepository.findByPath(path);
    }
    @PostMapping("/create")
    public Link createLink(@RequestBody Link link) {
        return linkRepository.save(link);
    }

}
