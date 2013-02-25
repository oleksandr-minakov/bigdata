package com.mirantis.aminakov.bigdatacourse.dao;

import java.io.IOException;
import java.io.InputStream;

public class Book {
	
	private int id;
	
	private String title;
	
	private String author;
	
	private String genre;
	
	private InputStream text;
	
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
	public InputStream getText() {
		return text;
	}
	public void setText(InputStream text) {
		this.text = text;
	}
	
	public String getReadbleText() throws IOException{
		
		byte[] newText = new byte[this.text.available()];
		this.text.read(newText);
		return new String(newText);
	}
	
	public void newBook(String title, String author, String genre, InputStream text){
		
		this.title=title;this.text=text;
		this.author=author;this.genre=genre;
	}
	
}