package com.itheima.bos.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.itheima.bos.dao.IUserDao;
import com.itheima.bos.domain.User;

/**
 * 
 * @author qiankeqin
 *
 */
public class BOSRealm extends AuthorizingRealm{

	@Autowired
	private IUserDao userDao;
	
	//��Ȩ����
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
		
		return null;
	}

	//��֤����
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		System.out.println("�Զ����realm��֤����ִ���ˡ�����");
		//�����û�����ѯ���ݿ��е�����
		UsernamePasswordToken passwordToken = (UsernamePasswordToken) token; 
		String username = passwordToken.getUsername();
		User user = userDao.findUserByUsername(username);
		if(user==null){
			//ҳ��������û���������
			return null;
		}
		
		//��ܸ���ȶ����ݿ��е������ҳ������������Ƿ�һ��
		//����֤��Ϣ����
		//�������û������û����룬Realm����
		AuthenticationInfo info = new SimpleAuthenticationInfo(user,user.getPassword(),this.getName());
		//������֤��Ϣ���󷵻أ��ɰ�ȫ������SecurityManager����ȥ�Ƚ������Ƿ���ȷ��
		//�������ȷ����ô���ظ�action�׳��쳣��֤ʧ�ܣ������ȷ����ôlogin��֤ͨ��
		return info;
	}
	
}
