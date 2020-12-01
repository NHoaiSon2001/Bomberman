import gameobjects.Bomber;
import gameobjects.GameObject;
import gameobjects.Powerup;
import gameobjects.Wall;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D.Float;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilterReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import javax.swing.JPanel;
import util.GameObjectCollection;
import util.Key;
import util.ResourceCollection;

public class GamePanel extends JPanel implements Runnable {
   static int panelWidth;
   static int panelHeight;
   private Thread thread;
   private boolean running;
   int resetDelay;
   private BufferedImage world;
   private Graphics2D buffer;
   private BufferedImage bg;
   private GameHUD gameHUD;
   private int mapWidth;
   private int mapHeight;
   private ArrayList<ArrayList<String>> mapLayout;
   private BufferedReader bufferedReader;
   private HashMap<Integer, Key> controls1;
   private HashMap<Integer, Key> controls2;
   private static final double SOFTWALL_RATE = 0.825D;

   GamePanel(String filename) {
      this.setFocusable(true);
      this.requestFocus();
      this.setControls();
      this.bg = ResourceCollection.background.getImage();
      this.loadMapFile(filename);
   }
   void init() {
      this.resetDelay = 0;
      GameObjectCollection.init();
      this.gameHUD = new GameHUD();
      this.generateMap();
      this.gameHUD.init();
      this.setPreferredSize(new Dimension(this.mapWidth * 32, this.mapHeight * 32 + 48));
      System.gc();
      this.running = true;
   }

   private void loadMapFile(String mapFile) {
      try {
         this.bufferedReader = new BufferedReader(new InputStreamReader(ResourceCollection.class.getResourceAsStream("/resources/default.csv")));
      } catch (NullPointerException var3) {
         
      }

      this.mapLayout = new ArrayList();

      String currentLine;
      try {
         while((currentLine = this.bufferedReader.readLine()) != null) {
            if (!currentLine.isEmpty()) {
               this.mapLayout.add(new ArrayList(Arrays.asList(currentLine.split(","))));
            }
         }
      } catch (NullPointerException | IOException var4) {
         System.out.println(var4 + ": Error parsing map data");
         var4.printStackTrace();
      }

   }

