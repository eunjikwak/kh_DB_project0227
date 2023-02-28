package com.kh.jdbc.vo;

public class OptionVO {

    String size;
    int option_num;
    int price;

    public OptionVO(String size, int option_num, int price) {
        this.size = size;
        this.option_num = option_num;
        this.price = price;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getOption_num() {
        return option_num;
    }

    public void setOption_num(int option_num) {
        this.option_num = option_num;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
