package com.kh.jdbc;

import com.kh.jdbc.dao.*;
import com.kh.jdbc.vo.*;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Kiosk {
    CustomerDAO cDao = new CustomerDAO();
    MenuDAO mDao = new MenuDAO();
    OptionDAO opDao = new OptionDAO();
    OrderDAO orDao = new OrderDAO();
    StoreDAO sDao = new StoreDAO();
    Scanner sc = new Scanner(System.in);

    //주문하기
    public void orderMode() {
        boolean isOrder = true;

        List<MenuVO> mnlist = null;
        String id = null;
        int t_price = 0;
        List<String> mName = new ArrayList<>();
        List<String> sName = new ArrayList<>();
        String size = null;
        String mName_rst = "";
        String sName_rst = "";
        int t_point = 0;

        while (isOrder) {
            System.out.println("============================  [ 주 문 하 기 ] ============================");
            //옵션 선택
            List<OptionVO> oplist = opDao.optionSelect();
            opDao.optionSizePrint(oplist);
            System.out.println();
            System.out.print("사이즈 선택 : ");
            int size_n = sc.nextInt();
            size = opDao.optionFind(size_n).getSize();
            //사이즈를 저장할 리스트
            sName.add(size);
            t_price += opDao.optionFind(size_n).getPrice();
            System.out.println(size + "를 선택하셨습니다.");
            System.out.println("----------------------------- 메뉴 LIST  ------------------------------- ");
            //맛 선택
            mnlist = mDao.MenuSelect();
            mDao.menuOrderPrint(mnlist);
            System.out.println(size_n + "가지 맛을 입력해 주세요.");

            for (int i = 0; i < size_n; i++) {
                System.out.print((i + 1) + "번째 맛 : ");
                int menu_n = sc.nextInt();
                String name = mDao.menuFind(menu_n).getName();
                int stock = mDao.menuFind(menu_n).getStock();
                int saleCnt = mDao.menuFind(menu_n).getCnt();
                mName.add(name);
                //재고 수량 업데이트
                mDao.stockUpdate(stock, saleCnt, name);
            }
            System.out.println("선택한 맛 : " + mName.toString());
            System.out.println("------------------------------------------------------------------------");
            // 추가 유무
            System.out.print("제품을 추가 하시겠습니까 ? [1]Yes [2]No  : ");
            int answer1 = sc.nextInt();
            isOrder = (answer1 == 1) ? true : false;
        }
        System.out.println("------------------------------------------------------------------------");
        //포인트 적립 유무
        System.out.print("포인트를 적립 하시겠습니까 ?  [1]Yes [2]No : ");
        int answer2 = sc.nextInt();
        boolean isPoint = (answer2 == 1) ? true : false;
        CustomerVO vo = null;
        if (isPoint) {
            //포인트 번호 4자리 입력
            System.out.print("전화번호 뒷 4자리를 입력해 주세요 : ");
            id = sc.next();
            vo = cDao.customerPointSelect(id);
            t_point = vo.getTotalPoint();
        } else {
            System.out.println("***** 회원가입 하시면 더 많은 혜택을 보실 수 있습니다. ****");
            System.out.print("회원 가입을 하시겠습니까?  [1]Yes [2]No : ");
            int answer3 = sc.nextInt();
            boolean isJoin = (answer3 == 1) ? true : false;
            if (isJoin) {
                cDao.customerInsert();
                System.out.print("전화번호 뒷 4자리를 입력해 주세요 : ");
                id = sc.next();
                vo = cDao.customerPointSelect(id);
                t_point = vo.getTotalPoint();
            } else {
                id = "";
                t_point = 0;
            }
        }
        // 스푼개수 입력
        System.out.print("스푼 개수 입력 [최대 10개] : ");
        int spoon = sc.nextInt();
        //결제방법 선택
        System.out.print("결제방법을 선택 하세요 [카드 / 현금] : ");
        String payment = sc.next();
        //주문완료
        System.out.println("주문이 완료되었습니다.");
        // 아이스크림 이름 한번에 문자열에 담기
        for (String e : mName) {
            mName_rst += e + " / ";
        }
        //사이즈 이름 한번에 문자열에 담기
        for (String e : sName) {
            sName_rst += e + " / ";
        }

        //마지막 / 지우기
        mName_rst = mName_rst.substring(0, mName_rst.length() - 3);
        sName_rst = sName_rst.substring(0, sName_rst.length() - 3);
        //주문번호 구하기
        List<OrderVO> orderNo = orDao.orderSelect();


        //고객 정보 포인트에 누적 포인트 업데이트 (id가 존재했을때만)
        if (id != "") {
            cDao.pointUpdate(t_price, t_point, id);
            //업데이트 된 포인트 다시 조회
            vo = cDao.customerPointSelect(id);
            t_point = vo.getTotalPoint();
        }

        //주문 내역 테이블에 행 추가
        orDao.orderInsert(orderNo.size(),sName_rst,mName_rst,spoon,payment,t_price,id,t_point);
        System.out.println("------------------------------------------------------------------------");

        //영수증 출력 유무
        System.out.print("영수증을 출력하시겠습니까? [1]Yes [2]No : ");
        int answer4= sc.nextInt();
        boolean isReceipt  = (answer4 == 1)? true:false;
        if(isReceipt){
            orDao.receiptPrint();
        }
        System.out.println("------------------------------------------------------------------------");
    }


        //회원정보조회
        public void memberMode () {
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
        public void systemMode () throws SQLException {
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
                        }
                        break;

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
                        System.out.print("(1) 메뉴 등록 (2) 메뉴 삭제 (3) 메뉴 조회 : ");
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
                        break;

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

