package com.duberlyguarnizo.designartifacts.controller;

import com.duberlyguarnizo.designartifacts.model.GraphContent;
import com.duberlyguarnizo.designartifacts.repository.ContentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
public class ContentController {
    private final ContentRepository contentRepository;

    @Autowired
    public ContentController(ContentRepository contentRepository) {
        this.contentRepository = contentRepository;
    }


    public List<GraphContent> getAllContent() {
        return contentRepository.findAll();
    }


    public GraphContent getContentById(Long id) {
        Optional<GraphContent> result = contentRepository.findById(id);
        return result.orElse(null);
    }

    public List<GraphContent> getContentByDate(LocalDate date) {
        return contentRepository.findByCreationDateBetween(
                date.atStartOfDay(), date.plusDays(1).atStartOfDay()
        );
    }


    public GraphContent createContent(GraphContent graphContent) {
        return contentRepository.save(graphContent);
    }


    public GraphContent updateGraphContent(Long contentId, GraphContent content) {
        if (contentRepository.findById(contentId).orElse(null) != null) {
            return contentRepository.save(content);
        }
        return null;
    }

    public void deleteGraphContent(Long id) {
        if (contentRepository.findById(id).orElse(null) != null) {
            contentRepository.deleteById(id);
        }
    }
}
