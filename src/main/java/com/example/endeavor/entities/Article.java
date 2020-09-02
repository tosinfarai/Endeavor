package com.example.endeavor.entities;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table (name = "articles")
public class Article {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private long id;
	
	@Column(name = "title", nullable = false)
	private String title;
	
	@Column(name = "content")
	private String content;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id")
	private User user;
	
	@Column(name = "date_time", updatable=false)
	@CreationTimestamp
	private LocalDateTime dateTime;
	
	public Article() {
		
	}
	
	public Article(long id, String title, String content,LocalDateTime dateTime) {
		super();
		this.id = id;
		this.title = title;
		this.content = content;
		this.dateTime = dateTime;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}	
	
	@Override
    public String toString() {
        return "Article [id=" + id + ", title=" + title + "]";
    }
	
	
}
	