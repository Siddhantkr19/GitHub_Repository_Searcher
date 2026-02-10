package com.github.repository.searcher.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "repositories")
public class RepositoryDetails {
    @Id
    @Column(name = "id", unique = true, nullable = false)
    private Long id;
    @Column(name = "name")
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "owner_name")
    private String ownerName;

    @Column(name = "language")
    private String language;

    @Column(name = "stars_count")
    private Long starsCount;

    @Column(name = "forks_count")
    private Long forksCount;

    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;
}
