package com.integral.util.office;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.BooleanUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
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
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public Workbook getWorkBook(File excelFile) {
        Workbook wb = null;
        try {
            wb = new HSSFWorkbook(new POIFSFileSystem(new FileInputStream(excelFile)));
        }
        catch (Exception e) {
            try {
                wb = new XSSFWorkbook(new FileInputStream(excelFile));
            }
            catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
            catch (IOException e1) {
                e1.printStackTrace();
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
     * @param header 写入文件的第一行为header, 显示header的各个名称
     * @param dataSet 要写入文件的内容(一个集合). 集合中存放符合javaBean格式的对象。
     *                支持数据类型有基本数据类型及String,Date,byte[](图片数据)
     * @param out 要写入的文件流, 可用于导出, 或者写入硬盘文件
     * @param map 要导出的字段。（格式：字段名 <-> 字段中文名）其中字段名应该是对应javaBean中的某属性。<br>
     *            若该字段为公式的话，必须符合一定的格式：公式名称&计算起始单元格列&计算终止单元格列&计算单元格行&分隔符（,:）&操作符<br>
     *            如：SUM&A&C&2&,&-   它返回的公司是：SUM(A3,-C3)。具体参照：
     *            <code>com.integral.util.office.ExcelFormula</code>
     * @param dateFormat 日期格式(可选)，默认：yyyy-MM-dd
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    @SuppressWarnings("deprecation")
    public void writExcelFile(String sheetName, String [] header, Collection<T> dataSet, OutputStream out, Map map, String dateFormat){
        if(dateFormat==null || "".equals(dateFormat.trim())){
            dateFormat = "yyyy-MM-dd";
        }
        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个表格
        HSSFSheet sheet = workbook.createSheet(sheetName);
        // 设置表格默认列宽度为15个字节
        sheet.setDefaultColumnWidth(15);
        // 设置表格的样式
        CellStyle headerStyle = setRootSheetSysle(workbook);
        CellStyle contentStyle = setContentSheetSysle(workbook);
        
        // 声明一个画图的顶级管理器
        HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
        // 产生表格标题行
        HSSFRow row = sheet.createRow(0);
        for (int i = 0; i < header.length; i++) {
            HSSFCell cell = row.createCell(i);
            cell.setCellStyle(headerStyle);
            HSSFRichTextString text = new HSSFRichTextString(header[i]);
            cell.setCellValue(text);
        }
    }

    /**
     * <p>
     * Discription:[测试用]
     * </p>
     * 
     * @param args
     * @author:[代超]
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public static void main(String[] args) {
        OfficeOperationUtils util = new OfficeOperationUtils();
        //File file = new File("src/账目信息导入模板.xls");
        File file = new File("src/账目信息导入模板.xlsx");
        
        System.out.print(util.readExcelFile(util.getWorkBook(file)));
    }

}
