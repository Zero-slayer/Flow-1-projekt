package jdbc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JDBC_DB_CONNECTIONTest {

    private String db_url;
    private String db_user;
    private String db_password;
    private JDBC_DB_CONNECTION connection = null;

    @BeforeEach
    void setUp() throws Exception {
        db_user = "root";
        db_password = "RPRw!YjPIHHgpF01GhWIdcvf2ZFh!YFMOB7QijYH";
        db_url = "jdbc:mysql://localhost/?serverTimezone=UTC";
        connection = new JDBC_DB_CONNECTION(db_url, db_user, db_password);
    }

    @Test
    void depositAmount() throws Exception {
        try {
            connection.depositAmount(100);
            connection.depositAmount(120);
            int expected = 220;
            int actual = connection.getBalance();
            assertEquals(expected, actual);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    @Test
    void withDrawAmount() throws Exception {
            connection.withDrawAmount(80);
    }

    @Test
    void getBalance() throws Exception {
            connection.getBalance();
    }
}