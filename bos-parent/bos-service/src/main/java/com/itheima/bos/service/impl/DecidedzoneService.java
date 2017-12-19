package com.itheima.bos.service.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.IDecidedzoneDao;
import com.itheima.bos.dao.ISubareaDao;
import com.itheima.bos.domain.Decidedzone;
import com.itheima.bos.domain.Subarea;
import com.itheima.bos.service.IDecidedzoneService;
import com.itheima.bos.utils.PageBean;

@Service("decidedzoneService")
@Transactional
public class DecidedzoneService implements IDecidedzoneService{

	@Autowired
	private IDecidedzoneDao decidedzoneDao;
	@Autowired
	private ISubareaDao subareaDao;
	
	//���涨������
	@Override
	public void save(Decidedzone model,String[] subareaid) {
		decidedzoneDao.save(model);
		for(String id:subareaid){
			//ά�����һ��
			Subarea subarea = subareaDao.findById(id);
			subarea.setDecidedzone(model);
			//subareaDao.save(subarea);
		}
	}

	//��ҳ��ѯ
	@Override
	public void pageQuery(PageBean pageBean) {
		// TODO Auto-generated method stub
		decidedzoneDao.pageQuery(pageBean);
	}

}
