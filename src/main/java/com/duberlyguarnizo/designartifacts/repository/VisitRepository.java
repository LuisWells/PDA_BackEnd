package com.duberlyguarnizo.designartifacts.repository;

import com.duberlyguarnizo.designartifacts.model.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface VisitRepository extends JpaRepository<Visit, Long> {
    List<Visit> findByVisitDateTimeBetween(LocalDateTime start, LocalDateTime end);

    long countByVisitDateTimeBetween(LocalDateTime start, LocalDateTime end);

    long countByClickedDownloadGraphIsTrue();

    long countByClickedGenerateOutputIsTrue();

    long countByClickedSelectGraphTypeIsTrue();

    long countByClickedSelectOutputIsTrue();

    long countByCopiedShareLinkIsTrue();

    long countByFromShareLinkIsTrue();

    long countByTypedGraphContentIsTrue();

    long countByHadInteractionsIsTrue();

    long countByWasAdminIsTrue();

    long countByGraphLink_GraphContent_ContentId(Long graphId);
}
