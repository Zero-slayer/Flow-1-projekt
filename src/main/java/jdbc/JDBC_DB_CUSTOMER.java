package jdbc;

public class JDBC_DB_CUSTOMER {
    private String name;
    private String city;
    private int bankId;

    public JDBC_DB_CUSTOMER(String name, String city, JDBC_DB_BANK bank) {
        this.name = name;
        this.city = city;
        this.bankId = bank.getId();
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public int getBankId() {
        return bankId;
    }
}
