package com.integral.system.systembackup.bean;

public class SystemBackSettingInfo implements java.io.Serializable {
    private String id;
    private String backType;
    private String backValue;
    private String cronValue;
    private String userName;
    private String backTime;
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getBackType() {
        return backType;
    }
    public void setBackType(String backType) {
        this.backType = backType;
    }
    public String getBackValue() {
        return backValue;
    }
    public void setBackValue(String backValue) {
        this.backValue = backValue;
    }
    public String getCronValue() {
        return cronValue;
    }
    public void setCronValue(String cronValue) {
        this.cronValue = cronValue;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getBackTime() {
        return backTime;
    }
    public void setBackTime(String backTime) {
        this.backTime = backTime;
    }
}
