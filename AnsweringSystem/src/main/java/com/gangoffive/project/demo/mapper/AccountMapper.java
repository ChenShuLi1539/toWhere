package com.gangoffive.project.demo.mapper;

import com.gangoffive.project.demo.entity.Account;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface AccountMapper {
    void regist (Account account);
    Account getPlayerByAccount (String account);
    Account getPlayerByName (String name);
    Account login (String account,String password);
}
