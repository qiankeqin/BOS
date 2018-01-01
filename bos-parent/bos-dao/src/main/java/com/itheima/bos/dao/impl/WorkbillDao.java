package com.itheima.bos.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.itheima.bos.dao.IWorkbillDao;
import com.itheima.bos.dao.base.impl.BaseDaoImpl;
import com.itheima.bos.domain.Workbill;

@Repository
public class WorkbillDao extends BaseDaoImpl<Workbill> implements IWorkbillDao{

	/**
	 * 获取新单数据
	 */
	@Override
	public List<Workbill> findNewWorkbills() {
		String hql = "From Workbill w where w.type='新单'";
		List<Workbill> list = (List<Workbill>) this.getHibernateTemplate().find(hql);
		return list;
	}

}
