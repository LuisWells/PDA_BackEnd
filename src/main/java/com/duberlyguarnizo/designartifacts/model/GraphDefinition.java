package com.duberlyguarnizo.designartifacts.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class GraphDefinition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long graphId;
    @NotNull
    private String name;
    private String version;
    @Column(columnDefinition = "TEXT")
    private String svg;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_creation_admin_id")
    @ToString.Exclude
    private Admin creationAdmin;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_update_admin_id")
    @ToString.Exclude
    private Admin updateAdmin;
    @Column(columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    private LocalDateTime creationDate;
    @Column(columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime updateDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        GraphDefinition graphDefinition = (GraphDefinition) o;
        return graphId != null && Objects.equals(graphId, graphDefinition.graphId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
