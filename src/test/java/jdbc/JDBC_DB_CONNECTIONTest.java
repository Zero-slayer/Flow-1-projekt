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
    private JDBC_DB_ACCOUNT account;

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
        account = new JDBC_DB_ACCOUNT(customer);
    }

    @Test
    void depositAmount() throws Exception {
        createAccount();
        account.depositAmount(100);
        account.depositAmount(120);
//        int expected = 220;
//        int actual = connection.getBalance();
//        assertEquals(expected, actual);
    }

    @Test
    void withDrawAmount() throws Exception {
        depositAmount();
        account.withdrawAmount(80);
    }

    @Test
    void getBalance() throws Exception {
        withDrawAmount();
        int expected = 140;
        int actual = account.getBalance();
        assertEquals(expected, actual);
        System.out.println(account.getBalance());
    }
}