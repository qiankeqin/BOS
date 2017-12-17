package com.itheima.bos.web.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.Region;
import com.itheima.bos.service.IRegionService;
import com.itheima.bos.utils.PageBean;
import com.itheima.bos.utils.PinYin4jUtils;
import com.itheima.bos.web.base.BaseAction;
import com.opensymphony.xwork2.ActionSupport;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * ����Action
 * @author qiankeqin
 *
 */
@Controller("regionAction")
@Scope("prototype")
public class RegionAction extends BaseAction<Region>{
	
	@Autowired
	private IRegionService regionService;
	
	//�ϴ����ļ�����
	private File regionFile;

	
	//����
	public String importXls() throws FileNotFoundException, IOException{
		//��װһ��Excel�ļ�����
		HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(regionFile));
		//��ȡ�ļ��е�һ��Sheet��ǩҳ
		//HSSFSheet hssfSheet = workbook.getSheetAt(0);
		HSSFSheet hssfSheet = workbook.getSheet("sheet1");
		//������ǩҳ���е���
		List<Region> regionList = new ArrayList<Region>();
		for(Row row : hssfSheet){
			int rowNum = row.getRowNum();//�к�
			if(rowNum==1){
				continue;
			}
			String id = row.getCell(0).getStringCellValue();
			String province = row.getCell(1).getStringCellValue();
			String city = row.getCell(2).getStringCellValue();
			String district = row.getCell(3).getStringCellValue();
			String postcode = row.getCell(4).getStringCellValue();
			
			//��װ���������
			Region region = new Region(id, province, city, district, postcode, null, null, null);
			
			province = province.substring(0,province.length()-1);
			city = city.substring(0,city.length()-1);
			district = district.substring(0,district.length()-1);
			
			String info = province+city+district;
			String[] headByString = PinYin4jUtils.getHeadByString(info);
			String shortcode = StringUtils.join(headByString);
			//���б��� --��shijiazhuang
			String citycode = PinYin4jUtils.hanziToPinyin(city,"");
			region.setShortcode(shortcode);
			region.setCitycode(citycode);
			regionList.add(region);
		}
		//һ���Ա������е�����
		regionService.saveBatch(regionList);
		return NONE;
	}
	
	public String pageQuery() throws IOException{
		regionService.pageQuery(pageBean);
		//��pageBean����ת����json�ַ���������Ӧ��������
		java2Json(pageBean,new String[]{"currentPage","detachedCriteria","pageSize","subareas"});
		return NONE;
	}
	
	private String q;
	

	public void setQ(String q) {
		this.q = q;
	}

	/**
	 * ��ѯ���е�����д��json����
	 * @return
	 * @throws IOException 
	 */
	public String listajax() throws IOException{
		List<Region> list = null;
		if(StringUtils.isNotBlank(q)){
			list = regionService.findAllByQ(q);
		}else{
			list = regionService.findAll();
		}
		
		java2Json(list, new String[]{"subareas"});
		return NONE;
	}
	
	
	public void setRegionFile(File regionFile) {
		this.regionFile = regionFile;
	}
	
	
	
}
