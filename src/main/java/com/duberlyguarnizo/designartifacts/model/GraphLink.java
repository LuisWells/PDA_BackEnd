package com.duberlyguarnizo.designartifacts.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class GraphLink {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long linkId;
    @NotNull
    private String path;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_graph_id")
    private GraphDefinition graphDefinition;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_content_id")
    private GraphContent graphContent;
    @Column(columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    private LocalDateTime creationDate;
}
