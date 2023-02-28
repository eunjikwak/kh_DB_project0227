package com.kh.jdbc.vo;

import java.math.BigDecimal;

public class CustomerVO {
    private String cid;
    private String cname;
    private String phone;
    private BigDecimal totalPoint;

    public CustomerVO(String cid, String cname, String phone, BigDecimal totalPoint) {
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

    public BigDecimal getTotalPoint() {
        return totalPoint;
    }

    public void setTotalPoint(BigDecimal totalPoint) {
        this.totalPoint = totalPoint;
    }
}



