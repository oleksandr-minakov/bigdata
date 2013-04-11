package com.mirantis.aminakov.bigdatacourse.dao.solr;

import com.mirantis.aminakov.bigdatacourse.dao.NAS.DaoNAS;

public class Parameters {

    final String URL;

    public int bookId;
    DaoNAS daoNAS;

    public Parameters(String url, DaoNAS daoNAS) {
        this.URL = url;
        this.daoNAS = daoNAS;
        this.bookId = 0;
    }
}
