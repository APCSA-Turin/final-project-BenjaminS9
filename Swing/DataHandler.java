import java.io.*;
import java.nio.file.*;
import java.util.*;

public class DataHandler {
    private static final String FILE = "login.txt"; //file that user data is stored

    //saves all data in player between different lines
    public static void saveData(Player player) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE, true))) {
            writer.write("user:" + player.getName());
            writer.newLine();
            writer.write("pass:" + player.getPass());
            writer.newLine();
            writer.write("money:" + (int) player.getPlayerMoney());
            writer.newLine();
            writer.write("moneyPerSec:" + (int) player.getMoneyPerSec());
            writer.newLine();
            writer.write("moneyPerClick:" + (int) player.getOnClick());
            writer.newLine();
            for (Map.Entry<String, Integer> entry : player.getUpgrades().entrySet()) {
                writer.write("upgrade:" + entry.getKey() + "=" + entry.getValue());
                writer.newLine();
            }
            writer.write("---"); //each user is separated by "---"
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //confirms matching username and password in the text file before loading data
    public static Player loadData(String username, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE))) {
            String line;
            String user = null;
            String pass = null;
            int money = 0;
            int moneyPerSec = 0;
            int moneyPerClick = 1;
            Map<String, Integer> upgrades = new HashMap<>();

            //assigns all variables to their respective value in the user's data
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("user:")) {
                    user = line.substring(5);
                } else if (line.startsWith("pass:")) {
                    pass = line.substring(5);
                } else if (line.startsWith("money:")) {
                    money = Integer.parseInt(line.substring(6));
                } else if (line.startsWith("moneyPerSec:")) {
                    moneyPerSec = Integer.parseInt(line.substring(12));
                } else if (line.startsWith("moneyPerClick:")) {
                    moneyPerClick = Integer.parseInt(line.substring(14));
                } else if (line.startsWith("upgrade:")) {
                    String[] parts = line.substring(8).split("=");
                    upgrades.put(parts[0], Integer.parseInt(parts[1]));
                } else if (line.equals("---")) { //located at the end of every user's data
                    //checks if the user matches the given username and password
                    if (user != null && user.equals(username) && pass.equals(password)) {
                        Player player = new Player(user, pass);
                        player.setPlayerMoney(money);
                        player.setPerSec(moneyPerSec);
                        player.setOnClick(moneyPerClick);
                        player.setUpgrades(upgrades);
                        return player; //returns a new Player object with the loaded data from the file
                    }
                    
                    //resets variables if the username and password do not match the user
                    user = null;
                    pass = null;
                    money = 0;
                    moneyPerSec = 0;
                    moneyPerClick = 1;
                    upgrades.clear();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    return null; //returns if there are no users that match the given username and password
    }

    //removes a player's data by excluding it when transfering it to a new file (meant to prevent duplicate instances of the same user)
    public static void removeData(Player player) {
        String username = player.getName();
        File file = new File("login.txt");
        File tempFile = new File("userdata_temp.txt");

        try (BufferedReader reader = new BufferedReader(new FileReader(file));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            boolean removeSection = false;

            while ((line = reader.readLine()) != null) { //continues until there is a blank line
                if (line.equals("user:" + username)) { //checks if the username in the user's data matches the player in the parameter
                    removeSection = true; //marks the line for removal
                    continue; //skips the iteration to prevent it from being added to the new temporary file
                }

                if (removeSection && line.equals("---")) { //when reaching the end of the user's data, it stops marking lines for removal
                    removeSection = false;
                    continue; //keeps the new temporary file from adding the line "---"
                }

                if (!removeSection) { //if the line isn't marked for removal, it is added to the new file
                    writer.write(line);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!file.delete()) { //deletes the original file of user data
            System.out.println("Could not delete original file");
            return;
        }
        if (!tempFile.renameTo(file)) { //changes the name of the temporary file that excludes the data of the player in the parameter to that of the original file
            System.out.println("Could not rename temp file");
        }
    }
}
