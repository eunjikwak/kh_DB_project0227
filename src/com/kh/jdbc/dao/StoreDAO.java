package com.kh.jdbc.dao;

import com.kh.jdbc.util.Common;
import com.kh.jdbc.vo.OrderVO;
import com.kh.jdbc.vo.StoreVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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

                while(rs.next()) {
                    String id = rs.getString("S_ID");
                    int pwd = rs.getInt("S_PWD");
                    String s_name = rs.getString("S_NAME");
                    String k_name = rs.getString("K_NAME");
                    String addr = rs.getString("ADDRESS");
                    int pos = rs.getInt("POS");;

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
            for(StoreVO e : list) {
                System.out.println("관리자 아이디 : " + e.getId());
                System.out.println("비밀번호 : " + e.getPwd());
                System.out.println("점포명 : " + e.getS_name());
                System.out.println("대표자 이름 : " + e.getK_name());
                System.out.println("주소 : " + e.getAddr());
                System.out.println("포스번호 : " + e.getPos());

                System.out.println("=========================");
            }
        }
    }


