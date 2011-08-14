package com.integral.util.office;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.BooleanUtils;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
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
public class OfficeOperationUtils {
    /**
     * <p>
     * Discription:[获取Excel的文档]
     * </p>
     * 
     * @param file
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
