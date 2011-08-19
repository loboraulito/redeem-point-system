package com.integral.system.codelist.bean;

/** 
 * <p>Description: [数据标准值]</p>
 * @author  <a href="mailto: swpigris81@126.com">代超</a>
 * @version $Revision$ 
 */
public class CodeListData {
    private String dataId;
    private String dataKey;
    private String dataValue;
    private String codeId;
    private String codeName;
    private String parentDataKey;
    private String parentDataValue;
    private String remark;
    
    public CodeListData(String dataId, String dataKey, String dataValue, String codeId, String codeName,
            String parentDataKey, String parentDataValue, String remark) {
        this.dataId = dataId;
        this.dataKey = dataKey;
        this.dataValue = dataValue;
        this.codeId = codeId;
        this.codeName = codeName;
        this.parentDataKey = parentDataKey;
        this.parentDataValue = parentDataValue;
        this.remark = remark;
    }
    
    public CodeListData() {
        
    }

    /**
     * <p>Discription:[方法功能中文描述]</p>
     * @return String dataId.
     */
    public String getDataId() {
        return dataId;
    }
    /**
     * <p>Discription:[方法功能中文描述]</p>
     * @param dataId The dataId to set.
     */
    public void setDataId(String dataId) {
        this.dataId = dataId;
    }
    /**
     * <p>Discription:[方法功能中文描述]</p>
     * @return String dataValue.
     */
    public String getDataValue() {
        return dataValue;
    }
    /**
     * <p>Discription:[方法功能中文描述]</p>
     * @param dataValue The dataValue to set.
     */
    public void setDataValue(String dataValue) {
        this.dataValue = dataValue;
    }
    /**
     * <p>Discription:[方法功能中文描述]</p>
     * @return String codeId.
     */
    public String getCodeId() {
        return codeId;
    }
    /**
     * <p>Discription:[方法功能中文描述]</p>
     * @param codeId The codeId to set.
     */
    public void setCodeId(String codeId) {
        this.codeId = codeId;
    }
    
    /**
     * <p>Discription:[方法功能中文描述]</p>
     * @return String codeName.
     */
    public String getCodeName() {
        return codeName;
    }
    /**
     * <p>Discription:[方法功能中文描述]</p>
     * @param codeName The codeName to set.
     */
    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }
    /**
     * <p>Discription:[方法功能中文描述]</p>
     * @return String dataKey.
     */
    public String getDataKey() {
        return dataKey;
    }
    /**
     * <p>Discription:[方法功能中文描述]</p>
     * @param dataKey The dataKey to set.
     */
    public void setDataKey(String dataKey) {
        this.dataKey = dataKey;
    }
    /**
     * <p>Discription:[方法功能中文描述]</p>
     * @return String parentDataKey.
     */
    public String getParentDataKey() {
        return parentDataKey;
    }
    /**
     * <p>Discription:[方法功能中文描述]</p>
     * @param parentDataKey The parentDataKey to set.
     */
    public void setParentDataKey(String parentDataKey) {
        this.parentDataKey = parentDataKey;
    }
    /**
     * <p>Discription:[方法功能中文描述]</p>
     * @return String parentDataValue.
     */
    public String getParentDataValue() {
        return parentDataValue;
    }
    /**
     * <p>Discription:[方法功能中文描述]</p>
     * @param parentDataValue The parentDataValue to set.
     */
    public void setParentDataValue(String parentDataValue) {
        this.parentDataValue = parentDataValue;
    }
    /**
     * <p>Discription:[方法功能中文描述]</p>
     * @return String remark.
     */
    public String getRemark() {
        return remark;
    }
    /**
     * <p>Discription:[方法功能中文描述]</p>
     * @param remark The remark to set.
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
}