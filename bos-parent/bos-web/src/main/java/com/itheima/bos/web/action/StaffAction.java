package com.itheima.bos.web.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.Staff;
import com.itheima.bos.service.IStaffService;
import com.itheima.bos.web.base.BaseAction;
import com.opensymphony.xwork2.ModelDriven;

/**
 * »°≈…‘±
 * @author qiankeqin
 *
 */

@Controller("staffAction")
@Scope("prototype")
public class StaffAction extends BaseAction<Staff> implements ModelDriven<Staff>{
	
	@Autowired
	private IStaffService staffService;
	

	public String add(){
		staffService.save(model);
		return LIST;
	}
	
}
