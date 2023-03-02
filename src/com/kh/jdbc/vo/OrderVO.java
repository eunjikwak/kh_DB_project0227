package com.kh.jdbc.vo;

public class OrderVO {
    int no;
    String date;
    String size;
    String mName;
    int spoon;
    String payment;
    int total_price;
    String id;
    int cs_point;
    int total_point;

    public OrderVO(int no, String date, String size, String mName, int spoon, String payment, int total_price, String id, int cs_point, int total_point) {
        this.no = no;
        this.date = date;
        this.size = size;
        this.mName = mName;
        this.spoon = spoon;
        this.payment = payment;
        this.total_price = total_price;
        this.id = id;
        this.cs_point = cs_point;
        this.total_point = total_point;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public int getSpoon() {
        return spoon;
    }

    public void setSpoon(int spoon) {
        this.spoon = spoon;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public int getTotal_price() {
        return total_price;
    }

    public void setTotal_price(int total_price) {
        this.total_price = total_price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getCs_point() {
        return cs_point;
    }

    public void setCs_point(int cs_point) {
        this.cs_point = cs_point;
    }

    public int getTotal_point() {
        return total_point;
    }

    public void setTotal_point(int total_point) {
        this.total_point = total_point;
    }
}
