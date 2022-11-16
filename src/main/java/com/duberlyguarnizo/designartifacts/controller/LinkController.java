package com.duberlyguarnizo.designartifacts.controller;

import com.duberlyguarnizo.designartifacts.model.GraphLink;
import com.duberlyguarnizo.designartifacts.repository.ContentRepository;
import com.duberlyguarnizo.designartifacts.repository.GraphRepository;
import com.duberlyguarnizo.designartifacts.repository.LinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;


@Controller
public class LinkController {
    private final LinkRepository linkRepository;

    @Autowired
    public LinkController(LinkRepository linkRepository, ContentRepository contentRepository, GraphRepository graphRepository) {
        this.linkRepository = linkRepository;
    }


    public List<GraphLink> getAllLinks() {
        return linkRepository.findAll();
    }

    public GraphLink getLinkById(Long id) {
        return linkRepository.findById(id).orElse(null);
    }

    public GraphLink getLinkByPath(String path) {
        return linkRepository.findByPath(path);
    }

    public GraphLink createLink(GraphLink graphLink) {
        return linkRepository.save(graphLink);
    }

    public GraphLink updateGraphContent(Long linkId, GraphLink content) {
        if (linkRepository.findById(linkId).orElse(null) != null) {
            return linkRepository.save(content);
        }
        return null;
    }

    public void deleteGraphContent(Long id) {
        if (linkRepository.findById(id).orElse(null) == null) {
            linkRepository.deleteById(id);
        }
    }
}
