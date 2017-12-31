package com.itheima.bos.web.action;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;

import org.junit.Test.None;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.Role;
import com.itheima.bos.service.IRoleService;
import com.itheima.bos.web.base.BaseAction;
@Controller("roleAction")
@Scope("prototype")
public class RoleAction extends BaseAction<Role>{
	//��������
	//Ȩ�޵�id��
	private String functionIds;

	public void setFunctionIds(String functionIds) {
		this.functionIds = functionIds;
	}

	@Autowired
	private IRoleService roleService;
	
	/**
	 * ��ӽ�ɫ
	 * @return
	 */
	public String add(){
		roleService.add(model,functionIds);
		return LIST;
	}
	
	/**
	 * ��ҳ��ѯ
	 * @return
	 * @throws IOException 
	 */
	public String list() throws IOException{
		roleService.pageQuery(pageBean);
		this.java2Json(pageBean, new String[]{"functions","users"});
		return NONE;
	}
	
	/**
	 * ��ȡ���еĽ�ɫ����
	 * @return
	 * @throws IOException 
	 */
	public String listajax() throws IOException{
		List<Role> list = roleService.findAll();
		this.java2Json(list, new String[]{"functions","users"});
		return NONE;
	}
}
