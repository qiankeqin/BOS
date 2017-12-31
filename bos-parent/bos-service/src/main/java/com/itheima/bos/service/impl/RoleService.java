package com.itheima.bos.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.IRoleDao;
import com.itheima.bos.domain.Function;
import com.itheima.bos.domain.Role;
import com.itheima.bos.service.IRoleService;
import com.itheima.bos.utils.PageBean;

@Service("roleService")
@Transactional
public class RoleService implements IRoleService {

	@Autowired
	private IRoleDao roleDao;
	/**
	 * 新增角色
	 */
	@Override
	public void add(Role model,String functionIds) {
		// TODO Auto-generated method stub
		roleDao.save(model);
		//判断是否有功能
		if(StringUtils.isNotBlank(functionIds)){
			String[] fIds = functionIds.split(",");
			for(String functionId : fIds){
				//创建权限对象
				Function func = new Function(functionId);
				//为角色添加权限
				model.getFunctions().add(func);
			}
		}
	}
	
	/**
	 * 分页查询方法
	 */
	@Override
	public void pageQuery(PageBean pageBean) {
		roleDao.pageQuery(pageBean);
	}

	/**
	 * 获取所有的角色数据
	 */
	@Override
	public List<Role> findAll() {
		List<Role> list = roleDao.findAll();
		return list;
	}

}
