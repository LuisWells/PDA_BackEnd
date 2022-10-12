package com.duberlyguarnizo.designartifacts.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
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
    @Column(columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    private LocalDateTime visitDateTime;
    private double duration;
    private boolean cookieExists;
    private LocalDateTime lastVisitDateTime;
    //Using IpRegistry free tier service in client side
    private String country;
    private String city;
    private String browser;
    private String browserVersion;
    private String device;
    private String os;

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
    @OneToOne
    @JoinColumn(name = "fk_link_id")
    @ToString.Exclude
    private GraphLink graphLink;

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
