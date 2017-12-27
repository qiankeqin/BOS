package com.itheima.bos.web.action;

import java.io.IOException;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.Workordermanage;
import com.itheima.bos.service.IWorkordermanageService;
import com.itheima.bos.web.base.BaseAction;

/**
 * 工作单Controller
 * @author qiankeqin
 *
 */
@Controller("workordermanageAction")
@Scope("prototype")
public class WorkordermanageAction extends BaseAction<Workordermanage>{
	@Autowired
	private IWorkordermanageService workordermanageService;
	
	/**
	 * 新增工作单
	 * @return
	 * @throws IOException
	 */
	public String add() throws IOException{
		String f = "1";
		try{
			workordermanageService.add(model);
		}catch(Exception e){
			e.printStackTrace();
			f = "0";
		}
		ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
		ServletActionContext.getResponse().getWriter().write(f);
		return NONE;
	}
	
	/**
	 * 查询工作单列表
	 * @return
	 * @throws IOException 
	 */
	public String list() throws IOException{
		workordermanageService.getList(pageBean);
		this.java2Json(pageBean, new String[]{});
		return NONE;
	}
}
