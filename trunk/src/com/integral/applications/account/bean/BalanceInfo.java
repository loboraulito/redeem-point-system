package com.integral.applications.account.bean;
// default package

import java.util.Date;


/**
 * BalanceInfo entity. @author MyEclipse Persistence Tools
 */

public class BalanceInfo  implements java.io.Serializable {


    // Fields    

     private String balanceid;
     private Date begindate;
     private Date enddate;
     private String balanceyear;
     private String balancemonth;
     private String balancetype;
     private Double accountenter;
     private Double accountout;
     private Double accountmargin;
     private Double balance;
     private String remark;
     private String userid;
     private String username;
     private Double alertvalue;


    // Constructors

    public String getUserid() {
		return userid;
	}


	public void setUserid(String userid) {
		this.userid = userid;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	/** default constructor */
    public BalanceInfo() {
    }

    
    /** full constructor */
    public BalanceInfo(Date begindate, Date enddate, String balancetype, Double accountenter, Double accountout, Double accountmargin, Double balance, String remark) {
        this.begindate = begindate;
        this.enddate = enddate;
        this.balancetype = balancetype;
        this.accountenter = accountenter;
        this.accountout = accountout;
        this.accountmargin = accountmargin;
        this.balance = balance;
        this.remark = remark;
    }

   
    // Property accessors

    public String getBalanceid() {
        return this.balanceid;
    }
    
    public void setBalanceid(String balanceid) {
        this.balanceid = balanceid;
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

    public String getBalancetype() {
        return this.balancetype;
    }
    
    public void setBalancetype(String balancetype) {
        this.balancetype = balancetype;
    }

    public Double getAccountenter() {
        return this.accountenter;
    }
    
    public void setAccountenter(Double accountenter) {
        this.accountenter = accountenter;
    }

    public Double getAccountout() {
        return this.accountout;
    }
    
    public void setAccountout(Double accountout) {
        this.accountout = accountout;
    }

    public Double getAccountmargin() {
        return this.accountmargin;
    }
    
    public void setAccountmargin(Double accountmargin) {
        this.accountmargin = accountmargin;
    }

    public Double getBalance() {
        return this.balance;
    }
    
    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getRemark() {
        return this.remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }


	public String getBalanceyear() {
		return balanceyear;
	}


	public void setBalanceyear(String balanceyear) {
		this.balanceyear = balanceyear;
	}


	public String getBalancemonth() {
		return balancemonth;
	}


	public void setBalancemonth(String balancemonth) {
		this.balancemonth = balancemonth;
	}


	public Double getAlertvalue() {
		return alertvalue;
	}


	public void setAlertvalue(Double alertvalue) {
		this.alertvalue = alertvalue;
	}
   








}