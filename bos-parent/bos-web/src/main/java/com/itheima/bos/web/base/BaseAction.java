package com.itheima.bos.web.base;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class BaseAction<T> extends ActionSupport implements ModelDriven<T>{

	protected final static String LIST = "list";
	//ģ�Ͷ���
	protected T model;
	
	@Override
	public T getModel() {
		// TODO Auto-generated method stub
		return model;
	}
		
	//�ڹ��췽���ж�̬��ȡʵ�����ͣ�ͨ�����䴴��model����
	public BaseAction(){
		ParameterizedType genericSuperclass = (ParameterizedType) this.getClass().getGenericSuperclass();
		//���BaseAction�������ķ�������
		Type[] actualTypeArguments = genericSuperclass.getActualTypeArguments();
		//���䴴��model����
		Class<T> entityClass = (Class<T>) actualTypeArguments[0]; 
		try {
			model = entityClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
