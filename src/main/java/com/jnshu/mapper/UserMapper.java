package com.jnshu.mapper;

import com.jnshu.pojo.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface UserMapper {
    @Insert("insert into user_7 (username,password,phone,picture,email) values (#{username},#{password},#{phone},#{picture},#{email})")
    void doInsert(User user);

    @Delete("delete from user_7 where id = #{id}")
    void doDelete(int id);

    @Update("update user_7 set username= #{username},phone=#{phone},picture= #{picture},email=#{email}")
    void doUpdate(User user);


    @Select("select * from user_7 where id =#{id}")
    User findById(int id);

    @Select("select * from user_7 where username =#{username}")
    User findByName(String username);

    @Select("select * from user_7")
    List<User> findAll();
}
