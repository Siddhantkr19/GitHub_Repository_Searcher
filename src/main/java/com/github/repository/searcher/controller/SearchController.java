package com.github.repository.searcher.controller;

import com.github.repository.searcher.entity.RepositoryDetails;
import com.github.repository.searcher.service.RepositorySearchService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/github")
public class SearchController {

    @Autowired
    private RepositorySearchService searchService;

    // Request DTO class
    @Data
    static class SearchRequest {
        private String query;
        private String language;
        private String sort;
    }

    @PostMapping("/search")
    public ResponseEntity<?> searchAndSave(@RequestBody SearchRequest request) {
        // Validate input
        if (request.getQuery() == null || request.getQuery().trim().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Query parameter is required"));
        }

        // Call Service
        searchService.searchAndSave(request.getQuery(), request.getLanguage(), request.getSort());

        // Return Success Response
        return ResponseEntity.ok(Map.of(
                "message", "Repositories fetched and saved successfully",
                "searchCriteria", request
        ));
    }
    @GetMapping("/repositories")
    public ResponseEntity<?> getRepositories(
            @RequestParam(required = false) String language,
            @RequestParam(required = false) Long minStars,
            @RequestParam(required = false, defaultValue = "stars") String sort
    ) {

        List<RepositoryDetails> results = searchService.getStoredRepositories(language, minStars, sort);

        return ResponseEntity.ok(Map.of(
                "count", results.size(),
                "repositories", results
        ));
    }
}