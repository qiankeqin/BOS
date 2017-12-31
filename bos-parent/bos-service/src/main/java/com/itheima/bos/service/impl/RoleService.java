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
	 * ������ɫ
	 */
	@Override
	public void add(Role model,String functionIds) {
		// TODO Auto-generated method stub
		roleDao.save(model);
		//�ж��Ƿ��й���
		if(StringUtils.isNotBlank(functionIds)){
			String[] fIds = functionIds.split(",");
			for(String functionId : fIds){
				//����Ȩ�޶���
				Function func = new Function(functionId);
				//Ϊ��ɫ���Ȩ��
				model.getFunctions().add(func);
			}
		}
	}
	
	/**
	 * ��ҳ��ѯ����
	 */
	@Override
	public void pageQuery(PageBean pageBean) {
		roleDao.pageQuery(pageBean);
	}

	/**
	 * ��ȡ���еĽ�ɫ����
	 */
	@Override
	public List<Role> findAll() {
		List<Role> list = roleDao.findAll();
		return list;
	}

}
