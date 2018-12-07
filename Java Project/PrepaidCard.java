/**
 * Created by Sarah on 11/29/17.
 */
public class PrepaidCard extends BankCard{
    public PrepaidCard(String cardHolder, long cardNumber, double balance){
        setName(cardHolder);
        setBalance(balance);
        setCardnum(cardNumber);
    }
    public PrepaidCard(String cardHolder, long cardNumber){
        setName(cardHolder);
        setBalance(0);
        setCardnum(cardNumber);
    }
    public boolean addTransaction(Transaction t){
        if(t.type().equalsIgnoreCase("debit")){
            if(t.amount() <= balance()){
                setBalance(balance() - t.amount());
                list.add(t);
                return true;
            }
            else{
                return false;
            }
        }
        else if(t.type().equalsIgnoreCase("credit")){
            setBalance(balance() - t.amount());
            list.add(t);
            return true;
        }
        else{
            return false;
        }
    }
    public boolean addFunds(double amount){
        if(amount >= 0){
            setBalance(balance()+amount);
            Transaction t = new Transaction("top-up","User Payment",amount);
            list.add(t);
            return true;
        }
        else{
            return false;
        }
    }
    public String toString(){
        String s = "Card #: " + number() + " Banlance : "+ balance();
        return s;
    }
    public void printStatement(){
        boolean fraud = false;
        System.out.println("Account info");
        System.out.println("--------------------");
        System.out.println("Name: " + cardHolder());
        System.out.println("Card Number: " + number());
        System.out.println("Banlance: "+ balance());
        System.out.println("Transactions");
        System.out.println("--------------------");
        for(int i = 0; i < list.size();i++){
            if(list.get(i).type().equalsIgnoreCase("debit") && list.get(i).amount() <= 1.50){
                if(!fraud && list.get(i).type().equalsIgnoreCase(list.get(i-1).type())){
                    fraud = true;
                }

                else if (fraud && list.get(i).type().equalsIgnoreCase(list.get(i-1).type())){
                    // fraud detected
                    //refund
                    System.out.println(list.get(i).toString());
                    double amount1 = list.get(i).amount();
                    double amount2 = list.get(i-1).amount();
                    Transaction t1 = new Transaction("credit","Fraud compensation",amount1);
                    Transaction t2 = new Transaction("credit","Fraud compensation",amount2);
                    addTransaction(t1);
                    addTransaction(t2);
                    System.out.println(t2.toString());
                    System.out.println(t1.toString());
                    System.out.println("** Account frozen due to suspected fraud **");
                    break;
                }
                else{
                    fraud = false;
                }
            }
            System.out.println(list.get(i).toString());
        }
    }
}
