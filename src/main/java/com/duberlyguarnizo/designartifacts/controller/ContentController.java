package com.duberlyguarnizo.designartifacts.controller;

import com.duberlyguarnizo.designartifacts.model.Content;
import com.duberlyguarnizo.designartifacts.repository.ContentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("api/content")
public class ContentController {
    @Autowired
    private ContentRepository contentRepository;

    public List<Content> getAllContent() {
        return contentRepository.findAll();
    }

    public Content getContentById(Long id) {
        Optional<Content> result = contentRepository.findById(id);
        return result.orElse(null);
    }

    public List<Content> getContentByDate(LocalDate date) {
        return contentRepository.findByCreationDateBetween(date.atStartOfDay(), date.plusDays(1).atStartOfDay());
    }
}
