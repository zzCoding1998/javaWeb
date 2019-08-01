package com.zz.jedis;

import org.junit.Test;

import com.zz.utils.JedisPoolUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisTest {

	@Test
	public void jedisTest() {
		Jedis jedis = new Jedis("192.168.241.131", 6379);
		jedis.set("username", "zhangsan");
		String username = jedis.get("username");
		System.out.println(username);
		jedis.close();
	}
	
	@Test
	public void jedisPoolTest() {
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		poolConfig.setMaxIdle(30);
		poolConfig.setMinIdle(10);
		poolConfig.setMaxTotal(50);
		
		JedisPool pool = new JedisPool(poolConfig,"192.168.241.131",6379);
		Jedis jedis = pool.getResource();
		String username = jedis.get("username");
		System.out.println(username);
		pool.close();
	}
	
	@Test
	public void JedisPoolUtilTest() {
		Jedis jedis = JedisPoolUtil.getJedis();
		String username = jedis.get("username");
		System.out.println(username);
	}
	
}
