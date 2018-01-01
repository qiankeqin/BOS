package com.itheima.bos.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.itheima.bos.dao.ISubareaDao;
import com.itheima.bos.dao.base.impl.BaseDaoImpl;
import com.itheima.bos.domain.Subarea;

@Repository("subareaDao")
public class SubareaDao extends BaseDaoImpl<Subarea> implements ISubareaDao{

	/**
	 * ��ȡ��������ֲ�����
	 */
	@Override
	public List<Object> findSubareaGroupByProvince() {
		String hql = "select r.province,count(*) from Subarea s LEFT OUTER JOIN s.region r GROUP BY r.province";
		return (List<Object>) this.getHibernateTemplate().find(hql);
	}
	
}
