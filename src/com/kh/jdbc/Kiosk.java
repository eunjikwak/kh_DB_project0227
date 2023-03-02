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
        boolean isAdmin = false;


        while (isOrder) {
            System.out.println("============================  [ 주 문 하 기 ] ============================");
            //옵션 선택
            List<OptionVO> oplist = opDao.optionSelect();
            opDao.optionSizePrint(oplist);
            System.out.println();
            int size_n = 0;
            while (true) {
                System.out.print("사이즈 선택 : ");
                size_n = sc.nextInt();
                if(size_n >0  && size_n < 7) break;
                System.out.println("사이즈를 잘못 입력하셨습니다. 다시입력해주세요.");
            }
            size = opDao.optionFind(size_n).getSize();
            //사이즈를 저장할 리스트
            sName.add(size);
            t_price += opDao.optionFind(size_n).getPrice();
            System.out.println(size + "를 선택하셨습니다.");
            System.out.println("----------------------------- 메 뉴 LIST  ------------------------------- ");
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
            System.out.print("제품을 추가 하시겠습니까 ? [1] Yes [2] No  : ");
            int answer1 = sc.nextInt();
            isOrder = (answer1 == 1) ? true : false;
        }
        System.out.println("------------------------------------------------------------------------");
        //포인트 적립 유무
        System.out.print("포인트를 적립 하시겠습니까 ?  [1] Yes [2] No : ");
        int answer2 = sc.nextInt();
        boolean isPoint = (answer2 == 1) ? true : false;
        CustomerVO vo = null;
        if (isPoint) {
                id = cDao.inputID();
                vo = cDao.customerPointSelect(id);
                t_point = vo.getTotalPoint();
                System.out.println(vo.getCname() +"님 잔여 포인트 : "+t_point+" point 입니다.");

        } else {
            System.out.println("***************** 회원 가입 하시면 더 많은 혜택을 보실 수 있습니다. *****************");
            System.out.print("회원 가입을 하시겠습니까?  [1] Yes [2] No : ");
            int answer3 = sc.nextInt();
            boolean isJoin = (answer3 == 1) ? true : false;
            if (isJoin) {
                cDao.customerInsert();
                id = cDao.inputID();
                vo = cDao.customerPointSelect(id);
                t_point = vo.getTotalPoint();
                System.out.println(vo.getCname() +"님 잔여 포인트 : "+t_point+" point 입니다.");

            } else {
                id = "";
                t_point = 0;
            }
        }
        int spoon;
        String payment = "";
        while (true){
            // 스푼개수 입력
            System.out.print("스푼 개수 입력 [최대 10개] : ");
            spoon = sc.nextInt();
            if(spoon<=10)break;
            System.out.println("잘못 입력하셨습니다. 다시입력해주세요.");
        }

        while(true){
            //결제방법 선택
            System.out.print("결제방법을 선택 하세요 [카드 / 현금] : ");
            payment = sc.next();
            if(payment.equals("카드") || payment.equals("현금"))break;
            System.out.println("잘못 입력하셨습니다. 다시입력해주세요.");
        }

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
        System.out.print("영수증을 출력하시겠습니까? [1] Yes [2] No : ");
        int answer4= sc.nextInt();
        boolean isReceipt  = (answer4 == 1)? true:false;
        if(isReceipt){
            receiptPrint(mName_rst,spoon,payment,t_price,sName,vo);

        }
        System.out.println("------------------------------------------------------------------------");
    }

    public void receiptPrint(String mName, int spoon, String payment, int t_total, List<String> opSize,CustomerVO vo) {
        String opName;
        int price;
        System.out.println("============================  [ 영  수  증 ] ============================");
        System.out.println("[ 주 문 번 호 ]");
        List<StoreVO> storeVo = sDao.storeSelect();
        sDao.storeSelectPrint(storeVo);
        // 주문시간
        java.util.Date now = new Date();
        System.out.println(now);
        System.out.println("------------------------------------------------------------------------");
        System.out.println("           제품명                                 금액  ");
        System.out.println("------------------------------------------------------------------------");

        for(String e : opSize){
            System.out.printf("%-30s",opDao.optionFind(e).getSize());
            System.out.printf("               %-10s\n",opDao.optionFind(e).getPrice());

        }
        System.out.println("------------------------------------------------------------------------");
        String[] menuArr = mName.split("/ ");
        System.out.println("[ 아 이 스 크 림 맛 ]");
        for(int i = 0 ; i < menuArr.length; i++) {
            System.out.println(menuArr[i]);
        }
        System.out.println("------------------------------------------------------------------------");
        System.out.println("스푼개수 (최대 10개) : " + spoon + "개");
        System.out.println("결제 방법 : " + payment);
        System.out.println("총 합계 : " + t_total + "원");
        if(vo != null){
            System.out.println("------------------------------------------------------------------------");
            System.out.println("[ 고   객    정    보 ]");
            System.out.println(vo.getCname() + "님 ");
            System.out.println("적립 포인트 : " + t_total/100 + " point");
            System.out.println("잔여 포인트 : " + vo.getTotalPoint()+ " point");
        }


    }


        //회원정보조회
    public void memberMode () {
        while (true) {
            System.out.println("[메뉴를 선택 하세요]");
            System.out.print("[1] 회원가입 [2] 포인트 조회 [3] 뒤로 가기 : ");
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
                case 3 :
                    return;
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
                System.out.println("==========================  [ 관 리 자  모 드 ]  ==========================");
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
            System.out.println("========================== [ 관리자  메뉴  선택 ] ==========================");
            System.out.print("[1] 매출 조회 [2] 재고 조회 [3] 주문내역 조회 [4] 메뉴 관리 [5] 회원 관리 [6] 뒤로 가기 : ");
            int sel = sc.nextInt();
            switch (sel) {
                case 1:
                    System.out.println("============================ [ 매 출  조 회 ] ============================");
                    System.out.print("[1] 월별매출 조회 [2] 총 매출 조회 [3] 아이스크림 판매 순위  : ");
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
                    System.out.println("============================ [ 메 뉴  관 리 ] =============================");
                    System.out.print("[1] 메뉴 등록 [2] 메뉴 삭제 [3] 메뉴 조회 : ");
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
                    System.out.println("========================== [ 회 원  관 리 ] ==========================");
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
                    break;
                case 6 :
                    return;
            }
        }
    }

}

