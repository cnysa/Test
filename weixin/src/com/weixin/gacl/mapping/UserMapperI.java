package com.weixin.gacl.mapping;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.weixin.gacl.domain.Admin;

public interface UserMapperI {

	//ʹ��@Insertע��ָ��add����Ҫִ�е�SQL
    @Insert("insert into wx_admin(username, password) values(#{username}, #{password})")
    public int add(Admin user);
    
    //ʹ��@Deleteע��ָ��deleteById����Ҫִ�е�SQL
    @Delete("delete from wx_admin where username=#{username}")
    public int deleteById(int id);
    
    //ʹ��@Updateע��ָ��update����Ҫִ�е�SQL
    @Update("update wx_admin set password=#{password} where username=#{username}")
    public int update(Admin user);
    
    //ʹ��@Selectע��ָ��getById����Ҫִ�е�SQL
    @Select("select * from wx_admin where username=#{username}")
    public Admin getById(int id);
    
    //ʹ��@Selectע��ָ��getAll����Ҫִ�е�SQL
    @Select("select * from wx_admin")
    public List<Admin> getAll();
}
