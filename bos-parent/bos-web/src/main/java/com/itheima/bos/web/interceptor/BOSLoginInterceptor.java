package com.itheima.bos.web.interceptor;

import org.apache.struts2.ServletActionContext;

import com.itheima.bos.domain.User;
import com.itheima.bos.utils.BOSUtils;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

/**
 * �Զ�����������ʵ���û�δ��¼�Զ���ת��½ҳ��
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
			//û�е�½����ת����½ҳ��
			return "login";
		}
		//����
		return invocation.invoke();
		
	}

}
