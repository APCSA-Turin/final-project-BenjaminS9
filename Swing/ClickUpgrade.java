public class ClickUpgrade extends Upgrades { //NOT FINISHED
   private Player player;


   public ClickUpgrade(Player player) {
       super("Click 1", 30, player);
       this.player = player;
   }


   public void buy() {
       super.buy();
       player.setOnClick(player.getOnClick() + 1);
   }
}
