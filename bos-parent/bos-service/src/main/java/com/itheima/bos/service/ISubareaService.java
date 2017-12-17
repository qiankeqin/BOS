package com.itheima.bos.service;

import java.util.List;

import com.itheima.bos.domain.Subarea;
import com.itheima.bos.utils.PageBean;

public interface ISubareaService {

	//保存
	void save(Subarea model);

	//分页查询
	void pageQuery(PageBean pageBean);

	//获取所有数据
	List<Subarea> findAll();

	List<Subarea> findByNotAssociation();

}
