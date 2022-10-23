package com.api.parking.service;

import com.api.parking.config.RedisOperations;
import com.api.parking.model.MemberModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RedisSvcImpl implements RedisSvc {

    private static final String KEY = "member";
    private final RedisOperations redisOperations;

    @Autowired
    public RedisSvcImpl(RedisOperations redisOperations) {
        this.redisOperations = redisOperations;
    }

    @Override
    public void putIfAbsen(MemberModel member) {
        redisOperations.putIfAbsen(KEY, member.getId(), member);
    }

    @Override
    public void put(Long id, MemberModel member) {
        redisOperations.hset(KEY, id, member);
    }

    @Override
    public Object hashGet() {
        return redisOperations.hget(KEY);
    }

    @Override
    public Object getById(Long id) {
        return redisOperations.hget(KEY, id);
    }

    @Override
    public void delete(Long id) {
        redisOperations.deleteByHashKey(KEY, id);
    }
}