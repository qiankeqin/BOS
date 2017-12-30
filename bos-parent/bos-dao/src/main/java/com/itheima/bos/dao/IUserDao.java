package com.itheima.bos.dao;

import com.itheima.bos.dao.base.IBaseDao;
import com.itheima.bos.domain.User;

public interface IUserDao extends IBaseDao<User> {

	/**
	 * 根据用户名和密码获取用户
	 * @param username
	 * @param password
	 * @return
	 */
	User findUserByUsernameAndPassword(String username, String password);

	/**
	 * 根据用户名获取到用户
	 * @param username
	 * @return
	 */
	User findUserByUsername(String username);
	
}
