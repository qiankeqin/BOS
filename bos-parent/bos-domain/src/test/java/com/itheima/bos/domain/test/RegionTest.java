package com.itheima.bos.domain.test;

import com.itheima.bos.domain.Region;

import net.sf.json.JSONObject;

public class RegionTest {
	@org.junit.Test
	public void Test(){
		Region region = new Region("001","�ӱ�ʡ","ʯ��ׯ��","������",null,null,null,null);
		System.out.println(JSONObject.fromObject(region).toString());
	}
}
