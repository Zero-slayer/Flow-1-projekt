public class Main {

    public static void main(String[] args) {
        Customer c1 = new Customer("Jon");
        Account account = new Account(c1);

        account.depositAmount(125);
        //System.out.println(String.format("Ny balance: %d", newBalance));

        account.depositAmount(325);
        //System.out.println(String.format("Ny balance: %d", newBalance));

        account.getBalance();
        //System.out.println(String.format("Ny balance: %d", newBalance));

       account.withdrawAmount(50);

        System.out.println(account.getBalance());




    }
}
