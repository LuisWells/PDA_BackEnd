package com.duberlyguarnizo.designartifacts.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Link {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long linkId;
    private String path;
    @OneToOne
    @JoinColumn(name = "graphId")
    private Graph graph;
    @OneToOne
    @JoinColumn(name = "contentId")
    private Content content;
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime creationDate;
}
