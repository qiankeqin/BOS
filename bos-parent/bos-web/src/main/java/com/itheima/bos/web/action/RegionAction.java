package com.itheima.bos.web.action;

import java.io.File;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;

/**
 * ����Action
 * @author qiankeqin
 *
 */
@Controller("regionAction")
@Scope("prototype")
public class RegionAction extends ActionSupport{
	//�ϴ����ļ�����
	private File regionFile;

	
	//����
	public String importXls(){
		System.out.println(regionFile);
		return NONE;
	}
	
	
	
	
	public void setRegionFile(File regionFile) {
		this.regionFile = regionFile;
	}
	
}
