package com.mirantis.aminakov.bigdatacourse;

public class Book {
	private int id;
	private String title;
	private String author;
	private String genre;
	private String text;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
}