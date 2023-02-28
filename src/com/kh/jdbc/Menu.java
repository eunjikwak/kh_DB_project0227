package com.kh.jdbc;

import com.kh.jdbc.dao.*;

import com.kh.jdbc.vo.CustomerVO;
import com.kh.jdbc.vo.MenuVO;

import java.util.List;
import java.util.Scanner;

public class Menu {
    CustomerDAO cDao = new CustomerDAO();
    MenuDAO mDao = new MenuDAO();
    OptionDAO otDao = new OptionDAO();
    OrderDAO orDao = new OrderDAO();
    StoreDAO sDao = new StoreDAO();
    Scanner sc = new Scanner(System.in);
    public void menu1(){
        while (true) {
            System.out.println("========== [CUSTOMER TABLE Command] ==========");
            System.out.println("메뉴를 선택 하세요 : ");
            System.out.print("[1]SELECT [2] INSERT, [3]UPDATE, [4]DELETE : ");
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
                    cDao.customerUpdate();
                    break;
                case 4 :
                    cDao.customerDelete();
                    return;
            }
        }
    }
    public void menu2(){
        while (true) {
            System.out.println("========== [MENU TABLE Command] ==========");
            System.out.println("메뉴를 선택 하세요 : ");
            System.out.print("[1]SELECT [2] INSERT : ");
            int sel = sc.nextInt();
            switch (sel) {
                case 1 :
                    List<MenuVO> list = mDao.MenuSelect();
                    mDao.menuSelectPrint(list);
                    break;
                case 2 :

                    break;
            }
        }
    }



    public void menu3(){
        while (true) {
            System.out.println("========== [OPTION TABLE Command] ==========");
            System.out.println("메뉴를 선택 하세요 : ");
            System.out.print("[1]SELECT [2] INSERT, [3]UPDATE, [4]DELETE : ");
            int sel = sc.nextInt();
            switch (sel) {
                case 1 :

                    break;
                case 2 :
                    break;
                case 3 :

                    break;
                case 4 :

                    return;
            }
        }
    }
    public void menu4(){
        while (true) {
            System.out.println("========== [ORDER TABLE Command] ==========");
            System.out.println("메뉴를 선택 하세요 : ");
            System.out.print("[1]SELECT [2] INSERT, [3]UPDATE, [4]DELETE : ");
            int sel = sc.nextInt();
            switch (sel) {
                case 1 :
                    break;
                case 2 :
                    break;
                case 3 :
                    break;
                case 4 :

                    return;
            }
        }
    }
    public void menu5(){
        while (true) {
            System.out.println("========== [STORE TABLE Command] ==========");
            System.out.println("메뉴를 선택 하세요 : ");
            System.out.print("[1]SELECT [2] INSERT, [3]UPDATE, [4]DELETE : ");
            int sel = sc.nextInt();
            switch (sel) {
                case 1 :

                    break;
                case 2 :

                    break;
                case 3 :

                    break;
                case 4 :

                    return;
            }
        }
    }


}
