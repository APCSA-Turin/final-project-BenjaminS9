public class Upgrades {
   private static int totalPurchased;
   private String name;
   private static int currentCost;
   private Player player;


   public Upgrades(String name, int startCost, Player player) {
       this.name = name;
       currentCost = startCost;
       this.player = player;
   }


   public int getCurrentCost() {
       return currentCost;
   }


   public void updateCurrentCost() {
       currentCost += (Math.pow(totalPurchased, 0.9));
   }


   public void buy() {
       if (player.getPlayerMoney() >= currentCost) {
           player.setPlayerMoney(player.getPlayerMoney() - currentCost);
           totalPurchased++;
           updateCurrentCost();
           player.setOnClick(player.getOnClick() + 1); //CHANGE THIS LATER
       } else {
           System.out.println("(ERROR) Insufficient funds");
       }
   }


   public void increaseMoneyEarned(int amount) {
       player.setPerSec(player.getMoneyPerSec() + amount);
   }
}
