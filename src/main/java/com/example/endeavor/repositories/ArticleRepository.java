package com.example.endeavor.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.endeavor.entities.Article;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long>{
	
	List<Article> findByUserId (Long userId);
	Optional<Article> findByIdAndUserId(Long id, Long userId);

}
