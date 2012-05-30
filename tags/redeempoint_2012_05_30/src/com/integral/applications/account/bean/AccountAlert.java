package com.integral.applications.account.bean;
// default package

import java.util.Date;


/**
 * AccountAlert entity. @author MyEclipse Persistence Tools
 */

public class AccountAlert  implements java.io.Serializable {


    // Fields    

     private String alertid;
     private String alerttype;
     private String userid;
     private String username;
     private Double alertvalue;
     private Date begindate;
     private Date enddate;
     private String remark;
     


    // Constructors

    public String getRemark() {
		return remark;
	}


	public void setRemark(String remark) {
		this.remark = remark;
	}


	/** default constructor */
    public AccountAlert() {
    }

    
    /** full constructor */
    public AccountAlert(String alerttype, String userid, String username, Double alertvalue, Date begindate, Date enddate) {
        this.alerttype = alerttype;
        this.userid = userid;
        this.username = username;
        this.alertvalue = alertvalue;
        this.begindate = begindate;
        this.enddate = enddate;
    }

   
    // Property accessors

    public String getAlertid() {
        return this.alertid;
    }
    
    public void setAlertid(String alertid) {
        this.alertid = alertid;
    }

    public String getAlerttype() {
        return this.alerttype;
    }
    
    public void setAlerttype(String alerttype) {
        this.alerttype = alerttype;
    }

    public String getUserid() {
        return this.userid;
    }
    
    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return this.username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }

    public Double getAlertvalue() {
        return this.alertvalue;
    }
    
    public void setAlertvalue(Double alertvalue) {
        this.alertvalue = alertvalue;
    }

    public Date getBegindate() {
        return this.begindate;
    }
    
    public void setBegindate(Date begindate) {
        this.begindate = begindate;
    }

    public Date getEnddate() {
        return this.enddate;
    }
    
    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }
   








}