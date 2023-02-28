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

        Menu menu = new Menu();

        while (true) {
            System.out.println("========== [CUSTOMER TABLE Command] ==========");
            System.out.println("메뉴를 선택 하세요 : ");
            System.out.print("[1]고객정보 [2] 메뉴, [3]주문내역, [4]옵션 [5]관리자 : ");
            int sel = sc.nextInt();
            switch (sel) {
                case 1 :
                    menu.menu1();
                    break;
                case 2 :
                    menu.menu2();
                    break;
                case 3 : menu.menu3();
                    break;
                case 4 :menu.menu4();
                    break;
                case 5 :menu.menu5();
                    return;
            }
        }

    }
}

