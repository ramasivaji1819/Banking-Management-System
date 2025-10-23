package com.example.bank.service;
import com.example.bank.dao.AccountDao;
import com.example.bank.dao.TransactionDao;
import com.example.bank.model.Account;
import com.example.bank.util.DBConnection;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;
public class BankingService {
    private final AccountDao accountDao = new AccountDao();
    private final TransactionDao txnDao = new TransactionDao();
    public void transferFunds(String fromAcctNum, String toAcctNum, BigDecimal
            amount, String description) throws SQLException {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) throw new
                IllegalArgumentException("Amount must be positive");
        if (fromAcctNum.equals(toAcctNum)) throw new
                IllegalArgumentException("Cannot transfer to the same account");
        try (Connection conn = DBConnection.getConnection()) {
            try {
                conn.setAutoCommit(false);
                Optional<Account> fromOpt =accountDao.findByAccountNumberForUpdate(conn, fromAcctNum);
                Optional<Account> toOpt =
                        accountDao.findByAccountNumberForUpdate(conn, toAcctNum);
                if (fromOpt.isEmpty()) throw new SQLException("From account not found");
                if (toOpt.isEmpty()) throw new SQLException("To account not found");
                Account from = fromOpt.get();
                Account to = toOpt.get();
                if (!from.isActive()) throw new SQLException("From account is not active");
                if (!to.isActive()) throw new SQLException("To account is not active");
                BigDecimal fromBalance = from.getBalance();
                BigDecimal toBalance = to.getBalance();
                if (fromBalance.compareTo(amount) < 0) throw new
                        SQLException("Insufficient funds");
                BigDecimal newFromBal = fromBalance.subtract(amount);
                BigDecimal newToBal = toBalance.add(amount);
                accountDao.updateBalance(conn, from.getAccountId(), newFromBal);
                accountDao.updateBalance(conn, to.getAccountId(), newToBal);
                txnDao.insertTransaction(conn, from.getAccountId(),
                        "TRANSFER_OUT", amount, newFromBal, to.getAccountNumber(), description);
                txnDao.insertTransaction(conn, to.getAccountId(),
                        "TRANSFER_IN", amount, newToBal, from.getAccountNumber(), description);
                conn.commit();
            } catch (Exception e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }
        }
    }
    public void deposit(String accountNumber, BigDecimal amount, String
            description) throws SQLException {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) throw new
                IllegalArgumentException("Amount must be positive");try (Connection conn = DBConnection.getConnection()) {
            try {
                conn.setAutoCommit(false);
                Optional<Account> opt =
                        accountDao.findByAccountNumberForUpdate(conn, accountNumber);
                if (opt.isEmpty()) throw new SQLException("Account not found");
                Account acc = opt.get();
                if (!acc.isActive()) throw new SQLException("Account is not active");
                BigDecimal newBal = acc.getBalance().add(amount);
                accountDao.updateBalance(conn, acc.getAccountId(), newBal);
                txnDao.insertTransaction(conn, acc.getAccountId(), "DEPOSIT",
                        amount, newBal, null, description);
                conn.commit();
            } catch (Exception e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }
        }
    }
    public void withdraw(String accountNumber, BigDecimal amount, String
            description) throws SQLException {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) throw new
                IllegalArgumentException("Amount must be positive");
        try (Connection conn = DBConnection.getConnection()) {
            try {
                conn.setAutoCommit(false);
                Optional<Account> opt =
                        accountDao.findByAccountNumberForUpdate(conn, accountNumber);
                if (opt.isEmpty()) throw new SQLException("Account not found");
                Account acc = opt.get();
                if (!acc.isActive()) throw new SQLException("Account is not active");
                if (acc.getBalance().compareTo(amount) < 0) throw new
                        SQLException("Insufficient funds");
                BigDecimal newBal = acc.getBalance().subtract(amount);
                accountDao.updateBalance(conn, acc.getAccountId(), newBal);
                txnDao.insertTransaction(conn, acc.getAccountId(),
                        "WITHDRAWAL", amount, newBal, null, description);
                conn.commit();
            } catch (Exception e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }
        }
    }
}