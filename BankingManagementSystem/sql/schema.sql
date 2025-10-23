CREATE DATABASE IF NOT EXISTS bank_db;

USE bank_db;
 CREATE TABLE customers (
 customer_id BIGINT AUTO_INCREMENT PRIMARY KEY,
 full_name VARCHAR(200) NOT NULL,
 email VARCHAR(150) UNIQUE,
 phone VARCHAR(30),
 created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
 );
 CREATE TABLE accounts (
 account_id BIGINT AUTO_INCREMENT PRIMARY KEY,
 customer_id BIGINT,
 account_number VARCHAR(30) NOT NULL UNIQUE,
 account_type ENUM('SAVINGS','CURRENT') DEFAULT 'SAVINGS',
 balance DECIMAL(15,2) NOT NULL DEFAULT 0.00,
 is_active BOOLEAN DEFAULT TRUE,
 created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
 updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
 CONSTRAINT fk_accounts_customer FOREIGN KEY (customer_id) REFERENCES
 customers(customer_id) ON DELETE SET NULL
 );
 CREATE TABLE transactions (
 txn_id BIGINT AUTO_INCREMENT PRIMARY KEY,
 account_id BIGINT NOT NULL,
 txn_type ENUM('DEPOSIT','WITHDRAWAL','TRANSFER_OUT','TRANSFER_IN') NOT NULL,
 amount DECIMAL(15,2) NOT NULL,
 balance_after DECIMAL(15,2) NOT NULL,
 related_account VARCHAR(30) NULL,
 description VARCHAR(500),
 txn_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
 CONSTRAINT fk_transactions_account FOREIGN KEY (account_id) REFERENCES
 accounts(account_id) ON DELETE CASCADE
 );
 CREATE TABLE admins (
 admin_id INT AUTO_INCREMENT PRIMARY KEY,
 username VARCHAR(100) UNIQUE,
 password_hash VARCHAR(255),
 created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
 );
 CREATE INDEX idx_txn_time ON transactions(txn_time);
 CREATE INDEX idx_account_balance ON accounts(balance);-- sample data
 INSERT INTO customers (full_name,email,phone) VALUES ('Alice Kumar','alice@example.com','+91-9000000000');
 INSERT INTO accounts (customer_id, account_number, account_type, balance)
 VALUES (1, 'ACC1000001', 'SAVINGS', 1000.00);