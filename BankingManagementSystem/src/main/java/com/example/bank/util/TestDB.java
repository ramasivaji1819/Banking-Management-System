package com.example.bank.util;

import java.sql.*;

public class TestDB {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            String url = "jdbc:mysql://localhost:3306/bank_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
            String username = "root";
            String password = "@rama1819";

            Connection con = DriverManager.getConnection(url, username, password);
            System.out.println("✅ Connection successful!");
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
