package com.integral.applications.account.bean;

/** 
 * <p>Description: [描述该类概要功能介绍]</p>
 * @author  <a href="mailto: swpigris81@126.com">代超</a>
 * @version $Revision$ 
 */
public class AccountCardInfo implements java.io.Serializable {

    /**
     * <p>Discription:[字段功能描述]</p>
     */
    private static final long serialVersionUID = 1L;
    /**
     * 账户唯一ID
     */
    private String accountId;
    /**
     * 卡号
     */
    private String cardId;
    /**
     * 账户名称
     */
    private String cardName;
    /**
     * 账户类型
     */
    private String cardType;
    /**
     * 账户状态
     */
    private String cardStatus;
    /**
     * 开户行
     */
    private String cardBank;
    /**
     * 币种
     */
    private String cardCurrency;
    /**
     * 备注
     */
    private String comment;
    /**
     * 余额
     */
    private Double cardBalance;
    /**
     * 持卡人
     */
    private String cardUser;
    
    public String getAccountId() {
        return accountId;
    }
    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
    public String getCardId() {
        return cardId;
    }
    public void setCardId(String cardId) {
        this.cardId = cardId;
    }
    public String getCardName() {
        return cardName;
    }
    public void setCardName(String cardName) {
        this.cardName = cardName;
    }
    public String getCardType() {
        return cardType;
    }
    public void setCardType(String cardType) {
        this.cardType = cardType;
    }
    public String getCardStatus() {
        return cardStatus;
    }
    public void setCardStatus(String cardStatus) {
        this.cardStatus = cardStatus;
    }
    public String getCardBank() {
        return cardBank;
    }
    public void setCardBank(String cardBank) {
        this.cardBank = cardBank;
    }
    public String getCardCurrency() {
        return cardCurrency;
    }
    public void setCardCurrency(String cardCurrency) {
        this.cardCurrency = cardCurrency;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    public Double getCardBalance() {
        return cardBalance;
    }
    public void setCardBalance(Double cardBalance) {
        this.cardBalance = cardBalance;
    }
    public String getCardUser() {
        return cardUser;
    }
    public void setCardUser(String cardUser) {
        this.cardUser = cardUser;
    }
    
}
