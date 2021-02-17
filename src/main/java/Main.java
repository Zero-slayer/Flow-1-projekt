/*To Do:
    -Fix add new Customer
    -Add remove customer + account
*/
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static int input = 0;
    public static boolean active = true;
    public static ArrayList<Customer> CustomerList = new ArrayList<>();
    public static ArrayList<Account> accountList = new ArrayList<>();
    public static void main(String[] args) {
        CustomerList.add(new Customer("Jon"));
        CustomerList.add(new Customer("Andrew"));
        for (Customer t : CustomerList){
            accountList.add(new Account(t));
        }
        //Account account = new Account(c1);
        //account.depositAmount(125);
        //System.out.println(String.format("Ny balance: %d", newBalance));

        //account.depositAmount(325);
        //System.out.println(String.format("Ny balance: %d", newBalance));

        //account.getBalance();
        //System.out.println(String.format("Ny balance: %d", newBalance));

        //account.withdrawAmount(50);
        Scanner sc = new Scanner(System.in);
        while(active){
            boolean isCustomer = false;
            System.out.println("Enter Customer name");
            Customer cSel;
            Account aSel = null;
            String nm = sc.nextLine();
            for(int i = CustomerList.size()-1; i >= 0; i--){
                if(CustomerList.get(i).getName().equals(nm) && !nm.equals("")){
                    cSel = CustomerList.get(i);
                    isCustomer = true;
                    for(Account a : accountList){
                        if(a.customer == cSel){
                            aSel = a;
                        }
                    }
                    break;
                }else if(!nm.equals("")){
                    System.out.println(nm + " is not a customer, Returning...");
                }
                // ignore spaghetti attempt to add new customer, will fix later
                /*else if(!nm.equals("")){
                    System.out.println(nm + " Is not a Customer, do you want to add as new customer?");
                    String confirm = sc.nextLine();
                    if(confirm.equals("y")){
                        System.out.println("Adding new...");
                        Customer newC = new Customer(nm);
                        CustomerList.add(newC);
                        accountList.add(new Account(newC));
                    }else if(confirm.equals("n")){
                        System.out.println("Returning...");
                    }else{
                        System.out.println("Not a valid input");
                    }
                }*/
            }
            while (isCustomer){
                System.out.println("Select mode");
                System.out.println("1: Input amount");
                System.out.println("2: Remove amount");
                System.out.println("3: Show balance");
                System.out.println("4: Print all transactions");
                System.out.println("5: Show all customers");
                System.out.println("6: Change selected Customer");
                System.out.println("7: Exit");
                input = sc.nextInt();
                assert aSel != null;
                switch (input){
                    case (1):
                        System.out.println("Enter amount to add");
                        int addAmt = sc.nextInt();
                        aSel.depositAmount(addAmt);
                        break;

                    case (2):
                        System.out.println("Enter amount to remove");
                        int remAmt = sc.nextInt();
                        if(remAmt > aSel.getBalance()){
                            System.out.println("Not enough in account; Cancelling transaction");
                        }
                        aSel.withdrawAmount(remAmt);
                        break;

                    case (3):
                        System.out.println("Current balance:");
                        System.out.println(aSel.getBalance());
                        break;

                    case (4):
                        System.out.println("Printing transactions:");
                        for(Transaction transaction : aSel.getTransactions()){
                            System.out.println(transaction.getAmount() + " : " + transaction.getDate());
                        }
                        break;

                    case (5):
                        System.out.println("Showing Customers:");
                        List<String> customList = null;
                        for (Customer customer : CustomerList) {
                            customList.add(customer.getName());
                            Collections.sort(customList);
                        }
                        for (String s : customList){
                            System.out.println(s);
                        }
                        break;

                    case (6):
                        isCustomer = false;
                        break;

                    case (7):
                        isCustomer = false;
                        active = false;
                        break;

                    default:
                        System.out.println("Please input a valid number");
                        break;
                }
            }
        }
    }
}
