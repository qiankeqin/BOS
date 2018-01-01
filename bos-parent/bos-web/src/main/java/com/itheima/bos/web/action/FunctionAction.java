package com.itheima.bos.web.action;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.Function;
import com.itheima.bos.service.IFunctionService;
import com.itheima.bos.web.base.BaseAction;

@Controller("functionAction")
@Scope("prototype")
public class FunctionAction extends BaseAction<Function>{
	@Autowired
	private IFunctionService functionService;
	
	/**
	 * ��ѯ����Ȩ�ޣ�����json����
	 * @return
	 * @throws IOException 
	 */
	public String listajax() throws IOException{
		List<Function> list = functionService.findTopAll();
		this.java2Json(list, new String[]{"parentFunction","roles"});
		return NONE;
	}
	/**
	 * ��ѯ����Ȩ�ޣ�����json����
	 * @return
	 * @throws IOException 
	 */
	public String listallajax() throws IOException{
		List<Function> list = functionService.findAll();
		this.java2Json(list, new String[]{"parentFunction","roles","children"});
		return NONE;
	}
	
	/**
	 * ���Ȩ��
	 * @return
	 */
	public String add(){
		if(model.getParentFunction()!=null && model.getParentFunction().getId().equals("")){
			model.setParentFunction(null);
		}
		functionService.save(model);
		return LIST;
	}
	
	/**
	 * ��ҳ��ѯ
	 * @return
	 * @throws IOException
	 */
	public String list() throws IOException{
		//����ע�������⣬������ע���λ���ֶ���pageBean�е�currentPage��ֵ
		String currentPage = this.model.getPage();
		this.pageBean.setCurrentPage(Integer.parseInt(currentPage));
		functionService.pageQuery(pageBean);
		this.java2Json(pageBean, new String[]{"parentFunction","roles","children"});
		return NONE;
	}
	
	/**
	 * ��ȡ�˵����ܣ����ݵ�ǰ��½�˲�ѯ��Ӧ�Ĳ˵����ݣ�����json
	 * @return
	 * @throws IOException 
	 */
	public String findMenu() throws IOException{
		List<Function> menuList = functionService.findMenu();
		this.java2Json(menuList, new String[]{"parentFunction","roles","children"});
		return NONE;
	}
}
