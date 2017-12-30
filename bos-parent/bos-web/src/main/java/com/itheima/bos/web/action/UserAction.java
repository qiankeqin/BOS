package com.itheima.bos.web.action;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.context.support.ContextExposingHttpServletRequest;

import com.itheima.bos.domain.User;
import com.itheima.bos.service.IUserService;
import com.itheima.bos.utils.BOSUtils;
import com.itheima.bos.utils.MD5Utils;
import com.itheima.bos.web.base.BaseAction;
import com.itheima.crm.Customer;
import com.itheima.crm.ICustomerService;


@Controller("userAction")
@Scope("prototype")
public class UserAction extends BaseAction<User> {

	//webservice
	@Autowired
	ICustomerService customerService;

	@Autowired
	IUserService userService;
	

	//属性驱动,验证码
	private String checkcode;
	
	public void setCheckcode(String checkcode) {
		this.checkcode = checkcode;
	}
	

	/**
	 * 登陆，使用shiro框架提供的方式进行认证操作
	 * @return
	 * @throws Exception
	 */
	public String login() throws Exception {
		//校验验证码
		String validateCode = (String) ServletActionContext.getRequest().getSession().getAttribute("key");
		if(StringUtils.isNotBlank(checkcode)&&checkcode.equals(validateCode)){
			//使用shiro框架提供的方法进行认证操作
			//获得当前用户对象，状态为 未认证
			Subject subject = SecurityUtils.getSubject();
			//创建用户名密码令牌对象
			AuthenticationToken token = new UsernamePasswordToken(model.getUsername(), MD5Utils.md5(model.getPassword()));
			try{
				//使用令牌进行登陆认证
				subject.login(token);
			}catch(UnknownAccountException accountEx){
				//账号不存在
				this.addActionError("账号不存在");
				return LOGIN;
			}catch(IncorrectCredentialsException incorrectCredentialEx){
				//密码错误
				this.addActionError("密码错误");
				return LOGIN;
			}
			catch(Exception ex){
				//如果login方法抛出异常，表示登陆不成功
				//如果Realm返回空，那么login方法就抛出异常
				ex.printStackTrace();
				return LOGIN;
			}
			//登陆认证成功
			//将subject中存入的user获取出来，放入到session中
			User user = (User) subject.getPrincipal();
			ServletActionContext.getRequest().getSession().setAttribute("user", user);
			return HOME;
		}
		else{
			this.addActionError("输入的验证码错误！");
			return LOGIN;
		}
	}

	//登陆方法
	public String login_bak() throws Exception {
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
