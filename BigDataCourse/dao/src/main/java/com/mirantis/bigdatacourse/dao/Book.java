package com.mirantis.bigdatacourse.dao;

import java.io.*;

@SuppressWarnings("serial")
public class Book implements Serializable {
	
	private String id;
	private String title;
	private String author;
	private String genre;
	private byte[] text;

    public String getId() {
		return id;
	}

	public void setId(String id) {
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
		return new ByteArrayInputStream(text);
	}

	public void setText(InputStream text) {
        byte[] buffer = new byte[0];
        try {
            buffer = new byte[text.available()];
            text.read(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.text = buffer;
	}
	
	public String getReadableText() throws IOException {
		
        BufferedInputStream bis = new BufferedInputStream(this.getText());
        InputStreamReader isr = new InputStreamReader(bis, "UTF-8");
        BufferedReader bufferedReader = new BufferedReader(isr);
        StringBuilder inputStringBuilder = new StringBuilder();
        String line;
        line = bufferedReader.readLine();
        while(line != null) {
            inputStringBuilder.append(line);
            inputStringBuilder.append('\n');
            line = bufferedReader.readLine();
        }
		return inputStringBuilder.toString();
	}
	
	public void newBook(String title, String author, String genre, InputStream text) {

        this.id = "0";
        this.title = title;
        this.author = author;
        this.genre = genre;
        setText(text);
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
        
    	id = in.readUTF();
        title = in.readUTF();
        author = in.readUTF();
        genre = in.readUTF();
        String temp = (String) in.readObject();
        text = temp.getBytes("UTF-8"); //
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.writeUTF(id);
        out.writeUTF(title);
        out.writeUTF(author);
        out.writeUTF(genre);
        out.writeObject(this.getReadableText());
    }
}