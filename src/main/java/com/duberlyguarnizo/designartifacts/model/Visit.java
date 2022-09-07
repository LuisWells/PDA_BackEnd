package com.duberlyguarnizo.designartifacts.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class Visit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long visitId;
    private String ip;
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime visitDateTime;
    private double duration;
    private boolean cookieExists;
    private LocalDateTime lastVisitDateTime;
    private String userAgent;
    private int visitCount;
    private boolean wasAdmin;
    private boolean hadInteractions;
    private boolean clickedSelectGraphType;
    private boolean typedGraphContent;
    private boolean clickedSelectOutput;
    private boolean clickedGenerateOutput;
    private boolean copiedShareLink;
    private boolean clickedDownloadGraph;
    private boolean fromShareLink;
    private Long linkId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Visit visit = (Visit) o;
        return visitId != null && Objects.equals(visitId, visit.visitId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
