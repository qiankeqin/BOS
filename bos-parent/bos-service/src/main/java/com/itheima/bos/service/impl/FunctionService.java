package com.itheima.bos.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.IFunctionDao;
import com.itheima.bos.domain.Function;
import com.itheima.bos.domain.User;
import com.itheima.bos.service.IFunctionService;
import com.itheima.bos.utils.BOSUtils;
import com.itheima.bos.utils.PageBean;

@Service("functionService")
@Transactional
public class FunctionService implements IFunctionService{
	@Autowired
	private IFunctionDao functionDao;
	
	/**
	 * 获取所有的父节点权限
	 */
	@Override
	public List<Function> findAll() {
		List<Function> list = functionDao.findAll();
		return list;
	}

	/**
	 * 添加function权限
	 */
	@Override
	public void save(Function model) {
		functionDao.save(model);
	}

	/**
	 * 分页查询
	 */
	@Override
	public void pageQuery(PageBean pageBean) {
		functionDao.pageQuery(pageBean);
	}

	/**
	 * 获取父功能菜单
	 */
	@Override
	public List<Function> findTopAll() {

		List<Function> list = functionDao.findTopAll();
		return list;
	}

	/**
	 * 根据登陆用户获取显示菜单
	 */
	@Override
	public List<Function> findMenu() {
		User user = BOSUtils.getLoginUser();
		List<Function> list = null;
		if(user.getUsername().equals("admin")){
			//如果是超级管理员，查询所有菜单
			list = functionDao.findAllMenu();
		}
		else{
			list = functionDao.findMenuByUserId(user.getId());
		}
		return list;
	}

}
