package com.itheima.bos.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.IStaffDao;
import com.itheima.bos.domain.Staff;
import com.itheima.bos.service.IStaffService;
import com.itheima.bos.utils.PageBean;

@Service("staffService")
@Transactional
public class StaffService implements IStaffService{

	@Autowired
	private IStaffDao staffDao;
	//����ȡ��Ա
	@Override
	public void save(Staff staff) {
		staffDao.save(staff);
	}
	//��ҳ��ѯ
	@Override
	public void pageQuery(PageBean pageBean) {
		// TODO Auto-generated method stub
		staffDao.pageQuery(pageBean);
	}
	//����ɾ��
	@Override
	public void deleteBatch(String ids) {
		// TODO Auto-generated method stub
		if(StringUtils.isNotBlank(ids)){
			String[] idArray = ids.split(",");
			for(int i=0;i<idArray.length;i++){
				String id = idArray[i];
				staffDao.executeUpdate("staff.delete", id);
			}
		}
	}
	//����id��ȡȡ��Ա
	@Override
	public Staff findById(String id) {
		// TODO Auto-generated method stub
		return staffDao.findById(id);
	}
	
	//����ȡ��Ա
	@Override
	public void update(Staff entity) {
		// TODO Auto-generated method stub
		staffDao.update(entity);
	}
	
	/**
	 * ��ȡδɾ����ȡ��Ա
	 */
	@Override
	public List<Staff> findListNotDelete() {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Staff.class);
		//��ӹ�������,deltag=0
		detachedCriteria.add(Restrictions.eq("deltag","0"));
		return staffDao.findByCriteria(detachedCriteria);
		
	}

}
