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

	//代表某个实体的类型
	private Class<T> entityClass;
	
	//注解在方法中的使用，工具类型注入spring工厂中的会话工厂sessionFactory
	@Resource
	public void setMySessionFactory(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}
	
	//那么如何获得entityClass的值
	//使用构造方法，构造方法被调用时就知道entityClass，及谁继承了。
	//在父类（BaseDaoImpl）的构造方法中动态获得entityClass
	public BaseDaoImpl(){
		//获取泛型类的子类，this值继承BaseDaoImpl<T>的子类
		ParameterizedType superclass = (ParameterizedType) this.getClass().getGenericSuperclass();
		//获得父类上声明的泛型数组，及<>中的T，在子类中BaseDaoImpl<User> 那么T就是User
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

	//queryName是执行的sql
	//objects是sql的参数
	@Override
	public void executeUpdate(String queryName, Object... objects) {
		 Session session = this.getSessionFactory().getCurrentSession();
		 //相当于session.createQuery("update xxxx");
		 Query query = session.getNamedQuery(queryName);
		 //赋值参数
		 int i = 0;
		 for(Object object:objects){
			 query.setParameter(i++, object);
		 }
		 //执行sql
		 query.executeUpdate();		 
	}

	@Override
	public void pageQuery(PageBean pageBean) {
		int pageSize = pageBean.getPageSize();
		int currentPage = pageBean.getCurrentPage();
		DetachedCriteria criteria = pageBean.getDetachedCriteria();
		this.getHibernateTemplate().findByCriteria(criteria);
		//查询总数据量total
		//指定hibernate框架发出sql的形式===》select count(*) from xxx
		criteria.setProjection(Projections.rowCount());
		List<Long> countlist = (List<Long>) this.getHibernateTemplate().findByCriteria(criteria);
		Long count = countlist.get(0);
		pageBean.setTotal(count);
		
		//查询rows---当前页需要展示的数据集合
		int firstResult = (currentPage-1)*pageSize;
		int maxResults = pageSize;  
		criteria.setProjection(null);//清空projectiion，这样，就不会以rowcount的形式查询了。
		List rows = this.getHibernateTemplate().findByCriteria(criteria, firstResult, maxResults);
		pageBean.setRows(rows);			
	}

}
