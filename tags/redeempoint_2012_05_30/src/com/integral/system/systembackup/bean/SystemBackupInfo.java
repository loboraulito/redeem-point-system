package com.integral.system.systembackup.bean;

// default package

import java.util.Date;

/**
 * AccountAlert entity. @author MyEclipse Persistence Tools
 */

public class SystemBackupInfo implements java.io.Serializable {

    // Fields

    private String backFileId;

    private String fileName;

    private Date backDate;

    private String backType;

    private String userName;

    private String memo;

    private String filePath;

    public String getBackFileId() {
        return backFileId;
    }

    public void setBackFileId(String backFileId) {
        this.backFileId = backFileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Date getBackDate() {
        return backDate;
    }

    public void setBackDate(Date backDate) {
        this.backDate = backDate;
    }

    public String getBackType() {
        return backType;
    }

    public void setBackType(String backType) {
        this.backType = backType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

}