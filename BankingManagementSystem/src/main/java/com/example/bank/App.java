package com.example.bank;
import com.example.bank.model.Account;
import com.example.bank.model.Customer;
import com.example.bank.dao.AccountDao;
import com.example.bank.dao.CustomerDao;
import com.example.bank.service.BankingService;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Scanner;
public class App {
    private static final BankingService svc = new BankingService();
    private static final CustomerDao customerDao = new CustomerDao();
    private static final AccountDao accountDao = new AccountDao();
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.println("=== Banking Management System (CLI) ===");
        while (true) {
            System.out.println("\nMenu: 1) Create Customer 2) Create Account 3) Deposit 4) Withdraw 5) Transfer 6) Exit");
            System.out.print("Choose: ");
            String choice = sc.nextLine().trim();
            try {
                switch (choice) {
                    case "1": createCustomer(sc); break;
                    case "2": createAccount(sc); break;
                    case "3": deposit(sc); break;
                    case "4": withdraw(sc); break;
                    case "5": transfer(sc); break;
                    case "6": System.out.println("Bye"); sc.close(); return;
                    default: System.out.println("Invalid choice");
                }

            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
    private static void createCustomer(Scanner sc) throws SQLException {
        System.out.print("Full name: "); String name = sc.nextLine();
        System.out.print("Email: "); String email = sc.nextLine();
        System.out.print("Phone: "); String phone = sc.nextLine();
        Customer c = new Customer();
        c.setFullName(name); c.setEmail(email); c.setPhone(phone);
        long id = customerDao.createCustomer(c);
        System.out.println("Created customer id=" + id);
    }
    private static void createAccount(Scanner sc) throws SQLException {
        System.out.print("Customer ID (or blank): "); String cust =
                sc.nextLine().trim();
        System.out.print("Account number: "); String accNum =
                sc.nextLine().trim();
        System.out.print("Initial balance: "); String balStr =
                sc.nextLine().trim();
        Account a = new Account();
        if (!cust.isEmpty()) a.setCustomerId(Long.parseLong(cust));
        a.setAccountNumber(accNum);
        a.setAccountType("SAVINGS");
        a.setBalance(new BigDecimal(balStr));
        a.setActive(true);
        long id = accountDao.createAccount(a);
        System.out.println("Created account id=" + id);
    }
    private static void deposit(Scanner sc) throws SQLException {
        System.out.print("Account number: "); String acc = sc.nextLine().trim();
        System.out.print("Amount: "); String amt = sc.nextLine().trim();
        System.out.print("Description: "); String desc = sc.nextLine();
        svc.deposit(acc, new BigDecimal(amt), desc);
        System.out.println("Deposit successful");
    }
    private static void withdraw(Scanner sc) throws SQLException {
        System.out.print("Account number: "); String acc = sc.nextLine().trim();
        System.out.print("Amount: "); String amt = sc.nextLine().trim();
        System.out.print("Description: "); String desc = sc.nextLine();
        svc.withdraw(acc, new BigDecimal(amt), desc);
        System.out.println("Withdraw successful");
    }

    private static void transfer(Scanner sc) throws SQLException {
        System.out.print("From account: "); String from = sc.nextLine().trim();
        System.out.print("To account: "); String to = sc.nextLine().trim();
        System.out.print("Amount: "); String amt = sc.nextLine().trim();
        System.out.print("Description: "); String desc = sc.nextLine();
        svc.transferFunds(from, to, new BigDecimal(amt), desc);
        System.out.println("Transfer successful");
    }
}