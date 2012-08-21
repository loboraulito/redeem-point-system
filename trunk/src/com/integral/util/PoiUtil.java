/**
 * 
 */
package com.integral.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

/**
 * POI处理Excel公共类
 * @author Jason
 */
public class PoiUtil<T> {
	/**
	 * 测试使用
	 * @param args
	 */
	public static void main(String[] args) {
	    //test1();
	    //test2();
	    test3();
	}
	/**
     * 仅仅用于测试
     */
	public static void test(){
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
	public static void test1(){
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
	public static void test2(){
	    PoiUtil<Student> util = new PoiUtil<Student>();
        Workbook wb = util.createWorkBook(100);
        List<Student> list = new ArrayList<Student>(100000);
        for(int i=0; i< 100000; i++){
            Student s = new Student("1", "测试", 13, true);
            list.add(s);
        }
        Map<String, String> map = new HashMap<String, String>();
        map.put("id", "学号");
        map.put("name", "姓名");
        map.put("age", "年龄");
        map.put("sex", "性别");
        FileOutputStream out = null;
        try {
            out = new FileOutputStream("c:/test1.xlsx");
            util.exportExcel2007(wb, "测试", map, null, list);
            wb.write(out);
        }
        catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
	}
	public static void test3(){
	    PoiUtil<Student> util = new PoiUtil<Student>();
        List<Student> list = new ArrayList<Student>(100000);
        for(int i=0; i< 100000; i++){
            Student s = new Student("1", "测试", 13, true);
            list.add(s);
        }
        Map<String, String> map = new HashMap<String, String>();
        map.put("id", "学号");
        map.put("name", "姓名");
        map.put("age", "年龄");
        map.put("sex", "性别");
        FileOutputStream out = null;
        try {
            out = new FileOutputStream("c:/test2.xlsx");
            util.exportExcel2007(map, list).write(out);
        }
        catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                out.close();
            } catch (IOException e) {
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
     * 创建excel表格
     * @return
     */
    public Workbook createWorkBook(){
        return new SXSSFWorkbook();
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
	    if(sheetName == null || "".equals(sheetName.trim())){
	        return createWorkSheet(wb);
	    }
		return wb.createSheet(sheetName);
	}
	/**
	 * <p>Discription:[导出数据到Excel文档]</p>
	 * @param map 导出的字段及其字段名称
	 * @param dataSet 导出的数据
	 * @return
	 * @throws Exception
	 * @author:[代超]
	 * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
	 */
	public Workbook exportExcel2007(Map<String, String> map, Collection<T> dataSet) throws Exception{
	    Workbook wb = createWorkBook(100);
	    exportExcel2007(wb, null, map, null, dataSet);
	    return wb;
	}
	/**
	 * <p>Discription:[导出数据到Excel文档]</p>
	 * @param sheetName 导出excel页的名称
	 * @param map 导出的字段及其字段名称
     * @param dataSet 导出的数据
	 * @return
	 * @throws Exception
	 * @author:[代超]
	 * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
	 */
	public Workbook exportExcel2007(String sheetName, Map<String, String> map, Collection<T> dataSet) throws Exception{
        Workbook wb = createWorkBook(100);
        exportExcel2007(wb, sheetName, map, null, dataSet);
        return wb;
    }
	
	/**
	 * <p>Discription:[将数据写入到Excel2007中]</p>
	 * @param wb Excel2007
	 * @param sheetName Excel中的sheet的名称
	 * @param map<字段名, 对应名称> Excel导出的字段及其对应的名称。名称将以抬头的形式写入Excel的第一行
	 * @param dateParten 如果到处数据是日期的话，给出一个日期格式。默认：yyyy-MM-dd
	 * @param dataSet 要导出的数据。必须是符合javabean的集合
	 * @throws Exception
	 * @author:[代超]
	 * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
	 */
	public void exportExcel2007(Workbook wb, String sheetName, Map<String, String> map, String dateParten, Collection<T> dataSet) throws Exception{
	    if(dataSet != null && map != null){
	        if(dateParten == null || "".equals(dateParten.trim())){
	            dateParten = "yyyy-MM-dd";
	        }
	        Sheet sheet = createWorkSheet(wb, sheetName);
	        //写入数据, 从第1行开始
	        try{
	            int rowNumber = 0;
	            // 遍历集合数据，产生数据行
	            Iterator<T> it = dataSet.iterator();
	            while(it.hasNext()){
	                T t = (T) it.next();
	                // 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
	                Field[] fields = t.getClass().getDeclaredFields();
	                //从第0列开始
	                int cellNumber = 0;
	                Object obj[] = new Object[fields.length];
	                String [] header = new String[fields.length];;
	                for(Field field : fields){
	                    String fieldName = field.getName();
	                    //导出指定字段
	                    String fn = map.get(fieldName);
	                    if(fn == null){
	                        continue;
	                    }else{
	                        if(rowNumber == 0){
	                            header[cellNumber] = fn;
	                        }
	                    }
	                    String getMethodName = "get"
	                            + fieldName.substring(0, 1).toUpperCase()
	                            + fieldName.substring(1);
	                    Class tCls = t.getClass();
	                    Method getMethod = tCls.getMethod(getMethodName,
	                            new Class[] {});
	                    Object value = getMethod.invoke(t, new Object[] {});
	                    obj[cellNumber] = value;
	                    cellNumber ++;
	                }
	                //写入第一行
	                if(rowNumber == 0){
	                    exportExcel2007(sheet, rowNumber, header);
                    }else{
                        exportExcel2007(sheet, rowNumber, obj);
                    }
	                rowNumber ++ ;
	            }
	        }catch(Exception e){
	            e.printStackTrace();
	            throw e;
	        }
	    }
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
					if(data[i] == null){
					    cell.setCellValue("");
					}else if(data[i] instanceof Integer){
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
     * 写数据到单元格(仅仅适用于Excel2007版本)
     * 
     * @param sheet
     *            excel的sheet页
     * @param rowNumber
     *            当前写入的是sheet的第rowNumber行
     * @param data
     *            写入数据(Object数组)
     * @param cellStyle
     *            单元格样式
     * @throws Exception
     */
    public static void exportExcel2007(Sheet sheet, int rowNumber,
            Object[] data, CellStyle cellStyle) throws Exception {
        try {
            if (data != null) {
                Row row = sheet.createRow(rowNumber);
                for (int i = 0; i < data.length; i++) {
                    Cell cell = row.createCell(i);
                    cell.setCellStyle(cellStyle);
                    if (data[i] == null) {
                        cell.setCellValue("");
                    } else if (data[i] instanceof Integer) {
                        cell.setCellValue((Integer) data[i]);
                    } else if (data[i] instanceof String) {
                        cell.setCellValue((String) data[i]);
                    } else if (data[i] instanceof Double) {
                        cell.setCellValue((Double) data[i]);
                    } else if (data[i] instanceof Float) {
                        cell.setCellValue((Float) data[i]);
                    } else if (data[i] instanceof Long) {
                        cell.setCellValue((Long) data[i]);
                    } else if (data[i] instanceof Boolean) {
                        cell.setCellValue((Boolean) data[i]);
                    } else if (data[i] instanceof Date) {
                        cell.setCellValue((Date) data[i]);
                    } else if (data[i] instanceof BigDecimal) {
                        cell.setCellValue(((BigDecimal) data[i]).doubleValue());
                    } else {
                        cell.setCellValue(String.valueOf(data[i]));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
/**
 * <p>Description: [用于测试]</p>
 * @author  <a href="mailto: swpigris81@126.com">代超</a>
 * @version $Revision$
 */
class Student{
    private String id;
    private String name;
    private int age;
    private boolean sex;
    
    public Student() {
    }
    public Student(String id, String name, int age, boolean sex) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.sex = sex;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public boolean getSex() {
        return sex;
    }
    public void setSex(boolean sex) {
        this.sex = sex;
    }
}