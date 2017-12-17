package com.itheima.bos.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.itheima.bos.dao.IRegionDao;
import com.itheima.bos.dao.base.impl.BaseDaoImpl;
import com.itheima.bos.domain.Region;

@Repository("regionDao")
public class RegionDao  extends BaseDaoImpl<Region>  implements IRegionDao{

	@Override
	public List<Region> findAllByQ(String q) {
		String hql = "FROM Region r WHERE r.shortcode LIKE ? or r.citycode LIKE ? or r.province LIKE ? or r.city LIKE ? or r.district like ?";
		List<Region> list = (List<Region>) this.getHibernateTemplate().find(hql, "%"+q.trim()+"%","%"+q.trim()+"%","%"+q.trim()+"%","%"+q.trim()+"%","%"+q.trim()+"%");
		return list;
	}
	
}
