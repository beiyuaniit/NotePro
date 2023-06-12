package org.example;

import redis.clients.jedis.Jedis;

import java.util.Set;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ) {
        Jedis jedis=new Jedis("192.168.10.104",6379);
        String res=jedis.ping();
        System.out.println(res);

        Set<String> keys =jedis.keys("*");
        keys.forEach(System.out::println);
        jedis.close();
    }
}
