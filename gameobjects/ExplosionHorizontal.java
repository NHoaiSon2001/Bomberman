package gameobjects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D.Float;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import util.GameObjectCollection;
import util.ResourceCollection;

public class ExplosionHorizontal extends Explosion {
   ExplosionHorizontal(Float position, int firepower, boolean pierce) {
      super(position);
      float leftX = this.checkHorizontal(this.position, firepower, pierce, -32);
      float rightX = this.checkHorizontal(this.position, firepower, pierce, 32);
      this.centerOffset = position.x - leftX;
      java.awt.geom.Rectangle2D.Float recH = new java.awt.geom.Rectangle2D.Float(leftX, this.position.y, rightX - leftX + 32.0F, 32.0F);
      this.init(recH);
      this.animation = this.drawSprite((int)this.width, (int)this.height);
      this.sprite = this.animation[0];
   }

   private float checkHorizontal(Float position, int firepower, boolean pierce, int blockWidth) {
      float value = position.x;

      for(int i = 1; i <= firepower; ++i) {
         value += (float)blockWidth;

         for(int index = 0; index < GameObjectCollection.tileObjects.size(); ++index) {
            TileObject obj = (TileObject)GameObjectCollection.tileObjects.get(index);
            if (obj.collider.contains((double)value, (double)position.y)) {
               if (!obj.isBreakable()) {
                  value -= (float)blockWidth;
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

         for(int j = 0; j < spriteAnimation[i].getWidth() / 32; ++j) {
            if (spriteAnimation[i].getWidth() / 32 != 1 && this.centerOffset != (float)(j * 32)) {
               if (j == 0) {
                  g2.drawImage(this.sprites[3][i], j * 32, 0, (ImageObserver)null);
               } else if (j == spriteAnimation[i].getWidth() / 32 - 1) {
                  g2.drawImage(this.sprites[4][i], j * 32, 0, (ImageObserver)null);
               } else {
                  g2.drawImage(this.sprites[1][i], j * 32, 0, (ImageObserver)null);
               }
            } else {
               g2.drawImage(this.sprites[0][i], j * 32, 0, (ImageObserver)null);
            }
         }

         g2.dispose();
      }

      return spriteAnimation;
   }
}
