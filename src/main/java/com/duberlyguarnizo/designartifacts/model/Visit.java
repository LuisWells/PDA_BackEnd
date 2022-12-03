package com.duberlyguarnizo.designartifacts.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class Visit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long visitId;

    private double duration;

    //Using IpRegistry free tier service in client side
    private String country;
    private String city;
    private String browser;
    private String browserVersion;
    private String device;
    private String os;

    private int visitCount;

    private boolean cookieExists;
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

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime visitDateTime;

    private LocalDateTime lastVisitDateTime;
}
