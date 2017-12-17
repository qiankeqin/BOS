package com.itheima.bos.service;

import java.util.List;

import com.itheima.bos.domain.Subarea;
import com.itheima.bos.utils.PageBean;

public interface ISubareaService {

	//����
	void save(Subarea model);

	//��ҳ��ѯ
	void pageQuery(PageBean pageBean);

	//��ȡ��������
	List<Subarea> findAll();

	List<Subarea> findByNotAssociation();

}
