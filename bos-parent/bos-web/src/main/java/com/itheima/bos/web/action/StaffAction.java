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
	
	
	/**
	 * ��ҳ��ѯ
	 * @throws IOException 
	 */
	public String pageQuery() throws IOException{
		staffService.pageQuery(pageBean);
		//��pageBean����ת����json�ַ���������Ӧ��������
		java2Json(pageBean,new String[]{"currentPage","detachedCriteria","pageSize"});
		return NONE;
	}
	
	//����ɾ���Ĳ���
	private String ids;
	
	public String deleteBatch(){
		staffService.deleteBatch(ids);
		return LIST;
	}
	
	/**
	 * �༭����
	 * @return
	 */
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
	
	/**
	 * ��ѯ����δɾ����ȡ��Ա������json����
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
