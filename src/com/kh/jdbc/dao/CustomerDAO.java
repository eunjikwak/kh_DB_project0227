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
        Connection conn = null; // 자바와 오라클에 대한 연결 설정
        Statement stmt = null; // SQL문을 수행하기 위한 객체
        PreparedStatement pStmt = null;
        ResultSet rs = null; // statement 동작에 대한 결과로 전달되는 DB의 내용

        Scanner sc = new Scanner(System.in);

        public List<CustomerVO> CustomerSelect() {
            List<CustomerVO> list = new ArrayList<>(); // 반환할 리스트를 위해 리스트 객체 생성
            try {
                conn = Common.getConnection();
                stmt = conn.createStatement();
                String sql = "SELECT * FROM CUSTOMER";
                rs = stmt.executeQuery(sql); // select 문과 같이 여러개의 레코드(행)로 결과가 반환 될 때 사용

                while(rs.next()) { //읽을 행이 있으면 참
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

    }
