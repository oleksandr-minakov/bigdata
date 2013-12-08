package com.mirantis.bigdatacourse.dao.memcached;

import net.spy.memcached.MemcachedClient;
import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;

import static org.junit.Assert.assertNotNull;

public class GetConnectionTest {

    @Test
    public void getConnectionTest() throws IOException {
        MemcachedClient client = new MemcachedClient(new InetSocketAddress("localhost" , 11211));
        assertNotNull(client);
    }
}
