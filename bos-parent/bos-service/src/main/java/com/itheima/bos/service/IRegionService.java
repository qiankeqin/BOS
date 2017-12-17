package com.itheima.bos.service;

import java.util.List;

import com.itheima.bos.domain.Region;
import com.itheima.bos.utils.PageBean;

public interface IRegionService {

	void saveBatch(List<Region> regionList);

	void pageQuery(PageBean pageBean);

}
