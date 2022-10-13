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
public class GraphContent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long contentId;
    @Column(columnDefinition = "TEXT")
    private String contentJson;
    @Column(columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    private LocalDateTime creationDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        GraphContent graphContent = (GraphContent) o;
        return contentId != null && Objects.equals(contentId, graphContent.contentId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
