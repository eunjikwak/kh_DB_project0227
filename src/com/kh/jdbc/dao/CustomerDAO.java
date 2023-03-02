package com.kh.jdbc.dao;

import com.kh.jdbc.util.Common;
import com.kh.jdbc.vo.CustomerVO;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CustomerDAO {
    Connection conn = null;
    Statement stmt = null;
    PreparedStatement pStmt = null;
    ResultSet rs = null;

    Scanner sc = new Scanner(System.in);

    public List<CustomerVO> CustomerSelect() {
        List<CustomerVO> list = new ArrayList<>(); // 반환할 리스트를 위해 리스트 객체 생성
        try {
            conn = Common.getConnection();
            stmt = conn.createStatement();
            String sql = "SELECT * FROM CUSTOMER ";
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String cid = rs.getString("CS_ID");
                String cname = rs.getString("CS_NAME");
                String phone = rs.getString("PHONE");
                int totalPoint = rs.getInt("TOTAL_POINT");
                CustomerVO vo = new CustomerVO(cid, cname, phone, totalPoint); // 하나의 행(레코드)에 대한 정보 저장을 위한 객체 생성
                list.add(vo);
            }
            Common.close(rs); // 연결과 역순으로 해제
            Common.close(stmt);
            Common.close(conn);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    public void customerSelectPrint(List<CustomerVO> list) {
        for (CustomerVO e : list) {
            System.out.println("회원 아이디 : " + e.getCid());
            System.out.println("회원 이름 : " + e.getCname());
            System.out.println("회원 전화번호 : " + e.getPhone());
            System.out.println("누적 포인트 : " + e.getTotalPoint());
            System.out.println("------------------------------------------------------------------------");
        }
    }


    public CustomerVO customerPointSelect(String val) {
        CustomerVO vo = null;
        try {
            conn = Common.getConnection();
            stmt = conn.createStatement();
            String sql = "SELECT * FROM CUSTOMER WHERE CS_ID = '" + val + "'";

            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String cid = rs.getString("CS_ID");
                String cname = rs.getString("CS_NAME");
                String phone = rs.getString("PHONE");
                int totalPoint = rs.getInt("TOTAL_POINT");
                vo = new CustomerVO(cid, cname, phone, totalPoint); // 하나의 행(레코드)에 대한 정보 저장을 위한 객체 생성
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        Common.close(rs);
        Common.close(stmt);
        Common.close(conn);
        return vo;
    }

    public void customerPointPrint(CustomerVO vo) {
//        if (vo == null) {
//            System.out.println("해당하는 고객 정보가 없습니다.");
//            return;}
        System.out.println("------------------------------------------------------------------------");
        System.out.println("회원 아이디 : " + vo.getCid());
        System.out.println("회원 이름 : " + vo.getCname());
        System.out.println("회원 전화번호 : " + vo.getPhone());
        System.out.println("누적 포인트 : " + vo.getTotalPoint());
        System.out.println("------------------------------------------------------------------------");


    }


    public void customerInsert () {
        System.out.println("----------------------------회원 가입창-------------------------------------");
        //System.out.print("회원 아이디(전화번호 뒷자리) : ");
        //int cid = sc.nextInt();
        System.out.print("회원 이름 : ");
        String cname = sc.next();
        System.out.print("전화번호 : ");
        String phone = sc.next();
        String cid = phone.substring(9);
        String sql = "INSERT INTO CUSTOMER(CS_ID, CS_NAME, PHONE) VALUES (?, ?, ?)";

        try {
            conn = Common.getConnection();
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, cid);
            pStmt.setString(2, cname);
            pStmt.setString(3, phone);

            int ret = pStmt.executeUpdate();
            System.out.println("Return : " + ret);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Common.close(stmt);
        Common.close(conn);
    }

    public void customerUpdate () {
        System.out.print("변경할 회원의 전화번호 뒷자리를 입력하세요 : ");
        String csid = sc.next();
        System.out.print("변경할 이름을 입력하세요 : ");
        String cname = sc.next();
        System.out.print("변경할 전화번호를 입력하세요 : ");
        String phone = sc.next();


        String sql = "UPDATE CUSTOMER SET CS_NAME = ?, PHONE = ? WHERE CS_ID = ?";

        try {
            conn = Common.getConnection();
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, cname);
            pStmt.setString(2, phone);
            pStmt.setString(3, csid);
            pStmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Common.close(pStmt);
        Common.close(conn);

    }

    public void customerDelete() {
        System.out.print("삭제할 회원의 아이디를 입력 하세요 (전화번호 뒷자리) : ");
        String csid = sc.next();
        String sql = "DELETE FROM CUSTOMER WHERE CS_ID = ?";
        try {
            conn = Common.getConnection();
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, csid);
            pStmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
        Common.close(pStmt);
        Common.close(conn);

    }

    public void pointUpdate(int price, int t_point,String id) {

        int point = price/100;
        t_point +=point;
        String sql = "UPDATE CUSTOMER SET TOTAL_POINT = ? WHERE CS_ID = ?";

        try {
            conn = Common.getConnection();
            pStmt = conn.prepareStatement(sql);
            pStmt.setInt(1, t_point);
            pStmt.setString(2, id);

            pStmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Common.close(pStmt);
        Common.close(conn);
    }
}
