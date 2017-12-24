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
	 * 根据客户手机号获取到客户信息
	 * @return
	 * @throws IOException
	 */
	public String findCustomerByTelephone() throws IOException{
		String telephone = model.getTelephone();
		Customer customer = customerService.findCustomerByTelephone(telephone);
		//注意customer不一定有值，但是我们不管是否为空，都进行转json
		this.java2Json(customer, new String[]{});
		return NONE;
	}
	
	/**
	 * 保存一个业务处理单，并自动分单
	 * @return
	 */
	public String add(){
		noticebillService.save(model);
		return LIST;
	}
}
