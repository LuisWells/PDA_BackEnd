package com.duberlyguarnizo.designartifacts.controller;

import com.duberlyguarnizo.designartifacts.model.Visit;
import com.duberlyguarnizo.designartifacts.repository.VisitRepository;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.util.List;

@Controller
public class VisitController {
    private final VisitRepository visitRepository;

    public VisitController(VisitRepository visitRepository) {
        this.visitRepository = visitRepository;
    }


    public List<Visit> getAllVisits() {
        return visitRepository.findAll();
    }


    public Visit getVisitById(Long id) {
        return visitRepository.findById(id).orElse(null);
    }


    public Long getTotalVisitorsDownloadedGraphs() {
        return visitRepository.countByClickedDownloadGraphIsTrue();
    }


    public Long getTotalVisitorsGeneratedOutput() {
        return visitRepository.countByClickedGenerateOutputIsTrue();
    }


    public Long getTotalVisitorsSelectedGraphType() {
        return visitRepository.countByClickedSelectGraphTypeIsTrue();
    }


    public Long getTotalVisitorsSelectedOutput() {
        return visitRepository.countByClickedSelectOutputIsTrue();
    }


    public Long getTotalVisitorsCopiedShareLink() {
        return visitRepository.countByCopiedShareLinkIsTrue();
    }


    public Long getTotalVisitorsFromShareLink() {
        return visitRepository.countByFromShareLinkIsTrue();
    }


    public Long getTotalVisitorsTypedGraphContent() {
        return visitRepository.countByTypedGraphContentIsTrue();
    }


    public Long getTotalVisitorsHadInteractions() {
        return visitRepository.countByHadInteractionsIsTrue();
    }


    public Long getTotalVisitorsWereAdmin() {
        return visitRepository.countByWasAdminIsTrue();
    }


    public Long getTotalVisitorsByGraphId(Long graphId) {
        return visitRepository.countByGraphLink_GraphContent_ContentId(graphId);
    }


    public List<Visit> getVisitsInDateRange(LocalDate start, LocalDate end) {
        return visitRepository.findByVisitDateTimeBetween(
                start.atStartOfDay(),
                end.plusDays(1)
                        .atStartOfDay());
    }


    public Visit createVisit(Visit visit) {
        return visitRepository.save(visit);
    }


    public Visit updateGraphContent(Long visitId, Visit visit) {
        if (visitRepository.findById(visitId).orElse(null) != null) {
            return visitRepository.save(visit);
        }
        return null;
    }


    public void deleteGraphContent(Long id) {
        if (visitRepository.findById(id).orElse(null) != null) {
            visitRepository.deleteById(id);
        }
    }

}
