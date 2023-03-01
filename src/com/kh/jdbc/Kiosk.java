package com.kh.jdbc;

import com.kh.jdbc.dao.*;
import com.kh.jdbc.vo.CustomerVO;
import com.kh.jdbc.vo.MenuVO;
import com.kh.jdbc.vo.OrderVO;
import com.kh.jdbc.vo.StoreVO;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Kiosk {
    CustomerDAO cDao = new CustomerDAO();
    MenuDAO mDao = new MenuDAO();
    OptionDAO opDao = new OptionDAO();
    OrderDAO orDao = new OrderDAO();
    StoreDAO sDao = new StoreDAO();
    Scanner sc = new Scanner(System.in);

    //주문하기
    public void orderMode() {
    }



    //회원정보조회
    public void memberMode() {
        System.out.println("회원정보 메소드 호출");
        while (true) {
            System.out.println("[메뉴를 선택 하세요]");
            System.out.print("[1]회원가입 [2] 포인트 조회 : ");
            int sel = sc.nextInt();
            switch (sel) {
                case 1:
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
    public void systemMode() throws SQLException {
        StoreDAO sDAO = new StoreDAO();
        boolean isAdmin = false;
        while (true) {
            if (!isAdmin) {
                System.out.println();
                System.out.println("==================== [관 리 자   모 드] =====================");
                System.out.print("아이디를 입력하세요 : ");
                String id = sc.next();
                System.out.print("비밀번호를 입력하세요(숫자) : ");
                int pwd = sc.nextInt();
                if (sDAO.isAmin(id, pwd)) {
                    isAdmin = true;
                } else {
                    System.out.println("아이디 또는 비밀번호가 틀렸습니다. 다시 입력해주세요.");
                    continue;
                }
            }
            System.out.println(" =================[ 관 리 자  메 뉴  선 택 ]====================");
            System.out.print("[1] 매출 조회 [2] 재고 조회 [3] 주문내역 조회 [4] 메뉴 관리 [5] 회원 관리 : ");
            int sel = sc.nextInt();
            switch (sel) {
                case 1:
                    System.out.println(" --------------------[ 매 출  조 회 ]---------------------- ");
                    System.out.print("(1) 월별매출 조회 (2) 총 매출 조회 (3) 아이스크림 판매 순위  : ");
                    int c_sel = sc.nextInt();
                    switch (c_sel) {
                        case 1: // 월별 매출 조회 쿼리
                            Map<String, Integer> map = sDao.salesByMonth();
                            sDAO.salesByMonthPrint(map);
                            break;
                        case 2: // 총 매출 조회 쿼리
                            sDAO.totalSalesPrint();
                            break;
                        case 3: // 상위 5개 아이스크림
                            List<MenuVO> mlist = sDao.top5Menu();
                            sDAO.top5MenuPrint(mlist);
                            break;
                    } break;

                case 2:
                    // 재고 조회 쿼리
                    sDAO.printStock();
                    break;

                case 3:
                    //주문 내역 조회
                    List<OrderVO> olist = orDao.orderSelect();
                    orDao.orderSelectPrint(olist);
                    break;
                case 4:
                    System.out.println();
                    System.out.println(" --------------------- [ 메 뉴  관 리 ] ------------------------");
                    System.out.print("(1) 메뉴 등록 (2) 메뉴 삭제 (3) 메뉴 조회 : " );
                    int mSel = sc.nextInt();
                    switch (mSel) {
                        case 1:
                            mDao.menuInsert();
                            System.out.println("메뉴 등록이 완료되었습니다.");
                            System.out.println();
                            break;
                        case 2:
                            // 메뉴 삭제
                            mDao.menuDelete();
                            System.out.println("메뉴 삭제가 완료되었습니다.");
                            System.out.println();
                            break;
                        case 3:
                            List<MenuVO> list = mDao.MenuSelect(); //메뉴명 조회
                            mDao.menuSelectPrint(list);
                            break;
                    }

                case 5:
                    System.out.println();
                    System.out.println(" ----------------------- [ 회 원  관 리 ] --------------------------");
                    System.out.print("[1] 회원 조회 [2] 회원정보 변경 [3] 회원 삭제 : ");
                    int cSel = sc.nextInt();
                    switch (cSel) {
                        case 1:
                            List<CustomerVO> clist = cDao.CustomerSelect();
                            cDao.customerSelectPrint(clist);
                            break;
                        case 2:
                            cDao.customerUpdate();
                            break;
                        case 3:
                            cDao.customerDelete();
                            System.out.println();
                            System.out.println("회원 삭제가 완료되었습니다.");
                            break;
                    }


            }
        }

    }
}
