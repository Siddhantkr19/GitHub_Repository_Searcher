package com.github.repository.searcher.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Map;

@Data
public class GithubRepoDto {

    private Long id;
    private String name;
    private String description;
    private String language;

    @JsonProperty("stargazers_count")
    private Long stars;

    @JsonProperty("forks_count")
    private Long forks;

    @JsonProperty("updated_at")
    private LocalDateTime lastUpdated;

    @JsonProperty("owner")
    private void unpackOwner(Map<String, Object> owner) {
        this.ownerName = (String) owner.get("login");
    }

    private String ownerName;
}