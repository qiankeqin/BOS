package com.itheima.bos.web.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.Subarea;
import com.itheima.bos.service.ISubareaService;
import com.itheima.bos.utils.FileUtils;
import com.itheima.bos.web.base.BaseAction;

@Controller("subareaAction")
@Scope("prototype")
public class SubareaAction extends BaseAction<Subarea>{
	@Autowired
	private ISubareaService subareaService;
	
	/**
	 * 添加分区
	 * @return
	 */
	public String add(){
		subareaService.save(model);
		return LIST;
	}
	
	/**
	 * 分页查询
	 * @return
	 * @throws IOException 
	 */
	public String pageQuery() throws IOException{
		DetachedCriteria dc = pageBean.getDetachedCriteria();
		//动态添加过滤条件
		String addresskey = model.getAddresskey();
		if(StringUtils.isNotBlank(addresskey)){
			dc.add(Restrictions.like("addresskey", "%"+addresskey+"%"));
		}
		if(model.getRegion()!=null){
			String province = model.getRegion().getProvince();
			String city = model.getRegion().getCity();
			String district = model.getRegion().getDistrict();

			//关联查询,使用别名的方式
			//参数1：分区对象中，关联的属性名称Region region
			//参数2：别名
			dc.createAlias("region", "r");
			if(StringUtils.isNotBlank(province)){
				dc.add(Restrictions.like("r.province", "%"+province+"%"));
			}
			if(StringUtils.isNotBlank(city)){
				dc.add(Restrictions.like("r.city", "%"+city+"%"));
			}
			if(StringUtils.isNotBlank(district)){
				dc.add(Restrictions.like("r.district", "%"+district+"%"));
			}
		}
		
		subareaService.pageQuery(pageBean);
		java2Json(pageBean, new String[]{"pageSize","currentPage","decidedzone","detachedCriteria","subareas"});
		return NONE;
	}
	
	/**
	 * 导出分区到excel
	 * @return
	 * @throws IOException 
	 */
	public String exportXls() throws IOException{
		//2）后台查询所有的分区数据
		List<Subarea> list = subareaService.findAll();
		//3）使用POI将数据写入到excel中
		//3.1 在内存中创建一个Excel文件
		HSSFWorkbook workbook = new HSSFWorkbook();
		//3.2创建一个标签页
		HSSFSheet sheet = workbook.createSheet("分区数据");
		//3.3创建标题行
		HSSFRow headrow = sheet.createRow(0);
		headrow.createCell(0).setCellValue("分区编号");
		headrow.createCell(1).setCellValue("开始编号");
		headrow.createCell(2).setCellValue("结束编号");
		headrow.createCell(3).setCellValue("位置信息");
		headrow.createCell(4).setCellValue("省市区");
		for(Subarea subarea : list){
			HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum()+1);
			dataRow.createCell(0).setCellValue(subarea.getId());
			dataRow.createCell(1).setCellValue(subarea.getStartnum());
			dataRow.createCell(2).setCellValue(subarea.getEndnum());
			dataRow.createCell(3).setCellValue(subarea.getPosition());
			dataRow.createCell(4).setCellValue(subarea.getRegion().getName());
		}
		//4）输出流进行文件下载(一个流，两个头)
		String filename = "分区数据.xls";
		String mimeType = ServletActionContext.getServletContext().getMimeType(filename);
		ServletOutputStream output = ServletActionContext.getResponse().getOutputStream();
		ServletActionContext.getResponse().setContentType(mimeType);
		String agent = ServletActionContext.getRequest().getHeader("User-Agent");
		filename = FileUtils.encodeDownloadFilename(filename, agent);
		ServletActionContext.getResponse().setHeader("content-disposition","attachment;filename="+filename+"");
		workbook.write(output);
		return NONE;
	}
	
	/**
	 * 查询所有未关联到分区的分区，返回json数据
	 * @return
	 * @throws IOException 
	 */
	public String listajax() throws IOException{
		List<Subarea> list = subareaService.findByNotAssociation();
		this.java2Json(list, new String[]{"decidedzone","region"});
		return NONE;
	}
	
	private String decidedzoneId;
	
	public void setDecidedzoneId(String decidedzoneId) {
		this.decidedzoneId = decidedzoneId;
	}

	/**
	 * 根据定区id查询关联的分区
	 * @return
	 * @throws IOException 
	 */
	public String findListByDecidedzoneId() throws IOException{
		List<Subarea> list = subareaService.findListByDecidedzoneId(decidedzoneId);
		this.java2Json(list, new String[]{"decidedzone","subareas"});
		return NONE;
	}
	
}
