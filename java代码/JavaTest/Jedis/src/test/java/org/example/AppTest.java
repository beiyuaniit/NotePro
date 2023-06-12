package org.example;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Set;

/**
 * Unit test for simple App.
 */
public class AppTest {
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() {
        Set<HostAndPort> set=new HashSet<>();
        set.add(new HostAndPort("192.168.10.104",6379));

        JedisCluster jedisCluster=new JedisCluster(set);

        String s = jedisCluster.get("k1");
        System.out.println(s);
        jedisCluster.close();
        assertTrue(true);
    }
}
