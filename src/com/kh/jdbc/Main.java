package com.kh.jdbc;

import com.kh.jdbc.dao.CustomerDAO;
import com.kh.jdbc.dao.EmpDAO;
import com.kh.jdbc.vo.CustomerVO;
import com.kh.jdbc.vo.EmpVO;

import java.util.List;
import java.util.Scanner;

public class Main{
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        CustomerDAO cDao = new CustomerDAO();

        Menu menu = new Menu();


        while (true) {
            System.out.println("========== [CUSTOMER TABLE Command] ==========");
            System.out.println("메뉴를 선택 하세요 : ");
            System.out.print("[1] SELECT [2] INSERT, [3]UPDATE, [4]DELETE [5]EXIT : ");
            int sel = sc.nextInt();
            switch (sel) {
                case 1 :
                    List<CustomerVO> list = cDao.CustomerSelect();
                    cDao.customerSelectPrint(list);
                    break;
                case 2 :
                    cDao.customerInsert();
                    break;

                case 3 : menu.menu3();
                case 4 :
                case 5 :
                    System.out.println("메뉴를 종료합니다.");
                    return;
            }
        }

    }
}

