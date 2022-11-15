package com.duberlyguarnizo.designartifacts.controller;

import com.duberlyguarnizo.designartifacts.config.PdaPasswordEncoder;
import com.duberlyguarnizo.designartifacts.model.*;
import com.duberlyguarnizo.designartifacts.repository.AdminRepository;
import com.duberlyguarnizo.designartifacts.repository.ContentRepository;
import com.duberlyguarnizo.designartifacts.repository.GraphRepository;
import com.duberlyguarnizo.designartifacts.repository.LinkRepository;
import com.duberlyguarnizo.designartifacts.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SiteController {
    private static final Logger logger = LoggerFactory.getLogger(SiteController.class);

    private final EmailService emailService;
    private final AdminRepository adminRepository;
    private final ContentRepository contentRepository;
    private final GraphRepository graphRepository;
    private final LinkRepository linkRepository;
    private final PdaPasswordEncoder pdaPasswordEncoder;

    @Autowired
    public SiteController(EmailService emailService, AdminRepository adminRepository, ContentRepository contentRepository, GraphRepository graphRepository, LinkRepository linkRepository, PdaPasswordEncoder pdaPasswordEncoder) {
        this.emailService = emailService;
        this.adminRepository = adminRepository;
        this.contentRepository = contentRepository;
        this.graphRepository = graphRepository;
        this.linkRepository = linkRepository;
        this.pdaPasswordEncoder = pdaPasswordEncoder;
    }

    @RequestMapping("/")
    public String index(Model model) {

        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
        //TODO: add last login date to current user
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
        return "redirect:thanks";
    }

    @GetMapping("/thanks")
    public String thanks() {
        return "thanks";
    }

    @GetMapping("/admin/users")
    public String adminUsers(@ModelAttribute Admin admin, Model model) {
        model.addAttribute("users", adminRepository.findAll());
        model.addAttribute("admin", admin);
        return "admin/users";
    }

    @GetMapping("/admin/contents")
    public String adminUsers(@ModelAttribute GraphContent content, Model model) {
        model.addAttribute("contents", contentRepository.findAll());
        return "admin/contents";
    }

    @GetMapping("/admin/graphs")
    public String adminUsers(@ModelAttribute GraphDefinition graphDefinition, Model model) {
        model.addAttribute("graphs", graphRepository.findAll());
        return "admin/graphs";
    }

    @GetMapping("/admin/links")
    public String adminUsers(@ModelAttribute GraphLink graphLink, Model model) {
        model.addAttribute("links", linkRepository.findAll());
        return "admin/links";
    }

    @GetMapping("/sample-user")
    public String sampleUser() {
        Admin testAdmin = new Admin();
        testAdmin.setEmail("luisdaniel@gmail.com");
        testAdmin.setPasswordHash(
                pdaPasswordEncoder
                        .bCryptPasswordEncoder()
                        .encode("luis"));
        testAdmin.setName("Luis Daniel");
        testAdmin.setActive(true);
        System.out.println(testAdmin.toString());
        testAdmin = adminRepository.save(testAdmin);
        System.out.println("Sample user created!!!!!");
        System.out.println(testAdmin.toString());
        return "redirect:index";
    }
}
