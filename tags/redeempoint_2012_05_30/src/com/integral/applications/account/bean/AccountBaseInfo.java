package com.integral.applications.account.bean;
// default package

import java.util.Date;


/**
 * AccountBaseInfo entity. @author MyEclipse Persistence Tools
 */

public class AccountBaseInfo  implements java.io.Serializable {


    // Fields    

     private String baseinfoid;
     private Date basedate;
     private String baseyear;
     private String basemonth;
     private Double accountenter = 0.0;
     private Double accountout = 0.0;
     private Double accountmargin = 0.0;
     private String remark;
     private String accountcard;
     private String deletetag;
     private String margintag;
     private String userid;
     private String username;
     private Double accountalert;
     private Double accountalertmon;
     private Double accountalertque;
     private Double accountalertyear;
     private String maintype;
     private String setype;


    // Constructors

    public Double getAccountalert() {
		return accountalert;
	}


	public void setAccountalert(Double accountalert) {
		this.accountalert = accountalert;
	}


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


	public String getMargintag() {
		return margintag;
	}


	public void setMargintag(String margintag) {
		this.margintag = margintag;
	}


	public String getDeletetag() {
		return deletetag;
	}


	public void setDeletetag(String deletetag) {
		this.deletetag = deletetag;
	}


	/** default constructor */
    public AccountBaseInfo() {
    }

    
    /** full constructor */
    public AccountBaseInfo(Date basedate, Double accountenter, Double accountout, Double accountmargin, String remark) {
        this.basedate = basedate;
        this.accountenter = accountenter;
        this.accountout = accountout;
        this.accountmargin = accountmargin;
        this.remark = remark;
    }

   
    // Property accessors

    public String getBaseinfoid() {
        return this.baseinfoid;
    }
    
    public void setBaseinfoid(String baseinfoid) {
        this.baseinfoid = baseinfoid;
    }

    public Date getBasedate() {
        return this.basedate;
    }
    
    public void setBasedate(Date basedate) {
        this.basedate = basedate;
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

    public String getRemark() {
        return this.remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }


	public String getBaseyear() {
		return baseyear;
	}


	public void setBaseyear(String baseyear) {
		this.baseyear = baseyear;
	}


	public String getBasemonth() {
		return basemonth;
	}


	public void setBasemonth(String basemonth) {
		this.basemonth = basemonth;
	}


	public Double getAccountalertmon() {
		return accountalertmon;
	}


	public void setAccountalertmon(Double accountalertmon) {
		this.accountalertmon = accountalertmon;
	}


	public Double getAccountalertque() {
		return accountalertque;
	}


	public void setAccountalertque(Double accountalertque) {
		this.accountalertque = accountalertque;
	}


	public Double getAccountalertyear() {
		return accountalertyear;
	}


	public void setAccountalertyear(Double accountalertyear) {
		this.accountalertyear = accountalertyear;
	}


    public String getAccountcard() {
        return accountcard;
    }


    public void setAccountcard(String accountcard) {
        this.accountcard = accountcard;
    }


    public String getMaintype() {
        return maintype;
    }


    public void setMaintype(String maintype) {
        this.maintype = maintype;
    }


    public String getSetype() {
        return setype;
    }


    public void setSetype(String setype) {
        this.setype = setype;
    }

}