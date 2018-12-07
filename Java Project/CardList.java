import java.util.ArrayList;

/**
 * Created by Sarah on 11/29/17.
 */
public class CardList {
    private ArrayList<BankCard> cardList;
    public CardList(){
        cardList = new ArrayList<>();
    }
    public void add(BankCard b){
        cardList.add(b);
    }
    public void add(int index, BankCard b){
        cardList.add(index,b);
    }
    public int size(){
        return cardList.size();
    }

    public BankCard get(int index){
        if(index >= size()){
            return null;
        }
        return cardList.get(index);
    }
    public int indexOf(long cardNumber){
        for(int i = 0; i <cardList.size(); i++){
            if(cardList.get(i).number() == cardNumber){
                return i;
            }
        }
        return -1;
    }
    public ArrayList<BankCard> list(){
        return cardList;
    }
}
