package pedro.ieslaencanta.com.dawairtemplate.model.enemy;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Supplier;
import pedro.ieslaencanta.com.dawairtemplate.model.Bullet;
import pedro.ieslaencanta.com.dawairtemplate.model.Bullet;

/**
 *
 * @author DAM
 */
public class FactoryBullets {
    private static HashMap<String, Supplier<Bullet>> bullets;
 private static ArrayList<String> names;
 static {
 bullets = new HashMap<>();
 names= new ArrayList<>();
 }
 public static void addBullets(String name,Supplier<Bullet> s){
 FactoryBullets.bullets.put(name, s);
 FactoryBullets.names.add(name);
 }
 public static Bullet get(Supplier<? extends Bullet> s) {
 return s.get();
 }
 public static List<String> getKeyNames(){
 return FactoryBullets.names;
 // return new ArrayList<String>(FactoryEnemigos.enemigos.keySet());
 }
 public static Bullet create(String nombre) {
 if (FactoryBullets.bullets.get(nombre) != null) {
 return FactoryBullets.bullets.get(nombre).get();
 } else {
 return null;
 }
 }

}
