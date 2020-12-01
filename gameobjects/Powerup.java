package gameobjects;

import java.awt.geom.Point2D.Float;
import java.util.Random;
import util.ResourceCollection;

public class Powerup extends TileObject {
   private int type;
   private static Random random = new Random();

   public Powerup(Float position, int type) {
      super(position, ResourceCollection.powerups[type].getImage());
      this.collider = new java.awt.geom.Rectangle2D.Float(position.x + 8.0F, position.y + 8.0F, this.width - 16.0F, this.height - 16.0F);
      this.type = type;
      this.breakable = true;
   }

   static final int randomPower() {
      return random.nextInt(ResourceCollection.powerups.length);
   }

   public void grantBonus(Bomber bomber) {
      switch(type) {
         case 0:
            bomber.addAmmo(1);
            break;
         case 1:
            bomber.addFirepower(1);
            break;
         case 2:
            bomber.addFirepower(6);
            break;
         case 3:
            bomber.addSpeed(1);
            break;
         case 4:
            bomber.setPierce(true);
            break;
         case 5:
            bomber.setKick(true);
            break;
         case 6:
            bomber.reduceTimer(15);
            break;
      }
   }

   public void update() {
      if (this.checkExplosion()) {
         this.destroy();
      }

   }

   public void onCollisionEnter(GameObject collidingObj) {
      collidingObj.handleCollision(this);
   }

   public void handleCollision(Bomb collidingObj) {
      this.destroy();
   }

   public boolean isBreakable() {
      return this.breakable;
   }
}
