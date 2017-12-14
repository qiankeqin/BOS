package com.itheima.bos.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.IStaffDao;
import com.itheima.bos.domain.Staff;
import com.itheima.bos.service.IStaffService;

@Service("staffService")
@Transactional
public class StaffService implements IStaffService{

	@Autowired
	private IStaffDao staffDao;
	//±£¥Ê»°≈…‘±
	@Override
	public void save(Staff staff) {
		staffDao.save(staff);
	}

}
