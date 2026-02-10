package com.github.repository.searcher.service;

import com.github.repository.searcher.dto.GithubRepoDto;
import com.github.repository.searcher.entity.RepositoryDetails;
import com.github.repository.searcher.repository.RepositoryInterface;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RepositorySearchServiceTest {

    @Mock
    private GithubClientService githubClientService;

    @Mock
    private RepositoryInterface repositoryInterface;

    @InjectMocks
    private RepositorySearchService searchService;

    @Test
    public void testSearchAndSave_NewRepository() {
        // 1. Prepare Mock Data (What GitHub "returns")
        GithubRepoDto mockDto = new GithubRepoDto();
        mockDto.setId(100L);
        mockDto.setName("Test-Repo");
        mockDto.setLanguage("Java");
        mockDto.setStars(50L);

        // 2. Define Behavior
        // When we ask GitHub for data, return our mock list
        when(githubClientService.searchRepositories("test", "Java", "stars"))
                .thenReturn(List.of(mockDto));

        // When we check DB for ID 100, return empty (simulating it's new)
        when(repositoryInterface.findById(100L)).thenReturn(Optional.empty());

        // 3. Execute the Service Method
        searchService.searchAndSave("test", "Java", "stars");

        // 4. Verify the Results
        // Verify that save() was called exactly once
        verify(repositoryInterface, times(1)).save(any(RepositoryDetails.class));
    }

    @Test
    public void testSearchAndSave_ExistingRepository_UpdatesData() {
        // 1. Prepare Mock Data
        GithubRepoDto mockDto = new GithubRepoDto();
        mockDto.setId(100L);
        mockDto.setName("Updated-Name"); // GitHub has a new name
        mockDto.setStars(100L);

        RepositoryDetails existingEntity = new RepositoryDetails();
        existingEntity.setId(100L);
        existingEntity.setName("Old-Name"); // DB has old name

        // 2. Define Behavior
        when(githubClientService.searchRepositories("test", "Java", "stars"))
                .thenReturn(List.of(mockDto));

        // When checking DB, return the EXISTING entity
        when(repositoryInterface.findById(100L)).thenReturn(Optional.of(existingEntity));

        // 3. Execute
        searchService.searchAndSave("test", "Java", "stars");

        // 4. Verify
        // Verify save was called
        verify(repositoryInterface, times(1)).save(existingEntity);
        // Verify the name was actually updated to "Updated-Name"
        assert(existingEntity.getName().equals("Updated-Name"));
    }
}