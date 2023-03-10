package com.kh.jdbc.dao;

import com.kh.jdbc.util.Common;
import com.kh.jdbc.vo.CustomerVO;
import com.kh.jdbc.vo.MenuVO;
import com.kh.jdbc.vo.OrderVO;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class OrderDAO {
    Connection conn = null;
    Statement stmt = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    Scanner sc = new Scanner(System.in);
    String no = "";
    public List<OrderVO> orderSelect() {

        List<OrderVO> list = new ArrayList<>();
        try {
            conn = Common.getConnection();
            stmt = conn.createStatement();
            String query = "SELECT * FROM CS_ORDER";
            rs = stmt.executeQuery(query);

            while(rs.next()) {
                int no = rs.getInt("OD_NO");
                String date = rs.getString("OD_DATE");
                String size = rs.getString("OP_SIZE");
                String mName = rs.getString("M_NAME");
                int spoon = rs.getInt("SPOON");
                String payment = rs.getString("PAYMENT");
                int total_price = rs.getInt("TOTAL_PRICE");
                String id = rs.getString("CS_ID");
                int cs_point = rs.getInt("CS_POINT");
                int total_point = rs.getInt("TOTAL_POINT");

                OrderVO vo = new OrderVO(no, date, size, mName,spoon,payment,total_price,id, cs_point, total_point);
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
    public void orderSelectPrint(List<OrderVO> list) {
        for(OrderVO e : list) {
            System.out.println("주문 번호 : " + e.getNo());
            System.out.println("주문 날짜 : " + e.getDate());
            System.out.println("통 크기 : " + e.getSize());
            System.out.println("메뉴명 : " + e.getmName());
            System.out.println("스푼 개수 : " + e.getSpoon());
            System.out.println("결제 방법 : " + e.getPayment());
            System.out.println("결제 금액 : " + e.getTotal_price());
            System.out.println("고객 아이디 : " + e.getId());
            System.out.println("적립 포인트 : " + e.getCs_point());
            System.out.println("누적 포인트 : " + e.getTotal_point());
            System.out.println("------------------------------------------------------------------------");
        }
    }

    public String orderNum(int cnt) { // 주문번호 생성하는 메소드
        String orderDate;
        java.util.Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
        orderDate = sdf.format(now);
        String orderNum = orderDate + String.format("%02d", ++cnt);
        return orderNum;
    }

    public void orderInsert(int cnt,String size, String name,int spoon,String payment ,int t_price,String id,int t_point) {

        int point =t_price / 100;
        no = orderNum(cnt);

        String sql = "INSERT INTO CS_ORDER VALUES(?,sysdate,?,?,?,?,?,?,?,?)";
        try {
            conn = Common.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,no);
            pstmt.setString(2,size);
            pstmt.setString(3,name);
            pstmt.setInt(4,spoon);
            pstmt.setString(5,payment);
            pstmt.setInt(6,t_price);
            pstmt.setString(7,id);
            pstmt.setInt(8,point);
            pstmt.setInt(9,t_point);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Common.close(pstmt);
        Common.close(conn);
    }


    public void orderByNamePrint(String cName) {
        List<OrderVO> orderlist = new ArrayList<>();
        CustomerVO cVo = null;
        OrderVO oVo = null;
        try {
                    conn = Common.getConnection();
                    String sql = "SELECT * FROM CS_ORDER o JOIN CUSTOMER c ON o.CS_ID = c.CS_ID WHERE c.CS_NAME = ?";
                    pstmt = conn.prepareStatement(sql);
                    pstmt.setString(1, cName);
                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        int no = rs.getInt("OD_NO");
                        String date = rs.getString("OD_DATE");
                        String size = rs.getString("OP_SIZE");
                        String mName = rs.getString("M_NAME");
                        int spoon = rs.getInt("SPOON");
                        String payment = rs.getString("PAYMENT");
                        int total_price = rs.getInt("TOTAL_PRICE");
                        String id = rs.getString("CS_ID");
                        String csName = rs.getString("CS_NAME");
                        int cs_point = rs.getInt("CS_POINT");
                        String number = rs.getString("PHONE");
                        int total_point = rs.getInt("TOTAL_POINT");
                        oVo = new OrderVO(no, date, size, mName, spoon, payment, total_price, id, cs_point, total_point);
                        orderlist.add(oVo);
                        cVo = new CustomerVO(id,csName,number,total_point);
                    }
                    Common.close(rs);
                    Common.close(stmt);
                    Common.close(conn);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //출력
                System.out.println("------------------------------------------------------------------------");
                System.out.println("고객 아이디 : " + cVo.getCid());
                System.out.println("고객 이름 : " +cVo.getCname());
                System.out.println("누적 포인트 : " + cVo.getTotalPoint());

                System.out.println("------------------------  "+cVo.getCname()+"님 주문내역 LIST  -----------------------------");
                for (OrderVO vo : orderlist) {
                    System.out.println("주문 번호 : " + vo.getNo());
                    System.out.println("주문 날짜 : " + vo.getDate());
                    System.out.println("통 크기 : " + vo.getSize());
                    System.out.println("메뉴명 : " + vo.getmName());
                    System.out.println("스푼 개수 : " + vo.getSpoon());
                    System.out.println("결제 방법 : " + vo.getPayment());
                    System.out.println("결제 금액 : " + vo.getTotal_price());
                    System.out.println("적립 포인트 : " + vo.getCs_point());
                    System.out.println("------------------------------------------------------------------------");
                }
        }
    }


