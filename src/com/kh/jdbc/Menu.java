package com.kh.jdbc;

import com.kh.jdbc.dao.CustomerDAO;
import com.kh.jdbc.vo.CustomerVO;

import java.util.List;
import java.util.Scanner;

public class Menu {
    CustomerDAO cDao = new CustomerDAO();
    Scanner sc = new Scanner(System.in);
    public void menu1(){}
    public void menu2(){
        while (true) {
            System.out.println("========== [CUSTOMER TABLE Command] ==========");
            System.out.println("메뉴를 선택 하세요 : ");
            System.out.print("[1]SELECT [2] INSERT, [3]UPDATE, [4]DELETE");
            int sel = sc.nextInt();
            switch (sel) {
                case 1 :
                    List<CustomerVO> list = cDao.CustomerSelect();
                    cDao.customerSelectPrint(list);
                    break;
                case 2 :
                    cDao.customerInsert();
                    break;
                case 3 :
                case 4 :

                    return;
            }
        }

    }
    public void menu3(){
        System.out.println("메뉴 3번 메소드 호출");
    }
    public void menu4(){}
    public void menu5(){}


}
