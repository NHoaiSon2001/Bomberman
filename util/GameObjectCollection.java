package util;

import gameobjects.Bomber;
import gameobjects.Explosion;
import gameobjects.GameObject;
import gameobjects.TileObject;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GameObjectCollection {
   public static List<List<? extends GameObject>> gameObjects;
   public static ArrayList<TileObject> tileObjects;
   public static ArrayList<Explosion> explosionObjects;
   public static ArrayList<Bomber> bomberObjects;

   public static void init() {
      gameObjects = new ArrayList();
      tileObjects = new ArrayList();
      explosionObjects = new ArrayList();
      bomberObjects = new ArrayList();
      gameObjects.add(tileObjects);
      gameObjects.add(explosionObjects);
      gameObjects.add(bomberObjects);
   }

   public static void spawn(TileObject spawnObj) {
      tileObjects.add(spawnObj);
   }

   public static void spawn(Explosion spawnObj) {
      explosionObjects.add(spawnObj);
   }

   public static void spawn(Bomber spawnObj) {
      bomberObjects.add(spawnObj);
   }

   public static void sortTileObjects() {
      tileObjects.sort(Comparator.comparing(GameObject::getPositionY));
   }

   public static void sortExplosionObjects() {
      explosionObjects.sort(Comparator.comparing(GameObject::getPositionY));
   }

   public static void sortBomberObjects() {
      bomberObjects.sort(Comparator.comparing(GameObject::getPositionY));
   }
}
