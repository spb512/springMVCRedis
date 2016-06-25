package com.pudp.test;

import redis.clients.jedis.Jedis;

public class TestRedis {
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		Jedis jedis = new Jedis("127.0.0.1",6379);
		jedis.set("key001", "redis001");
		String value = jedis.get("key001");
		System.out.println(value);
//		 js.set("key001", "redis001"); 
//         String val = js.get("key001"); 
//         System.out.println(val); 
//         js.del("key001");
			
	}

}