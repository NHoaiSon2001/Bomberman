package gameobjects;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D.Float;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import util.ResourceCollection;

public abstract class Explosion extends GameObject {
   protected BufferedImage[][] sprites;
   protected BufferedImage[] animation;
   protected float centerOffset;
   private int spriteIndex;
   private int spriteTimer;

   Explosion(Float position) {
      super(position);
      this.sprites = ResourceCollection.explosion_spritemap.getSprites();
      this.centerOffset = 0.0F;
      this.spriteIndex = 0;
      this.spriteTimer = 0;
   }

   protected void init(java.awt.geom.Rectangle2D.Float collider) {
      this.collider = collider;
      this.width = this.collider.width;
      this.height = this.collider.height;
      this.sprite = new BufferedImage((int)this.width, (int)this.height, 2);
   }

   public void update() {
      if (this.spriteTimer++ >= 4) {
         ++this.spriteIndex;
         this.spriteTimer = 0;
      }

      if (this.spriteIndex >= this.animation.length) {
         this.destroy();
      } else {
         this.sprite = this.animation[this.spriteIndex];
      }

   }

   public void onCollisionEnter(GameObject collidingObj) {
      collidingObj.handleCollision(this);
   }

   public void drawImage(Graphics g) {
      AffineTransform rotation = AffineTransform.getTranslateInstance((double)this.collider.x, (double)this.collider.y);
      rotation.rotate(Math.toRadians((double)this.rotation), (double)this.collider.width / 2.0D, (double)this.collider.height / 2.0D);
      Graphics2D g2d = (Graphics2D)g;
      g2d.drawImage(this.sprite, rotation, (ImageObserver)null);
   }
}
