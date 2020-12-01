package gameobjects;

import java.awt.geom.Point2D.Float;

enum KickDirection {
   FromTop(new Float(0.0F, 6.0F)),
   FromBottom(new Float(0.0F, -6.0F)),
   FromLeft(new Float(6.0F, 0.0F)),
   FromRight(new Float(-6.0F, 0.0F)),
   Nothing(new Float(0.0F, 0.0F));

   private Float velocity;

   private KickDirection(Float velocity) {
      this.velocity = velocity;
   }
   
   public Float getVelocity() {
      return this.velocity;
   }
}
