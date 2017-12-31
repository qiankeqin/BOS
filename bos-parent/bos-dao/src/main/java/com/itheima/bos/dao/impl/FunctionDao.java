package com.itheima.bos.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.itheima.bos.dao.IFunctionDao;
import com.itheima.bos.dao.base.impl.BaseDaoImpl;
import com.itheima.bos.domain.Function;

@Repository("functionDao")
public class FunctionDao extends BaseDaoImpl<Function> implements IFunctionDao{
	/**
	 * ��дFunctionDao�е�findAll��������ȡ��Function�Ķ���Ԫ��
	 * ������Function�е�childrenΪ��������
	 */
	public List<Function> findAll(){
		String hql = "from Function f";
		List<Function> list = (List<Function>) this.getHibernateTemplate().find(hql);
		return list;
	}

	@Override
	public List<Function> findTopAll() {
		String hql = "from Function f  where f.parentFunction is NULL";
		List<Function> list = (List<Function>) this.getHibernateTemplate().find(hql);
		return list;
	}

	//�����û�Id��ѯ��Ӧ���û�Ȩ��
	@Override
	public List<Function> findFunctionListByUserId(String id) {
		String hql = "select distinct f from Function f LEFT OUTER JOIN f.roles r LEFT OUTER JOIN r.users u WHERE u.id=?";
		List<Function> list = (List<Function>) this.getHibernateTemplate().find(hql, id);
		return list;
	}
}
