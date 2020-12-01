package gameobjects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D.Float;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import util.GameObjectCollection;
import util.ResourceCollection;

public class ExplosionVertical extends Explosion {
   ExplosionVertical(Float position, int firepower, boolean pierce) {
      super(position);
      float topY = this.checkVertical(this.position, firepower, pierce, -32);
      float bottomY = this.checkVertical(this.position, firepower, pierce, 32);
      this.centerOffset = position.y - topY;
      java.awt.geom.Rectangle2D.Float recV = new java.awt.geom.Rectangle2D.Float(this.position.x, topY, 32.0F, bottomY - topY + 32.0F);
      this.init(recV);
      this.animation = this.drawSprite((int)this.width, (int)this.height);
      this.sprite = this.animation[0];
   }

   private float checkVertical(Float position, int firepower, boolean pierce, int blockHeight) {
      float value = position.y;

      for(int i = 1; i <= firepower; ++i) {
         value += (float)blockHeight;

         for(int index = 0; index < GameObjectCollection.tileObjects.size(); ++index) {
            TileObject obj = (TileObject)GameObjectCollection.tileObjects.get(index);
            if (obj.collider.contains((double)position.x, (double)value)) {
               if (!obj.isBreakable()) {
                  value -= (float)blockHeight;
               }

               if (!pierce) {
                  return value;
               }
            }
         }
      }

      return value;
   }

   private BufferedImage[] drawSprite(int width, int height) {
      BufferedImage[] spriteAnimation = new BufferedImage[ResourceCollection.explosion_spritemap.getImage().getWidth() / 32];

      int i;
      for(i = 0; i < spriteAnimation.length; ++i) {
         spriteAnimation[i] = new BufferedImage(width, height, 2);
      }

      for(i = 0; i < spriteAnimation.length; ++i) {
         Graphics2D g2 = spriteAnimation[i].createGraphics();
         g2.setColor(new Color(0, 0, 0, 0));
         g2.fillRect(0, 0, spriteAnimation[i].getWidth(), spriteAnimation[i].getHeight());

         for(int j = 0; j < spriteAnimation[i].getHeight() / 32; ++j) {
            if (spriteAnimation[i].getHeight() / 32 != 1 && this.centerOffset != (float)(j * 32)) {
               if (j == 0) {
                  g2.drawImage(this.sprites[5][i], 0, j * 32, (ImageObserver)null);
               } else if (j == spriteAnimation[i].getHeight() / 32 - 1) {
                  g2.drawImage(this.sprites[6][i], 0, j * 32, (ImageObserver)null);
               } else {
                  g2.drawImage(this.sprites[2][i], 0, j * 32, (ImageObserver)null);
               }
            } else {
               g2.drawImage(this.sprites[0][i], 0, j * 32, (ImageObserver)null);
            }
         }

         g2.dispose();
      }

      return spriteAnimation;
   }
}
