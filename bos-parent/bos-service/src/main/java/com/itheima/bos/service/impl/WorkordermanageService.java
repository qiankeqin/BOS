package com.itheima.bos.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.IWorkordermanageDao;
import com.itheima.bos.domain.Workordermanage;
import com.itheima.bos.service.IWorkordermanageService;
import com.itheima.bos.utils.PageBean;

/**
 * ������Service
 * @author qiankeqin
 *
 */
@Service("workordermanageService")
@Transactional
public class WorkordermanageService implements IWorkordermanageService{
	@Autowired
	private IWorkordermanageDao workordermanageDao;

	/**
	 * ���������湤����
	 */
	@Override
	public void add(Workordermanage model) {
		workordermanageDao.insert(model);
	}

	@Override
	public void getList(PageBean pageBean) {
		workordermanageDao.pageQuery(pageBean);
	}
	
}
