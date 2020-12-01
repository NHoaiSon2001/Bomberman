package gameobjects;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Point2D.Float;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

public abstract class GameObject implements Observable, Collidable, Comparable<GameObject> {
   BufferedImage sprite;
   Float position;
   java.awt.geom.Rectangle2D.Float collider;
   float rotation;
   float width;
   float height;
   private boolean destroyed;

   GameObject(Float position) {
      this.position = new Float(position.x, position.y);
      this.rotation = 0.0F;
   }

   GameObject(Float position, BufferedImage sprite) {
      this(sprite);
      this.position = new Float(position.x, position.y);
      this.rotation = 0.0F;
      this.collider = new java.awt.geom.Rectangle2D.Float(position.x, position.y, this.width, this.height);
   }

   private GameObject(BufferedImage sprite) {
      this.sprite = sprite;
      this.width = (float)this.sprite.getWidth();
      this.height = (float)this.sprite.getHeight();
   }

   void destroy() {
      this.destroyed = true;
   }

   public boolean isDestroyed() {
      return this.destroyed;
   }

   void solidCollision(GameObject obj) {
      Rectangle2D intersection = this.collider.createIntersection(obj.collider);
      if (intersection.getWidth() >= intersection.getHeight()) {
         if (intersection.getMaxY() >= this.collider.getMaxY()) {
            this.position.setLocation((double)this.position.x, (double)this.position.y - intersection.getHeight());
         }

         if (intersection.getMaxY() >= obj.collider.getMaxY()) {
            this.position.setLocation((double)this.position.x, (double)this.position.y + intersection.getHeight());
         }

         if (intersection.getWidth() < 16.0D) {
            if (intersection.getMaxX() >= this.collider.getMaxX()) {
               this.position.setLocation((double)this.position.x - 0.5D, (double)this.position.y);
            }

            if (intersection.getMaxX() >= obj.collider.getMaxX()) {
               this.position.setLocation((double)this.position.x + 0.5D, (double)this.position.y);
            }
         }
      }

      if (intersection.getHeight() >= intersection.getWidth()) {
         if (intersection.getMaxX() >= this.collider.getMaxX()) {
            this.position.setLocation((double)this.position.x - intersection.getWidth(), (double)this.position.y);
         }

         if (intersection.getMaxX() >= obj.collider.getMaxX()) {
            this.position.setLocation((double)this.position.x + intersection.getWidth(), (double)this.position.y);
         }

         if (intersection.getHeight() < 16.0D) {
            if (intersection.getMaxY() >= this.collider.getMaxY()) {
               this.position.setLocation((double)this.position.x, (double)this.position.y - 0.5D);
            }

            if (intersection.getMaxY() >= obj.collider.getMaxY()) {
               this.position.setLocation((double)this.position.x, (double)this.position.y + 0.5D);
            }
         }
      }

   }

   public java.awt.geom.Rectangle2D.Float getCollider() {
      return this.collider;
   }

   public Float getColliderCenter() {
      return new Float((float)this.collider.getCenterX(), (float)this.collider.getCenterY());
   }

   public float getPositionY() {
      return this.position.y + this.height;
   }

   public void drawImage(Graphics g) {
      AffineTransform rotation = AffineTransform.getTranslateInstance(this.position.getX(), this.position.getY());
      rotation.rotate(Math.toRadians((double)this.rotation), (double)this.sprite.getWidth() / 2.0D, (double)this.sprite.getHeight() / 2.0D);
      Graphics2D g2d = (Graphics2D)g;
      g2d.drawImage(this.sprite, rotation, (ImageObserver)null);
   }

   public void drawCollider(Graphics g) {
      Graphics2D g2d = (Graphics2D)g;
      g2d.draw(this.collider);
   }

   public int compareTo(GameObject o) {
      return java.lang.Float.compare(this.position.y, o.position.y);
   }
}
