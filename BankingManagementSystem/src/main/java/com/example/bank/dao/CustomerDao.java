package com.example.bank.dao;
import com.example.bank.model.Customer;
import com.example.bank.util.DBConnection;
import java.sql.*;
public class CustomerDao {
    public long createCustomer(Customer c) throws SQLException {
        String sql = "INSERT INTO customers (full_name,email,phone) VALUES(?,?,?)";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement
                ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, c.getFullName());
            ps.setString(2, c.getEmail());
            ps.setString(3, c.getPhone());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getLong(1);
                else throw new SQLException("Creating customer failed, no IDobtained.");
            }
        }
    }
    // Additional methods: findById, findByEmail etc. (omitted for brevity)
}