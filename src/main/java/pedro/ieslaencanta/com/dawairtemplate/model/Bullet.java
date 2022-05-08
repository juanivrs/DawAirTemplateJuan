/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pedro.ieslaencanta.com.dawairtemplate.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import pedro.ieslaencanta.com.dawairtemplate.IWarnClock;
import pedro.ieslaencanta.com.dawairtemplate.model.sprites.ICollision;
import pedro.ieslaencanta.com.dawairtemplate.model.sprites.SpriteMove;

/**
 *
 * @author DAM
 */
public class Bullet extends SpriteMove implements IWarnClock,ICollision{

   


    protected Image img;
    protected State State;
    protected Direction direccion;
    public Bullet(){
        super();
        this.setLive(false);
    }

   @Override
    public void draw(GraphicsContext gc) {
          gc.drawImage(img, 0, 0, this.getSize().getWidth()/2, this.getSize().getHeight()/2,
                        this.getPosicion().getX(), this.getPosicion().getY(),
                        this.getSize().getWidth(), this.getSize().getHeight());
            }
    
    public void init(Coordenada p, Rectangle Board,int inc1,int direccion) { 
        super.init(inc1, new Size(36,8), p, true, true, Board);

        if (direccion==1){
          this.img = new Image(getClass().getResourceAsStream("/" + "bullets/bullet_rigth.png"));         
        }
        else if (direccion==2){
         this.img = new Image(getClass().getResourceAsStream("/" + "bullets/bullet_left.png"));
    }

    }
    private void setDirection(Direction d){
        this.direccion=d;
    }
    public void move(){
        this.move(Direction.RIGHT);
    }
    public int getInc(){
        return this.inc;
    }
    @Override
    public void TicTac() {
        move(Direction.RIGHT);
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

   
}
