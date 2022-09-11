package com.duberlyguarnizo.designartifacts.controller;

import com.duberlyguarnizo.designartifacts.model.Content;
import com.duberlyguarnizo.designartifacts.repository.ContentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

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
        return contentRepository.findById(id).get();
    }

    public List<Content> getContentByDate(LocalDate date) {
        return contentRepository.getContentByCreationDate(date);
    }
}
