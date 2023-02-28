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
                String sql = "SELECT * FROM CUSTOMER";
                rs = stmt.executeQuery(sql);

                while(rs.next()) {
                    int cid = rs.getInt("CS_ID");
                    String cname = rs.getString("CS_NAME");
                    String phone = rs.getString("PHONE");
                    BigDecimal totalPoint = rs.getBigDecimal("TOTAL_POINT");
                    CustomerVO vo = new CustomerVO(cid, cname,phone,totalPoint); // 하나의 행(레코드)에 대한 정보 저장을 위한 객체 생성
                    list.add(vo);
                }
                Common.close(rs); // 연결과 역순으로 해제
                Common.close(stmt);
                Common.close(conn);

            } catch (Exception e ) {
                e.printStackTrace();
            }
            return list;
        }


        public void customerSelectPrint(List<CustomerVO> list) {
            for(CustomerVO e : list) {
                System.out.println("회원 아이디 : " + e.getCid());
                System.out.println("회원 이름 : " + e.getCname());
                System.out.println("회원 전화번호 : " + e.getPhone());
                System.out.println("누적 포인트 : " + e.getTotalPoint());
                System.out.println("=========================");
            }
        }
        public void customerInsert() {
            System.out.println("회원가입창");
            System.out.print("회원 아이디(전화번호 뒷자리) : ");
            int cid = sc.nextInt();
            System.out.print("회원 이름 : ");
            String cname = sc.next();
            System.out.print("전화번호 : ");
            String phone = sc.next();

            String sql = "INSERT INTO CUSTOMER(CS_ID,CS_NAME,PHONE) VALUES("
                    + cid + ", " + "'" + cname + "'" + ", " + "'" + phone + "'" + ")";

            try {
                conn = Common.getConnection();
                stmt = conn.createStatement();
                int ret = stmt.executeUpdate(sql); // 반환 값 정수타입. 영향받는 행이 1개면 1이 넘어오도록
                System.out.println("Return : " + ret);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Common.close(stmt);
            Common.close(conn);
        }

    public void customerUpdate() {
        System.out.print("변경할 회원의 전화번호 뒷자리를 입력하세요 : ");
        String csid = sc. next();
        System.out.print("변경할 이름을 입력하세요 : ");
        String cname = sc.next();
        System.out.print("변경할 전화번호를 입력하세요 : ");
        String phone = sc.next();


        String sql = "UPDATE CUSTOMER SET CS_NAME = ?, PHONE = ? WHERE CS_ID = ?";

        try{
            conn = Common.getConnection();
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, cname);
            pStmt.setString(2,phone);
            pStmt.setString(3,csid);
            pStmt.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
        Common.close(pStmt);
        Common.close(conn);

    }

    public void customerDelete(){
        System.out.print("삭제할 회원의 아이디를 입력 하세요 (전화번호 뒷자리) : ");
        String csid = sc.next();
        String sql = "DELETE FROM CUSTOMER WHERE CS_ID = ?";
        try{
            conn = Common.getConnection();
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1,csid);
            pStmt.executeUpdate();

        }catch (Exception e){
            e.printStackTrace();
        }
        Common.close(pStmt);
        Common.close(conn);

    }