   private void generateMap() {
      this.mapWidth = ((ArrayList)this.mapLayout.get(0)).size();
      this.mapHeight = this.mapLayout.size();
      panelWidth = this.mapWidth * 32;
      panelHeight = this.mapHeight * 32;
      this.world = new BufferedImage(this.mapWidth * 32, this.mapHeight * 32, 1);

      for(int y = 0; y < this.mapHeight; ++y) {
         for(int x = 0; x < this.mapWidth; ++x) {
            String var3 = (String)((ArrayList)this.mapLayout.get(y)).get(x);
            byte var4 = -1;
            switch(var3.hashCode()) {
            case 49:
               if (var3.equals("1")) {
                  var4 = 2;
               }
               break;
            case 50:
               if (var3.equals("2")) {
                  var4 = 3;
               }
               break;
            case 51:
               if (var3.equals("3")) {
                  var4 = 4;
               }
               break;
            case 52:
               if (var3.equals("4")) {
                  var4 = 5;
               }
               break;
            case 72:
               if (var3.equals("H")) {
                  var4 = 1;
               }
               break;
            case 83:
               if (var3.equals("S")) {
                  var4 = 0;
               }
               break;
            case 2546:
               if (var3.equals("PB")) {
                  var4 = 6;
               }
               break;
            case 2555:
               if (var3.equals("PK")) {
                  var4 = 11;
               }
               break;
            case 2557:
               if (var3.equals("PM")) {
                  var4 = 8;
               }
               break;
            case 2560:
               if (var3.equals("PP")) {
                  var4 = 10;
               }
               break;
            case 2563:
               if (var3.equals("PS")) {
                  var4 = 9;
               }
               break;
            case 2564:
               if (var3.equals("PT")) {
                  var4 = 12;
               }
               break;
            case 2565:
               if (var3.equals("PU")) {
                  var4 = 7;
               }
            }

            switch(var4) {
            case 0:
               if (Math.random() < 0.825D) {
                  BufferedImage sprSoftWall = ResourceCollection.soft_wall.getImage();
                  Wall softWall = new Wall(new Float((float)(x * 32), (float)(y * 32)), sprSoftWall, true);
                  GameObjectCollection.spawn(softWall);
               }
               break;
            case 1:
               int code = 0;
               if (y > 0 && ((String)((ArrayList)this.mapLayout.get(y - 1)).get(x)).equals("H")) {
                  ++code;
               }

               if (y < this.mapHeight - 1 && ((String)((ArrayList)this.mapLayout.get(y + 1)).get(x)).equals("H")) {
                  code += 4;
               }

               if (x > 0 && ((String)((ArrayList)this.mapLayout.get(y)).get(x - 1)).equals("H")) {
                  code += 8;
               }

               if (x < this.mapWidth - 1 && ((String)((ArrayList)this.mapLayout.get(y)).get(x + 1)).equals("H")) {
                  code += 2;
               }

               BufferedImage sprHardWall = ResourceCollection.getHardWallTile(code);
               Wall hardWall = new Wall(new Float((float)(x * 32), (float)(y * 32)), sprHardWall, false);
               GameObjectCollection.spawn(hardWall);
               break;
            case 2:
               BufferedImage[][] sprMapP1 = ResourceCollection.player1.getSprites();
               Bomber player1 = new Bomber(new Float((float)(x * 32), (float)(y * 32 - 16)), sprMapP1);
               PlayerController playerController1 = new PlayerController(player1, this.controls1);
               this.addKeyListener(playerController1);
               this.gameHUD.assignPlayer(player1, 0);
               GameObjectCollection.spawn(player1);
               break;
            case 3:
               BufferedImage[][] sprMapP2 = ResourceCollection.player2.getSprites();
               Bomber player2 = new Bomber(new Float((float)(x * 32), (float)(y * 32 - 16)), sprMapP2);
               PlayerController playerController2 = new PlayerController(player2, this.controls2);
               this.addKeyListener(playerController2);
               this.gameHUD.assignPlayer(player2, 1);
               GameObjectCollection.spawn(player2);
               break;
            case 6:
               Powerup powerBomb = new Powerup(new Float((float)(x * 32), (float)(y * 32)), 0);
               GameObjectCollection.spawn(powerBomb);
               break;
            case 7:
               Powerup powerFireup = new Powerup(new Float((float)(x * 32), (float)(y * 32)), 1);
               GameObjectCollection.spawn(powerFireup);
               break;
            case 8:
               Powerup powerFiremax = new Powerup(new Float((float)(x * 32), (float)(y * 32)), 2);
               GameObjectCollection.spawn(powerFiremax);
               break;
            case 9:
               Powerup powerSpeed = new Powerup(new Float((float)(x * 32), (float)(y * 32)), 3);
               GameObjectCollection.spawn(powerSpeed);
               break;
            case 10:
               Powerup powerPierce = new Powerup(new Float((float)(x * 32), (float)(y * 32)), 4);
               GameObjectCollection.spawn(powerPierce);
               break;
            case 11:
               Powerup powerKick = new Powerup(new Float((float)(x * 32), (float)(y * 32)), 5);
               GameObjectCollection.spawn(powerKick);
               break;
            case 12:
               Powerup powerTimer = new Powerup(new Float((float)(x * 32), (float)(y * 32)), 6);
               GameObjectCollection.spawn(powerTimer);
            }
         }
      }

   }

   private void setControls() {
      this.controls1 = new HashMap();
      this.controls2 = new HashMap();
      this.controls1.put(38, Key.up);
      this.controls1.put(40, Key.down);
      this.controls1.put(37, Key.left);
      this.controls1.put(39, Key.right);
      this.controls1.put(47, Key.action);
      this.controls2.put(87, Key.up);
      this.controls2.put(83, Key.down);
      this.controls2.put(65, Key.left);
      this.controls2.put(68, Key.right);
      this.controls2.put(69, Key.action);
   }

