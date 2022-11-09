package com.duberlyguarnizo.designartifacts.controller;

import com.duberlyguarnizo.designartifacts.model.Comment;
import com.duberlyguarnizo.designartifacts.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SiteController {
    private static final Logger logger = LoggerFactory.getLogger(SiteController.class);

    private final EmailService emailService;

    public SiteController(EmailService emailService) {
        this.emailService = emailService;
    }

    @RequestMapping("/")
    public String index(Model model) {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/admin")
    public String adminPanel(Model model) {
        return "admin";
    }

    @PostMapping(path = "/comentarios", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public String processComments(Comment comment) {
        logger.warn("--------------------------------------------");
        logger.warn(comment.toString());
        logger.warn("--------------------------------------------");

        String emailFrom = comment.getEmail();
        String emailContent = comment.getMessage();
        String emailSubject = comment.getName();
        emailService.send(emailFrom, "duberlygfr@gmail.com", emailSubject, emailContent);
        return "redirect:index"; //TODO:create web "redirect:/comment/thanks";
    }
}
