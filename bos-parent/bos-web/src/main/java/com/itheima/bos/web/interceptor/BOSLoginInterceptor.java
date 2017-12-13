package com.itheima.bos.web.interceptor;

import org.apache.struts2.ServletActionContext;

import com.itheima.bos.domain.User;
import com.itheima.bos.utils.BOSUtils;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

/**
 * 自定义拦截器，实现用户未登录自动跳转登陆页面
 * @author qiankeqin
 *
 */
public class BOSLoginInterceptor extends MethodFilterInterceptor{

	@Override
	protected String doIntercept(ActionInvocation invocation) throws Exception {
		ActionProxy  proxy = invocation.getProxy();
		String actionName = proxy.getActionName();
		String namespace = proxy.getNamespace();
		String method = proxy.getMethod();
		User user = BOSUtils.getLoginUser();
		if(user == null){
			//没有登陆，跳转到登陆页面
			return "login";
		}
		//放行
		return invocation.invoke();
		
	}

}
