package jdbc;

public class JDBC_DB_ACCOUNT {
    JDBC_DB_CONNECTION connection;
    JDBC_DB_CUSTOMER customer;

    public JDBC_DB_ACCOUNT(JDBC_DB_CUSTOMER customer) {
        this.customer = customer;
        this.connection = customer.getConnection();
        try {
            connection.createAccount(customer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getId() throws Exception {
        return connection.getAccountId(customer);
    }
}
