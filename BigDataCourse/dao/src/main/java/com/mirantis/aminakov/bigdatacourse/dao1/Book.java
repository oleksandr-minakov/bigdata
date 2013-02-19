package com.mirantis.aminakov.bigdatacourse.dao1;

import java.io.IOException;
import java.io.InputStream;

public class Book {

	public String getReadbleText() throws IOException{
		
		byte[] newText = new byte[this.text.available()];
		this.text.read(newText);
		return new String(newText);
	}
	
	public void newBook(int id, String title, String author, String genre, InputStream text){
		
		this.id=id;this.title=title;this.text=text;
		this.author=author;this.genre=genre;
	}
	
	private int id;
	private String title;
	private String author;
	private String genre;
	private InputStream text;

	public int getId() {
		return this.id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getTitle() {
		return this.title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getAuthor() {
		return this.author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	
	public String getGenre() {
		return this.genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	
	public InputStream getText() {
		return this.text;
	}
	public void setText(InputStream text) {
		this.text = text;
	}

}