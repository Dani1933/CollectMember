package com.api.parking.service;

import com.api.parking.model.MemberModel;

public interface RedisSvc {

    void putIfAbsen(MemberModel product);

    void put(Long id, MemberModel product);

    Object hashGet();

    Object getById(Long id);

    void delete(Long id);
}
