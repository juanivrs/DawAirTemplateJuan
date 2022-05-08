/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pedro.ieslaencanta.com.dawairtemplate.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import pedro.ieslaencanta.com.dawairtemplate.IWarnClock;
import pedro.ieslaencanta.com.dawairtemplate.model.sprites.ICollision;
import pedro.ieslaencanta.com.dawairtemplate.model.sprites.ICollision.State;
import pedro.ieslaencanta.com.dawairtemplate.model.sprites.IKeyListener;
import pedro.ieslaencanta.com.dawairtemplate.model.sprites.IMove;
import pedro.ieslaencanta.com.dawairtemplate.model.sprites.SpriteMove;

/**
 *
 * @author DAM
 */
public class Fighter extends SpriteMove implements IKeyListener, IWarnClock,ICollision {

    private boolean[] keys_presed;
    private Image img;
    //path para la imagen
    private static String pathurl="avion.png";
    //para la animación
    private int original_height;
    protected LinkedList<Bullet> balas;
    protected State State;
    /**
     * 
     * @param inc incremento del movimiento
     * @param s tamaño del avión
     * @param p coordenadas iniciales
     * @param board rectangulo con las dimensiones del juego para no salirse
     */
    public Fighter(int inc, Size s, Coordenada p, Rectangle board) {
        super(inc, s, p, true, true, board);
        this.keys_presed = new boolean[5];
        this.img = new Image(getClass().getResourceAsStream("/" + Fighter.pathurl));
        //cambia al mover arriba y abajo
        this.original_height=s.getHeight();
         this.balas = new LinkedList<Bullet>();
    }
    /**
     * acciones al pulsar las teclas
     * @param code 
     */
    @Override
    public void onKeyPressed(KeyCode code) {

       if (code == KeyCode.RIGHT) {
            this.keys_presed[0] = true;
        }
        if (code == KeyCode.LEFT) {
            this.keys_presed[1] = true;
        }
        if (code == KeyCode.UP) {
            this.keys_presed[2] = true;
            this.getSize().setHeight(40);
        }
        if (code == KeyCode.DOWN) {
            this.keys_presed[3] = true;
            this.getSize().setHeight(40);
        }
       
    }
    /**
     * acciones al soltar el teclado
     * @param code 
     */
    @Override
    public void onKeyReleased(KeyCode code) {
       
        if (code == KeyCode.X) {
        Bullet tempo=new Bullet();
        tempo.init(new Coordenada(this.getPosicion().getX()+this.getSize().getWidth()-8, this.getPosicion().getY()+10), board,8,1);
        balas.add(tempo);
        }
        if (code == KeyCode.RIGHT) {
            this.keys_presed[0] = false;
        }
        if (code == KeyCode.LEFT) {
            this.keys_presed[1] = false;
        }
        if (code == KeyCode.UP) {
            this.keys_presed[2] = false;
            this.getSize().setHeight(original_height);
        }
        if (code == KeyCode.DOWN) {
            this.keys_presed[3] = false;
            this.getSize().setHeight(original_height);
        }
    }
    /**
     * dibujar, es algo más complejo al moverse las alas
     * @param gc 
     */
    @Override
    public void draw(GraphicsContext gc) {
         Iterator<Bullet> it = balas.iterator();
         Bullet tempo;
        while (it.hasNext()) {
          tempo=it.next();
          
          tempo.draw(gc);
        }
        if (keys_presed[2]) {
            gc.drawImage(img, 163, 7, this.getSize().getWidth() / 2, this.getSize().getHeight() / 2,
                    this.getPosicion().getX(), this.getPosicion().getY(),
                    this.getSize().getWidth(), this.getSize().getHeight());
        } else {
            if (keys_presed[3]) {
                gc.drawImage(img, 54, 7, this.getSize().getWidth() / 2, this.getSize().getHeight() / 2,
                        this.getPosicion().getX(), this.getPosicion().getY(),
                        this.getSize().getWidth(), this.getSize().getHeight());
            } else {
                gc.drawImage(img, 105, 8, this.getSize().getWidth() / 2, this.getSize().getHeight() / 2,
                        this.getPosicion().getX(), this.getPosicion().getY(),
                        this.getSize().getWidth(), this.getSize().getHeight());
            }
        } 
    }
    //movimiento del avión
    private void move() {

        if (this.keys_presed[0]) {
            this.move(IMove.Direction.RIGHT);
        }
        if (this.keys_presed[1]) {
            this.move(IMove.Direction.LEFT);
        }
        if (this.keys_presed[2]) {
            this.move(IMove.Direction.UP);
        }
        if (this.keys_presed[3]) {
            this.move(IMove.Direction.DOWN);
        }
    }
    /** 
     * cada vez que se recibe un tictac se mueve, faltan las balas del fighter
     */
    @Override
    public void TicTac() {
        this.move();
        
        balas.forEach(e -> {
            e.TicTac();
        });
       
         balas.removeIf((e->{return e.getPosicion().getX()>=board.getEnd().getX()-45;}) );
         balas.removeIf((e->{return e.getState()==ICollision.State.DEAD;}) );
    }  
    @Override
    public String toString(){
      return "X="+this.getPosicion().getX()+" Y="+this.getPosicion().getY();  
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