package com.itheima.bos.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.IFunctionDao;
import com.itheima.bos.domain.Function;
import com.itheima.bos.service.IFunctionService;
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

	@Override
	public List<Function> findTopAll() {

		List<Function> list = functionDao.findTopAll();
		return list;
	}

}
