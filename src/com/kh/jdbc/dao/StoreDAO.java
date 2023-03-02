package com.kh.jdbc.dao;

import com.kh.jdbc.util.Common;
import com.kh.jdbc.vo.MenuVO;
import com.kh.jdbc.vo.StoreVO;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

public class StoreDAO {

    Connection conn = null;
    Statement stmt = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    Scanner sc = new Scanner(System.in);


    public List<StoreVO> storeSelect() {
        List<StoreVO> list = new ArrayList<>();
        try {
            conn = Common.getConnection();
            stmt = conn.createStatement();
            String query = "SELECT * FROM STORE";
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                String id = rs.getString("S_ID");
                int pwd = rs.getInt("S_PWD");
                String s_name = rs.getString("S_NAME");
                String k_name = rs.getString("K_NAME");
                String addr = rs.getString("ADDRESS");
                int pos = rs.getInt("POS");
                ;

                StoreVO vo = new StoreVO(id, pwd, s_name, k_name, addr, pos);
                list.add(vo);
            }
            Common.close(rs);
            Common.close(stmt);
            Common.close(conn);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void storeSelectPrint(List<StoreVO> list) {
        for (StoreVO e : list) {
            System.out.println("관리자 아이디 : " + e.getId());
            System.out.println("비밀번호 : " + e.getPwd());
            System.out.println("점포명 : " + e.getS_name());
            System.out.println("대표자 이름 : " + e.getK_name());
            System.out.println("주소 : " + e.getAddr());
            System.out.println("포스번호 : " + e.getPos());

            System.out.println("=========================");
        }
    }

    public boolean isAmin(String id, int pwd) { // 관리자 테이블에서 아이디, 비밀번호 조회 하는 메소드
        boolean isAdmin = false;
        try {
            conn = Common.getConnection();
            String query = "SELECT COUNT(*) FROM STORE WHERE S_ID = ? AND S_PWD = ?";
            // COUNT(*) 일치하는 레코드가 있는지 확인! 만약 반환된 값이 0보다 크면, 일치하는 레코드가 존재한다는 것을 의미
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, id);
            pstmt.setInt(2, pwd);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                int count = rs.getInt(1);
                if (count > 0) {
                    isAdmin = true;
                }
            }
            Common.close(rs);
            Common.close(pstmt);
            Common.close(conn);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return isAdmin;
    }

    //월별 매출 조회 메소드

    public Map<String, Integer> salesByMonth() {
        Map<String, Integer> salesByMonth = new HashMap<>();  // Map의 키 값은 월, 값은 해당 월의 매출액
        try {
            conn = Common.getConnection();
            stmt = conn.createStatement();
            String sql = "SELECT TO_CHAR(OD_DATE, 'MM') AS 월 , SUM(TOTAL_PRICE) AS 월별매출 FROM cs_order GROUP BY TO_CHAR(OD_DATE, 'MM')";
            rs = stmt.executeQuery(sql); // 월별로 그룹화하여 월별매출 조회

            while (rs.next()) {
                String month = rs.getString("월");
                int monthSales = rs.getInt("월별매출");
                salesByMonth.put(month, monthSales); //쿼리문으로부터 월, 월별매출을 받아와 Map에 저장
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return salesByMonth;
    }

    public void salesByMonthPrint(Map<String, Integer> salesByMonth) {
        for (Map.Entry<String, Integer> entry : salesByMonth.entrySet()) { // Map 객체의 월별 매출액 출력
            String month = entry.getKey();
            int sales = entry.getValue();
            System.out.println("-----------------------------------");
            System.out.println(month + " 월 매출 : " + sales + " 원");
            System.out.println("-----------------------------------");
        }
    }


    public int totalSales() {
        int totalSales = 0;
        try {
            conn = Common.getConnection();
            stmt = conn.createStatement();
            String sql = "SELECT SUM(TOTAL_PRICE) as TOTAL_PRICE FROM cs_order";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                totalSales = rs.getInt("TOTAL_PRICE");
            }
            Common.close(rs);
            Common.close(pstmt);
            Common.close(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return totalSales;
    }

    public void totalSalesPrint() {
        StoreDAO sDAO = new StoreDAO();
        int totalSales = sDAO.totalSales();
        System.out.println("-----------------------------------");
        System.out.println("총 매출 : " + totalSales + "원");
        System.out.println("-----------------------------------");
    }


    public List<MenuVO> top5Menu() {
        List<MenuVO> list = new ArrayList<>();
        try {
            conn = Common.getConnection();
            stmt = conn.createStatement();
            String sql = "SELECT M_NAME, M_CNT FROM (SELECT M_NAME, M_CNT FROM MENU ORDER BY M_CNT DESC) WHERE ROWNUM <= 5";
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String mName = rs.getString("M_NAME");
                int mCnt = rs.getInt("M_CNT");
                MenuVO vo = new MenuVO(mName, mCnt);
                list.add(vo);
            }
            Common.close(rs);
            Common.close(stmt);
            Common.close(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    public void top5MenuPrint(List<MenuVO> menuList) {
        System.out.println("============ TOP 5 MENU =============");

        for (int i = 0; i < menuList.size(); i++) { // 내림차순으로 정렬해서 조회한 결과를 list에 담은 것을
            MenuVO vo = menuList.get(i); // list 순회하면서 객체에서 메뉴명을 가져와 출력
            System.out.println((i + 1) + "위 : " + vo.getName() );
            // System.out.println((i + 1) + "위 : " + vo.getName() + " - " + vo.getCnt() + " 개 판매 "); //- 판매개수까지 출력
        }
        System.out.println("-----------------------------------");
    }


    public void printStock() { // 재고조회
        try { conn = Common.getConnection();
            String sql = "SELECT M_NAME, STOCK FROM MENU ORDER BY STOCK ";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            System.out.println("-----------------------------------");
            while (rs.next()) {
                String menuName = rs.getString("M_NAME");
                int stock = rs.getInt("STOCK");
                if (stock == 0) {
                    System.out.printf("%s - (품절)\n", menuName);
                } else {
                    System.out.printf("%s - 재고 %d개 \n", menuName, stock);
                }
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("-----------------------------------");
    }
}







