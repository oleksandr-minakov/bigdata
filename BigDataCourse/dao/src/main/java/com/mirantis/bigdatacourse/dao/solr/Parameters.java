package com.mirantis.bigdatacourse.dao.solr;

import com.mirantis.bigdatacourse.dao.NAS.NASMapping;

public class Parameters {

    protected String URL;
    protected int bookId = 0;
    protected NASMapping daoNAS;

    public void setURL(String URL) {
        this.URL = URL;
    }

    public void setDaoNAS(NASMapping daoNAS) {
        this.daoNAS = daoNAS;
    }
}
