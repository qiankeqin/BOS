package com.itheima.bos.web.base;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class BaseAction<T> extends ActionSupport implements ModelDriven<T>{

	protected final static String LIST = "list";
	//模型对象
	protected T model;
	
	@Override
	public T getModel() {
		// TODO Auto-generated method stub
		return model;
	}
		
	//在构造方法中动态获取实体类型，通过反射创建model对象
	public BaseAction(){
		ParameterizedType genericSuperclass = (ParameterizedType) this.getClass().getGenericSuperclass();
		//获得BaseAction上声明的泛型数组
		Type[] actualTypeArguments = genericSuperclass.getActualTypeArguments();
		//反射创建model对象
		Class<T> entityClass = (Class<T>) actualTypeArguments[0]; 
		try {
			model = entityClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
