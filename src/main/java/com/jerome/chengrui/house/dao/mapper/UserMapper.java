package com.jerome.chengrui.house.dao.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import com.jerome.chengrui.house.model.User;

public interface UserMapper {

	@Insert("INSERT INTO user(openID, sessionKey, unionID) VALUES(#{openID}, #{sessionKey}, #{unionID})")
	public void insertUser(User user);

	@Select("SELECT COUNT(*) FROM user WHERE openID = #{openID}")
	public int userExists(String openID);
}
