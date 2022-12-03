package com.duberlyguarnizo.designartifacts.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class GraphDefinition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long graphId;

    @Column(unique = true)
    @NotBlank
    private String name;

    private String version;

    @Column(columnDefinition = "TEXT")
    @NotBlank
    private String svg;

    @CreatedBy
    @Column(updatable = false)
    private String creationAdmin;

    @LastModifiedBy
    private String updateAdmin;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime creationDate;

    @UpdateTimestamp
    private LocalDateTime updateDate;
}
