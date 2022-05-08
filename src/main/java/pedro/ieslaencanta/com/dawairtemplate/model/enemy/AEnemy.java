/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pedro.ieslaencanta.com.dawairtemplate.model.enemy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import pedro.ieslaencanta.com.dawairtemplate.IWarnClock;
import pedro.ieslaencanta.com.dawairtemplate.model.Bullet;
import pedro.ieslaencanta.com.dawairtemplate.model.Coordenada;
import pedro.ieslaencanta.com.dawairtemplate.model.Rectangle;
import pedro.ieslaencanta.com.dawairtemplate.model.Size;
import pedro.ieslaencanta.com.dawairtemplate.model.sprites.ICollision;
import pedro.ieslaencanta.com.dawairtemplate.model.sprites.ICollision.State;
import pedro.ieslaencanta.com.dawairtemplate.model.sprites.IEnemy;
import pedro.ieslaencanta.com.dawairtemplate.model.sprites.SpriteMove;

/**
 *
 * @author DAM
 */
public abstract class AEnemy extends SpriteMove implements IEnemy,IWarnClock,ICollision {

    
     protected Image img;
      protected String pathurl;
    private LinkedList<Bullet> balas;
    protected State State;
      
 
    public abstract void init(Coordenada p,Rectangle Board);
    
   

      @Override
    public void draw(GraphicsContext gc) {
          gc.drawImage(img, 0, 0, this.getSize().getWidth() / 2, this.getSize().getHeight() / 2,
                        this.getPosicion().getX(), this.getPosicion().getY(),
                        this.getSize().getWidth(), this.getSize().getHeight());
            } 

     @Override
    public void TicTac() {
        this.move(Direction.LEFT);
    }
    
  @Override
    public int getX() {
        return this.getPosicion().getX();
    }

    @Override
    public int getY() {
        return this.getPosicion().getY();
    }
  /**
     * @return the State
     */
    public State getState() {
        return State;
    }

    /**
     * @param State the State to set
     */
    public void setState(State State) {
        this.State = State;
    }
    @Override
    public int getHeight() {
       return this.getSize().getHeight();
    }

    @Override
    public int getWidht() {
       return this.getSize().getWidth();
    }
    @Override
    public boolean hascollided() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setColision() {
        this.setState(getState().DEAD);
    }

    @Override
    public void setFree() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    /**
     * @return the balas
     */
    public LinkedList<Bullet> getBalas() {
        return balas;
    }

    /**
     * @param balas the balas to set
     */
    public void setBalas(LinkedList<Bullet> balas) {
        this.balas = balas;
    }
}
