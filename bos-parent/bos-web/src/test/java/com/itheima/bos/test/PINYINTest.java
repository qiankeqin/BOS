package com.itheima.bos.test;

import java.util.Arrays;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import com.itheima.bos.utils.PinYin4jUtils;

public class PINYINTest {
	@Test
	public void test1(){
		String province = "河北省";
		String city = "石家庄市";
		String district = "桥西区";
		//简码 ---》 HBSJZQX
		province = province.substring(0,province.length()-1);
		city = city.substring(0,city.length()-1);
		district = district.substring(0,district.length()-1);
		
		String info = province+city+district;
		System.out.println(info);
		
		String[] headByString = PinYin4jUtils.getHeadByString(info);
		System.out.println(Arrays.toString(headByString));
		
		String shortCode = StringUtils.join(headByString);
		System.out.println(shortCode);
		//城市编码 --》shijiazhuang
		String hanziToPinyin = PinYin4jUtils.hanziToPinyin(city,"");
		System.out.println(hanziToPinyin);
	}
}
