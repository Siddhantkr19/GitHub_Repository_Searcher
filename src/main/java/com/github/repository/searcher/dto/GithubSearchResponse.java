package com.github.repository.searcher.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
public class GithubSearchResponse {

    @JsonProperty("total_count")
    private int totalCount;

    @JsonProperty("items")
    private List<GithubRepoDto> items;
}
