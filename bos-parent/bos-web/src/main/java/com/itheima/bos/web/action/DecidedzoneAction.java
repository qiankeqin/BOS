package com.itheima.bos.web.action;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.Decidedzone;
import com.itheima.bos.service.IDecidedzoneService;
import com.itheima.bos.web.base.BaseAction;
import com.itheima.crm.Customer;
import com.itheima.crm.ICustomerService;
/**
 * ����
 * @author qiankeqin
 *
 */
@Controller("decidedzoneAction")
@Scope("prototype")
public class DecidedzoneAction extends BaseAction<Decidedzone>{
	//��������
	private String[] subareaid;

	public void setSubareaid(String[] subareaid) {
		this.subareaid = subareaid;
	}
	
	@Autowired
	private IDecidedzoneService decidedzoneService;
	
	/**
	 * ��Ӷ���
	 * @return
	 */
	public String add(){
		decidedzoneService.save(model,subareaid);
		return LIST;
	}
	
	/**
	 * ��ҳ��ѯ
	 * @return
	 * @throws IOException
	 */
	public String pageQuery() throws IOException{
		DetachedCriteria detachedCriteria = pageBean.getDetachedCriteria();
		if(StringUtils.isNotBlank(model.getId())){
			detachedCriteria.add(Restrictions.like("id", "%"+model.getId()+"%"));
		}
		if(model.getStaff()!=null && StringUtils.isNotBlank(model.getStaff().getStation())){
			detachedCriteria.createAlias("staff", "s");
			detachedCriteria.add(Restrictions.like("s.station", "%"+model.getStaff().getStation()+"%"));
		}
		decidedzoneService.pageQuery(pageBean);
		java2Json(pageBean, new String[]{"currentPage","detachedCriteria","pageSize","subareas","decidedzones"});
		return NONE;
	}

	//ע��������
	@Autowired
	private ICustomerService proxy;
	
	//��ȡδ�������û�
	public String findListNotAssociation() throws IOException{
		List<Customer> list = proxy.findListNotAssociation();
		this.java2Json(list, new String[]{});
		return NONE;
	}
	
	//��ȡ�ö������ѹ������û�
	public String findListHasAssociation() throws IOException{
		List<Customer> list = proxy.findListHasAssociation(model.getId());
		System.out.println(model.getId());
		this.java2Json(list, new String[]{});
		return NONE;
	}
}
