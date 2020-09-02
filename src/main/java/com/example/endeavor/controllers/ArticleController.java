package com.example.endeavor.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.endeavor.entities.Article;
import com.example.endeavor.exceptions.ResourceNotFoundException;
import com.example.endeavor.repositories.ArticleRepository;
import com.example.endeavor.repositories.UserRepository;

@RestController
@RequestMapping ("/users/{userId}")
public class ArticleController {
	
	@Autowired
	private ArticleRepository articleRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping ("/articles")
	public List<Article> getArticlesbyUserId(@PathVariable(value = "userId") Long userId) {
		return articleRepository.findByUserId(userId);
	}
	
	/*@GetMapping("/{id}")
	public ResponseEntity<Article> getArticleById(@PathVariable(value = "id") Long articleId)
		throws ResourceNotFoundException {
	        Article article = articleRepository.findById(articleId)
	          .orElseThrow(() -> new ResourceNotFoundException
	        		  ("article " + articleId + " not found"));
	        return ResponseEntity.ok().body(article);
	}*/
	
	@PostMapping("/articles")
	public Article createArticle (@PathVariable(value = "userId") Long userId,
			@Valid @RequestBody Article article) throws ResourceNotFoundException {
		return userRepository.findById(userId).map(user -> {
			article.setUser(user);
			return articleRepository.save(article);
		}).orElseThrow(() -> new ResourceNotFoundException
				("user not found"));
	}
	
	
	 @PutMapping("/articles/{articleId}")
	    public Article updateArticle(@PathVariable(value = "userId") Long userId,
	        @PathVariable(value = "articleId") Long articleId, @Valid @RequestBody Article articleRequest)
	    throws ResourceNotFoundException {
	        if (!userRepository.existsById(userId)) {
	            throw new ResourceNotFoundException("userId not found");
	        }

	        return articleRepository.findById(articleId).map(article -> {
	        	article.setTitle(articleRequest.getTitle());
	        	article.setContent(articleRequest.getContent());
	        	return articleRepository.save(article);
	        }).orElseThrow(() -> new ResourceNotFoundException ("article id not found"));
	        
	    }

	
	 /*@DeleteMapping("/articles/{articleId}")
	    public ResponseEntity << ? > deleteArticle(@PathVariable(value = "userId") Long userId,
	        @PathVariable(value = "articleId") Long articleId) 
	        		throws ResourceNotFoundException {
	        return articleRepository.findByIdAndUserId(articleId, userId).map(article - > {
	            articleRepository.delete(article);
	            return ResponseEntity.ok().build();
	        }).orElseThrow(() - > new ResourceNotFoundException(
	            "Course not found with id " + articleId + " and instructorId " + userId));
	    }*/
	
}
