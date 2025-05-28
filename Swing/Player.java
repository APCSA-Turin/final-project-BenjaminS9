public class Player {
   private int money;
   private int moneyPerSec;
   private int onClickMoney;
   private String username;
   private String password;


   public Player(String user, String pass) {
       username = user;
       password = pass;
       money = 0;
       moneyPerSec = 0;
       onClickMoney = 1;
   }


   public int getPlayerMoney() {
       return money;
   }


   public void setPlayerMoney(int newMoney) {
       money = newMoney;
   }


   public int getMoneyPerSec() {
       return moneyPerSec;
   }


   public void setPerSec(int newMoney) {
       moneyPerSec = newMoney;
   }


   public int getOnClick() {
       return onClickMoney;
   }


   public void setOnClick(int newMoney) {
       onClickMoney = newMoney;
   }
}
