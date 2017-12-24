package com.itheima.bos.web.action;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.Noticebill;
import com.itheima.bos.service.INoticebillService;
import com.itheima.bos.web.base.BaseAction;
import com.itheima.crm.Customer;
import com.itheima.crm.ICustomerService;

@Controller("noticebillAction")
@Scope("prototype")
public class NoticebillAction extends BaseAction<Noticebill>{
	@Autowired
	private ICustomerService customerService;
	@Autowired
	private INoticebillService noticebillService;
	/**
	 * ���ݿͻ��ֻ��Ż�ȡ���ͻ���Ϣ
	 * @return
	 * @throws IOException
	 */
	public String findCustomerByTelephone() throws IOException{
		String telephone = model.getTelephone();
		Customer customer = customerService.findCustomerByTelephone(telephone);
		//ע��customer��һ����ֵ���������ǲ����Ƿ�Ϊ�գ�������תjson
		this.java2Json(customer, new String[]{});
		return NONE;
	}
	
	/**
	 * ����һ��ҵ���������Զ��ֵ�
	 * @return
	 */
	public String add(){
		noticebillService.save(model);
		return LIST;
	}
}
