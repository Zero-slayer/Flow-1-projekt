import java.util.ArrayList;
import java.util.List;

public class Account {

    List<Transaction>transactions;
    Customer customer;

    public Account(Customer customer){
       this.transactions = new ArrayList<>();
       this.customer = customer;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public Customer getCustomer() {
        return customer;
    }

    public int getBalance(){
        // TODO: return sum of the transactions
        return 0;
    }

    public void withdrawAmount(){
        // TODO: make it so you can't withdraw more than the balance
    }

    public void depositAmount(){

    }
}
