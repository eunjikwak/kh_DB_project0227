package com.kh.jdbc.vo;



public class CustomerVO {
    private String cid;
    private String cname;
    private String phone;
    private int totalPoint;

    public CustomerVO(String cid, String cname, String phone, int totalPoint) {
        this.cid = cid;
        this.cname = cname;
        this.phone = phone;
        this.totalPoint = totalPoint;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getTotalPoint() {
        return totalPoint;
    }

    public void setTotalPoint(int totalPoint) {
        this.totalPoint = totalPoint;
    }
}



