package com.kh.jdbc.dao;

import com.kh.jdbc.util.Common;
import com.kh.jdbc.vo.MenuVO;
import com.kh.jdbc.vo.OptionVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class OptionDAO {
    Connection conn = null; // 자바와 오라클에 대한 연결 설정
    Statement stmt = null; // SQL문을 수행하기 위한 객체
    ResultSet rs = null; // statement 동작에 대한 결과로 전달되는 DB의 내용

    Scanner sc = new Scanner(System.in);

    public List<OptionVO> optionSelect() {
        List<OptionVO> list = new ArrayList<>(); // 반환할 리스트를 위해 리스트 객체 생성
        try {
            conn = Common.getConnection();
            stmt = conn.createStatement();
            String sql = "SELECT * FROM OP_TABLE";
            rs = stmt.executeQuery(sql); // select 문과 같이 여러개의 레코드(행)로 결과가 반환 될 때 사용

            while (rs.next()) { //읽을 행이 있으면 참
                String size = rs.getString("OP_SIZE");
                int num = rs.getInt("OP_NUM");
                int price = rs.getInt("PRICE");
                OptionVO vo = new OptionVO(size,num,price); // 하나의 행(레코드)에 대한 정보 저장을 위한 객체 생성
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
    public void optionSelectPrint(List<OptionVO> list) {
        for(OptionVO e : list) {
            System.out.println("메뉴번호 : " + e.getSize());
            System.out.println("메뉴명 : " + e.getOption_num());
            System.out.println("메뉴설명 : " + e.getPrice());
            System.out.println("=========================");
        }
    }
    public void optionSizePrint(List<OptionVO> list) {
        for(OptionVO e : list) {
            System.out.print("[" + e.getOption_num() + "]"+e.getSize() + "  ");
        }

    }

    public OptionVO optionFind(int num) {
        OptionVO vo = null;
        try {
            conn = Common.getConnection();
            stmt = conn.createStatement();
            String sql = "SELECT * FROM OP_TABLE WHERE OP_NUM ="+num;
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String size = rs.getString("OP_SIZE");
                int op_num = rs.getInt("OP_NUM");
                int price = rs.getInt("PRICE");
                vo = new OptionVO(size, op_num, price); // 하나의 행(레코드)에 대한 정보 저장을 위한 객체 생성
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Common.close(rs);
        Common.close(stmt);
        Common.close(conn);
        return vo;
    }

}
