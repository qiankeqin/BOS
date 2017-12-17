package com.itheima.bos.domain.test;

import com.itheima.bos.domain.Region;

import net.sf.json.JSONObject;

public class RegionTest {
	@org.junit.Test
	public void Test(){
		Region region = new Region("001","河北省","石家庄市","长安区",null,null,null,null);
		System.out.println(JSONObject.fromObject(region).toString());
	}
}
