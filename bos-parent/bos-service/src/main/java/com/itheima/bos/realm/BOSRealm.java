package com.itheima.bos.realm;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;

import com.itheima.bos.dao.IFunctionDao;
import com.itheima.bos.dao.IUserDao;
import com.itheima.bos.dao.impl.FunctionDao;
import com.itheima.bos.domain.Function;
import com.itheima.bos.domain.User;

/**
 * 
 * @author qiankeqin
 *
 */
public class BOSRealm extends AuthorizingRealm{

	@Autowired
	private IUserDao userDao;
	@Autowired
	private IFunctionDao functionDao;
	
	//��Ȩ����
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		//Ϊ�û���Ȩ
		//info.addStringPermission("staff-list");
		//���ݵ�ǰ��½�û���ѯ���ݿ⣬��ȡʵ�ʶ�Ӧ��Ȩ��
		//�������������Ի�ȡ����ǰ��½�Ķ���
		User user = (User) SecurityUtils.getSubject().getPrincipal();
		//User user2 = (User) principals.getPrimaryPrincipal();
		//���ݵ�ǰ��½���û���ѯ���ݿ⣬��ȡ�û���Ӧ��Ȩ��
		List<Function> list = null;
		if(user.getUsername().equals("admin")){
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Function.class);
			//��������Ա�����û�����ѯ����Ȩ������
			list = functionDao.findByCriteria(detachedCriteria);
		}
		else{
			list = functionDao.findFunctionListByUserId(user.getId());
		}
		if(list!=null && list.size()>0){
			for(Function func : list){
				info.addStringPermission(func.getCode());
			}
		}
		
		return info;
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
