package com.itheima.bos.dao;

import java.util.List;

import com.itheima.bos.dao.base.IBaseDao;
import com.itheima.bos.domain.Workbill;

public interface IWorkbillDao extends IBaseDao<Workbill>{

	List<Workbill> findNewWorkbills();

}
