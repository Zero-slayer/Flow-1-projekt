package jdbc;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

public class JDBC_DB_CONNECTION implements AutoCloseable{

    private Connection connection;
    private String db_url;
    private String db_user;
    private String db_password;
    private Calendar cal = Calendar.getInstance();

    private PreparedStatement ps_getBalance;
    private PreparedStatement ps_newTransaction;
    private PreparedStatement ps_getBankId;
    private PreparedStatement ps_createBank;
    private PreparedStatement ps_createCustomer;
    private PreparedStatement ps_getCustomerId;
    private PreparedStatement ps_createAccount;
    private PreparedStatement ps_getAccountId;

    public JDBC_DB_CONNECTION(String db_url, String db_user, String db_password) throws SQLException {
        this.db_url = db_url;
        this.db_user = db_user;
        this.db_password = db_password;
        prepareConnection();
    }

    @Override
    public void close() throws Exception {
        connection.close();
    }

    public static String username()
    {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter username: ");
        return input.nextLine();
    }

    public static String password()
    {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter password: ");
        return input.nextLine();
    }

    public void depositAmount(int amount, JDBC_DB_CUSTOMER customer) throws Exception{
        if (amount < 0) {
            try {
                throw new Exception("Cannot deposit less than 0");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        ps_newTransaction.setInt(1, amount);
        ps_newTransaction.setDate(2, new java.sql.Date(cal.getTimeInMillis()));
        ps_newTransaction.setInt(3, getAccountId(customer));
        if (ps_newTransaction.executeUpdate() != 1) {
            throw new Exception("Could not deposit amount");
        }
    }

    public void withDrawAmount(int amount, JDBC_DB_CUSTOMER customer) throws Exception {
        if (Math.abs(amount) > getBalance()) {
            try {
                throw new Exception(String.format("Cannot withdraw more than %d", getBalance()));
        } catch(Exception e) {
            e.printStackTrace();
            }
        }
        ps_newTransaction.setInt(1, (Math.abs(amount) * -1));
        ps_newTransaction.setDate(2, new java.sql.Date(cal.getTimeInMillis()));
        ps_newTransaction.setInt(3, getAccountId(customer));
        if (ps_newTransaction.executeUpdate() != 1) {
            throw new Exception("Could not withdraw amount");
        }
    }

    public int getBalance() throws Exception {
        try (ResultSet rs = ps_getBalance.executeQuery()) {
            int sum = -1;
            while(rs.next()){
                sum = rs.getInt(1);
            }
            return sum;
        } catch(SQLException e) {
            throw new Exception(e);
        }
    }

    public int getBankID(int id) throws Exception{
        ps_getBankId.setInt(1, id);

        try (ResultSet rs = ps_getBankId.executeQuery()) {
            int val = -1;
            while(rs.next()) {
                val = rs.getInt(1);
            }
            return val;
        } catch (SQLException e) {
            throw new Exception(e);
        }
    }

    public void createBank(JDBC_DB_BANK bank) throws Exception {
        ps_createBank.setInt(1, bank.getId());
        ps_createBank.setString(2, bank.getName());
        ps_createBank.setString(3, bank.getCity());
        if (ps_createBank.executeUpdate() != 1) {
            throw new Exception("Could not create bank");
        }
    }

    public void createCustomer(JDBC_DB_CUSTOMER customer) throws Exception {
        ps_createCustomer.setString(1, customer.getName());
        ps_createCustomer.setString(2, customer.getCity());
        ps_createCustomer.setInt(3, customer.getBankId());
        if (ps_createCustomer.executeUpdate() != 1) {
            throw new Exception("Could not create customer");
        }
    }


    public int getCustomerId(JDBC_DB_CUSTOMER customer) throws Exception {
        ps_getCustomerId.setString(1, customer.getName());
        ps_getCustomerId.setString(2, customer.getCity());
        ps_getCustomerId.setInt(3, customer.getBankId());
        try (ResultSet rs = ps_getCustomerId.executeQuery()) {
            int val = -1;
            while(rs.next()) {
                val = rs.getInt(1);
            }
            return val;
        } catch (SQLException e) {
            throw new Exception(e);
        }
    }

    public int getAccountId(JDBC_DB_CUSTOMER customer) throws Exception {
        ps_getAccountId.setInt(1, getCustomerId(customer));
        try (ResultSet rs = ps_getAccountId.executeQuery()) {
           int val = -1;
           while (rs.next()) {
               val = rs.getInt(1);
           }
           return val;
        } catch (SQLException e) {
           throw new Exception(e);
        }
    }

    public void createAccount(JDBC_DB_CUSTOMER customer) throws Exception {
        ps_createAccount.setInt(1, getCustomerId(customer));
        if (ps_createAccount.executeUpdate() != 1) {
            throw new Exception("Could not create account");
        }
    }

    private void prepareConnection() throws SQLException {
        connection = DriverManager.getConnection(db_url, db_user, db_password);
        ps_newTransaction = connection.prepareStatement("INSERT INTO bank.transaction " +
                "(Amount, Date, idAccount) VALUES (?,?,?)");
        ps_getBalance = connection.prepareStatement("SELECT SUM(Amount) FROM bank.transaction");
        ps_getBankId = connection.prepareStatement("SELECT idBank FROM bank.bank WHERE idBank = ?");
        ps_createBank = connection.prepareStatement("INSERT INTO bank.bank " +
                "(idBank, Name, City) VALUES (?,?,?)");
        ps_createCustomer = connection.prepareStatement("INSERT INTO bank.customer " +
                    "(Name, City, idBank) VALUES (?,?,?)");
        ps_getCustomerId = connection.prepareStatement("SELECT idCustomer FROM bank.customer " +
                "WHERE bank.customer.Name = ? and bank.customer.City = ? and bank.customer.idBank = ?");
        ps_createAccount = connection.prepareStatement("INSERT INTO bank.account " +
                "(idCustomer) VALUES (?)");
        ps_getAccountId = connection.prepareStatement("SELECT idAccount FROM bank.account WHERE bank.account.idCustomer = ?");
    }
}
