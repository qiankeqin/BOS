package com.itheima.bos.web.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.Subarea;
import com.itheima.bos.service.ISubareaService;
import com.itheima.bos.web.base.BaseAction;

@Controller("subareaAction")
@Scope("prototype")
public class SubareaAction extends BaseAction<Subarea>{
	@Autowired
	private ISubareaService subareaService;
	
	/**
	 * Ìí¼Ó·ÖÇø
	 * @return
	 */
	public String add(){
		subareaService.save(model);
		return NONE;
	}
	
}
