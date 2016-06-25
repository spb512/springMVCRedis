package com.pudp.bae.dao.redis;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.pudp.bae.base.RedisGeneratorDao;
import com.pudp.bae.model.Member;

@Repository(value = "memberDao")
public class MemberDaoImpl extends RedisGeneratorDao<String, Member> implements MemberDao {
	/**
	 * 添加对象
	 */
	@Override
	public boolean add(Member member) {
		redisTemplate.execute(new RedisCallback<Boolean>() {

			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				RedisSerializer<String> redisSerializer = getRedisSerializer();
				byte[] key = redisSerializer.serialize(member.getId());
				byte[] name = redisSerializer.serialize(member.getNickname());
				return connection.setNX(key, name);
			}
		});
		return false;
	}

	/**
	 * 添加集合
	 */
	@Override
	public boolean add(List<Member> list) {
		Assert.notEmpty(list);
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				RedisSerializer<String> redisSerializer = getRedisSerializer();
				for (Member member : list) {
					byte[] key = redisSerializer.serialize(member.getId());
					byte[] name = redisSerializer.serialize(member.getNickname());
					connection.setNX(key, name);
				}
				return true;
			}
		}, false, true);
		return result;
	}

	/**
	 * 删除对象 ,依赖key
	 */
	@Override
	public void delete(String key) {
		List<String> list = new ArrayList<String>();
		list.add(key);
		delete(list);
	}

	/**
	 * 删除集合 ,依赖key集合
	 */
	public void delete(List<String> keys) {
		redisTemplate.delete(keys);
	}

	/**
	 * 修改对象
	 */
	public boolean update(final Member member) {
		String key = member.getId();
		if (get(key) == null) {
			throw new NullPointerException("数据行不存在, key = " + key);
		}
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				RedisSerializer<String> serializer = getRedisSerializer();
				byte[] key = serializer.serialize(member.getId());
				byte[] name = serializer.serialize(member.getNickname());
				connection.set(key, name);
				return true;
			}
		});
		return result;
	}

	/**
	 * 根据key获取对象
	 */
	@Override
	public Member get(String keyId) {
		Member result = redisTemplate.execute(new RedisCallback<Member>() {
			public Member doInRedis(RedisConnection connection) throws DataAccessException {
				RedisSerializer<String> serializer = getRedisSerializer();
				byte[] key = serializer.serialize(keyId);
				byte[] value = connection.get(key);
				if (value == null) {
					return null;
				}
				String nickname = serializer.deserialize(value);
				return new Member(keyId, nickname);
			}
		});
		return result;
	}

}
