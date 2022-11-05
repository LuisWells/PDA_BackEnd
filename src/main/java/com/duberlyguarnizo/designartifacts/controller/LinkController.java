package com.duberlyguarnizo.designartifacts.controller;

import com.duberlyguarnizo.designartifacts.model.GraphContent;
import com.duberlyguarnizo.designartifacts.model.GraphDefinition;
import com.duberlyguarnizo.designartifacts.model.GraphLink;
import com.duberlyguarnizo.designartifacts.repository.ContentRepository;
import com.duberlyguarnizo.designartifacts.repository.GraphRepository;
import com.duberlyguarnizo.designartifacts.repository.LinkRepository;
import com.duberlyguarnizo.designartifacts.util.SvgParser;
import org.springframework.stereotype.Controller;

import java.util.List;


@Controller
public class LinkController {
    private final LinkRepository linkRepository;
    private final ContentRepository contentRepository;
    private final GraphRepository graphRepository;

    public LinkController(LinkRepository linkRepository, ContentRepository contentRepository, GraphRepository graphRepository) {
        this.linkRepository = linkRepository;
        this.contentRepository = contentRepository;
        this.graphRepository = graphRepository;
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

    public String getSvgImage(Long graphId, Long contentId) {
        GraphContent content = contentRepository.findById(contentId).orElse(null);
        String contentString = content == null ? "" : content.getContentJson();
        GraphDefinition graphDefinition = graphRepository.findById(graphId).orElse(null);
        String graphDefinitionString = graphDefinition == null ? "" : graphDefinition.getSvg();
        SvgParser svgParser = new SvgParser(graphDefinitionString);
        return svgParser.parse(contentString);
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