   void exit() {
      this.running = false;
   }

   void resetGame() {
      this.init();
   }

   private void resetMap() {
      GameObjectCollection.init();
      this.generateMap();
      System.gc();
   }

   public void addNotify() {
      super.addNotify();
      if (this.thread == null) {
         this.thread = new Thread(this, "GameThread");
         this.thread.start();
      }

   }

   public void run() {
      long timer = System.currentTimeMillis();
      long lastTime = System.nanoTime();
      double NS = 1.6666666666666666E7D;
      double delta = 0.0D;

      while(this.running) {
         long currentTime = System.nanoTime();
         delta += (double)(currentTime - lastTime) / 1.6666666666666666E7D;
         lastTime = currentTime;
         if (delta >= 1.0D) {
            this.update();
            --delta;
         }

         this.repaint();
      }

      System.exit(0);
   }

   private void update() {
      GameObjectCollection.sortBomberObjects();

      label66:
      for(int list = 0; list < GameObjectCollection.gameObjects.size(); ++list) {
         int objIndex = 0;

         while(true) {
            while(true) {
               if (objIndex >= ((List)GameObjectCollection.gameObjects.get(list)).size()) {
                  continue label66;
               }

               GameObject obj = (GameObject)((List)GameObjectCollection.gameObjects.get(list)).get(objIndex);
               obj.update();
               if (obj.isDestroyed()) {
                  obj.onDestroy();
                  ((List)GameObjectCollection.gameObjects.get(list)).remove(obj);
               } else {
                  for(int list2 = 0; list2 < GameObjectCollection.gameObjects.size(); ++list2) {
                     for(int objIndex2 = 0; objIndex2 < ((List)GameObjectCollection.gameObjects.get(list2)).size(); ++objIndex2) {
                        GameObject collidingObj = (GameObject)((List)GameObjectCollection.gameObjects.get(list2)).get(objIndex2);
                        if (obj != collidingObj && obj.getCollider().intersects(collidingObj.getCollider())) {
                           collidingObj.onCollisionEnter(obj);
                        }
                     }
                  }

                  ++objIndex;
               }
            }
         }
      }

      if (!this.gameHUD.matchSet) {
         this.gameHUD.updateScore();
      } else if (GameObjectCollection.bomberObjects.size() <= 1) {
         this.resetMap();
         this.gameHUD.matchSet = false;
      }

      ++this.resetDelay;

      try {
         Thread.sleep(6L);
      } catch (InterruptedException var7) {
      }

   }

   public void paintComponent(Graphics g) {
      Graphics2D g2 = (Graphics2D)g;
      this.buffer = this.world.createGraphics();
      this.buffer.clearRect(0, 0, this.world.getWidth(), this.world.getHeight());
      super.paintComponent(g2);
      //this.gameHUD.drawHUD();

      int i;
      int j;
      for(i = 0; i < this.world.getWidth(); i += this.bg.getWidth()) {
         for(j = 0; j < this.world.getHeight(); j += this.bg.getHeight()) {
            this.buffer.drawImage(this.bg, i, j, (ImageObserver)null);
         }
      }

      for(i = 0; i < GameObjectCollection.gameObjects.size(); ++i) {
         for(j = 0; j < ((List)GameObjectCollection.gameObjects.get(i)).size(); ++j) {
            GameObject obj = (GameObject)((List)GameObjectCollection.gameObjects.get(i)).get(j);
            obj.drawImage(this.buffer);
         }
      }

      i = panelWidth / 4;
      g2.drawImage(this.gameHUD.getP1info(), i * 0, 0, (ImageObserver)null);
      g2.drawImage(this.gameHUD.getP2info(), i * 1, 0, (ImageObserver)null);
      g2.drawImage(this.world, 0, 48, (ImageObserver)null);
      g2.dispose();
      this.buffer.dispose();
   }
}
