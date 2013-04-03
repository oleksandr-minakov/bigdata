package com.mirantis.aminakov.bigdatacourse.dao.memcached;

import com.mirantis.aminakov.bigdatacourse.dao.Book;
import com.mirantis.aminakov.bigdatacourse.dao.DaoException;
import net.spy.memcached.MemcachedClient;
import org.apache.log4j.Logger;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

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
        LOG.info("Delete " + key + " from cache");
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

    public List<Book> pagination(int pageNum, int pageSize, List<Book> list) {
        List<Book> resultList = new ArrayList<Book>();
        if (list.size() > pageSize * pageNum) {
            resultList = list.subList((pageNum - 1) * pageSize, pageNum * pageSize);
        } else if (list.size() > pageSize * (pageNum - 1) && pageNum * pageSize >= list.size()) {
            resultList = list.subList((pageNum - 1) * pageSize, list.size());
        } else if (list.size() < pageSize && list.size() <= pageNum * pageSize) {
            resultList = list.subList(0, list.size());
        } else if (list.size() == 0) {
            resultList = list;
        }
        return resultList;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
	public TreeSet<String> pagination(int pageNum, int pageSize, TreeSet<String> set) {
        List list = new ArrayList<>(set);
        List resultList = new ArrayList<>();
        if (list.size() > pageSize * pageNum) {
            resultList = list.subList((pageNum - 1) * pageSize, pageNum * pageSize);
        } else if (list.size() > pageSize * (pageNum - 1) && pageNum * pageSize >= list.size()) {
            resultList = list.subList((pageNum - 1) * pageSize, list.size());
        } else if (list.size() < pageSize && list.size() <= pageNum * pageSize) {
            resultList = list.subList(0, list.size());
        } else if (list.size() == 0) {
            resultList = list;
        }
        TreeSet resultSet = new TreeSet(resultList);
        return resultSet;
    }
}
