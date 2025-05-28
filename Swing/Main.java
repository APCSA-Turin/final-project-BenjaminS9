import javax.swing.*;


public class Main {
   public static void main(String[] args) {
       System.out.println("Headless: " + java.awt.GraphicsEnvironment.isHeadless());


       JFrame frame = new JFrame("Swing Example");
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       frame.setSize(300, 200);
      
       JLabel label = new JLabel("Hello, Swing!", SwingConstants.CENTER);
       frame.add(label);
      
       frame.setVisible(true);
   }
}
