package com.duberlyguarnizo.designartifacts.controller;

import com.duberlyguarnizo.designartifacts.model.Visit;
import com.duberlyguarnizo.designartifacts.repository.VisitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("api/visit")
public class VisitController {
    @Autowired
    private VisitRepository visitRepository;

    @GetMapping("all")
    public List<Visit> getAllVisits() {
        return visitRepository.findAll();
    }

    @GetMapping("/id/{id}")
    public Visit getVisitById(@PathVariable("id") Long id) {
        return visitRepository.findById(id).orElse(null);
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
        return visitRepository.countByLink_Graph_GraphId(graphId);
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
