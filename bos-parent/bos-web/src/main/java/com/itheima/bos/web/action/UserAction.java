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
	

	//修改密码
	public String editPassword() throws IOException{
		String f = "1";
		User user = BOSUtils.getLoginUser();
		try{
			userService.editPassword(user.getId(),model.getPassword());
		}catch(Exception e){
			f = "0";
			e.printStackTrace();
		}
		//设置回写值的类型，这里直接以文本的形式回写
		ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
		ServletActionContext.getResponse().getWriter().write(f);
		return NONE;
	}
}
