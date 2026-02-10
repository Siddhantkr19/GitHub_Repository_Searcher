package com.github.repository.searcher.service;

import com.github.repository.searcher.dto.GithubRepoDto;
import com.github.repository.searcher.entity.RepositoryDetails;
import com.github.repository.searcher.repository.RepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RepositorySearchService {

    @Autowired
    private GithubClientService githubClientService;

    @Autowired
    private RepositoryInterface repositoryInterface;

    @Transactional
    public void searchAndSave(String query, String language, String sort) {
        // 1. Fetch data from GitHub API
        List<GithubRepoDto> githubResults = githubClientService.searchRepositories(query, language, sort);

        // 2. Loop through and save/update each repo
        for (GithubRepoDto dto : githubResults) {
            saveOrUpdate(dto);
        }
    }

    private void saveOrUpdate(GithubRepoDto dto) {
        // Check if exists in DB
        Optional<RepositoryDetails> existingOpt = repositoryInterface.findById(dto.getId());

        RepositoryDetails repo;
        if (existingOpt.isPresent()) {
            // UPDATE: Use existing entity
            repo = existingOpt.get();
        } else {
            // INSERT: Create new entity
            repo = new RepositoryDetails();
            repo.setId(dto.getId());
        }

        // Map fields (Always update these to keep data fresh)
        repo.setName(dto.getName());
        repo.setDescription(dto.getDescription());
        repo.setOwnerName(dto.getOwnerName());
        repo.setLanguage(dto.getLanguage());
        repo.setStarsCount(dto.getStars());
        repo.setForksCount(dto.getForks());
        repo.setLastUpdated(dto.getLastUpdated()); // from GitHub

        // Save to DB
        repositoryInterface.save(repo);
    }
}