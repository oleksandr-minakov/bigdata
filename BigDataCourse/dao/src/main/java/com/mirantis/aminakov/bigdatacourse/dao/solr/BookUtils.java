package com.mirantis.aminakov.bigdatacourse.dao.solr;

import com.mirantis.aminakov.bigdatacourse.dao.Book;
import org.apache.solr.common.SolrDocument;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class BookUtils {

    public static Book map(SolrDocument document) {
        Book book = new Book();
        book.setId(Integer.parseInt(document.get("id").toString()));
        book.setTitle(document.get("title").toString());
        book.setAuthor(document.get("author").toString());
        book.setGenre(document.get("genre").toString());
        book.setText(new ByteArrayInputStream((document.get("text")).toString().getBytes()));
        return book;
    }

    public static TreeSet<String> pagination(int pageNum, int pageSize, TreeSet<String> set) {
        List<String> list = new ArrayList<>(set);
        List<String> resultList = new ArrayList<String>();
        if (list.size() > pageSize * pageNum) {
            resultList = list.subList((pageNum - 1) * pageSize, pageNum * pageSize);
        } else if (list.size() > pageSize * (pageNum - 1) && pageNum * pageSize >= list.size()) {
            resultList = list.subList((pageNum - 1) * pageSize, list.size());
        } else if (list.size() < pageSize && list.size() <= pageNum * pageSize) {
            resultList = list.subList(0, list.size());
        } else if (list.size() == 0) {
            resultList = list;
        }
        return new TreeSet<String>(resultList);
    }
}
