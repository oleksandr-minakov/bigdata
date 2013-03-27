package com.mirantis.aminakov.bigdatacourse.dao;

import java.io.*;

public class Book implements Serializable {
	
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
	
	public String getReadableText() throws IOException{
		byte[] newText = new byte[this.text.available()];
		this.text.read(newText);
		return new String(newText);
	}
	
	public void newBook(String title, String author, String genre, InputStream text){
		this.title=title;this.text=text;
		this.author=author;this.genre=genre;
	}

    @Override
    public String toString() {
        try {
            return id + " " + title + " " + author + " " + genre + " " + this.getReadableText();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return " ";
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        id = in.readInt();
        title = in.readUTF();
        author = in.readUTF();
        genre = in.readUTF();
        String temp = (String) in.readObject();
        text = new ByteArrayInputStream(temp.getBytes("UTF-8"));
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.writeInt(id);
        out.writeUTF(title);
        out.writeUTF(author);
        out.writeUTF(genre);
        out.writeObject(this.getReadableText());
    }
}