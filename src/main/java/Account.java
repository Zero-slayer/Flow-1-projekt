import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Account {
    int sum = 0;
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
        for (Transaction transaction : transactions) {
            sum += transaction.getAmount();
        }
        return sum;
    }

    public int withdrawAmount(int amount){
        try {
            if (amount <= getBalance())
                sum=getBalance()-amount;
            transactions.add(new Transaction(amount, new Date()));

        }
        catch (IndexOutOfBoundsException e){
            System.out.println("Amount is bigger than the account balance");
            e.printStackTrace();
        }
        return 0;
    }

    public int depositAmount(int amount){
        // TODO: skal debugges og returnere ny saldo. Smid fejl hvis amount < 0.
        transactions.add(new Transaction(amount, new Date()));
        return 0;

    }
}