package com.itheima.bos.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class POITest {
	public static void main(String[] args) throws FileNotFoundException, IOException {
		String filePath = "F:\\BaiduYunDownload\\Java32��\\BOS-day05\\BOS-day05\\����\\�������������.xls";
		//��װһ��Excel�ļ�����
		HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(new File(filePath)));
		//��ȡ�ļ��е�һ��Sheet��ǩҳ
		HSSFSheet hssfSheet = workbook.getSheetAt(0);
		//������ǩҳ���е���
		for(Row row : hssfSheet){
			int rowNum = row.getRowNum();//�к�
			if(rowNum==0){
				continue;
			}
			System.out.println();
			for(Cell cell : row){
				String value = cell.getStringCellValue();
				System.out.print(value+" ");
			}
		}
		
	}
}
