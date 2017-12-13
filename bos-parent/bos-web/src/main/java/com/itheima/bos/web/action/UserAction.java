package com.itheima.bos.web.action;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.context.support.ContextExposingHttpServletRequest;

import com.itheima.bos.domain.User;
import com.itheima.bos.service.IUserService;
import com.itheima.bos.utils.BOSUtils;
import com.itheima.bos.web.base.BaseAction;


@Controller("userAction")
@Scope("prototype")
public class UserAction extends BaseAction<User> {

	private final static String HOME = "home";
	//��������,��֤��
	private String checkcode;
	
	
	public void setCheckcode(String checkcode) {
		this.checkcode = checkcode;
	}

	@Autowired
	IUserService userService;

	//��½����
	public String login() throws Exception {
		//У����֤��
		String validateCode = (String) ServletActionContext.getRequest().getSession().getAttribute("key");
		if(StringUtils.isNotBlank(checkcode)&&checkcode.equals(validateCode)){
			//�������֤����ȷ
			User user = userService.login(model);
			if(user!=null){
				//��½�ɹ�����user������뵽session�У�����ת����ҳ
				ServletActionContext.getRequest().getSession().setAttribute("user", user);
				return HOME;
			}
			else{
				this.addActionError("������˺�������������������");
				return LOGIN;
			}
		}
		else{
			this.addActionError("�������֤�����");
			return LOGIN;
		}
	}
	
	//ע������
	public String logout(){
		ServletActionContext.getRequest().removeAttribute("user");
		return LOGIN;
	}
	

	//�޸�����
	public String editPassword() throws IOException{
		String f = "1";
		User user = BOSUtils.getLoginUser();
		try{
			userService.editPassword(user.getId(),model.getPassword());
		}catch(Exception e){
			f = "0";
			e.printStackTrace();
		}
		//���û�дֵ�����ͣ�����ֱ�����ı�����ʽ��д
		ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
		ServletActionContext.getResponse().getWriter().write(f);
		return NONE;
	}
}
