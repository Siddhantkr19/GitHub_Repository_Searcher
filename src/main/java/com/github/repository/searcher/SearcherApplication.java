package com.github.repository.searcher;

import com.github.repository.searcher.service.GithubClientService;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SearcherApplication {

	public static void main(String[] args) {
		SpringApplication.run(SearcherApplication.class, args);
	}
	@Bean
	public CommandLineRunner testGithubFetch(GithubClientService service) {
		return args -> {
			System.out.println("--- TESTING GITHUB FETCH ---");
			var results = service.searchRepositories("tetris", "assembly", "stars");
			results.forEach(repo -> System.out.println("Found: " + repo.getName() + " | Stars: " + repo.getStars()));
			System.out.println("--- END TEST ---");
		};
	}

}
