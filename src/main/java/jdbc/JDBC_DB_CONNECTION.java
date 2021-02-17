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

    public String username()
    {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter username: ");
        return input.nextLine();
    }

    public String password()
    {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter password: ");
        return input.nextLine();
    }

    public void depositAmount(int amount) throws Exception{
        if (amount < 0) {
            try {
                throw new Exception("Cannot deposit less than 0");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        ps_newTransaction.setInt(1, amount);
        ps_newTransaction.setDate(2, new java.sql.Date(cal.getTimeInMillis()));
        if (ps_newTransaction.executeUpdate() != 1) {
            throw new Exception("Could not deposit amount");
        }
    }

    public void withDrawAmount(int amount) throws Exception {
        if (Math.abs(amount) > getBalance()) {
            try {
                throw new Exception(String.format("Cannot withdraw more than %d", getBalance()));
        } catch(Exception e) {
            e.printStackTrace();
            }
        }
        ps_newTransaction.setInt(1, (Math.abs(amount) * -1));
        ps_newTransaction.setDate(2, new java.sql.Date(cal.getTimeInMillis()));
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

    private void prepareConnection() throws SQLException {
        connection = DriverManager.getConnection(db_url, db_user, db_password);
        ps_newTransaction = connection.prepareStatement("INSERT INTO bank.transaction " +
                "(Amount, Date) VALUES (?,?)");
        ps_getBalance = connection.prepareStatement("SELECT SUM(Amount) FROM bank.transaction");
        ps_getBankId = connection.prepareStatement("SELECT idBank FROM bank.bank WHERE idBank = ?");
        ps_createBank = connection.prepareStatement("INSERT INTO bank.bank " +
                "(idBank, Name, City) VALUES (?,?,?)");
        ps_createCustomer = connection.prepareStatement("INSERT INTO bank.customer " +
                    "(Name, City, idBank) VALUES (?,?,?)");
    }

}
