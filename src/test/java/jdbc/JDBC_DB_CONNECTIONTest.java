package jdbc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JDBC_DB_CONNECTIONTest {

    private String db_url;
    private String db_user;
    private String db_password;
    private JDBC_DB_CONNECTION connection = null;
    private JDBC_DB_BANK bank;
    private JDBC_DB_CUSTOMER customer;

    @BeforeEach
    void setUp() throws Exception {
        db_user = "";
        db_password = "";
        db_url = "jdbc:mysql://localhost/?serverTimezone=UTC";
        connection = new JDBC_DB_CONNECTION(db_url, db_user, db_password);
    }

    @Test
    void createBank() throws Exception {
        bank = new JDBC_DB_BANK(1, "test bank", "Aarhus", connection);
    }

    @Test
    void createCustomer() throws Exception {
        createBank();
        customer = new JDBC_DB_CUSTOMER("Lars", "Oddense", bank);
        connection.createCustomer(customer);
    }

    @Test
    void createAccount() throws Exception {
        createCustomer();
        JDBC_DB_ACCOUNT account = new JDBC_DB_ACCOUNT(customer);
    }

    @Test
    void depositAmount() throws Exception {
        createAccount();
        connection.depositAmount(100, customer);
        connection.depositAmount(120, customer);
//        int expected = 220;
//        int actual = connection.getBalance();
//        assertEquals(expected, actual);
    }

    @Test
    void withDrawAmount() throws Exception {
        depositAmount();
        connection.withDrawAmount(80, customer);
    }

    @Test
    void getBalance() throws Exception {
        withDrawAmount();
        int expected = 140;
        int actual = connection.getBalance();
        assertEquals(expected, actual);
        System.out.println(connection.getBalance());
    }
}