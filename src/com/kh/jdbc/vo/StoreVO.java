package com.kh.jdbc.vo;

public class StoreVO {
    String id;
    int pwd;
    String s_name;
    String k_name;
    String addr;
    int pos;

    public StoreVO(String id, int pwd, String s_name, String k_name, String addr, int pos) {
        this.id = id;
        this.pwd = pwd;
        this.s_name = s_name;
        this.k_name = k_name;
        this.addr = addr;
        this.pos = pos;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPwd() {
        return pwd;
    }

    public void setPwd(int pwd) {
        this.pwd = pwd;
    }

    public String getS_name() {
        return s_name;
    }

    public void setS_name(String s_name) {
        this.s_name = s_name;
    }

    public String getK_name() {
        return k_name;
    }

    public void setK_name(String k_name) {
        this.k_name = k_name;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }
}
