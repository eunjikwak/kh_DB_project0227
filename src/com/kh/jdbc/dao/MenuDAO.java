package com.kh.jdbc.dao;


import com.kh.jdbc.util.Common;
import com.kh.jdbc.vo.MenuVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MenuDAO {
    Connection conn = null; // 자바와 오라클에 대한 연결 설정
    Statement stmt = null; // SQL문을 수행하기 위한 객체
    ResultSet rs = null; // statement 동작에 대한 결과로 전달되는 DB의 내용
    PreparedStatement pstmt = null;
    Scanner sc = new Scanner(System.in);

    public List<MenuVO> MenuSelect() {
        List<MenuVO> list = new ArrayList<>(); // 반환할 리스트를 위해 리스트 객체 생성
        try {
            conn = Common.getConnection();
            stmt = conn.createStatement();
            String sql = "SELECT * FROM MENU";
            rs = stmt.executeQuery(sql); // select 문과 같이 여러개의 레코드(행)로 결과가 반환 될 때 사용

            while (rs.next()) { //읽을 행이 있으면 참
                int mNo = rs.getInt("M_NO");
                String mName = rs.getString("M_NAME");
                String desc = rs.getString("M_DESC");
                int stock = rs.getInt("STOCK");
                int mCnt = rs.getInt("M_CNT");
                MenuVO vo = new MenuVO(mNo, mName, desc, stock, mCnt); // 하나의 행(레코드)에 대한 정보 저장을 위한 객체 생성
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
    public void menuSelectPrint(List<MenuVO> list) {
        for(MenuVO e : list) {
            System.out.println("메뉴번호 : " + e.getNo());
            System.out.println("메뉴명 : " + e.getName());
            System.out.println("메뉴설명 : " + e.getDesc());
            System.out.println("재고수량 : " + e.getStock());
            System.out.println("판매수량 : " + e.getCnt());
            System.out.println("------------------------------------------------------------------------");
        }
    }
    public void menuOrderPrint(List<MenuVO> list) {
        for(MenuVO e : list) {
            System.out.print ("[" + e.getNo()+"]" + e.getName() +" ");
            if(e.getNo() % 4 == 0){
                System.out.println();
                System.out.println("------------------------------------------------------------------------");
            }
        }
        System.out.println();
        System.out.println("------------------------------------------------------------------------");
    }
    public MenuVO menuFind(int num) {
        MenuVO vo = null;
        try {
            conn = Common.getConnection();
            stmt = conn.createStatement();
            String sql = "SELECT * FROM MENU WHERE M_NO ="+num;
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int mNo = rs.getInt("M_NO");
                String mName = rs.getString("M_NAME");
                String desc = rs.getString("M_DESC");
                int stock = rs.getInt("STOCK");
                int mCnt = rs.getInt("M_CNT");
                vo = new MenuVO(mNo, mName, desc, stock, mCnt);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Common.close(rs);
        Common.close(stmt);
        Common.close(conn);
        return vo;
    }

    public void menuInsert() {
        System.out.println("[ 메 뉴 등 록 ]");
        System.out.print("메뉴 번호 입력 : ");
        int no = sc.nextInt();
        sc.nextLine();
        System.out.print("메뉴명을 입력하세요: ");
        String name = sc.nextLine();
        System.out.print("설명 입력 : ");
        String desc = sc.nextLine();

        String sql = "INSERT INTO MENU(M_NO,M_NAME,M_DESC) VALUES(?,?,?)";

        try {
            conn = Common.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,no);
            pstmt.setString(2,name);
            pstmt.setString(3,desc);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Common.close(pstmt);
        Common.close(conn);
    }

    public void menuDelete() {
        System.out.print("삭제할 메뉴의 이름을 입력하세요 : ");
        String mName = sc.next();
        String sql = "DELETE FROM MENU WHERE M_NAME = ?";
        try {
            conn = Common.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, mName);
            pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
        Common.close(pstmt);
        Common.close(conn);

    }
    public void stockUpdate(int stock, int saleCnt, String name) {

        String sql = "UPDATE MENU SET STOCK = ?, M_CNT = ? WHERE M_NAME = ?";

        try {
            conn = Common.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, --stock);
            pstmt.setInt(2, ++saleCnt);
            pstmt.setString(3, name);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Common.close(pstmt);
        Common.close(conn);

    }
}