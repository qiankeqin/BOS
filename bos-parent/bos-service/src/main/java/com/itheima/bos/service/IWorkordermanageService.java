package com.itheima.bos.service;

import java.util.List;

import com.itheima.bos.domain.Workordermanage;
import com.itheima.bos.utils.PageBean;

/**
 * 工作单Service
 * @author qiankeqin
 *
 */
public interface IWorkordermanageService {

	/**
	 * 保存工作单
	 * @param model
	 */
	void add(Workordermanage model);

	void getList(PageBean pageBean);
	
}
