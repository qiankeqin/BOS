package com.itheima.bos.dao;

import com.itheima.bos.dao.base.IBaseDao;
import com.itheima.bos.domain.User;

public interface IUserDao extends IBaseDao<User> {

	/**
	 * �����û����������ȡ�û�
	 * @param username
	 * @param password
	 * @return
	 */
	User findUserByUsernameAndPassword(String username, String password);

	/**
	 * �����û�����ȡ���û�
	 * @param username
	 * @return
	 */
	User findUserByUsername(String username);
	
}
