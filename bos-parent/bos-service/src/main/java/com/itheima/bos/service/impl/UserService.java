package com.itheima.bos.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.IUserDao;
import com.itheima.bos.domain.Role;
import com.itheima.bos.domain.User;
import com.itheima.bos.service.IUserService;
import com.itheima.bos.utils.MD5Utils;
import com.itheima.bos.utils.PageBean;

@Service("userService")
@Transactional
public class UserService implements IUserService {
	@Autowired
	private IUserDao userDao;
	
	@Override
	public User login(User user) {
		//ʹ��MD5���ܷ���
		String password = MD5Utils.md5(user.getPassword());
		User existsUser = userDao.findUserByUsernameAndPassword(user.getUsername(),password);
		return existsUser;
	}

	@Override
	public void editPassword(String id, String password) {
		password = MD5Utils.md5(password);
		userDao.executeUpdate("user.editpassword", password,id);
	}

	/**
	 * �����û�
	 */
	@Override
	public void add(User model, String[] roleIds) {
		//�������
		model.setPassword(MD5Utils.md5(model.getPassword()));
		userDao.save(model);
		if(roleIds!=null){
			for(int i=0;i<roleIds.length;i++){
				//�ֶ������йܶ���
				Role role = new Role(roleIds[i]);
				//�û����������ɫ����
				model.getRoles().add(role);
			}
		}
	}

	/**
	 * �û���ҳ��ѯ
	 */
	@Override
	public void pageQuery(PageBean pageBean) {
		userDao.pageQuery(pageBean);
	}


}
