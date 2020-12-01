package util;

import java.awt.image.BufferedImage;

public class ResourceCollectionSpriteMaps {
   private BufferedImage image = null;
   private BufferedImage[][] sprites = null;

   public void setImage(BufferedImage image) {
      this.image = image;
   }

   public void setSprites(BufferedImage[][] sprites) {
      this.sprites = sprites;
   }

   public BufferedImage getImage() {
      return this.image;
   }

   public BufferedImage[][] getSprites() {
      return this.sprites;
   }
}
