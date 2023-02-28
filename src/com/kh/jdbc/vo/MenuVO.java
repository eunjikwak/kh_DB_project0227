package com.kh.jdbc.vo;

public class MenuVO {
    int no;
    String name;
    String desc;
    int stock;
    int cnt;

    public MenuVO(int no, String name, String desc, int stock, int cnt) {
        this.no = no;
        this.name = name;
        this.desc = desc;
        this.stock = stock;
        this.cnt = cnt;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }
}
