package com.taotao.rest.jedis;

import java.util.HashSet;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

public class JedisTest {

	@Test
	public void testJedis(){
		
		Jedis jedis = new Jedis("192.168.244.128",6379);
		jedis.set("key2", "nihao");
		String string = jedis.get("key2");
		System.out.println(string);
		jedis.close();
	}
	
	@Test
	public void testJedisPool(){
		//创建连接池
		JedisPool pool = new JedisPool("192.168.244.128",6379);
		//从连接池中获得对象
		Jedis jedis = pool.getResource();
		jedis.set("key1", "zhangsan");
		String string = jedis.get("key1");
		System.out.println(string);
		//关闭对象
		jedis.close();
		pool.close();
	}
	
	//测试集群
	@Test
	public void testJedisCluster(){
		//设置连接节点
		HashSet<HostAndPort> nodes = new HashSet<>();
		nodes.add(new HostAndPort("192.168.244.128", 7001));
		nodes.add(new HostAndPort("192.168.244.128", 7002));
		nodes.add(new HostAndPort("192.168.244.128", 7003));
		nodes.add(new HostAndPort("192.168.244.128", 7004));
		nodes.add(new HostAndPort("192.168.244.128", 7005));
		nodes.add(new HostAndPort("192.168.244.128", 7006));
		//连接
		JedisCluster cluster = new JedisCluster(nodes);
		//测试值
		cluster.set("key2", "1000");
		String string = cluster.get("key2");
		System.out.println(string);
		cluster.close();
	}
	
	//整合spring单机版测试
	@Test
	public void testSpringJedisSingle(){
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
		JedisPool pool = (JedisPool) applicationContext.getBean("redisClient");
		Jedis jedis = pool.getResource();
		String string = jedis.get("key1");
		System.out.println(string);
		jedis.close();
		pool.close();
	}
	
	//整合spring集群版测试
		@Test
		public void testSpringJedisCluster(){
			ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
			JedisCluster jedisCluster = (JedisCluster) applicationContext.getBean("redisClient");
			String string = jedisCluster.get("key2");
			System.out.println(string);
			jedisCluster.close();
		}
}
