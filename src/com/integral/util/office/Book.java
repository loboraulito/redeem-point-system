package com.integral.util.office;

import java.util.Date;

public class Book{
    public Book(int bookId, String name, String author, float price,
            String isbn, String pubName, byte[] preface, boolean bool, Date date) {

        super();
        this.author = author;
        this.bookId = bookId;
        this.isbn = isbn;
        this.name = name;
        this.preface = preface;
        this.price = price;
        this.pubName = pubName;
        this.bool = bool;
        this.date = date;
    }
    
    public Book(int bookId, String name, String author, float price,
            String isbn, String pubName) {

        super();
        this.author = author;
        this.bookId = bookId;
        this.isbn = isbn;
        this.name = name;
        this.price = price;
        this.pubName = pubName;
    }
    
    public Book(){
        
    }

    public int bookId;
    private String name;
    private String author;
    private float price;
    private String isbn;
    private String pubName;
    private byte[] preface;
    private boolean bool;
    private Date date;

    /**
     * <p>Discription:[方法功能中文描述]</p>
     * @return Date date.
     */
    public Date getDate() {
        return date;
    }

    /**
     * <p>Discription:[方法功能中文描述]</p>
     * @param date The date to set.
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * <p>Discription:[方法功能中文描述]</p>
     * @return boolean bool.
     */
    public boolean isBool() {
        return bool;
    }

    /**
     * <p>Discription:[方法功能中文描述]</p>
     * @param bool The bool to set.
     */
    public void setBool(boolean bool) {
        this.bool = bool;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getPubName() {
        return pubName;
    }

    public void setPubName(String pubName) {
        this.pubName = pubName;
    }

    public byte[] getPreface() {
        return preface;
    }

    public void setPreface(byte[] preface) {
        this.preface = preface;
    }
}
