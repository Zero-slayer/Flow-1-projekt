import java.util.ArrayList;
import java.util.Date;
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
        int sum= 0;
        for (Transaction transaction : transactions) {
            sum += transaction.getAmount();
        }
        return sum;
    }

    public int withdrawAmount(int amount){
        try {
            if (amount <= getBalance())
                transactions.add(new Transaction(Math.abs(amount)*-1, new Date()));

        }
        catch (IndexOutOfBoundsException e){
            System.out.println("Amount is bigger than the account balance");
            e.printStackTrace();
        }
        return getBalance();
    }

    public int depositAmount(int amount){
        if (amount > 0) {
            transactions.add(new Transaction(amount, new Date()));
            } else
                try {
                    throw new IndexOutOfBoundsException("Amount less than 0");
                }catch (IndexOutOfBoundsException e) {
                    System.out.println("Amount is less or equal to 0");
                    e.printStackTrace();
        }
        return getBalance();
          }
}