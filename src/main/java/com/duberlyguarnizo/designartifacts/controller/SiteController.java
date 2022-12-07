package com.duberlyguarnizo.designartifacts.controller;

import com.duberlyguarnizo.designartifacts.config.AdminUserDetail;
import com.duberlyguarnizo.designartifacts.config.PdaPasswordEncoder;
import com.duberlyguarnizo.designartifacts.model.Admin;
import com.duberlyguarnizo.designartifacts.model.Comment;
import com.duberlyguarnizo.designartifacts.model.GraphContent;
import com.duberlyguarnizo.designartifacts.model.GraphDefinition;
import com.duberlyguarnizo.designartifacts.repository.*;
import com.duberlyguarnizo.designartifacts.service.EmailService;
import com.duberlyguarnizo.designartifacts.service.SvgService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
public class SiteController {
    private static final Logger logger = LoggerFactory.getLogger(SiteController.class);

    private final EmailService emailService;
    private final AdminRepository adminRepository;
    private final ContentRepository contentRepository;
    private final GraphRepository graphRepository;
    private final LinkRepository linkRepository;
    private final VisitRepository visitRepository;
    private final PdaPasswordEncoder pdaPasswordEncoder;
    private final SvgService svgService;

    @Autowired
    public SiteController(EmailService emailService, AdminRepository adminRepository, ContentRepository contentRepository, GraphRepository graphRepository, LinkRepository linkRepository, VisitRepository visitRepository, PdaPasswordEncoder pdaPasswordEncoder, SvgService sgvService) {
        this.emailService = emailService;
        this.adminRepository = adminRepository;
        this.contentRepository = contentRepository;
        this.graphRepository = graphRepository;
        this.linkRepository = linkRepository;
        this.visitRepository = visitRepository;
        this.pdaPasswordEncoder = pdaPasswordEncoder;
        this.svgService = sgvService;
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

    @GetMapping("/admin-proxy")
    public String adminProxy() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object principal = auth.getPrincipal();
        AdminUserDetail userDetails = (AdminUserDetail) principal;
        adminRepository.findById(userDetails.getId()).ifPresent(admin -> {
            admin.setLastLoginDate(LocalDateTime.now());
            adminRepository.save(admin);
            logger.info("Last login date set for admin login attempt for admin: {}", admin.getEmail());
            logger.info(admin.getLastLoginDate().toString());
        });
        return "redirect:admin";
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
    public String adminContents(@ModelAttribute GraphContent content, Model model) {
        model.addAttribute("contents", contentRepository.findAll(Sort.by(Sort.Direction.ASC, "contentId")));
        return "admin/contents";
    }

    @GetMapping("/admin/graphs")
    public String adminGraphs(@ModelAttribute GraphDefinition graphDefinition, Model model) {
        model.addAttribute("graphs", graphRepository.findAll(Sort.by(Sort.Direction.ASC, "graphId")));
        return "admin/graphs";
    }

    @GetMapping("/admin/links")
    public String adminLinks(Model model) {
        model.addAttribute("links", linkRepository.findAll(Sort.by(Sort.Direction.DESC, "creationDate")));
        return "admin/links";
    }

    @GetMapping("/admin/visits")
    public String adminVisits(Model model) {
        long totalVisitsToday = visitRepository.countByVisitDateTimeBetween(LocalDateTime.now().withHour(0).withMinute(0).withSecond(0), LocalDateTime.now());
        long totalVisits = visitRepository.count();
        long totalVisitsDownloadedGraphs = visitRepository.countByClickedDownloadGraphIsTrue();
        long totalVisitsGeneratedOutput = visitRepository.countByClickedGenerateOutputIsTrue();
        long totalVisitsSelectedGraphType = visitRepository.countByClickedSelectGraphTypeIsTrue();
        long totalVisitsSelectedOutput = visitRepository.countByClickedSelectOutputIsTrue();
        long totalVisitsCopiedShareLink = visitRepository.countByCopiedShareLinkIsTrue();
        long totalVisitsFromShareLink = visitRepository.countByFromShareLinkIsTrue();
        long totalVisitsTypedGraphContent = visitRepository.countByTypedGraphContentIsTrue();
        long totalVisitsHadInteractions = visitRepository.countByHadInteractionsIsTrue();
        long totalVisitsWasAdmin = visitRepository.countByWasAdminIsTrue();
        model.addAttribute("vToday", totalVisitsToday);
        model.addAttribute("vTotal", totalVisits);
        model.addAttribute("vDownloadedGraphs", totalVisitsDownloadedGraphs);
        model.addAttribute("vGeneratedOutput", totalVisitsGeneratedOutput);
        model.addAttribute("vSelectedGraphType", totalVisitsSelectedGraphType);
        model.addAttribute("vSelectedOutput", totalVisitsSelectedOutput);
        model.addAttribute("vCopiedShareLink", totalVisitsCopiedShareLink);
        model.addAttribute("vFromShareLink", totalVisitsFromShareLink);
        model.addAttribute("vTypedGraphContent", totalVisitsTypedGraphContent);
        model.addAttribute("vHadInteractions", totalVisitsHadInteractions);
        model.addAttribute("vWasAdmin", totalVisitsWasAdmin);
        return "admin/visits";
    }

    @GetMapping("/artifact")
    public String getArtifact(@RequestParam String path, Model model) {
        String svg = svgService.getSvgByPath(path);
        model.addAttribute("svgArtifact", svg);
        return "artifact";
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
        adminRepository.save(testAdmin);
        logger.warn("Sample user created!!!");
        return "redirect:index";
    }
}
