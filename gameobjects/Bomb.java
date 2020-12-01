package gameobjects;

import gameobjects.ExplosionHorizontal;
import gameobjects.ExplosionVertical;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Point2D.Float;
import java.awt.image.BufferedImage;
import util.GameObjectCollection;
import util.ResourceCollection;

public class Bomb extends TileObject {
   private Bomber bomber;
   private BufferedImage[][] sprites;
   private int spriteIndex;
   private int spriteTimer;
   private int firepower;
   private boolean pierce;
   private int timeToDetonate;
   private int timeElapsed;
   private boolean kicked;
   private KickDirection kickDirection;

   public Bomb(Float position, int firepower, boolean pierce, int timer, Bomber bomber) {
      super(position, pierce ? ResourceCollection.bomb_pierce.getSprites()[0][0] : ResourceCollection.bomb.getSprites()[0][0]);
      this.collider.setRect(this.position.x, this.position.y, this.width, this.height);
      this.sprites = pierce ? ResourceCollection.bomb_pierce.getSprites() : ResourceCollection.bomb.getSprites();
      this.spriteIndex = 0;
      this.spriteTimer = 0;
      this.firepower = firepower;
      this.pierce = pierce;
      this.timeToDetonate = timer;
      this.bomber = bomber;
      this.timeElapsed = 0;
      this.breakable = true;
      this.kicked = false;
      this.kickDirection = KickDirection.Nothing;
   }

   private void explode() {
      this.snapToGrid();
      GameObjectCollection.spawn(new ExplosionHorizontal(this.position, this.firepower, this.pierce));
      GameObjectCollection.spawn(new ExplosionVertical(this.position, this.firepower, this.pierce));
      this.bomber.restoreAmmo();
   }

   public void setKicked(boolean kicked, KickDirection kickDirection) {
      this.kicked = kicked;
      this.kickDirection = kickDirection;
   }

   public boolean isKicked() {
      return this.kicked;
   }

   public void stopKick() {
      this.kicked = false;
      this.kickDirection = KickDirection.Nothing;
      this.snapToGrid();
   }

   public void update() {
      this.collider.setRect(this.position.x, this.position.y, this.width, this.height);
      if (this.spriteTimer++ >= 4) {
         ++this.spriteIndex;
         this.spriteTimer = 0;
      }

      if (this.spriteIndex >= this.sprites[0].length) {
         this.spriteIndex = 0;
      }

      this.sprite = this.sprites[0][this.spriteIndex];
      if (this.timeElapsed++ >= this.timeToDetonate) {
         this.destroy();
      }

      if (this.kicked) {
         this.position.setLocation(this.position.x + this.kickDirection.getVelocity().x, this.position.y + this.kickDirection.getVelocity().y);
      }

   }

   public void onDestroy() {
      this.explode();
   }

   public void onCollisionEnter(GameObject collidingObj) {
      collidingObj.handleCollision(this);
   }

   public void handleCollision(Bomber collidingObj) {
      Float temp = new Float((float)this.collider.getCenterX() + this.kickDirection.getVelocity().x, (float)this.collider.getCenterY() + this.kickDirection.getVelocity().y);
      Rectangle2D intersection = this.collider.createIntersection(collidingObj.collider);
      if (this.kicked && intersection.contains(temp)) {
         this.stopKick();
         this.solidCollision(collidingObj);
         this.snapToGrid();
      }

   }

   public void handleCollision(Wall collidingObj) {
      this.solidCollision(collidingObj);
      this.stopKick();
   }

   public void handleCollision(Bomb collidingObj) {
      this.solidCollision(collidingObj);
      this.stopKick();
   }

   public void handleCollision(Explosion collidingObj) {
      this.destroy();
   }

   public boolean isBreakable() {
      return this.breakable;
   }
}
