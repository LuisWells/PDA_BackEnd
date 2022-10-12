package com.duberlyguarnizo.designartifacts.controller;

import com.duberlyguarnizo.designartifacts.model.GraphContent;
import com.duberlyguarnizo.designartifacts.repository.ContentRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("api/content")
public class ContentController {
    private final ContentRepository contentRepository;

    public ContentController(ContentRepository contentRepository) {
        this.contentRepository = contentRepository;
    }

    @GetMapping("/all")
    public List<GraphContent> getAllContent() {
        return contentRepository.findAll();
    }

    @GetMapping("/id/{id}")
    public GraphContent getContentById(@PathVariable("id") Long id) {
        Optional<GraphContent> result = contentRepository.findById(id);
        return result.orElse(null);
    }

    @GetMapping("/date/{date}")
    public List<GraphContent> getContentByDate(@PathVariable("date") LocalDate date) {
        return contentRepository.findByCreationDateBetween(
                date.atStartOfDay(), date.plusDays(1).atStartOfDay()
        );
    }
    @PostMapping("/create")
    public GraphContent createContent(@RequestBody GraphContent graphContent) {
        return contentRepository.save(graphContent);
    }
}
