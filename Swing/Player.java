import java.util.*;

public class Player {
    private double money;
    private double moneyPerSec;
    private int onClickMoney;
    private String username;
    private String password;
    private Map<String,Integer> upgrades = new HashMap<>();
    private Map<String, Integer> startingCosts;
    private Map<String, Integer> startingClickCosts;
    private Map<String, Integer> upgradeAmounts;
 
    public Player(String user, String pass) {
        username = user;
        password = pass;
        money = 0;
        moneyPerSec = 0;
        onClickMoney = 1;

        //beginning costs for all automatic upgrades
        startingCosts = new LinkedHashMap<>();
        startingCosts.put("lumberjack", 1);
        startingCosts.put("logging camp", 15);
        startingCosts.put("logging truck", 50);
        startingCosts.put("sawmill", 100);
        startingCosts.put("harvester", 5000);
        startingCosts.put("feller buncher", 10000);
        startingCosts.put("forwarder", 50000);
        startingCosts.put("skidder", 100000);
        startingCosts.put("sawmill conveyor", 500000);
        startingCosts.put("chipping machine", 1000000);
        startingCosts.put("slash bundler", 5000000);
        startingCosts.put("pelletizer", 10000000);
        startingCosts.put("smart logging", 50000000);
        startingCosts.put("drone-piloted", 100000000);
        startingCosts.put("bioharvester", 1000000000);

        //starting costs for all click upgrades
        startingClickCosts = new LinkedHashMap<>();
        startingClickCosts.put("gloves", 50);
        startingClickCosts.put("stone axe", 1000);
        startingClickCosts.put("steel axe", 50000);
        startingClickCosts.put("manual saw", 1000000);
        startingClickCosts.put("chainsaw", 1000000000);

        //the amount that each upgrade increases the amount earned by
        upgradeAmounts = new LinkedHashMap<>();
        upgradeAmounts.put("lumberjack", 1);
        upgradeAmounts.put("logging camp", 2);
        upgradeAmounts.put("logging truck", 3);
        upgradeAmounts.put("sawmill", 5);
        upgradeAmounts.put("harvester", 10);
        upgradeAmounts.put("feller buncher", 15);
        upgradeAmounts.put("forwarder", 20);
        upgradeAmounts.put("skidder", 25);
        upgradeAmounts.put("sawmill conveyor", 50);
        upgradeAmounts.put("chipping machine", 100);
        upgradeAmounts.put("slash bundler", 250);
        upgradeAmounts.put("pelletizer", 500);
        upgradeAmounts.put("smart logging", 1000);
        upgradeAmounts.put("drone-piloted", 1500);
        upgradeAmounts.put("bioharvester", 2000);

        upgradeAmounts.put("gloves", 1);
        upgradeAmounts.put("stone axe", 3);
        upgradeAmounts.put("steel axe", 5);
        upgradeAmounts.put("manual saw", 15);
        upgradeAmounts.put("chainsaw", 75);
    }
 
    public String getName() {
        return username;
    }

    public void setName(String newName) {
        username = newName;
    }
 
    public String getPass() {
        return password;
    }

    public void setPass(String newPass) {
        password = newPass;
    }
 
    public double getPlayerMoney() {
        return money;
    }
 
 
    public void setPlayerMoney(double newMoney) {
        money = newMoney;
    }
 
 
    public double getMoneyPerSec() {
        return moneyPerSec;
    }
 
 
    public void setPerSec(double newMoney) {
        moneyPerSec = newMoney;
    }
 
 
    public int getOnClick() {
        return onClickMoney;
    }
 
 
    public void setOnClick(int newMoney) {
        onClickMoney = newMoney;
    }

    public Map<String, Integer> getStartingCosts() {
        return startingCosts;
    }

    public Map<String, Integer> getClickCosts() {
        return startingClickCosts;
    }
    
    //adds an upgrade specified by the name parameter to the upgrades HashMap to keep track of upgrades owned
    public void buyUpgrade(String name) {
        upgrades.put(name, upgrades.getOrDefault(name, 0) + 1);
    }
 
    public int getUpgradeCount(String name) {
        return upgrades.getOrDefault(name, 0); 
    }
 
    public Map<String,Integer> getUpgrades() {
        return upgrades;
    }
 
    public void setUpgrades(Map<String,Integer> newUpgrades) {
        upgrades = newUpgrades;
    }

    public Map<String, Integer> getUpgradeAmounts() {
        return upgradeAmounts;
    }
} 
