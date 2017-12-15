package com.itheima.bos.web.action;

import java.io.File;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 区域Action
 * @author qiankeqin
 *
 */
@Controller("regionAction")
@Scope("prototype")
public class RegionAction extends ActionSupport{
	//上传的文件名称
	private File regionFile;

	
	//导入
	public String importXls(){
		System.out.println(regionFile);
		return NONE;
	}
	
	
	
	
	public void setRegionFile(File regionFile) {
		this.regionFile = regionFile;
	}
	
}
