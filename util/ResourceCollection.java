package util;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import javax.imageio.ImageIO;

public class ResourceCollection {
   private static HashMap<Integer, BufferedImage> hardWallTiles;

   public static ResourceCollectionImages icon = new ResourceCollectionImages();
   public static ResourceCollectionImages background = new ResourceCollectionImages();
   public static ResourceCollectionImages soft_wall = new ResourceCollectionImages();
   public static ResourceCollectionImages[] powerups;
   public static ResourceCollectionSpriteMaps player1 = new ResourceCollectionSpriteMaps();
   public static ResourceCollectionSpriteMaps player2 = new ResourceCollectionSpriteMaps();
   public static ResourceCollectionSpriteMaps hard_walls = new ResourceCollectionSpriteMaps();
   public static ResourceCollectionSpriteMaps bomb = new ResourceCollectionSpriteMaps();
   public static ResourceCollectionSpriteMaps bomb_pierce = new ResourceCollectionSpriteMaps();
   public static ResourceCollectionSpriteMaps explosion_spritemap = new ResourceCollectionSpriteMaps();

   public static BufferedImage getHardWallTile(Integer key) {
      return (BufferedImage) hardWallTiles.get(key);
   }

   public static void readFiles() {
      try {
         powerups = new ResourceCollectionImages[7];
         for (int i = 0; i < 7; ++i) {
            powerups[i] = new ResourceCollectionImages();
         }
         icon.setImage(ImageIO.read(ResourceCollection.class.getResource("/resources/icon.png")));
         background.setImage(ImageIO.read(ResourceCollection.class.getResource("/resources/bg.png")));
         soft_wall.setImage(ImageIO.read(ResourceCollection.class.getResource("/resources/softWall.png")));
         powerups[0].setImage(ImageIO.read(ResourceCollection.class.getResource("/resources/power_bomb.png")));
         powerups[1].setImage(ImageIO.read(ResourceCollection.class.getResource("/resources/power_fireup.png")));
         powerups[2].setImage(ImageIO.read(ResourceCollection.class.getResource("/resources/power_firemax.png")));
         powerups[3].setImage(ImageIO.read(ResourceCollection.class.getResource("/resources/power_speed.png")));
         powerups[4].setImage(ImageIO.read(ResourceCollection.class.getResource("/resources/power_pierce.png")));
         powerups[5].setImage(ImageIO.read(ResourceCollection.class.getResource("/resources/power_kick.png")));
         powerups[6].setImage(ImageIO.read(ResourceCollection.class.getResource("/resources/power_timer.png")));
         player1.setImage(ImageIO.read(ResourceCollection.class.getResource("/resources/bomber1.png")));
         player2.setImage(ImageIO.read(ResourceCollection.class.getResource("/resources/bomber2.png")));
         hard_walls.setImage(ImageIO.read(ResourceCollection.class.getResource("/resources/hardWalls.png")));
         bomb.setImage(ImageIO.read(ResourceCollection.class.getResource("/resources/bomb.png")));
         bomb_pierce.setImage(ImageIO.read(ResourceCollection.class.getResource("/resources/bomb_pierce.png")));
         explosion_spritemap.setImage(ImageIO.read(ResourceCollection.class.getResource("/resources/explosion.png")));
      } catch (IOException | NullPointerException ex) {
      }
   }

   public static void init() {
         player1.setSprites(sliceSpriteMap(player1.getImage(), 32, 48));
         player2.setSprites(sliceSpriteMap(player2.getImage(), 32, 48));
         hard_walls.setSprites(sliceSpriteMap(hard_walls.getImage(), 32, 32));
         bomb.setSprites(sliceSpriteMap(bomb.getImage(), 32, 32));
         bomb_pierce.setSprites(sliceSpriteMap(bomb_pierce.getImage(), 32, 32));
         explosion_spritemap.setSprites(sliceSpriteMap(explosion_spritemap.getImage(), 32, 32));
         loadHardWallTiles(hard_walls.getSprites());
   }

   private static BufferedImage[][] sliceSpriteMap(BufferedImage spriteMap, int spriteWidth, int spriteHeight) {
      int rows = spriteMap.getHeight() / spriteHeight;
      int cols = spriteMap.getWidth() / spriteWidth;
      BufferedImage[][] sprites = new BufferedImage[rows][cols];

      for(int row = 0; row < rows; ++row) {
         for(int col = 0; col < cols; ++col) {
            sprites[row][col] = spriteMap.getSubimage(col * spriteWidth, row * spriteHeight, spriteWidth, spriteHeight);
         }
      }

      return sprites;
   }

   private static void loadHardWallTiles(BufferedImage[][] tiles) {
      hardWallTiles = new HashMap();
      hardWallTiles.put(0, tiles[0][0]);
      hardWallTiles.put(1, tiles[0][2]);
      hardWallTiles.put(2, tiles[0][3]);
      hardWallTiles.put(4, tiles[0][1]);
      hardWallTiles.put(8, tiles[0][4]);
      hardWallTiles.put(3, tiles[2][3]);
      hardWallTiles.put(9, tiles[2][4]);
      hardWallTiles.put(6, tiles[2][1]);
      hardWallTiles.put(12, tiles[2][2]);
      hardWallTiles.put(10, tiles[3][0]);
      hardWallTiles.put(5, tiles[2][0]);
      hardWallTiles.put(11, tiles[1][2]);
      hardWallTiles.put(7, tiles[1][3]);
      hardWallTiles.put(14, tiles[1][1]);
      hardWallTiles.put(13, tiles[1][4]);
      hardWallTiles.put(15, tiles[1][0]);
   }
}
