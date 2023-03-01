package com.kh.jdbc;

import java.sql.SQLException;
import java.util.Scanner;

public class Main{
    public static void main(String[] args) throws SQLException {
        Scanner sc = new Scanner(System.in);
        Kiosk kio = new Kiosk();

        while (true) {
            System.out.println("========== [BR ICE CREAM] ==========");
            System.out.println("[메뉴를 선택 하세요]");
            System.out.print("[1]주문하기 [2] 회원정보, [3]관리자모드, [4]종료 :");
            int sel = sc.nextInt();
            switch (sel) {
                case 1 :
                   kio.orderMode();
                    break;
                case 2 :
                   kio.memberMode();
                    break;
                case 3 :
                    kio.systemMode();
                    break;
                case 4 :
                   System.exit(0);
                    break;
            }
        }








    }
}

