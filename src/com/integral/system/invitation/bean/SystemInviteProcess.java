/*
 * XXXX系统 Copyright (c) 2009 HZH All Rights Reserved.
 */
package com.integral.system.invitation.bean;

import java.util.Date;

/**
 * <br>
 * ����system_invite_process<br>
 * @author Hibernate Tools 3.4.0.CR1
 * @version 1.0
 * @since Dec 23, 2011 2:49:55 PM
 */
public class SystemInviteProcess implements java.io.Serializable {
    /**
     * UID
     */
    private static final long serialVersionUID = 1L;

    /**
     * ??.
     */
    private String id;

    /**
     * ?????.
     */
    private String sponsor;

    /**
     * ?????.
     */
    private String recipient;

    /**
     * ??????.
     */
    private Date sponsorTime;

    /**
     * ??????.
     */
    private Date processTime;

    /**
     * ??????.
     */
    private String processStatus;

    /**
     * ??????.
     */
    private String invitationMenu;

    /**
     * ??????.
     */
    private String processResultCode;

    /**
     * ????.
     */
    private String invitationEvent;

    /**
     * ????.
     */
    private String invitationReason;

    /**
     * ???.
     */
    private String nextaction;

    public SystemInviteProcess() {
    }

    public SystemInviteProcess(String id) {
        this.id = id;
    }

    public SystemInviteProcess(String id, String sponsor, String recipient, Date sponsorTime, Date processTime,
            String processStatus, String invitationMenu, String processResultCode, String invitationEvent,
            String invitationReason, String nextaction) {
        this.id = id;
        this.sponsor = sponsor;
        this.recipient = recipient;
        this.sponsorTime = sponsorTime;
        this.processTime = processTime;
        this.processStatus = processStatus;
        this.invitationMenu = invitationMenu;
        this.processResultCode = processResultCode;
        this.invitationEvent = invitationEvent;
        this.invitationReason = invitationReason;
        this.nextaction = nextaction;
    }

    /**  
     * ȡ�� ??.
     * @return ??
     */
    public String getId() {
        return this.id;
    }

    /**
     * ���� ??.
     * @param id ??
     */
    public void setId(String id) {
        this.id = id;
    }

    /**  
     * ȡ�� ?????.
     * @return ?????
     */
    public String getSponsor() {
        return this.sponsor;
    }

    /**
     * ���� ?????.
     * @param sponsor ?????
     */
    public void setSponsor(String sponsor) {
        this.sponsor = sponsor;
    }

    /**  
     * ȡ�� ?????.
     * @return ?????
     */
    public String getRecipient() {
        return this.recipient;
    }

    /**
     * ���� ?????.
     * @param recipient ?????
     */
    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    /**  
     * ȡ�� ??????.
     * @return ??????
     */
    public Date getSponsorTime() {
        return this.sponsorTime;
    }

    /**
     * ���� ??????.
     * @param sponsorTime ??????
     */
    public void setSponsorTime(Date sponsorTime) {
        this.sponsorTime = sponsorTime;
    }

    /**  
     * ȡ�� ??????.
     * @return ??????
     */
    public Date getProcessTime() {
        return this.processTime;
    }

    /**
     * ���� ??????.
     * @param processTime ??????
     */
    public void setProcessTime(Date processTime) {
        this.processTime = processTime;
    }

    /**  
     * ȡ�� ??????.
     * @return ??????
     */
    public String getProcessStatus() {
        return this.processStatus;
    }

    /**
     * ���� ??????.
     * @param processStatus ??????
     */
    public void setProcessStatus(String processStatus) {
        this.processStatus = processStatus;
    }

    /**  
     * ȡ�� ??????.
     * @return ??????
     */
    public String getInvitationMenu() {
        return this.invitationMenu;
    }

    /**
     * ���� ??????.
     * @param invitationMenu ??????
     */
    public void setInvitationMenu(String invitationMenu) {
        this.invitationMenu = invitationMenu;
    }

    /**  
     * ȡ�� ??????.
     * @return ??????
     */
    public String getProcessResultCode() {
        return this.processResultCode;
    }

    /**
     * ���� ??????.
     * @param processResultCode ??????
     */
    public void setProcessResultCode(String processResultCode) {
        this.processResultCode = processResultCode;
    }

    /**  
     * ȡ�� ????.
     * @return ????
     */
    public String getInvitationEvent() {
        return this.invitationEvent;
    }

    /**
     * ���� ????.
     * @param invitationEvent ????
     */
    public void setInvitationEvent(String invitationEvent) {
        this.invitationEvent = invitationEvent;
    }

    /**  
     * ȡ�� ????.
     * @return ????
     */
    public String getInvitationReason() {
        return this.invitationReason;
    }

    /**
     * ���� ????.
     * @param invitationReason ????
     */
    public void setInvitationReason(String invitationReason) {
        this.invitationReason = invitationReason;
    }

    /**  
     * ȡ�� ???.
     * @return ???
     */
    public String getNextaction() {
        return this.nextaction;
    }

    /**
     * ���� ???.
     * @param nextaction ???
     */
    public void setNextaction(String nextaction) {
        this.nextaction = nextaction;
    }

}
