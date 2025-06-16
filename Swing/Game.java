import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;


public class Game {
    private Player player;
    private Map<String, Integer> startingCosts;
    private Map<String, Integer> startingClickCosts;
    private Map<String, Integer> upgradeAmounts;
    private DecimalFormat df = new DecimalFormat("#.##");

    public Game(Player player) {
        this.player = player;
        startingCosts = player.getStartingCosts();
        startingClickCosts = player.getClickCosts();
        upgradeAmounts = player.getUpgradeAmounts();
    }

    public void startGame() {
        //creates the default GUI size
        JFrame frame = new JFrame("Tree Tapper");

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize((int) screenSize.getWidth(), (int) screenSize.getHeight());
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //creates a button to login
        JButton loginButton = new JButton("LOGIN");
        loginButton.setPreferredSize(new Dimension(125, 50));
        loginButton.setMaximumSize(new Dimension(125, 50));
        loginButton.setAlignmentY(Component.TOP_ALIGNMENT);

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onLoginClick();
            }
        });

        //creates a button to create an account
        JButton createAccButton = new JButton("CREATE ACC");
        createAccButton.setPreferredSize(new Dimension(125, 50));
        createAccButton.setMaximumSize(new Dimension(125, 50));
        createAccButton.setAlignmentY(Component.TOP_ALIGNMENT);

        createAccButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCreateClick();
            }
        });

        //puts the login and create account buttons in a panel to be put side to side
        JPanel accPanel = new JPanel();
        accPanel.setLayout(new BoxLayout(accPanel, BoxLayout.X_AXIS));
        accPanel.add(loginButton);
        accPanel.add(createAccButton);

        //creates an money count
        JLabel moneyLabel = new JLabel(df.format(player.getPlayerMoney()) + " wood");
        moneyLabel.setHorizontalAlignment(SwingConstants.CENTER);
        moneyLabel.setFont(new Font("Arial", Font.BOLD, 36));
        moneyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        //creates an count of the rate at which money is automatically earned
        JLabel rateLabel = new JLabel(player.getMoneyPerSec() + " wood per sec");
        rateLabel.setFont(new Font("Arial", Font.BOLD, 24));
        rateLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        //stores the two labels in a panel in order to place them vertically
        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS));
        labelPanel.add(Box.createVerticalStrut(10));
        labelPanel.add(moneyLabel);
        labelPanel.add(rateLabel);
        labelPanel.add(Box.createVerticalStrut(10));
        frame.add(labelPanel, BorderLayout.NORTH);

        //adds both the panel with the account buttons and the panel with the labels to a new panel to be put at the top of the screen
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.add(accPanel, BorderLayout.WEST);
        topPanel.add(labelPanel, BorderLayout.CENTER);
        frame.add(topPanel, BorderLayout.NORTH);

        //updates the text every 100 ms
        javax.swing.Timer timer = new javax.swing.Timer(100, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                moneyLabel.setText(df.format(player.getPlayerMoney()) + " wood");
                rateLabel.setText(player.getMoneyPerSec() + " wood per sec");
            }
        });
        timer.start();

        //adds money from the amount rate every second
        javax.swing.Timer timer2 = new javax.swing.Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                player.setPlayerMoney(player.getPlayerMoney() + player.getMoneyPerSec());
                
            }
        });
        timer2.start();

        //automatically saves the player's data every second, removing it first to prevent a duplicate instance
        javax.swing.Timer timer3 = new javax.swing.Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (player.getName() != null) {
                    DataHandler.removeData(player);
                    DataHandler.saveData(player);
                }
            }
        });
        timer3.start();

        //creates a clickable button that increases money
        ImageIcon tree = new ImageIcon("/Users/benjamin/Downloads/final-project-BenjaminS9-main 3/Images/Tree.png");
        Image treeImage = tree.getImage();
        Image treeScaled = treeImage.getScaledInstance(300, 300, Image.SCALE_SMOOTH);
        ImageIcon treeScaledIcon = new ImageIcon(treeScaled);
        JButton clickButton = new JButton(treeScaledIcon);
        clickButton.setPreferredSize(new Dimension(300, 300));
        clickButton.setBorderPainted(false);
        clickButton.setContentAreaFilled(false);
        clickButton.setFocusPainted(false);
        clickButton.setOpaque(false);
        frame.add(clickButton, BorderLayout.CENTER);

        //activates when the button is clicked
        JLayeredPane layeredPane = frame.getLayeredPane();
        clickButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onClick(layeredPane);
            }
        });

        //creates a panel containing an array of buttons to represent upgrades that increases the amount earned per click
        JPanel clickUpgradePanel = new JPanel();
        clickUpgradePanel.setLayout(new BoxLayout(clickUpgradePanel, BoxLayout.X_AXIS));
        clickUpgradePanel.setBackground(new Color(39, 174, 96));
        clickUpgradePanel.add(Box.createVerticalGlue());
        JScrollPane scrollPane = new JScrollPane(clickUpgradePanel);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 0));
        scrollPane.setPreferredSize(new Dimension(350, 0));
        for (Map.Entry<String, Integer> entry : startingClickCosts.entrySet()) {
            String upgradeName = entry.getKey();
            int upgradeCost = entry.getValue();
            int increaseAmount = upgradeAmounts.get(upgradeName);
            int owned = 0;
            Map<String, Integer> userOwned = player.getUpgrades();
            for (Map.Entry<String, Integer> entry2 : userOwned.entrySet()) {
                String ownedName = entry2.getKey();
                if (ownedName.equals(upgradeName)) {
                    owned = entry2.getValue();
                }
            } 


            JButton clickUpgrade = new JButton(upgradeName + "- $" + upgradeCost);
            clickUpgrade.setPreferredSize(new Dimension(150,150));
            clickUpgrade.setMaximumSize(new Dimension(150, 150));
            clickUpgrade.setFont(new Font("Arial", Font.BOLD, 14));
            clickUpgrade.setBackground(new Color(46, 204, 113));
            clickUpgrade.setForeground(Color.BLACK);
            clickUpgrade.setFocusPainted(false);
            clickUpgrade.setContentAreaFilled(true);
            clickUpgrade.setOpaque(true);
            clickUpgrade.setBorderPainted(false);
        
            clickUpgradePanel.add(Box.createHorizontalStrut(10));
            clickUpgrade.setAlignmentX(Component.CENTER_ALIGNMENT);

            Upgrades upgradeClick = new Upgrades(upgradeName, upgradeCost, player, increaseAmount, owned);
            upgradeClick.updateCurrentCost();

            clickUpgradePanel.add(clickUpgrade);
            clickUpgradePanel.add(Box.createHorizontalStrut(10));
    
            clickUpgrade.setText(upgradeName + "- $" + df.format(upgradeClick.getCurrentCost()));

            clickUpgrade.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    boolean clickSuccess = upgradeClick.buy("click");
                    if (clickSuccess) {
                        clickUpgrade.setText(upgradeName + "- $" + df.format(upgradeClick.getCurrentCost()));
                    }
                }
            });
        }
        //creates a panel to hold the upgrades
        JPanel upgradePanel = new JPanel();
        upgradePanel.setLayout(new BoxLayout(upgradePanel, BoxLayout.Y_AXIS));
        upgradePanel.setBackground(new Color(39, 174, 96));
        JScrollPane scrollPane2 = new JScrollPane(upgradePanel);
        scrollPane2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        scrollPane2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane2.setPreferredSize(new Dimension(350, 400));

        //creates an array of buttons
        for (Map.Entry<String, Integer> entry : startingCosts.entrySet()) {
            String upgradeName = entry.getKey();
            int upgradeCost = entry.getValue();
            int upgradeAmount = upgradeAmounts.get(upgradeName);
            int owned = 0;
            Map<String, Integer> userOwned = player.getUpgrades();
            for (Map.Entry<String, Integer> entry2 : userOwned.entrySet()) {
                String ownedName = entry2.getKey();
                if (ownedName.equals(upgradeName)) {
                    owned = entry2.getValue();
                }
            } 

            JButton upgradeBtn = new JButton(upgradeName + "- $" + upgradeCost);

            upgradeBtn.setPreferredSize(new Dimension(300, 80));
            upgradeBtn.setMaximumSize(new Dimension(300, 80));
            upgradeBtn.setFont(new Font("Arial", Font.BOLD, 16));
            upgradeBtn.setBackground(new Color(46, 204, 113));
            upgradeBtn.setForeground(Color.BLACK);
            upgradeBtn.setFocusPainted(false);
            upgradeBtn.setContentAreaFilled(true);
            upgradeBtn.setOpaque(true);
            upgradeBtn.setBorderPainted(false);
        
            upgradePanel.add(Box.createVerticalStrut(10));  
            upgradeBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

            Upgrades upgrade = new Upgrades(upgradeName, upgradeCost, player, upgradeAmount, owned);
            upgrade.updateCurrentCost();

            upgradeBtn.setText(upgradeName + "- $" + df.format(upgrade.getCurrentCost()));

            upgradeBtn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    boolean success = upgrade.buy("auto");
                    if (success) {
                        upgradeBtn.setText(upgradeName + "- $" + df.format(upgrade.getCurrentCost()));
                    }
                }
            });

            upgradePanel.add(upgradeBtn);
            upgradePanel.add(Box.createVerticalStrut(10));
        }

        //adds both upgrade panels to the same panel to display on the right side of the screen
        JPanel eastPanel = new JPanel();
        eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.Y_AXIS));
        eastPanel.add(scrollPane);
        eastPanel.add(scrollPane2);
        eastPanel.add(Box.createVerticalStrut(10));
        frame.add(eastPanel, BorderLayout.EAST);
    }

    //increases the player's money
    private void onClick(JLayeredPane layeredPane) {
        player.setPlayerMoney(player.getPlayerMoney() + player.getOnClick());

        //creates a text that appears where the mouse is and visually displays the amount earned
        Point mouseLocation = MouseInfo.getPointerInfo().getLocation();
        SwingUtilities.convertPointFromScreen(mouseLocation, layeredPane);
        JLabel clickText = new JLabel("+" + player.getOnClick() + "wood");
        clickText.setFont(new Font("Arial", Font.BOLD, 14));
        clickText.setForeground(Color.GREEN);
        clickText.setSize(clickText.getPreferredSize());
        clickText.setLocation(mouseLocation.x, mouseLocation.y);
        layeredPane.add(clickText, JLayeredPane.POPUP_LAYER);

        //makes the text dissapear after a second
        javax.swing.Timer visibleTimer = new javax.swing.Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                layeredPane.remove(clickText);
                layeredPane.repaint();
            }
        });
        visibleTimer.setRepeats(false);
        visibleTimer.start();
    }

    //creates a JDialog allowing the user to input a username, password, and press submit
    private void onLoginClick() {
        JDialog dialog = new JDialog((Frame) null, "Login", true);
        dialog.setLayout(new GridLayout(3, 2, 5, 5));

        JLabel userLabel = new JLabel("Username:");
        JTextField userField = new JTextField(15);
        JLabel passLabel = new JLabel("Password:");
        JPasswordField passField = new JPasswordField(15);
        JButton submit = new JButton("Submit");

        submit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loginConfirm(userField, passField, dialog);
            }
        });

        dialog.add(userLabel);
        dialog.add(userField);
        dialog.add(passLabel);
        dialog.add(passField);
        dialog.add(new JLabel());
        dialog.add(submit);

        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

    //checks if the username and password given matches that of any user in the text file containing all user data
    private void loginConfirm(JTextField user, JPasswordField pass, JDialog dialog) {
        String username = user.getText();
        String password = new String(pass.getPassword());

        Player loadPlayer = DataHandler.loadData(username, password);
        if (player != null) { //if successful, the GUI is relaunches to update the displayed data
            player = loadPlayer;
            JOptionPane.showMessageDialog(dialog, "Login successful!");
            dialog.dispose();
    
            Window window = SwingUtilities.getWindowAncestor(dialog);
            window.dispose();
            new Game(player).startGame();
        } else {
            JOptionPane.showMessageDialog(dialog, "Invalid username or password");
        }
    }

    //creates a JDialog allowing the user to input a username, password, and press submit
    private void onCreateClick() {
        JDialog dialog = new JDialog((Frame) null, "Create Account", true);
        dialog.setLayout(new GridLayout(3, 2, 5, 5));

        JLabel userLabel = new JLabel("Username:");
        JTextField userField = new JTextField(15);
        JLabel passLabel = new JLabel("Password:");
        JPasswordField passField = new JPasswordField(15);
        JButton submit = new JButton("Submit");

        submit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                createAcc(userField, passField, dialog);
            }
        });

        dialog.add(userLabel);
        dialog.add(userField);
        dialog.add(passLabel);
        dialog.add(passField);
        dialog.add(new JLabel());
        dialog.add(submit);

        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);        
    }

    //adds the new player with the given username and password to the text file containing all user data
    private void createAcc(JTextField user, JPasswordField pass, JDialog dialog) {
        String username = user.getText();
        String password = new String(pass.getPassword());

        player.setName(username);
        player.setPass(password);
        DataHandler.saveData(player);

        JOptionPane.showMessageDialog(dialog, "Account created successfully");
        dialog.dispose();
    }

    //starts the game by calling a non-static method in the main method
    public static void main(String[] args) {
        Player player = new Player(null, null); //starts without a username and password

        SwingUtilities.invokeLater(() -> {
            new Game(player).startGame();
        });
    }
}
