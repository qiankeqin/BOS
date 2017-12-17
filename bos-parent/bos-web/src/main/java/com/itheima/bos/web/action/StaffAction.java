package com.itheima.bos.web.action;

import java.io.IOException;
import java.util.List;

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
	
	
	/**
	 * 分页查询
	 * @throws IOException 
	 */
	public String pageQuery() throws IOException{
		staffService.pageQuery(pageBean);
		//将pageBean对象转换成json字符串，并响应到界面上
		java2Json(pageBean,new String[]{"currentPage","detachedCriteria","pageSize"});
		return NONE;
	}
	
	//批量删除的参数
	private String ids;
	
	public String deleteBatch(){
		staffService.deleteBatch(ids);
		return LIST;
	}
	
	/**
	 * 编辑功能
	 * @return
	 */
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
	
	/**
	 * 查询所有未删除的取派员，返回json数据
	 * @return
	 * @throws IOException 
	 */
	public String listajax() throws IOException{
		List<Staff> list = staffService.findListNotDelete();
		this.java2Json(list, new String[]{"decidedzones"});
		return NONE;
	}
	


	public void setIds(String ids) {
		this.ids = ids;
	}
	
	
	
	
	
}
