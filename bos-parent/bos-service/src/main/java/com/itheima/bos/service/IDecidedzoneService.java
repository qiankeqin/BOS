package com.itheima.bos.service;

import com.itheima.bos.domain.Decidedzone;
import com.itheima.bos.utils.PageBean;

public interface IDecidedzoneService {

	void save(Decidedzone model,String[] subareaid);

	void pageQuery(PageBean pageBean);

}
