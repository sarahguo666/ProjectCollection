import java.util.Collections;

/**
 * Created by Leo on 11/29/17.
 */
public class CreditCard extends BankCard {
    private int date;
    protected double limit;
    public CreditCard(){

    }
    public CreditCard(String cardHolder, long cardNumber, int expiration,
                      double limit){
        setName(cardHolder);
        setCardnum(cardNumber);
        date = expiration;
        this.limit = limit;
    }
    public CreditCard(String cardHolder, long cardNumber, int
            expiration){
        setName(cardHolder);
        setCardnum(cardNumber);
        date = expiration;
        limit = 500;
    }
    public double limit(){
        return this.limit;
    }
    public double availableCredit(){
        return limit - balance();
    }
    public int expiration(){
        return date;
    }
    public void setDate(int i){
        date = i;
    }
    public String toString(){
        String temp = Integer.toString(date);
        String dat = "";
        if(temp.length() == 3){
            dat +="0" +temp.charAt(0)+"/"+temp.charAt(1)+temp.charAt(2);
        }
        else{
            dat += temp.charAt(0);
            dat += temp.charAt(1)+"/"+temp.charAt(2);
            dat +=temp.charAt(3);
        }
        String s = "Card# ";
        s+= number() + " Expire: " + dat + "  Balance: $" + balance();
        return s;
    }
    public boolean getCashAdvance(double cash){
        double amount = cash *1.05;
        String round = String.format("%.2f",amount);
        amount = Double.parseDouble(round);

        if (amount <= availableCredit()){
            setBalance(balance() + amount);
            Transaction t1 = new Transaction("advance","CSEBank",cash);
            Transaction t2 = new Transaction("fee","Cash advance fee",amount - cash);
            list.add(t1);
            list.add(t2);
            return true;
        }
        return false;

    }

    public boolean addTransaction(Transaction t){
        if(t.type().equalsIgnoreCase("debit")){
            if(Math.abs(t.amount()) <= availableCredit()){
                setBalance(balance()+t.amount());
                list.add(t);
                return true;
            }
            else{
                return false;
            }
        }
        else if(t.type().equalsIgnoreCase("credit")){
            if(Math.abs(t.amount()) <= availableCredit()){
                setBalance(balance() + t.amount());
                list.add(t);
                return true;
            }
            return false;
        }
        else{
            return false;
        }
    }

    public void printStatement(){
        boolean fraud = false;
        Collections.sort(list);
        System.out.println("Account info");
        System.out.println("--------------------");
        System.out.println("Name: " + cardHolder());
        System.out.println("Card Number: " + number());
        String temp = Integer.toString(date);
        String dat = "";
        if(temp.length() == 3){
            dat +="0" +temp.charAt(0)+"/"+temp.charAt(1)+temp.charAt(2);
        }
        else{
            dat += temp.charAt(0);
            dat += temp.charAt(1)+"/"+temp.charAt(2);
            dat +=temp.charAt(3);
        }
        System.out.println("Expire: " + dat);
        System.out.println("Avaliable Credit: " + availableCredit());
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
