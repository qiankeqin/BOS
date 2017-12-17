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

	//分页数据
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
	 * 将指定对象转化为json，并响应到前台
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
	 * 将list转成json字符串
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
