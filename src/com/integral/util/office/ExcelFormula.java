package com.integral.util.office;

/**
 * Excel公式解析类
 * 
 * @author swpigris81@126.com
 * 
 */
public class ExcelFormula {
    /** 起始单元格列：A */
    private String beginCell;
    /** 终止单元格列：C */
    private String endCell;
    /** 公式：SUM,AVERAGE等Excel通用公式 */
    private String formula;
    /** 起始单元格行：1 */
    private int colIndex;
    /** 分隔符：,: */
    private String comma;
    /** 操作符：+-*\/ */
    private String operater;

    public String getBeginCell() {
        return beginCell;
    }

    public void setBeginCell(String beginCell) {
        this.beginCell = beginCell;
    }

    public String getEndCell() {
        return endCell;
    }

    public void setEndCell(String endCell) {
        this.endCell = endCell;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public int getColIndex() {
        return colIndex;
    }

    public void setColIndex(int colIndex) {
        this.colIndex = colIndex;
    }

    public String getComma() {
        return comma;
    }

    public void setComma(String comma) {
        this.comma = comma;
    }

    public String getOperater() {
        return operater;
    }

    public void setOperater(String operater) {
        this.operater = operater;
    }

    /**
     * 构造函数
     * 
     * @author swpigris81@126.com
     * @param beginCell
     *            起始单元格列
     * @param endCell
     *            终止单元格列
     * @param formula
     *            公式：SUM,AVERAGE等Excel通用公式
     * @param colIndex
     *            起始单元格行：1
     * @param comma
     *            分隔符：,:
     * @param operater
     *            操作符：+-*\/
     */
    public ExcelFormula(String beginCell, String endCell, String formula,
            int colIndex, String comma, String operater) {
        this.beginCell = beginCell;
        this.endCell = endCell;
        this.formula = formula;
        this.colIndex = colIndex;
        this.comma = comma;
        this.operater = operater;
    }

    public ExcelFormula() {

    }

    /**
     * 组装公式
     * @author swpigris81@126.com Description:
     * @param beginCell
     *            起始单元格列
     * @param endCell
     *            终止单元格列
     * @param formula
     *            公式：SUM,AVERAGE等Excel通用公式
     * @param colIndex
     *            起始单元格行：1
     * @param comma
     *            分隔符：,:
     * @param operater
     *            操作符：+-*\/
     * @return
     */
    public static String parseFormula(String beginCell, String endCell,
            String formula, int colIndex, String comma, String operater) {
        String formulas = "";
        StringBuffer sb = new StringBuffer();
        sb.append(formula);
        sb.append("(");
        sb.append(beginCell);
        sb.append(colIndex);
        sb.append(comma);
        if (!(operater == null || "".equals(operater))) {
            sb.append(operater);
        }
        sb.append(endCell);
        sb.append(colIndex);
        sb.append(")");
        formulas = sb.toString();
        return formulas;
    }

    /**
     * 需要对参数formulaString进行解析，以&符号分隔,最少需要4个&符号，少一个都不行
     * 
     * @author swpigris81@126.com Description:
     * @param formulaString
     *            要进行解析的公司表达式
     * @return
     * @throws Exception 
     */
    public static String parseFormula(String formulaString) throws Exception {
        try {
            String formulas = "";
            if (formulaString == null || "".equals(formulaString)
                    || formulaString.indexOf("&") < 0) {
                return formulas;
            }

            String fs[] = formulaString.split("&");
            if (fs == null || fs.length < 1) {
                return formulas;
            }
            String formula = fs[0];
            String beginCell = fs[1];
            String endCell = fs[2];
            int colIndex = Integer.parseInt(fs[3]);
            String comma = fs[4];
            String operater = "";
            if (fs.length > 5) {
                operater = fs[5];
            }
            return parseFormula(beginCell, endCell, formula, colIndex, comma,
                    operater);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 测试函数
     * @throws Exception 
     */
    public static void main(String[] args) throws Exception {
        // SUM(A2,-C2)
        System.out.println(parseFormula("A", "C", "SUM", 2, ",", "-"));
        // SUM(A3,-C3)
        System.out.println(parseFormula("SUM&A&C&3&,&-"));
        // SUM(A3,C3)
        System.out.println(parseFormula("SUM&A&C&3&,&"));
        // SUM(A3,C3)
        System.out.println(parseFormula("SUM&A&C&3&,"));
        // ""
        System.out.println(parseFormula("&1&"));
    }
}
