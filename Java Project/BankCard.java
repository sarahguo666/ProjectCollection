import java.lang.reflect.Array;
import java.util.ArrayList;

public abstract class BankCard {
    private String name;
    private long cardnum;
    private double balance;
    protected ArrayList<Transaction> list;
    public BankCard(){
        name = "";
        cardnum = 0;
        balance = 0;
        list = new ArrayList<>();
    }

    public BankCard(String cardholderName, long cardNumber){
        name = cardholderName;
        cardnum = cardNumber;
        balance = 0.0;
        list = new ArrayList<>();
    }
    public double balance(){
        return balance;
    }
    public long number(){
        return cardnum;
    }
    public String cardHolder(){
        return name;
    }
    public void setName(String s){
        name = s;
    }
    public void setCardnum(long d){
        cardnum = d;
    }
    public void setBalance (double d){
        balance = d;
    }

    public String toString(){
        String s = "Card# ";
        s+= cardnum +"  Balance: $" + balance;
        return s;
    }
    public abstract boolean addTransaction(Transaction t);
    public abstract void printStatement();
}
