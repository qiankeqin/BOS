package com.itheima.bos.dao.base.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import com.itheima.bos.dao.base.IBaseDao;
import com.itheima.bos.utils.PageBean;

public class BaseDaoImpl<T> extends HibernateDaoSupport implements IBaseDao<T>{

	//����ĳ��ʵ�������
	private Class<T> entityClass;
	
	//ע���ڷ����е�ʹ�ã���������ע��spring�����еĻỰ����sessionFactory
	@Resource
	public void setMySessionFactory(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}
	
	//��ô��λ��entityClass��ֵ
	//ʹ�ù��췽�������췽��������ʱ��֪��entityClass����˭�̳��ˡ�
	//�ڸ��ࣨBaseDaoImpl���Ĺ��췽���ж�̬���entityClass
	public BaseDaoImpl(){
		//��ȡ����������࣬thisֵ�̳�BaseDaoImpl<T>������
		ParameterizedType superclass = (ParameterizedType) this.getClass().getGenericSuperclass();
		//��ø����������ķ������飬��<>�е�T����������BaseDaoImpl<User> ��ôT����User
		Type[] actualTypeArguments = superclass.getActualTypeArguments();
		entityClass = (Class<T>) actualTypeArguments[0];
	}
	
	@Override
	public void save(T entity) {
		// TODO Auto-generated method stub
		getHibernateTemplate().save(entity);
	}

	@Override
	public void delete(T entity) {
		// TODO Auto-generated method stub
		getHibernateTemplate().delete(entity);
	}

	@Override
	public void update(T entity) {
		// TODO Auto-generated method stub
		getHibernateTemplate().update(entity);
	}

	@Override
	public T findById(Serializable id) {
		return getHibernateTemplate().get(entityClass, id);
	}

	@Override
	public List<T> findAll() {
		String hsql = "FROM "+entityClass.getSimpleName();
		return (List<T>) getHibernateTemplate().find(hsql);
	}

	//queryName��ִ�е�sql
	//objects��sql�Ĳ���
	@Override
	public void executeUpdate(String queryName, Object... objects) {
		 Session session = this.getSessionFactory().getCurrentSession();
		 //�൱��session.createQuery("update xxxx");
		 Query query = session.getNamedQuery(queryName);
		 //��ֵ����
		 int i = 0;
		 for(Object object:objects){
			 query.setParameter(i++, object);
		 }
		 //ִ��sql
		 query.executeUpdate();		 
	}

	@Override
	public void pageQuery(PageBean pageBean) {
		int pageSize = pageBean.getPageSize();
		int currentPage = pageBean.getCurrentPage();
		DetachedCriteria criteria = pageBean.getDetachedCriteria();
		this.getHibernateTemplate().findByCriteria(criteria);
		//��ѯ��������total
		//ָ��hibernate��ܷ���sql����ʽ===��select count(*) from xxx
		criteria.setProjection(Projections.rowCount());
		List<Long> countlist = (List<Long>) this.getHibernateTemplate().findByCriteria(criteria);
		Long count = countlist.get(0);
		pageBean.setTotal(count);
		
		//��ѯrows---��ǰҳ��Ҫչʾ�����ݼ���
		int firstResult = (currentPage-1)*pageSize;
		int maxResults = pageSize;  
		criteria.setProjection(null);//���projectiion���������Ͳ�����rowcount����ʽ��ѯ�ˡ�
		List rows = this.getHibernateTemplate().findByCriteria(criteria, firstResult, maxResults);
		pageBean.setRows(rows);			
	}

}
