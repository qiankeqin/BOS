package com.itheima.bos.web.action;

import java.io.IOException;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.Staff;
import com.itheima.bos.service.IStaffService;
import com.itheima.bos.utils.PageBean;
import com.itheima.bos.web.base.BaseAction;
import com.opensymphony.xwork2.ModelDriven;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * 取派员
 * @author qiankeqin
 *
 */

@Controller("staffAction")
@Scope("prototype")
public class StaffAction extends BaseAction<Staff> implements ModelDriven<Staff>{
	
	@Autowired
	private IStaffService staffService;
	

	public String add(){
		model.setDeltag("0");
		staffService.save(model);
		return LIST;
	}
	
	private int page;
	private int rows;
	
	/**
	 * 分页查询
	 * @throws IOException 
	 */
	public String pageQuery() throws IOException{
		//构建pageBean
		PageBean pageBean = new PageBean();
		pageBean.setPageSize(rows);
		pageBean.setCurrentPage(page);
		//查询条件
		DetachedCriteria criteria = DetachedCriteria.forClass(Staff.class);
		pageBean.setDetachedCriteria(criteria);
		staffService.pageQuery(pageBean);
		
		//将pageBean中的对象封装成json，通过输出流写回页面
		//这里使用json-lib
		//JSONObject--将单一对象转为json
		//JSONArray--将数组或则集合对象转为json
		JsonConfig jsonConfig = new JsonConfig();
		//指定哪些属性不需要转json
		jsonConfig.setExcludes(new String[]{"currentPage","detachedCriteria","pageSize"});
		JSONObject jsonObj = JSONObject.fromObject(pageBean,jsonConfig);
		String jsonStr = jsonObj.toString();
		ServletActionContext.getResponse().setContentType("text/json;charset=utf-8");
		ServletActionContext.getResponse().getWriter().write(jsonStr);
		return NONE;
	}
	
	//批量删除的参数
	private String ids;
	
	public String deleteBatch(){
		staffService.deleteBatch(ids);
		return LIST;
	}
	
	public String edit(){
		//先查询数据库，根据id查询原始数据
		Staff updateModel = staffService.findById(model.getId());
		
		//使用页面提交的数据进行修改
		updateModel.setName(model.getName());
		updateModel.setTelephone(model.getTelephone());
		updateModel.setStandard(model.getStandard());
		updateModel.setHaspda(model.getHaspda());
		updateModel.setStation(model.getStation());
		
		staffService.update(updateModel);
		return LIST;
	}
	

	public void setPage(int page) {
		this.page = page;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}
	
	
	
	
	
}
