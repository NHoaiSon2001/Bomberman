package gameobjects;

import java.awt.geom.Point2D.Float;
import java.awt.image.BufferedImage;

public abstract class TileObject extends GameObject {
   protected Explosion explosionContact;
   protected boolean breakable;

   TileObject(Float position, BufferedImage sprite) {
      super(position, sprite);
      this.snapToGrid();
   }

   public abstract boolean isBreakable();

   protected boolean checkExplosion() {
      return this.isBreakable() && this.explosionContact != null && this.explosionContact.isDestroyed();
   }

   protected void snapToGrid() {
      float x = (float)(Math.round(this.position.getX() / 32.0D) * 32L);
      float y = (float)(Math.round(this.position.getY() / 32.0D) * 32L);
      this.position.setLocation(x, y);
   }

   public void handleCollision(Explosion collidingObj) {
      if (this.isBreakable() && this.explosionContact == null) {
         this.explosionContact = collidingObj;
      }

   }
}
