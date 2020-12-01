import gameobjects.Bomber;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

public class GameHUD {
   private Bomber[] players = new Bomber[2];
   private BufferedImage[] playerInfo = new BufferedImage[2];
   private int[] playerScore = new int[2];
   boolean matchSet = false;

   GameHUD() {
   }

   void init() {
      int height = 48;
      int infoWidth = GamePanel.panelWidth / 4;
      this.playerInfo[0] = new BufferedImage(infoWidth, height, 1);
      this.playerInfo[1] = new BufferedImage(infoWidth, height, 1);
   }

   BufferedImage getP1info() {
      return this.playerInfo[0];
   }

   BufferedImage getP2info() {
      return this.playerInfo[1];
   }

   void assignPlayer(Bomber player, int playerID) {
      this.players[playerID] = player;
   }

   public void updateScore() {
      int deadPlayers = 0;

      int i;
      for(i = 0; i < this.players.length; ++i) {
         if (this.players[i].isDead()) {
            ++deadPlayers;
         }
      }

      if (deadPlayers == this.players.length - 1) {
         for(i = 0; i < this.players.length; ++i) {
            if (!this.players[i].isDead()) {
               this.playerScore[i]++;
               this.matchSet = true;
            }
         }
      } else if (deadPlayers >= this.players.length) {
         this.matchSet = true;
      }

   }

   void drawHUD() {
      Graphics[] playerGraphics = new Graphics[]{this.playerInfo[0].createGraphics(), this.playerInfo[1].createGraphics()};
      playerGraphics[0].clearRect(0, 0, this.playerInfo[0].getWidth(), this.playerInfo[0].getHeight());
      playerGraphics[1].clearRect(0, 0, this.playerInfo[1].getWidth(), this.playerInfo[1].getHeight());
      playerGraphics[0].setColor(Color.WHITE);
      playerGraphics[1].setColor(Color.GRAY);

      for(int i = 0; i < playerGraphics.length; ++i) {
         Font font = new Font("Courier New", 1, 24);
         playerGraphics[i].drawRect(1, 1, this.playerInfo[i].getWidth() - 2, this.playerInfo[i].getHeight() - 2);
         playerGraphics[i].drawImage(this.players[i].getBaseSprite(), 0, 0, (ImageObserver)null);
         playerGraphics[i].setFont(font);
         playerGraphics[i].setColor(Color.WHITE);
         playerGraphics[i].drawString(String.valueOf(this.playerScore[i]), this.playerInfo[i].getWidth() / 2, 32);
         playerGraphics[i].dispose();
      }

   }
}
