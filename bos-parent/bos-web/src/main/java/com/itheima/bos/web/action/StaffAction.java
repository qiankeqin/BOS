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
 * ȡ��Ա
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
	 * ��ҳ��ѯ
	 * @throws IOException 
	 */
	public String pageQuery() throws IOException{
		//����pageBean
		PageBean pageBean = new PageBean();
		pageBean.setPageSize(rows);
		pageBean.setCurrentPage(page);
		//��ѯ����
		DetachedCriteria criteria = DetachedCriteria.forClass(Staff.class);
		pageBean.setDetachedCriteria(criteria);
		staffService.pageQuery(pageBean);
		
		//��pageBean�еĶ����װ��json��ͨ�������д��ҳ��
		//����ʹ��json-lib
		//JSONObject--����һ����תΪjson
		//JSONArray--��������򼯺϶���תΪjson
		JsonConfig jsonConfig = new JsonConfig();
		//ָ����Щ���Բ���Ҫתjson
		jsonConfig.setExcludes(new String[]{"currentPage","detachedCriteria","pageSize"});
		JSONObject jsonObj = JSONObject.fromObject(pageBean,jsonConfig);
		String jsonStr = jsonObj.toString();
		ServletActionContext.getResponse().setContentType("text/json;charset=utf-8");
		ServletActionContext.getResponse().getWriter().write(jsonStr);
		return NONE;
	}
	
	//����ɾ���Ĳ���
	private String ids;
	
	public String deleteBatch(){
		staffService.deleteBatch(ids);
		return LIST;
	}
	
	public String edit(){
		//�Ȳ�ѯ���ݿ⣬����id��ѯԭʼ����
		Staff updateModel = staffService.findById(model.getId());
		
		//ʹ��ҳ���ύ�����ݽ����޸�
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
