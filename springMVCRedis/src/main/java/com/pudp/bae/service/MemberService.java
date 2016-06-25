package com.pudp.bae.service;

import javax.annotation.Resource;

import com.pudp.bae.dao.redis.MemberDao;
import com.pudp.bae.model.Member;

public class MemberService {
	@Resource(name="memberDao")
    private MemberDao memberDao;
     
    public void setMemberDao(MemberDao memberDao)
    {
       this.memberDao = memberDao;
    }
      
    public void add(Member member){
        memberDao.add(member);
    }
     
    public void delete(String id){
        memberDao.delete(id);
    }
     
    public Member get(String id)
    {
        return memberDao.get(id);
    }
}
