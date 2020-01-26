package main;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

public final class RedisConnection {

	private static RedissonClient redissonClient;

	public RedisConnection() {

	}

	public RedissonClient getRedissonClient() {
		if (redissonClient == null) {
			synchronized (this) {
				if (redissonClient == null) {
					Config config = new Config();
					config.useSingleServer().setAddress("redis://127.0.0.1:6379");
					redissonClient = Redisson.create(config);
				}
			}
		}
		return redissonClient;
	}
}
