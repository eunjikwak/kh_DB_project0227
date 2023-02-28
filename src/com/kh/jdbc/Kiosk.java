package com.kh.jdbc;

import com.kh.jdbc.dao.*;
import com.kh.jdbc.vo.CustomerVO;

import java.util.List;
import java.util.Scanner;

public class Kiosk {
    CustomerDAO cDao = new CustomerDAO();
    MenuDAO mDao = new MenuDAO();
    OptionDAO opDao = new OptionDAO();
    OrderDAO orDao = new OrderDAO();
    StoreDAO sDao = new StoreDAO();
    Scanner sc = new Scanner(System.in);

    //주문하기
    public void orderMode(){
        System.out.println("주문하기 메소드 호출");

    }
    //회원정보조회
    public void memberMode(){
        System.out.println("회원정보 메소드 호출");
        while(true){
            System.out.println("[메뉴를 선택 하세요]");
            System.out.print("[1]회원가입 [2] 포인트 조회 :");
            int sel = sc.nextInt();
            switch (sel){
                case 1 :
                    //고객 정보에 생성메소드
                    cDao.customerInsert();
                    System.out.println("회원가입이 완료 되었습니다.");
                    break;
                case 2:
                    //고객 정보에 포인트 조회하기
                    // 전화번호 뒷자리 4개 입력
                    System.out.print("전화번호 뒷자리(4자리) 입력 : ");
                    String num = sc.next();
                   CustomerVO vo = cDao.customerPointSelect(num);
                   cDao.customerPointPrint(vo);
                    break;
            }
        }


    }
    //관리자모드
    public void systemMode(){
        System.out.println("관리자모드 메소드 호출");


    }

}
