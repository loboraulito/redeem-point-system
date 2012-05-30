package com.integral.util.office;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.beanutils.BeanMap;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * <p>
 * Description: [Office文档操作类]
 * </p>
 * 
 * @author <a href="mailto: swpigris81@126.com">代超</a>
 * @version $Revision$
 */
public class OfficeOperationUtils<T> {
    /**
     * <p>
     * Discription:[获取Excel的文档]
     * </p>
     * 
     * @param file excel文件
     * @return
     * @author:[代超]
     * @throws IOException 
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public Workbook getWorkBook(File excelFile) throws IOException {
        Workbook wb = null;
        try {
            wb = new HSSFWorkbook(new POIFSFileSystem(new FileInputStream(excelFile)));
        }
        catch (Exception e) {
            try {
                wb = new XSSFWorkbook(new FileInputStream(excelFile));
            }
            catch (FileNotFoundException e1) {
                throw e1;
            }
            catch (IOException e1) {
                throw e1;
            }
        }
        return wb;
    }
    /**
     * <p>Discription:[读取xls或者xlsx文档的所有页，并且以map格式输出：sheet名：sheet内容。
     * 其中sheet内容是以列表存储，列表中存储的是String数组，sheet中的每一行则是一个String数组]</p>
     * @param wb 工作表格,文档
     * @return
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public Map<String, Object> readExcelFile(Workbook wb) {
        if (wb == null) {
            return null;
        }
        DecimalFormat df = new DecimalFormat("#.##");
        int sheetNumber = wb.getNumberOfSheets();
        List list = new ArrayList();
        Map<String, Object> excelMap = new HashMap<String, Object>();
        for (int i = 0; i < sheetNumber; i++) {
            Sheet sheet = wb.getSheetAt(i);
            List<String[]> strs=new ArrayList<String[]>();
            // 注意得到的行数是基于0的索引 遍历所有的行
            for (int k = 0; k <= sheet.getLastRowNum(); k++) {
                Row rows = sheet.getRow(k);
                if(rows == null){
                    continue;
                }
                String[] str = new String[rows.getLastCellNum()];
                // 遍历每一列
                for (int l = 0; l < rows.getLastCellNum(); l++) {
                    Cell cell = rows.getCell(l);
                    // 单元格类型
                    if(cell == null){
                        continue;
                    }
                    int cellType = cell.getCellType();
                    switch (cellType) {
                    case 0:// 数字类型
                        str[l] = df.format(cell.getNumericCellValue());
                        break;
                    case 1:// String类型
                        str[l] = cell.getStringCellValue();
                        break;
                    case 2:// Formula Cell type 公式类型
                        FormulaEvaluator he = null;
                        try {
                            he = new HSSFFormulaEvaluator((HSSFWorkbook) wb);
                        }
                        catch (Exception e) {
                            he = new XSSFFormulaEvaluator((XSSFWorkbook) wb);
                        }
                        if (he != null && he.evaluateFormulaCell(cell) == 0) {
                            str[l] = df.format(he.evaluate(cell).getNumberValue());
                        }
                        else {
                            str[l] = he.evaluate(cell).getStringValue();
                        }
                        break;
                    case 3:// 空格
                        break;
                    case 4:// Boolean Cell type
                        str[l] = BooleanUtils.toStringTrueFalse(cell.getBooleanCellValue());
                        break;
                    case 5:// Errors
                        break;
                    default:// 其它格式的数据
                        break;
                    }
                }
                strs.add(str);
            }
            //list.add(strs);
            excelMap.put(sheet.getSheetName(), strs);
        }
        return excelMap;
    }
    /**
     * <p>Discription:[设置文件头显示样式]</p>
     * @param wb
     * @return
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public CellStyle setRootSheetSysle(Workbook wb){
        CellStyle style = wb.createCellStyle();
        // 设置这些样式
        style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 对齐方式：居中对齐
        // 生成一个字体
        Font font = wb.createFont();
        font.setColor(HSSFColor.VIOLET.index);
        font.setFontHeightInPoints((short) 12);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        // 把字体应用到当前的样式
        style.setFont(font);
        return style;
    }
    /**
     * <p>Discription:[设置文件内容的显示样式]</p>
     * @param wb
     * @return
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public CellStyle setContentSheetSysle(Workbook wb){
        CellStyle style = wb.createCellStyle();
        // 生成并设置另一个样式
        style.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        // 生成一个字体
        Font font = wb.createFont();
        font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        // 把字体应用到当前的样式
        style.setFont(font);
        return style;
    }
    /**
     * <p>Discription:[写入excel文件,可用于导出,修改excel文件.目前只产生xls文件,并不产生xlsx文件]</p>
     * @param sheetName 写入excel的sheet名称
     * @param dataSet 要写入文件的内容(一个集合). 集合中存放符合javaBean格式的对象。
     *                支持数据类型有基本数据类型及String,Date,byte[](图片数据)
     * @param out 要写入的文件流, 可用于导出, 或者写入硬盘文件
     * @param map 要导出的字段。（格式：字段名 <-> 字段中文名）其中字段名应该是对应javaBean中的某属性。<br>
     *            若该字段为公式的话，必须符合一定的格式：公式标识符formula公式名称&计算起始单元格列&计算终止单元格列&计算单元格行&分隔符（,:）&操作符<br>
     *            如：SUM&A&C&2&,&-   它返回的公司是：SUM(A3,-C3)。具体参照：
     *            <code>com.integral.util.office.ExcelFormula</code>
     * @param dateFormat 日期格式(可选)，默认：yyyy-MM-dd
     * @author:[代超]
     * @throws IOException 
     * @throws Exception 
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    @SuppressWarnings("deprecation")
    public void writExcelFile(String sheetName, Collection<T> dataSet, OutputStream out, Map map, String dateFormat) throws Exception{
        if(dataSet == null || dataSet.size() <1){
            return;
        }
        if(map == null || map.size()<1){
            return;
        }
        if(dateFormat==null || "".equals(dateFormat.trim())){
            dateFormat = "yyyy-MM-dd";
        }
        sheetName = sheetName == null ? "" : sheetName;
        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个表格
        HSSFSheet sheet = workbook.createSheet(sheetName);
        // 设置表格默认列宽度为15个字节
        sheet.setDefaultColumnWidth(15);
        // 设置表格的样式
        CellStyle headerStyle = setRootSheetSysle(workbook);
        
        // 产生表格标题行
        HSSFRow row = sheet.createRow(0);
        //将第一行冻结
        sheet.createFreezePane(1, 1);
        
        if(map != null){
            Object[] obj = map.values().toArray();
            for (int i = 0; i < obj.length; i++) {
                HSSFCell cell = row.createCell(i);
                String header = obj[i] == null ? "" : obj[i].toString();
                cell.setCellStyle(headerStyle);
                if(obj[i] != null && obj[i].toString().indexOf("formula") > -1){
                    //公式
                    header = obj[i].toString().replace("formula","");
                }
                HSSFRichTextString text = new HSSFRichTextString(header);
                cell.setCellValue(text);
            }
        }
        // 遍历数据集合，产生数据行
        Iterator<T> it = dataSet.iterator();
        for(int i=1; it.hasNext(); i++){
            row = sheet.createRow(i);
            T t = it.next();
            //BeanMap bm = new BeanMap(t);
            Map b = PropertyUtils.describe(t);
            Map c = new TreeMap();
            //排序
            c.putAll(b);
            writeRow(row, c, map, dateFormat, t);
        }
        workbook.write(out);
    }
    /**
     * <p>Discription:[写一行]</p>
     * @param row 行
     * @param keyValues 一个beanMap对象，包含着这一行中的所有数据
     * @param properties 哪些值需要写入行
     * @param dateFormat 日期格式，默认：yyyy-MM-dd
     * @author:[代超]
     * @throws Exception 
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public void writeRow(Row row, Map keyValues, Map properties,String dateFormat, T t) throws Exception{
        //表格内容样式
        CellStyle contentStyle = setContentSheetSysle(row.getSheet().getWorkbook());
        if(keyValues == null || keyValues.size() <1 || row == null){
            return;
        }
        if(dateFormat == null || "".equals(dateFormat.trim())){
            dateFormat = "yyyy-MM-dd";
        }
        Iterator it = keyValues.entrySet().iterator();
        for(int i = 0; it.hasNext(); i++){
            Map.Entry next = (Map.Entry)it.next();
            String dataValue = ObjectUtils.toString(properties.get(next.getKey()), "");
            if(dataValue == null || "".equals(dataValue.trim())){
                //无需导出当前字段
                i--;
                continue;
            }
            Object value = keyValues.get(next.getKey());
            //值为空时仍然创建单元格并且赋予样式。
            Cell cell = row.createCell(i);
            cell.setCellStyle(contentStyle);
            if(value == null){
                //当值为空的时候，不必做其他操作了
                continue;
            }
            if(dataValue.toLowerCase().indexOf("formula") > -1){
                //公式
                String formula = ExcelFormula.parseFormula(value.toString()).replaceAll("-1", String.valueOf(cell.getRowIndex()+1));
                cell.setCellFormula(formula);
            }else{
                Class c = PropertyUtils.getPropertyType(t, next.getKey().toString());
                writeCell(cell,value, c.getSimpleName(),dateFormat);
            }
        }
    }
    
    /**
     * <p>Discription:[写一个单元格]</p>
     * @param cell 单元格
     * @param value 写入的值
     * @param valueType 写入的值的类型
     * @param dateFormat 日期格式，默认yyyy-MM-dd
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public void writeCell(Cell cell, Object value, String valueType, String dateFormat){
        if(cell == null || value == null){
            return;
        }
        if(dateFormat == null || "".equals(dateFormat.trim())){
            dateFormat = "yyyy-MM-dd";
        }
        String cellValue = "";
        if("String".equals(valueType)){
            cellValue = value.toString();
        }else if("int".equals(valueType)){
            cellValue = String.valueOf(value);
        }else if("float".equals(valueType)){
            cellValue = String.valueOf(value);
        }else if("double".equals(valueType)){
            cellValue = String.valueOf(value);
        }else if("Number".equals(valueType)){
            cellValue = String.valueOf(value);
        }else if("BigDecimal".equals(valueType)){
            cellValue = String.valueOf(value);
        }else if("byte[]".equals(valueType)){
            // 有图片时，设置行高为60px;
            cell.getRow().setHeightInPoints(60);
            // 设置图片所在列宽度为80px,注意这里单位的一个换算
            cell.getSheet().setColumnWidth(cell.getColumnIndex(), (short) (35.7 * 80));
            // sheet.autoSizeColumn(i);
            byte[] bsValue = (byte[]) value;
            HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0,
                    1023, 255, (short) 6, cell.getRowIndex(), (short) 6, cell.getRowIndex());
            anchor.setAnchorType(2);
            // 声明一个画图的顶级管理器
            cell.getSheet().createDrawingPatriarch().createPicture(anchor, cell.getSheet().getWorkbook().addPicture(
                    bsValue, HSSFWorkbook.PICTURE_TYPE_JPEG));
            return;
        }else if("Date".equals(valueType)){
            SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
            cellValue = sdf.format(value);
        }else if("boolean".equals(valueType)){
            boolean bool = (Boolean) value;
            if(bool){
                cellValue = "是";
            }else{
                cellValue = "否";
            }
        }else if("Boolean".equals(valueType)){
            boolean bool = (Boolean) value;
            if(bool){
                cellValue = "是";
            }else{
                cellValue = "否";
            }
        }else{
            cellValue = String.valueOf(value);
        }
        cell.setCellValue(cellValue);
        return;
    }
    
    public Object getBeanValue(T t){
        BeanMap bm = new BeanMap(t);
        for(Object propertyName : bm.keySet()){
            System.out.println("Property: " + propertyName + " value : " + bm.get(propertyName) + " property type = " + bm.getType(propertyName.toString()));
            Class clazz = bm.getType(propertyName.toString());
            
            System.out.println(clazz.getName() + "   " + clazz.getSimpleName());
            writeCell(null, bm.get(propertyName), clazz.getSimpleName(), "");
        }
        System.out.println(bm);
        return null;
    }

    /**
     * <p>
     * Discription:[测试用]
     * </p>
     * 
     * @param args
     * @author:[代超]
     * @throws IOException 
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public static void main(String[] args) throws IOException {
        OfficeOperationUtils util = new OfficeOperationUtils();
        //File file = new File("src/账目信息导入模板.xls");
        File file = new File("src/数据标准值.xls");
        
        /**
         * 导出数据
         */
        /*
        List list = new ArrayList();
        byte[] buf = null;
        try {
            BufferedInputStream bis = new BufferedInputStream(
                    new FileInputStream("src/图片1.jpg"));
            buf = new byte[bis.available()];
            while ((bis.read(buf)) != -1) {
                // 读取图片到缓冲池中
            }
        }
        catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        
        Book b = new Book(1000000,"","",50.2f,"","出版社", buf, false, null, "SUM&A&C&,&-&2");
        Book b1 = new Book(1000000,"","",50.2f,"","出版社", buf, false, null, "SUM&A&C&,&-&a");
        list.add(b);
        list.add(b1);
        //Map map = new HashMap();
        Map map = new TreeMap();
        map.put("bookId", "书编号");
        map.put("name", "书名");
        map.put("author", "作者");
        map.put("price", "定价");
        map.put("isbn", "ISBN");
        map.put("pubName", "出版社");
        map.put("preface", "封面");
        map.put("date", "出版日期");
        map.put("formula", "formula合计");
        
        File outPutFile = new File("src/test.xls");
        try{
            if(!outPutFile.exists()){
                outPutFile.createNewFile();
            }
            OutputStream out = new FileOutputStream(outPutFile);
            util.writExcelFile("测试用", list, out, map, null);
            out.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        */
        /**
         * 导入数据
         */
        Map map = util.readExcelFile(util.getWorkBook(file));
        System.out.print(map);
    }
}

