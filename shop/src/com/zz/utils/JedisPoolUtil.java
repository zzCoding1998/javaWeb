package com.zz.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisPoolUtil {
	
	private static JedisPool pool = null;

	static {
		try {
			InputStream in = JedisPoolUtil.class.getClassLoader().getResourceAsStream("jedisPool.properties");
			Properties prop = new Properties();
			prop.load(in);
			JedisPoolConfig poolConfig = new JedisPoolConfig();
			poolConfig.setMinIdle(Integer.parseInt(prop.getProperty("minIdle")));
			poolConfig.setMaxIdle(Integer.parseInt(prop.getProperty("maxIdle")));
			poolConfig.setMaxTotal(Integer.parseInt(prop.getProperty("maxTotal")));
			pool = new JedisPool(poolConfig, prop.getProperty("host"),Integer.parseInt(prop.getProperty("port")));
		} catch (IOException e) {
			System.out.println("缺少配置文件...");
			e.printStackTrace();
		}
	}
	
	public static Jedis getJedis() {
		return pool.getResource();
	}
	
	public static JedisPool getJedisPool() {
		return pool;
	}
	
}
