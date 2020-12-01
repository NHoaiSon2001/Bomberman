import util.ResourceCollection;

public class GameLauncher {
   static GameWindow window;

   public static void main(String[] args) {
      ResourceCollection.readFiles();
      ResourceCollection.init();

      GamePanel game = new GamePanel((String)null);

      game.init();
      window = new GameWindow(game);
      System.gc();
   }
}
