package com.weixin.gacl.mapping;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.weixin.gacl.domain.Admin;

public interface UserMapperI {

	//使用@Insert注解指明add方法要执行的SQL
    @Insert("insert into wx_admin(username, password) values(#{username}, #{password})")
    public int add(Admin user);
    
    //使用@Delete注解指明deleteById方法要执行的SQL
    @Delete("delete from wx_admin where username=#{username}")
    public int deleteById(int id);
    
    //使用@Update注解指明update方法要执行的SQL
    @Update("update wx_admin set password=#{password} where username=#{username}")
    public int update(Admin user);
    
    //使用@Select注解指明getById方法要执行的SQL
    @Select("select * from wx_admin where username=#{username}")
    public Admin getById(int id);
    
    //使用@Select注解指明getAll方法要执行的SQL
    @Select("select * from wx_admin")
    public List<Admin> getAll();
}
