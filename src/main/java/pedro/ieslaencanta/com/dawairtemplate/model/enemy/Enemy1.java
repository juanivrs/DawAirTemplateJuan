/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pedro.ieslaencanta.com.dawairtemplate.model.enemy;

import java.util.Iterator;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import pedro.ieslaencanta.com.dawairtemplate.model.Bullet;
import pedro.ieslaencanta.com.dawairtemplate.model.Coordenada;
import pedro.ieslaencanta.com.dawairtemplate.model.Rectangle;
import pedro.ieslaencanta.com.dawairtemplate.model.Size;
import pedro.ieslaencanta.com.dawairtemplate.model.sprites.ICollision;
import pedro.ieslaencanta.com.dawairtemplate.model.sprites.IMove;

/**
 *
 * @author DAM
 */
public class Enemy1 extends AEnemy implements ICollision {
      private ICollision.State State;
    
  
     
   
    @Override
    public void draw(GraphicsContext gc) {
          gc.drawImage(img, 0, 0, this.getSize().getWidth() / 2, this.getSize().getHeight() / 2,
                        this.getPosicion().getX(), this.getPosicion().getY(),
                        this.getSize().getWidth(), this.getSize().getHeight());

 
         
            }

    @Override
    public void init(Coordenada p, Rectangle Board) {
        this.img = new Image(getClass().getResourceAsStream("/" + "enemigos/e2.png"));     
        this.inc=3;
        this.setSize(new Size(194,78));
        this.setPosicion(p);
        this.board=Board;
        FactoryBullets.addBullets("BalaNormal", Bullet::new);
    }
    @Override
      public void TicTac() {
      this.move(IMove.Direction.LEFT);      
      }

    @Override
    public void shoot() {
        int random=(int) (Math.random()*200);
        if (random<2){
         Bullet tempo=FactoryBullets.create("BalaNormal");
        tempo.init(new Coordenada(this.getPosicion().getX() -5, this.getPosicion().getY()+40), board,-5, 2);
         getBalas().add(tempo);
        }
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
    public ICollision.State getState() {
        return State;
    }

    /**
     * @param State the State to set
     */
    public void setState(ICollision.State State) {
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
}
