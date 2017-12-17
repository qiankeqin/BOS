package com.itheima.bos.service.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.ISubareaDao;
import com.itheima.bos.domain.Subarea;
import com.itheima.bos.service.ISubareaService;
import com.itheima.bos.utils.PageBean;

@Service("subareaService")
@Transactional
public class SubareaService implements ISubareaService{

	@Autowired
	private ISubareaDao subareaDao;
	
	/**
	 * 保存数据
	 */
	@Override
	public void save(Subarea model) {
		subareaDao.save(model);
	}

	/**
	 * 分页查询
	 */
	@Override
	public void pageQuery(PageBean pageBean) {
		// TODO Auto-generated method stub
		subareaDao.pageQuery(pageBean);
	}

	/**
	 * 获取所有的分区
	 */
	@Override
	public List<Subarea> findAll() {
		// TODO Auto-generated method stub
		return subareaDao.findAll();
	}

	/**
	 * 获取所有未关联的分区
	 */
	@Override
	public List<Subarea> findByNotAssociation() {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Subarea.class);
		//添加过滤条件，分区对象中decidedzone属性未null
		detachedCriteria.add(Restrictions.isNull("decidedzone"));
		return subareaDao.findByCriteria(detachedCriteria);
	}
	
}
