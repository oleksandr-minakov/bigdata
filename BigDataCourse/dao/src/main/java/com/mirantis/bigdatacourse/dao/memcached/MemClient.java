package com.mirantis.bigdatacourse.dao.memcached;

import com.mirantis.bigdatacourse.dao.DaoException;
import net.spy.memcached.MemcachedClient;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.InetSocketAddress;

public class MemClient {

    private MemcachedClient client;
    public static final Logger LOG = Logger.getLogger(MemClient.class);

    public MemClient(InetSocketAddress memcachedAddress) throws DaoException {
        try {
            client = new MemcachedClient(memcachedAddress);
        } catch (IOException e) {
            throw new DaoException(e);
        }
    }

    public void set(String key, int ttl, Object o) {
        client.set(key, ttl, o);
        LOG.info("Add " + key + " to cache");
    }

    public Object get(String key) {
        Object object = client.get(key);
        LOG.info("GET " + key + " from cache");
        if(object == null) {
            LOG.info("Miss for key: " + key);
        } else {
            LOG.info("Hit for key: " + key);
        }
        return object;
    }

    public Object delete(String key) {
        LOG.info("Delete " + key + " from cache");
        return client.delete(key);
    }

    public void close() {
        client.flush();
        client.shutdown();
    }
}
