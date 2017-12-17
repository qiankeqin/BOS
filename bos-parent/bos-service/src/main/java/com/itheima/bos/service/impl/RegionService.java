package com.itheima.bos.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.IRegionDao;
import com.itheima.bos.domain.Region;
import com.itheima.bos.service.IRegionService;
import com.itheima.bos.utils.PageBean;

@Service("regionService")
@Transactional
public class RegionService implements IRegionService {

	@Autowired
	private IRegionDao regionDao;
	
	//批量保存Region
	@Override
	public void saveBatch(List<Region> regionList) {
		// TODO Auto-generated method stub
		for(Region region : regionList){
			regionDao.save(region);
		}
	}

	//分页查询
	@Override
	public void pageQuery(PageBean pageBean) {
		// TODO Auto-generated method stub
		regionDao.pageQuery(pageBean);
	}
	
}
