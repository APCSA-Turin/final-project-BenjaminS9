public class Upgrades {
    private int totalPurchased;
    private String name;
    private int startCost;
    private double currentCost;
    private Player player;
    private int increaseAmount;
 
 
    public Upgrades(String name, int startCost, Player player, int increaseAmount, int owned) {
        this.name = name;
        this.startCost = startCost;
        currentCost = startCost;
        this.player = player;
        totalPurchased = owned;
        this.increaseAmount = increaseAmount;
    }
 
    public void setPurchased(int amount) {
        totalPurchased = amount;
        updateCurrentCost(); //updates current cost to accomodate for the change in amount owned
    }
 
 
    public double getCurrentCost() {
        return currentCost;
    }
 
    //upgrade costs scale with the amount of the upgrade already owned
    public void updateCurrentCost() {
        currentCost = startCost * (Math.pow(1.3, totalPurchased)); //calculates the price for the upgrade
    }

    //distiguishes between the "auto" and "click" upgrades
    public boolean buy(String type) {
        if (player.getPlayerMoney() >= currentCost) { //makes sure that the user can afford to buy the upgrade
            player.setPlayerMoney(player.getPlayerMoney() - currentCost); //deducts the user's money by the upgrade's cost
            totalPurchased++;
            updateCurrentCost();
            player.buyUpgrade(name); //updates the amount of the upgrade owned
            if (type.equals("click")) { //distiguishes between the two upgrade to determine the variable that is increased
                player.setOnClick(player.getOnClick() + increaseAmount);
            } else {
                player.setPerSec(player.getMoneyPerSec() + increaseAmount);
            }
            return true; //returns true on successful purchase
        }
        return false; //returns false when the user cannot afford the upgrade
    }
}
 