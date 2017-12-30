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
	

	//��������,��֤��
	private String checkcode;
	
	public void setCheckcode(String checkcode) {
		this.checkcode = checkcode;
	}
	

	/**
	 * ��½��ʹ��shiro����ṩ�ķ�ʽ������֤����
	 * @return
	 * @throws Exception
	 */
	public String login() throws Exception {
		//У����֤��
		String validateCode = (String) ServletActionContext.getRequest().getSession().getAttribute("key");
		if(StringUtils.isNotBlank(checkcode)&&checkcode.equals(validateCode)){
			//ʹ��shiro����ṩ�ķ���������֤����
			//��õ�ǰ�û�����״̬Ϊ δ��֤
			Subject subject = SecurityUtils.getSubject();
			//�����û����������ƶ���
			AuthenticationToken token = new UsernamePasswordToken(model.getUsername(), MD5Utils.md5(model.getPassword()));
			try{
				//ʹ�����ƽ��е�½��֤
				subject.login(token);
			}catch(UnknownAccountException accountEx){
				//�˺Ų�����
				this.addActionError("�˺Ų�����");
				return LOGIN;
			}catch(IncorrectCredentialsException incorrectCredentialEx){
				//�������
				this.addActionError("�������");
				return LOGIN;
			}
			catch(Exception ex){
				//���login�����׳��쳣����ʾ��½���ɹ�
				//���Realm���ؿգ���ôlogin�������׳��쳣
				ex.printStackTrace();
				return LOGIN;
			}
			//��½��֤�ɹ�
			//��subject�д����user��ȡ���������뵽session��
			User user = (User) subject.getPrincipal();
			ServletActionContext.getRequest().getSession().setAttribute("user", user);
			return HOME;
		}
		else{
			this.addActionError("�������֤�����");
			return LOGIN;
		}
	}

	//��½����
	public String login_bak() throws Exception {
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
