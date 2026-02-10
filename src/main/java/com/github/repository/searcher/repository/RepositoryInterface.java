package com.github.repository.searcher.repository;

import com.github.repository.searcher.entity.RepositoryDetails;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepositoryInterface extends JpaRepository<RepositoryDetails, Long> {

    @Query("SELECT r FROM RepositoryDetails r WHERE " +
            "(:language IS NULL OR r.language = :language) AND " +
            "(:minStars IS NULL OR r.starsCount >= :minStars)")
    List<RepositoryDetails> findRepositories(
            @Param("language") String language,
            @Param("minStars") Long minStars,
            Sort sort
    );
}