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
import pedro.ieslaencanta.com.dawairtemplate.model.FireBullet;
import pedro.ieslaencanta.com.dawairtemplate.model.Rectangle;
import pedro.ieslaencanta.com.dawairtemplate.model.Size;
import pedro.ieslaencanta.com.dawairtemplate.model.sprites.ICollision;
import pedro.ieslaencanta.com.dawairtemplate.model.sprites.IMove;

/**
 *
 * @author DAM
 */
public class Enemy2 extends AEnemy implements ICollision{
   private ICollision.State State;
    
   
     
   
    @Override
    public void draw(GraphicsContext gc) {
          gc.drawImage(img, 43,68 , this.getSize().getWidth() / 2, this.getSize().getHeight() / 2,
                        this.getPosicion().getX(), this.getPosicion().getY(),
                        this.getSize().getWidth(), this.getSize().getHeight());
            }

    @Override
    public void init(Coordenada p, Rectangle Board) {
        this.img = new Image(getClass().getResourceAsStream("/" + "enemigos/a5.png"));     
        this.inc=3;
        this.setSize(new Size(114,42));
        this.setPosicion(p);
        this.board=Board;
        FactoryBullets.addBullets("BalaFuego", FireBullet::new);
    }
    @Override
      public void TicTac() {
      this.move(IMove.Direction.LEFT); 
      }

   @Override
    public void shoot() {
        int random=(int) (Math.random()*200);
        Bullet tempo;
        if (random<2){
        tempo=FactoryBullets.create("BalaFuego");
        tempo.init(new Coordenada(this.getPosicion().getX(), this.getPosicion().getY()+30), board,-5, 2);
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
