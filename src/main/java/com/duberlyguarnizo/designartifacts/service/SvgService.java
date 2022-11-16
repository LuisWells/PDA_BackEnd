package com.duberlyguarnizo.designartifacts.service;

import com.duberlyguarnizo.designartifacts.model.GraphContent;
import com.duberlyguarnizo.designartifacts.model.GraphDefinition;
import com.duberlyguarnizo.designartifacts.model.GraphLink;
import com.duberlyguarnizo.designartifacts.repository.ContentRepository;
import com.duberlyguarnizo.designartifacts.repository.GraphRepository;
import com.duberlyguarnizo.designartifacts.repository.LinkRepository;
import com.duberlyguarnizo.designartifacts.util.SvgParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SvgService {
    private final LinkRepository linkRepository;
    private final ContentRepository contentRepository;
    private final GraphRepository graphRepository;

    @Autowired
    public SvgService(LinkRepository linkRepository, ContentRepository contentRepository, GraphRepository graphRepository) {
        this.linkRepository = linkRepository;
        this.contentRepository = contentRepository;

        this.graphRepository = graphRepository;
    }

    public String getSvgByPath(String path) {
        String result = "";
        GraphLink link = linkRepository.findByPath(path);
        if (link != null) {
            var content = link.getGraphContent();
            var graph = link.getGraphDefinition();

            if (content != null && graph != null) {

                result = getSvgImage(graph.getGraphId(), content.getContentId());
            }
        }
        return result;
    }

    private String getSvgImage(Long graphId, Long contentId) {
        GraphContent content = contentRepository.findById(contentId).orElse(null);
        String contentString = content == null ? "" : content.getContentJson();
        GraphDefinition graphDefinition = graphRepository.findById(graphId).orElse(null);
        String graphDefinitionString = graphDefinition == null ? "" : graphDefinition.getSvg();
        SvgParser svgParser = new SvgParser(graphDefinitionString);
        return svgParser.parse(contentString);
    }
}
