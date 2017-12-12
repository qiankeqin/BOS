package com.itheima.bos.web.action;

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
import com.itheima.bos.web.base.BaseAction;


@Controller("userAction")
@Scope("prototype")
public class UserAction extends BaseAction<User> {

	private final static String HOME = "home";
	//属性驱动,验证码
	private String checkcode;
	
	
	public void setCheckcode(String checkcode) {
		this.checkcode = checkcode;
	}

	@Autowired
	IUserService userService;

	//登陆方法
	public String login() throws Exception {
		//校验验证码
		String validateCode = (String) ServletActionContext.getRequest().getSession().getAttribute("key");
		if(StringUtils.isNotBlank(checkcode)&&checkcode.equals(validateCode)){
			//输入的验证码正确
			User user = userService.login(model);
			if(user!=null){
				//登陆成功，将user对象放入到session中，并跳转到首页
				ServletActionContext.getRequest().getSession().setAttribute("user", user);
				return HOME;
			}
			else{
				this.addActionError("输入的账号密码有误，请重新输入");
				return LOGIN;
			}
		}
		else{
			this.addActionError("输入的验证码错误！");
			return LOGIN;
		}
	}
	
	//注销方法
	public String logout(){
		ServletActionContext.getRequest().removeAttribute("user");
		return LOGIN;
	}
}
