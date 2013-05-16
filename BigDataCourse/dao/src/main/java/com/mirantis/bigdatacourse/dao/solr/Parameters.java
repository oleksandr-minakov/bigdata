package com.mirantis.bigdatacourse.dao.solr;

import com.mirantis.bigdatacourse.dao.NAS.NASMapping;
import org.springframework.beans.factory.annotation.Value;

public class Parameters {

    @Value("#{properties.solr_url}")
    public String URL;

    public NASMapping daoNAS;

    public void setDaoNAS(NASMapping daoNAS) {
        this.daoNAS = daoNAS;
    }
}
