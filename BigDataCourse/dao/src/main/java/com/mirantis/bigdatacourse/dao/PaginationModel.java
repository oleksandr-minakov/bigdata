package com.mirantis.bigdatacourse.dao;

import java.io.Serializable;
import java.util.List;

public class PaginationModel implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Book> books;
    private int firstBook;
    private int lastBook;
    private int numberOfRecords;

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setFirstBook(int firstBook) {
        this.firstBook = firstBook;
    }

    public int getFirstBook() {
        return firstBook;
    }

    public void setLastBook(int lastBook) {
        this.lastBook = lastBook;
    }

    public int getLastBook() {
        return lastBook;
    }

    public void setNumberOfRecords(int numberOfRecords) {
        this.numberOfRecords = numberOfRecords;
    }

    public int getNumberOfRecords() {
       	return numberOfRecords;
    }
}
