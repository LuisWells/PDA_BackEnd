package com.duberlyguarnizo.designartifacts.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class Link {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long linkId;
    private String path;
    @ManyToOne
    @JoinColumn(name = "fk_graph_id")
    private Graph graph;
    @OneToOne
    @JoinColumn(name = "fk_content_id")
    private Content content;
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime creationDate;
}
