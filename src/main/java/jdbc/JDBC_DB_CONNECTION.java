package jdbc;

import java.sql.*;
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

    private void prepareConnection() throws SQLException{
        connection = DriverManager.getConnection(db_url, db_user, db_password);
        ps_newTransaction = connection.prepareStatement("INSERT INTO bank.transaction " +
                "(Amount, Date) VALUES (?,?)");
        ps_getBalance = connection.prepareStatement("SELECT SUM(Amount) FROM bank.transaction");
    }

}
