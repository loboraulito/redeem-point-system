package com.integral.system.user.bean;

/**
 * <p>
 * Description: [描述该类概要功能介绍]
 * </p>
 * 
 * @author <a href="mailto: swpigris81@126.com">Chao Dai</a>
 * @createDate May 17, 2011
 */
public class UserInfo {
    private String userId;
    private String userCode;
    private String userName;
    private String password;
    private String telphoneNo;
    private String phoneNo;
    private String privence;
    private String city;
    private String address;
    private String zip;
    private String email;

    public UserInfo() {
    }

    public UserInfo(String userId, String userCode, String userName,
            String password, String telphoneNo, String phoneNo,
            String privence, String city, String address, String zip,
            String email) {
        this.userId = userId;
        this.userCode = userCode;
        this.userName = userName;
        this.password = password;
        this.telphoneNo = telphoneNo;
        this.phoneNo = phoneNo;
        this.privence = privence;
        this.city = city;
        this.address = address;
        this.zip = zip;
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelphoneNo() {
        return telphoneNo;
    }

    public void setTelphoneNo(String telphoneNo) {
        this.telphoneNo = telphoneNo;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getPrivence() {
        return privence;
    }

    public void setPrivence(String privence) {
        this.privence = privence;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
