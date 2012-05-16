/**
 * 
 */
package com.integral.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

/**
 * POI处理Excel公共类
 * @author Jason
 */
public class PoiUtil {
	/**
	 * 测试使用
	 * @param args
	 */
	public static void main(String[] args) {
		PoiUtil util = new PoiUtil();
		Workbook wb = util.createWorkBook(100);
		Sheet sheet = util.createWorkSheet(wb);
		Object[] data = new Object[]{"aaaaa", 1223, true, 23.13, "20090817"};
		FileOutputStream out = null;
		try {
			out = new FileOutputStream("c:/test.xlsx");
			for(int i=0; i< 100000; i++){
				util.exportExcel2007(sheet, i, data);
			}
			wb.write(out);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 创建excel表格
	 * @param defaultLines 默认存储在内存中的数据行数
	 * @return
	 */
	public Workbook createWorkBook(int defaultLines){
		return new SXSSFWorkbook(defaultLines);
	}
	
	/**
	 * 创建excel页
	 * @param wb excel表格
	 * @return
	 */
	public Sheet createWorkSheet(Workbook wb){
		return createWorkSheet(wb, "新建sheet页");
	}
	/**
	 * 创建excel页
	 * @param wb excel表格
	 * @param sheetName sheet页名称, 名称长度必须大于等于1并且小于等于31
	 * @return
	 */
	public Sheet createWorkSheet(Workbook wb, String sheetName){
		return wb.createSheet(sheetName);
	}
	/**
	 * 写数据到单元格(仅仅适用于Excel2007版本)
	 * @param sheet excel的sheet页
	 * @param rowNumber 当前写入的是sheet的第rowNumber行
	 * @param data 写入数据(Object数组)
	 * @throws Exception
	 */
	public void exportExcel2007(Sheet sheet, int rowNumber, Object [] data) throws Exception{
		try{
			if(data != null){
				Row row = sheet.createRow(rowNumber);
				for(int i=0; i<data.length; i++){
					Cell cell = row.createCell(i);
					if(data[i] instanceof Integer){
						cell.setCellValue((Integer)data[i]);
					}else if(data[i] instanceof String){
						cell.setCellValue((String)data[i]);
					}else if(data[i] instanceof Double){
						cell.setCellValue((Double)data[i]);
					}else if(data[i] instanceof Float){
						cell.setCellValue((Float)data[i]);
					}else if(data[i] instanceof Long){
						cell.setCellValue((Long)data[i]);
					}else if(data[i] instanceof Boolean){
						cell.setCellValue((Boolean)data[i]);
					}else if(data[i] instanceof Date){
						cell.setCellValue((Date)data[i]);
					}else if(data[i] instanceof BigDecimal){
						cell.setCellValue(((BigDecimal)data[i]).doubleValue());
					}else{
						cell.setCellValue(String.valueOf(data[i]));
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
	}
	
	/**
	 * 仅仅用于测试
	 */
	private static void test(){
		// 只在内存中保留100行记录,
		Workbook wb = new SXSSFWorkbook(100);
		Sheet sh = wb.createSheet();
		int rownum = 0;
		try{
			while(true){
				Row row = sh.createRow(rownum);
				for (int cellnum = 0; cellnum < 10; cellnum++) {
					Cell cell = row.createCell(cellnum);
					String address = new CellReference(cell).formatAsString();
					cell.setCellValue(address);
				}
				System.out.println(rownum);
				rownum++;
				if (rownum >= 1000000)//最多写100w行—EXcel2007的记录上线大概是100w多点
					break;
			}
			FileOutputStream out = new FileOutputStream("c:/test.xlsx");
			wb.write(out);
			out.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
