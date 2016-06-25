package com.pudp.bae.dao.redis;

import java.util.List;

import com.pudp.bae.model.Member;

public interface MemberDao {
    boolean add(Member member);
 
    abstract boolean add(List<Member> list);
 
    void delete(String key);
 
    Member get(String keyId);
     
}