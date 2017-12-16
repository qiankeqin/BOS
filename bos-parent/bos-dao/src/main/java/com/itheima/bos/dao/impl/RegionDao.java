package com.itheima.bos.dao.impl;

import org.springframework.stereotype.Repository;

import com.itheima.bos.dao.IRegionDao;
import com.itheima.bos.dao.base.impl.BaseDaoImpl;
import com.itheima.bos.domain.Region;

@Repository("regionDao")
public class RegionDao  extends BaseDaoImpl<Region>  implements IRegionDao{
	
}
