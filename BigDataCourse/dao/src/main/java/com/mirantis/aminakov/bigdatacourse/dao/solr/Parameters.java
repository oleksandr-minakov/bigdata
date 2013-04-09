package com.mirantis.aminakov.bigdatacourse.dao.solr;

public class Parameters {

    final String URL;

    public int bookId;

    public Parameters(String url) {
        URL = url;
        this.bookId = 0;
    }
}
