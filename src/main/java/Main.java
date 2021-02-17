/*To Do:
    -Fix add new Customer
    -Add remove customer + account
*/
import java.util.*;

public class Main {
    public static int input = 0;
    public static boolean active = true;
    public static ArrayList<Customer> CustomerList = new ArrayList<>();
    public static ArrayList<Account> accountList = new ArrayList<>();
    public static void main(String[] args) {
        CustomerList.add(new Customer("Jon"));
        CustomerList.add(new Customer("Andrew"));
        CustomerList.add(new Customer("Dan"));
        for (Customer t : CustomerList){
            accountList.add(new Account(t));
        }
        Scanner sc = new Scanner(System.in);
        while(active){
            boolean isCustomer = false;
            System.out.println("Enter Customer name");
            Customer cSel;
            Account aSel = null;
            String nm = sc.nextLine();
            ArrayList<String> customNames = new ArrayList<>();
            for(Customer temp : CustomerList){
                customNames.add(temp.getName());
            }
            if(customNames.contains(nm)){
                for (Customer c : CustomerList){
                    if(c.getName().equals(nm)){
                        cSel = c;
                        isCustomer = true;
                        for (Account a : accountList){
                            if(a.customer == cSel){
                                aSel = a;
                            }
                        }
                    }
                }
            }else if(!nm.equals("")){
                System.out.println(nm + " is not a Customer, returning...");
            }
            while (isCustomer){
                System.out.println("Select mode");
                System.out.println("1: Deposit amount");
                System.out.println("2: Withdraw amount");
                System.out.println("3: Show balance");
                System.out.println("4: Print all transactions");
                System.out.println("5: Show all customers");
                System.out.println("6: Show all accounts");
                System.out.println("7: Change selected Customer");
                System.out.println("8: Exit");
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
                        ArrayList<String> customList = new ArrayList<>();
                        for (Customer customer : CustomerList) {
                            customList.add(customer.getName());
                        }
                        Collections.sort(customList);
                        for (String s : customList){
                            System.out.println(s);
                        }
                        break;

                    case (6):
                        System.out.println("Showing accounts");
                        HashMap<String, Integer> map = new HashMap<>();
                        for(Account a : accountList){
                            map.put(a.getCustomer().getName(), a.getBalance());
                        }
                        List<Map.Entry<String, Integer>> list = new LinkedList<>(map.entrySet());
                        list.sort(Map.Entry.comparingByValue());
                        HashMap<String, Integer> temp = new LinkedHashMap<>();
                        for(Map.Entry<String, Integer> aa : list){
                            temp.put(aa.getKey(), aa.getValue());
                        }
                        for(Map.Entry<String, Integer> en : map.entrySet()){
                            System.out.println(en.getKey() + " : " + en.getValue());
                        }
                        break;

                    case (7):
                        isCustomer = false;
                        break;

                    case (8):
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
