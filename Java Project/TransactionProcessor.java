/**
 * Created by Sarah on 11/27/17.
 */
// Driver class for the final project
import javax.smartcardio.Card;
import java.util.*;
        import java.io.*;

public class TransactionProcessor
{
    public static void main(String[] args) throws IOException {
        // write your code here
        System.out.println("Please enter the card data filename: ");
        Scanner input = new Scanner(System.in);
        String cardFile = input.nextLine();
        CardList list = loadCardData(cardFile);

        System.out.println("Please enter the transaction data filename: ");
        Scanner input1 = new Scanner(System.in);
        String transFile = input.nextLine();
        processTransactions(transFile,list);

        for(BankCard b: list.list()){
            b.printStatement();
        }
    }
    private static String getCardType (long number)
    {
        // Return a String indicating whether 'number' belongs to a
        // CreditCard, RewardsCard, or a PrepaidCard (or null if it's none
        // of the three)

        String result;

        int firstTwo = Integer.parseInt(("" + number).substring(0,2));

        switch(firstTwo)
        {
            case 84:
            case 85: result = "CreditCard"; break;
            case 86:
            case 87: result = "RewardsCard"; break;
            case 88:
            case 89: result = "PrepaidCard"; break;
            default: result = null; // invalid card number
        }

        return result;
    }
    public static BankCard convertToCard(String data){

        String[]split = data.trim().split("\\s+");
        long cardNum = Long.parseLong(split[0]);
        String type = getCardType(cardNum);
        String name = split[1];
        name = name.replace("_"," ");
        if(type.equalsIgnoreCase("CreditCard")){
            int exp = Integer.parseInt(split[2]);
            if(split.length == 3){
                return new CreditCard(name,cardNum,exp);
            }else{
                double amount = Double.parseDouble(split[3]);
                return new CreditCard(name,cardNum,exp,amount);
            }
        }else if(type.equalsIgnoreCase("RewardsCard")){
            int exp = Integer.parseInt(split[2]);
            if(split.length == 3){
                return new RewardsCard(name,cardNum,exp);
            }else{
                double amount = Double.parseDouble(split[3]);
                return new RewardsCard(name,cardNum,exp,amount);
            }
        }else if(type.equalsIgnoreCase("PrepaidCard")){
            if(split.length == 2){
                return new PrepaidCard(name,cardNum);
            }
            else{
                double balance = Double.parseDouble(split[2]);
                return new PrepaidCard(name,cardNum,balance);
            }
        }else{
            return null;
        }
    }
    public static CardList loadCardData(String fName) throws IOException {
        File file = new File(fName);
        BufferedReader br = new BufferedReader(new FileReader(file));
        CardList list = new CardList();
        String st;
        while ((st = br.readLine()) != null){
            list.add(convertToCard(st));
        }
        return list;
    }
    public static void processTransactions(String filename, CardList c) throws IOException {
        File file = new File(filename);
        BufferedReader br = new BufferedReader(new FileReader(file));
        CardList list = new CardList();
        String st;
        while ((st = br.readLine()) != null){
            if(st.equals("") ){
                continue;
            }
            String[] split = st.split("\\s");
            long cardNum = Long.parseLong(split[0]);
            int index = c.indexOf(cardNum);
            String type = split[1];
            if(type.equalsIgnoreCase("redeem")){
                int pt = Integer.parseInt(split[2]);
                RewardsCard temp = (RewardsCard) c.get(index);
                temp.redeemPoints(pt);
            }else if(type.equalsIgnoreCase("top-up")){
                double d = Double.parseDouble(split[2]);
                PrepaidCard temp = (PrepaidCard)c.get(index);
                temp.addFunds(d);
            }else if(type.equalsIgnoreCase("advance")){
                double amount = Double.parseDouble(split[2]);
                CreditCard temp = (CreditCard) c.get(index);
                temp.getCashAdvance(amount);

            }
            else if(type.equalsIgnoreCase("fee")){
                //do nothing
            }
            else{
                double amount = Double.parseDouble(split[2]);
                String merchan = split[3];
                merchan = merchan.replace("_"," ");
                Transaction t = new Transaction(type,merchan,amount);
                BankCard temp = (BankCard)c.get(index);
                temp.addTransaction(t);
            }
        }
    }
}