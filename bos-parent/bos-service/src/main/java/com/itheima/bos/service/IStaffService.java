package com.itheima.bos.service;

import java.util.List;

import com.itheima.bos.domain.Staff;
import com.itheima.bos.utils.PageBean;

public interface IStaffService {

	void save(Staff staff);

	void pageQuery(PageBean pageBean);

	void deleteBatch(String ids);

	Staff findById(String id);

	void update(Staff updateModel);

	List<Staff> findListNotDelete();
	
}
