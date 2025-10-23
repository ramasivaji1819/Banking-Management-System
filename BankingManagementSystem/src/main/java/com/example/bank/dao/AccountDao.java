package com.example.bank.dao;

import com.example.bank.model.Account;
import com.example.bank.util.DBConnection;
import java.math.BigDecimal;
import java.sql.*;
import java.util.Optional;
public class AccountDao {
    public long createAccount(Account acc) throws SQLException {
        String sql = "INSERT INTO accounts (customer_id, account_number,account_type, balance, is_active) VALUES (?,?,?,?,?)";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement
                ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            if (acc.getCustomerId() == null) ps.setNull(1, Types.BIGINT); else
                ps.setLong(1, acc.getCustomerId());
            ps.setString(2, acc.getAccountNumber());
            ps.setString(3, acc.getAccountType());
            ps.setBigDecimal(4, acc.getBalance());
            ps.setBoolean(5, acc.isActive());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getLong(1);
                else throw new SQLException("Creating account failed, no IDobtained.");
            }
        }
    }
    public Optional<Account> findByAccountNumberForUpdate(Connection conn,
                                                          String accountNumber) throws SQLException {
        String sql = "SELECT account_id, customer_id, account_number, account_type, balance, is_active FROM accounts WHERE account_number = ? FOR UPDATE";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, accountNumber);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Account a = new Account();
                    a.setAccountId(rs.getLong("account_id"));
                    long custId = rs.getLong("customer_id");
                    if (rs.wasNull()) a.setCustomerId(null); else
                        a.setCustomerId(custId);
                    a.setAccountNumber(rs.getString("account_number"));
                    a.setAccountType(rs.getString("account_type"));
                    a.setBalance(rs.getBigDecimal("balance"));
                    a.setActive(rs.getBoolean("is_active"));
                    return Optional.of(a);
                }return Optional.empty();
            }
        }
    }
    public Optional<Account> findByAccountNumber(String accountNumber) throws
            SQLException {
        String sql = "SELECT account_id, customer_id, account_number,account_type, balance, is_active FROM accounts WHERE account_number = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement
                ps = conn.prepareStatement(sql)) {
            ps.setString(1, accountNumber);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Account a = new Account();
                    a.setAccountId(rs.getLong("account_id"));
                    long custId = rs.getLong("customer_id");
                    if (rs.wasNull()) a.setCustomerId(null); else
                        a.setCustomerId(custId);
                    a.setAccountNumber(rs.getString("account_number"));
                    a.setAccountType(rs.getString("account_type"));
                    a.setBalance(rs.getBigDecimal("balance"));
                    a.setActive(rs.getBoolean("is_active"));
                    return Optional.of(a);
                }
                return Optional.empty();
            }
        }
    }
    public void updateBalance(Connection conn, long accountId, BigDecimal
            newBalance) throws SQLException {
        String sql = "UPDATE accounts SET balance = ? WHERE account_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setBigDecimal(1, newBalance);
            ps.setLong(2, accountId);
            int updated = ps.executeUpdate();
            if (updated != 1) throw new SQLException("Failed to update account balance for id="+accountId);
        }
    }
}
