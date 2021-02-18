package jdbc;

public class JDBC_DB_BANK {

    private int id;
    private String name;
    private String city;
    private JDBC_DB_CONNECTION connection;

    public JDBC_DB_BANK(int id, String name, String city, JDBC_DB_CONNECTION connection) throws Exception {
        this.id = id;
        this.name = name;
        this.city = city;
        this.connection = connection;

        if (getId() > 0)
        if(checkBankID()) {
            connection.createBank(this);
        }
    }

    private boolean checkBankID() throws Exception {
        return getId() != connection.getBankID(getId());
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public JDBC_DB_CONNECTION getConnection() {
        return connection;
    }
}
