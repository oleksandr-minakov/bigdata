package com.mirantis.bigdatacourse.dao.solr;

import com.mirantis.bigdatacourse.dao.Book;
import com.mirantis.bigdatacourse.dao.DaoException;
import com.mirantis.bigdatacourse.dao.FSMapping;
import org.apache.solr.common.SolrDocument;

import java.io.IOException;

public class BookUtils {

    public static Book map(SolrDocument document, FSMapping daoNAS) throws IOException, DaoException {
        Book book = new Book();
        book.setId(document.get("id").toString());
        book.setTitle(document.get("title").toString());
        book.setAuthor(document.get("author").toString());
        book.setGenre(document.get("genre").toString());
        book.setText(daoNAS.readFile(book.getId()));
        return book;
    }
}
