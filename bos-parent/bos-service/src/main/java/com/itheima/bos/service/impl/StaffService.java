package com.itheima.bos.service.impl;

import org.apache.commons.lang3.StringUtils;
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
	//保存取派员
	@Override
	public void save(Staff staff) {
		staffDao.save(staff);
	}
	//分页查询
	@Override
	public void pageQuery(PageBean pageBean) {
		// TODO Auto-generated method stub
		staffDao.pageQuery(pageBean);
	}
	//批量删除
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
	//根据id获取取派员
	@Override
	public Staff findById(String id) {
		// TODO Auto-generated method stub
		return staffDao.findById(id);
	}
	
	//更新取派员
	@Override
	public void update(Staff entity) {
		// TODO Auto-generated method stub
		staffDao.update(entity);
	}

}
