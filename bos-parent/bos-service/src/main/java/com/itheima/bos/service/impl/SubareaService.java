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

	/**
	 * 根据定区id获取分区list
	 */
	@Override
	public List<Subarea> findListByDecidedzoneId(String decidedzoneId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Subarea.class);
		//添加过滤条件,注意：如果时join两张表的时候，需要用到别名
		//如果需要发送join请求，请求其他表中的数据，那么就需要添加表的别名
		//定区id，存在于分区中，所以此时不需要join连接，如果用到定区name查询，那么就需要使用别名了
		criteria.add(Restrictions.eq("decidedzone.id", decidedzoneId));
		List<Subarea> list = subareaDao.findByCriteria(criteria);
		return list;
	}

	/**
	 * 获取区域分区分布数据
	 */
	@Override
	public List<Object> findSubareaGroupByProvince() {
		List<Object> list = subareaDao.findSubareaGroupByProvince();
		return list;
	}
	
}
