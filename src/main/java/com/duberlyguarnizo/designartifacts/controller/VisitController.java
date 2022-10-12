package com.duberlyguarnizo.designartifacts.controller;

import com.duberlyguarnizo.designartifacts.model.Visit;
import com.duberlyguarnizo.designartifacts.repository.VisitRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("api/visit")
public class VisitController {
    private final VisitRepository visitRepository;

    public VisitController(VisitRepository visitRepository) {
        this.visitRepository = visitRepository;
    }

    @GetMapping("all")
    public List<Visit> getAllVisits() {
        return visitRepository.findAll();
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Visit> getVisitById(@PathVariable("id") Long id) {
        Visit result = visitRepository.findById(id).orElse(null);
        if (result == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("downloaded-graphs")
    public long getTotalVisitorsDownloadedGraphs() {
        return visitRepository.countByClickedDownloadGraphIsTrue();
    }

    @GetMapping("generated-output")
    public long getTotalVisitorsGeneratedOutput() {
        return visitRepository.countByClickedGenerateOutputIsTrue();
    }

    @GetMapping("selected-graph-type")
    public long getTotalVisitorsSelectedGraphType() {
        return visitRepository.countByClickedSelectGraphTypeIsTrue();
    }

    @GetMapping("selected-output")
    public long getTotalVisitorsSelectedOutput() {
        return visitRepository.countByClickedSelectOutputIsTrue();
    }

    @GetMapping("copied-share-link")
    public long getTotalVisitorsCopiedShareLink() {
        return visitRepository.countByCopiedShareLinkIsTrue();
    }

    @GetMapping("from-share-link")
    public long getTotalVisitorsFromShareLink() {
        return visitRepository.countByFromShareLinkIsTrue();
    }

    @GetMapping("typed-graph-content")
    public long getTotalVisitorsTypedGraphContent() {
        return visitRepository.countByTypedGraphContentIsTrue();
    }

    @GetMapping("had-interactions")
    public long getTotalVisitorsHadInteractions() {
        return visitRepository.countByHadInteractionsIsTrue();
    }

    @GetMapping("were-admins")
    public long getTotalVisitorsWereAdmin() {
        return visitRepository.countByWasAdminIsTrue();
    }

    @GetMapping("graph/{graphId}")
    public long getTotalVisitorsByGraphId(@PathVariable("graphId") Long graphId) {
        return visitRepository.countByGraphLink_GraphContent_ContentId(graphId);
    }

    @GetMapping("date/{start}/{end}")
    public List<Visit> getVisitsInDateRange(@PathVariable("start") LocalDate start, @PathVariable("end") LocalDate end) {
        return visitRepository.findByVisitDateTimeBetween(start.atStartOfDay(), end.plusDays(1).atStartOfDay());
    }

    @PostMapping("/create")
    public Visit createVisit(@RequestBody Visit visit) {
        return visitRepository.save(visit);
    }

}
