package com.duberlyguarnizo.designartifacts.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_creation_admin_id")
    @ToString.Exclude
    private Admin creationAdmin;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_update_admin_id")
    @ToString.Exclude
    private Admin updateAdmin;

    @CreationTimestamp
    private LocalDateTime creationDate;

    @UpdateTimestamp
    private LocalDateTime updateDate;
}
