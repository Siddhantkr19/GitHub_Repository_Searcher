package com.github.repository.searcher.service;

import com.github.repository.searcher.dto.GithubRepoDto;
import com.github.repository.searcher.dto.GithubSearchResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.List;

@Service
public class GithubClientService {

    @Autowired
    private RestTemplate restTemplate;

    // Base URL for GitHub API
    private final String GITHUB_API_URL = "https://api.github.com/search/repositories";

    public List<GithubRepoDto> searchRepositories(String query, String language, String sort) {

        String finalQuery = query;
        if (language != null && !language.isEmpty()) {
            finalQuery += "+language:" + language;
        }

        // 2. Build the full URL
        String url = UriComponentsBuilder.fromHttpUrl(GITHUB_API_URL)
                .queryParam("q", finalQuery)
                .queryParam("sort", sort) // "stars", "forks", or "updated"
                .queryParam("order", "desc") // High to Low
                .toUriString();

        // 3. Make the call
        try {
            GithubSearchResponse response = restTemplate.getForObject(url, GithubSearchResponse.class);

            if (response != null && response.getItems() != null) {
                return response.getItems();
            }
        } catch (Exception e) {

            System.err.println("Error fetching data from GitHub: " + e.getMessage());

        }

        return Collections.emptyList();
    }
}