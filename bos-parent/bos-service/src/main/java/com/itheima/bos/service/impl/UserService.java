package com.itheima.bos.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.IUserDao;
import com.itheima.bos.domain.User;
import com.itheima.bos.service.IUserService;
import com.itheima.bos.utils.MD5Utils;

@Service("userService")
@Transactional
public class UserService implements IUserService {
	@Autowired
	private IUserDao userDao;
	
	@Override
	public User login(User user) {
		//使用MD5加密方法
		String password = MD5Utils.md5(user.getPassword());
		User existsUser = userDao.findUserByUsernameAndPassword(user.getUsername(),password);
		return existsUser;
	}

	@Override
	public void editPassword(String id, String password) {
		// TODO Auto-generated method stub
		password = MD5Utils.md5(password);
		userDao.executeUpdate("user.editpassword", password,id);
	}


}
