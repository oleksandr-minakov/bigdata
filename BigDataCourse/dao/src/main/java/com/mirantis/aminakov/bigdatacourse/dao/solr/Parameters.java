package com.mirantis.aminakov.bigdatacourse.dao.solr;

import com.mirantis.aminakov.bigdatacourse.dao.NAS.NASMapping;

public class Parameters {

    final String URL;

    public int bookId;
    NASMapping daoNAS;

    public Parameters(String url, NASMapping daoNAS) {
        this.URL = url;
        this.daoNAS = daoNAS;
        this.bookId = 0;
    }
}
