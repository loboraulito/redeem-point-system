package com.integral.applications.account.bean;
// default package



/**
 * BalanceRight entity. @author MyEclipse Persistence Tools
 */

public class BalanceRight  implements java.io.Serializable {


    // Fields    

     private String balancerightid;
     private String userid;
     private String username;
     private String allowuserid;
     private String allowusername;


    // Constructors

    /** default constructor */
    public BalanceRight() {
    }

    
    /** full constructor */
    public BalanceRight(String userid, String username, String allowuserid, String allowusername) {
        this.userid = userid;
        this.username = username;
        this.allowuserid = allowuserid;
        this.allowusername = allowusername;
    }

   
    // Property accessors

    public String getBalancerightid() {
        return this.balancerightid;
    }
    
    public void setBalancerightid(String balancerightid) {
        this.balancerightid = balancerightid;
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

    public String getAllowuserid() {
        return this.allowuserid;
    }
    
    public void setAllowuserid(String allowuserid) {
        this.allowuserid = allowuserid;
    }

    public String getAllowusername() {
        return this.allowusername;
    }
    
    public void setAllowusername(String allowusername) {
        this.allowusername = allowusername;
    }
   








}