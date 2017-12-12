package com.itheima.bos.dao.base.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import com.itheima.bos.dao.base.IBaseDao;

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

}
