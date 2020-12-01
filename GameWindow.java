import java.awt.BorderLayout;
import java.awt.Component;
import javax.swing.JFrame;
import util.ResourceCollection;

class GameWindow extends JFrame {
   static final int HUD_HEIGHT = 48;
   static final String TITLE = "Bomberman";

   GameWindow(GamePanel game) {
      this.setTitle("Bomberman");
      this.setIconImage(ResourceCollection.icon.getImage());
      this.setLayout(new BorderLayout());
      this.add(game, "Center");
      this.setResizable(false);
      this.setDefaultCloseOperation(3);
      this.pack();
      this.setLocationRelativeTo((Component)null);
      this.setVisible(true);
   }
}
