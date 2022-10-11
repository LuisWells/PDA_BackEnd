package com.duberlyguarnizo.designartifacts.controller;

import com.duberlyguarnizo.designartifacts.model.Content;
import com.duberlyguarnizo.designartifacts.repository.ContentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("api/content")
public class ContentController {
    @Autowired
    private ContentRepository contentRepository;

    @GetMapping("/all")
    public List<Content> getAllContent() {
        return contentRepository.findAll();
    }

    @GetMapping("/id/{id}")
    public Content getContentById(@PathVariable("id") Long id) {
        Optional<Content> result = contentRepository.findById(id);
        return result.orElse(null);
    }

    @GetMapping("/date/{date}")
    public List<Content> getContentByDate(@PathVariable("date") LocalDate date) {
        return contentRepository.findByCreationDateBetween(
                date.atStartOfDay(), date.plusDays(1).atStartOfDay()
        );
    }
    @PostMapping("/create")
    public Content createContent(@RequestBody Content content) {
        return contentRepository.save(content);
    }
}
