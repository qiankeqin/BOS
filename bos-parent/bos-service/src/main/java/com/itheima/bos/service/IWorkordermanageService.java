package com.itheima.bos.service;

import java.util.List;

import com.itheima.bos.domain.Workordermanage;
import com.itheima.bos.utils.PageBean;

/**
 * ������Service
 * @author qiankeqin
 *
 */
public interface IWorkordermanageService {

	/**
	 * ���湤����
	 * @param model
	 */
	void add(Workordermanage model);

	void getList(PageBean pageBean);
	
}
