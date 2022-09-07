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
public class Graph {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long graphId;
    private String name;
    private String version;
    private String json;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_creation_user_id")
    @ToString.Exclude
    private Admin creationAdmin;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_update_user_id")
    @ToString.Exclude
    private Admin updateAdmin;
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime creationDate;
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime updateDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Graph graph = (Graph) o;
        return graphId != null && Objects.equals(graphId, graph.graphId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
