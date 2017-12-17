package com.itheima.bos.web.base;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;

import com.itheima.bos.domain.Region;
import com.itheima.bos.utils.PageBean;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

public class BaseAction<T> extends ActionSupport implements ModelDriven<T>{

	protected final static String LIST = "list";

	//��ҳ����
	protected PageBean pageBean = new PageBean();
	protected int page;
	protected int rows;
	protected DetachedCriteria criteria = null;

	public void setPage(int page) {
		pageBean.setCurrentPage(page);
	}

	public void setRows(int rows) {
		pageBean.setPageSize(rows);
	}
	
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
		criteria = DetachedCriteria.forClass(entityClass);
		pageBean.setDetachedCriteria(criteria);
		try {
			model = entityClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * ��ָ������ת��Ϊjson������Ӧ��ǰ̨
	 * @param o
	 * @param excludes
	 * @throws IOException 
	 */
	public void java2Json(Object o,String[] excludes) throws IOException{
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(excludes);
		String jsonStr = JSONObject.fromObject(o,jsonConfig).toString();
		
		ServletActionContext.getResponse().setContentType("text/json;charset=utf-8");
		ServletActionContext.getResponse().getWriter().write(jsonStr);
	}
	
	/**
	 * ��listת��json�ַ���
	 * @param list
	 * @param excludes
	 * @throws IOException
	 */
	public void java2Json(List list,String[] excludes) throws IOException{
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(excludes);
		String jsonStr = JSONArray.fromObject(list,jsonConfig).toString();
		
		ServletActionContext.getResponse().setContentType("text/json;charset=utf-8");
		ServletActionContext.getResponse().getWriter().write(jsonStr);
		
	}
	
}
