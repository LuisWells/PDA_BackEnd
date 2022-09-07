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
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String name;
    private String email;
    private String passwordHash;
    private boolean isActive;
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime creationDate;
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime updateDate;
    private LocalDateTime lastLoginDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Admin admin = (Admin) o;
        return userId != null && Objects.equals(userId, admin.userId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
