package com.itheima.bos.dao.impl;

import org.springframework.stereotype.Repository;

import com.itheima.bos.dao.ISubareaDao;
import com.itheima.bos.dao.base.impl.BaseDaoImpl;
import com.itheima.bos.domain.Subarea;

@Repository("subareaDao")
public class SubareaDao extends BaseDaoImpl<Subarea> implements ISubareaDao{
	
}
