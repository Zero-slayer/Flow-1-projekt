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

    public int withdrawAmount(int amount) {
        int val = -1;
        try {
            val = connection.withDrawAmount(amount, customer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return val;
    }

    public int depositAmount(int amount) {
        int val = -1;
        try {
            val = connection.depositAmount(amount, customer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return val;
    }

    public int getBalance() {
        int sum = -1;
        try {
            sum = connection.getBalance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sum;
    }

    public JDBC_DB_CUSTOMER getCustomer() {
        return customer;
    }

}
