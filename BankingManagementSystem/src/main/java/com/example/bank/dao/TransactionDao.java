package com.example.bank.dao;
import com.example.bank.model.TransactionRecord;
import com.example.bank.util.DBConnection;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
public class TransactionDao {
    public void insertTransaction(Connection conn, long accountId, String
            txnType, BigDecimal amount, BigDecimal balanceAfter, String relatedAccount,
                                  String description) throws SQLException {
        String sql = "INSERT INTO transactions (account_id, txn_type, amount, balance_after, related_account, description) VALUES (?,?,?,?,?,?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, accountId);
            ps.setString(2, txnType);
            ps.setBigDecimal(3, amount);
            ps.setBigDecimal(4, balanceAfter);
            if (relatedAccount == null) ps.setNull(5, Types.VARCHAR);
            else
                ps.setString(5, relatedAccount);
            ps.setString(6, description);
            ps.executeUpdate();
        }
    }

    public List<TransactionRecord> findByAccountNumber(String accountNumber)
            throws SQLException {
        String sql = "SELECT t.* FROM transactions t JOIN accounts a ON t.account_id = a.account_id WHERE a.account_number = ? ORDER BY t.txn_time DESC";
        List<TransactionRecord> result = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection(); PreparedStatement
                ps = conn.prepareStatement(sql)) {
            ps.setString(1, accountNumber);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    TransactionRecord tr = new TransactionRecord();
                    tr.setTxnId(rs.getLong("txn_id"));
                    tr.setAccountId(rs.getLong("account_id"));
                    tr.setTxnType(rs.getString("txn_type"));
                    tr.setAmount(rs.getBigDecimal("amount"));
                    tr.setBalanceAfter(rs.getBigDecimal("balance_after"));
                    tr.setRelatedAccount(rs.getString("related_account"));
                    tr.setDescription(rs.getString("description"));
                    Timestamp ts = rs.getTimestamp("txn_time");
                    if (ts != null) tr.setTxnTime(ts.toLocalDateTime());
                    result.add(tr);
                }
            }
        }
        return result;
    }
}