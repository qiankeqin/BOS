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
	
	//授权方法
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		//为用户授权
		//info.addStringPermission("staff-list");
		//根据当前登陆用户查询数据库，获取实际对应的权限
		//下面两个都可以获取到当前登陆的对象
		User user = (User) SecurityUtils.getSubject().getPrincipal();
		//User user2 = (User) principals.getPrimaryPrincipal();
		//根据当前登陆的用户查询数据库，获取用户对应的权限
		List<Function> list = null;
		if(user.getUsername().equals("admin")){
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Function.class);
			//超级管理员内置用户，查询所有权限数据
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

	//认证方法
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		System.out.println("自定义的realm认证方法执行了。。。");
		//根据用户名查询数据库中的密码
		UsernamePasswordToken passwordToken = (UsernamePasswordToken) token; 
		String username = passwordToken.getUsername();
		User user = userDao.findUserByUsername(username);
		if(user==null){
			//页面输入的用户名不存在
			return null;
		}
		
		//框架负责比对数据库中的密码和页面输入的密码是否一致
		//简单认证信息对象
		//参数：用户对象，用户密码，Realm名称
		AuthenticationInfo info = new SimpleAuthenticationInfo(user,user.getPassword(),this.getName());
		//将简单认证信息对象返回，由安全管理器SecurityManager对象去比较密码是否正确，
		//如果不正确，那么返回给action抛出异常认证失败，如果正确，那么login认证通过
		return info;
	}
	
}
