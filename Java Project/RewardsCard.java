/**
 * Created by Sarah on 11/29/17.
 */
public class RewardsCard extends CreditCard {
    protected int rewardspt;
    public RewardsCard(String holder, long number, int expiration, double
            limit){
        setName(holder);
        setCardnum(number);
        setDate(expiration);
        this.limit = limit;
    }
    public RewardsCard(String holder, long number, int expiration){
        setName(holder);
        setCardnum(number);
        setDate(expiration);
        this.limit = 500;
    }
    public int rewardPoints(){
        return rewardspt;
    }
    public boolean redeemPoints(int points){
        if(points <= rewardspt){
            double reduce = points / 100.00;
            if(reduce > balance()){
                reduce = balance();
                points = (int)(balance() *100);
            }
            rewardspt -= points;
            setBalance(balance() - reduce);
            Transaction t = new Transaction("redemption","CSEBank",reduce);
            list.add(t);
            return true;
        }
        else{
            return false;
        }
    }

    public String toString(){
        String temp = Integer.toString(expiration());
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
        s+= number() + " Expire: " + dat + "  Balance: $" + balance() + " Reward Pts: " + rewardspt;
        return s;
    }
    public boolean addTransaction(Transaction t){
        if(t.type().equalsIgnoreCase("debit")){
            if(t.amount() <= availableCredit()){
                setBalance(balance()+t.amount());
                list.add(t);
                int points = (int) (t.amount()*100);
                rewardspt += points;
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
        System.out.println("Account info");
        System.out.println("--------------------");
        System.out.println("Name: " + cardHolder());
        System.out.println("Card Number: " + number());
        String temp = Integer.toString(expiration());
        String dat = "";
        boolean fraud = false;
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
        System.out.println("Reward Points: " + rewardspt);
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
                    System.out.println(" the balance after refund is : " + this.availableCredit());
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
