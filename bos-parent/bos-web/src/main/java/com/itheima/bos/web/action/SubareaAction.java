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
	 * ��ӷ���
	 * @return
	 */
	public String add(){
		subareaService.save(model);
		return LIST;
	}
	
	/**
	 * ��ҳ��ѯ
	 * @return
	 * @throws IOException 
	 */
	public String pageQuery() throws IOException{
		DetachedCriteria dc = pageBean.getDetachedCriteria();
		//��̬��ӹ�������
		String addresskey = model.getAddresskey();
		if(StringUtils.isNotBlank(addresskey)){
			dc.add(Restrictions.like("addresskey", "%"+addresskey+"%"));
		}
		if(model.getRegion()!=null){
			String province = model.getRegion().getProvince();
			String city = model.getRegion().getCity();
			String district = model.getRegion().getDistrict();

			//������ѯ,ʹ�ñ����ķ�ʽ
			//����1�����������У���������������Region region
			//����2������
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
	 * ����������excel
	 * @return
	 * @throws IOException 
	 */
	public String exportXls() throws IOException{
		//2����̨��ѯ���еķ�������
		List<Subarea> list = subareaService.findAll();
		//3��ʹ��POI������д�뵽excel��
		//3.1 ���ڴ��д���һ��Excel�ļ�
		HSSFWorkbook workbook = new HSSFWorkbook();
		//3.2����һ����ǩҳ
		HSSFSheet sheet = workbook.createSheet("��������");
		//3.3����������
		HSSFRow headrow = sheet.createRow(0);
		headrow.createCell(0).setCellValue("�������");
		headrow.createCell(1).setCellValue("��ʼ���");
		headrow.createCell(2).setCellValue("�������");
		headrow.createCell(3).setCellValue("λ����Ϣ");
		headrow.createCell(4).setCellValue("ʡ����");
		for(Subarea subarea : list){
			HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum()+1);
			dataRow.createCell(0).setCellValue(subarea.getId());
			dataRow.createCell(1).setCellValue(subarea.getStartnum());
			dataRow.createCell(2).setCellValue(subarea.getEndnum());
			dataRow.createCell(3).setCellValue(subarea.getPosition());
			dataRow.createCell(4).setCellValue(subarea.getRegion().getName());
		}
		//4������������ļ�����(һ����������ͷ)
		String filename = "��������.xls";
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
	 * ��ѯ����δ�����������ķ���������json����
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
	 * ���ݶ���id��ѯ�����ķ���
	 * @return
	 * @throws IOException 
	 */
	public String findListByDecidedzoneId() throws IOException{
		List<Subarea> list = subareaService.findListByDecidedzoneId(decidedzoneId);
		this.java2Json(list, new String[]{"decidedzone","subareas"});
		return NONE;
	}
	
}
