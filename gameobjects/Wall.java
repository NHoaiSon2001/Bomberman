package gameobjects;

import java.awt.geom.Point2D.Float;
import java.awt.image.BufferedImage;
import util.GameObjectCollection;

public class Wall extends TileObject {
   public Wall(Float position, BufferedImage sprite, boolean isBreakable) {
      super(position, sprite);
      this.breakable = isBreakable;
   }

   public void update() {
      if (this.checkExplosion()) {
         this.destroy();
      }

   }

   public void onDestroy() {
      double random = Math.random();
      if (random < 0.5D) {
         Powerup powerup = new Powerup(this.position, Powerup.randomPower());
         GameObjectCollection.spawn(powerup);
      }

   }

   public void onCollisionEnter(GameObject collidingObj) {
      collidingObj.handleCollision(this);
   }

   public boolean isBreakable() {
      return this.breakable;
   }
}
