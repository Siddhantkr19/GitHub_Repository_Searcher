package com.github.repository.searcher.repository;

import com.github.repository.searcher.entity.RepositoryDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryInterface extends JpaRepository<RepositoryDetails, Long> {

}