/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pedro.ieslaencanta.com.dawairtemplate.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.function.Predicate;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import pedro.ieslaencanta.com.dawairtemplate.Background;
import pedro.ieslaencanta.com.dawairtemplate.IWarnClock;
import pedro.ieslaencanta.com.dawairtemplate.model.enemy.AEnemy;

import pedro.ieslaencanta.com.dawairtemplate.model.enemy.Enemy;
import pedro.ieslaencanta.com.dawairtemplate.model.enemy.Enemy1;
import pedro.ieslaencanta.com.dawairtemplate.model.enemy.Enemy2;
import pedro.ieslaencanta.com.dawairtemplate.model.enemy.FactoryEnemies;
import pedro.ieslaencanta.com.dawairtemplate.model.sprites.ICollision;
import pedro.ieslaencanta.com.dawairtemplate.model.sprites.IDrawable;
import pedro.ieslaencanta.com.dawairtemplate.model.sprites.IKeyListener;
import pedro.ieslaencanta.com.dawairtemplate.stages.GameStage;
import pedro.ieslaencanta.com.dawairtemplate.stages.IScene;

/**
 *
 * @author Pedro
 */
public class Level implements IDrawable, IWarnClock, IKeyListener {

   

    public enum Estado {
        PRE_STARTED,
        RUNNING,
        STOPPED,
        PAUSED,
        PRE_END,
        END
    } 
    protected LinkedList<Bullet> balasenemigas;
    private static String[] msg = {"\"Pulsar una tecla para empezar", "Siguiente nivel..."};
    private String background_path;
    private int speed;
    private int position;
    private int fin;
    private Background background;
    private Fighter fighter;
    private GraphicsContext bg_ctx;
    private MediaPlayer player;
    private float[] probabilidadenemigos;
    private Size s;
    private Enemy enemy1;
    private LinkedList<AEnemy> enemigos;
    private Estado estado;
    private Player p;
    private final Image newImagen=new Image(getClass().getResourceAsStream("/" + "avion.png"));
    
    
    public Level(String image_path, String music_path, Size s, int speed, Coordenada start_position, GraphicsContext bg_ctx, float[] probabilidad_enemigos, int fin) {
        this.background = new Background(image_path, s, speed, start_position);
        this.background.setBg(bg_ctx);
        this.position = 0;
        this.speed = speed;
        this.estado = Estado.PRE_STARTED;
        this.fin = fin;
        this.s = s;
        this.fighter = new Fighter(
                3,
                new Size(74, 26),
                new Coordenada(20, s.getHeight() / 2),
                new Rectangle(new Coordenada(0, 0), new Coordenada(s.getWidth(), s.getHeight())));

     
        this.probabilidadenemigos = probabilidad_enemigos;
        FactoryEnemies.addEnemy("Enemigo1", Enemy::new);
        FactoryEnemies.addEnemy("Enemigo2", Enemy1::new);
        FactoryEnemies.addEnemy("Enemigo3", Enemy2::new);
        this.initSound(music_path);
        enemigos = new LinkedList<AEnemy>();
        this.balasenemigas = new LinkedList<Bullet>();
        this.p=new Player();

    }

    private void initSound(String music_path) {
        this.player = new MediaPlayer(new Media(getClass().getResource(music_path).toString()));

        player.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                player.seek(Duration.ZERO);
            }
        });

    }

    @Override
    public void draw(GraphicsContext gc) {

        this.background.paint(gc);
        this.fighter.draw(gc);
        
         //enemigos.forEach( e -> {
           
         //});
         enemigos.forEach( e -> {
             if (e.getState() != ICollision.State.DEAD) {
                e.draw(gc);
            }
         });
  this.balasenemigas.forEach( b -> {
                 b.draw(gc);
             });

        if (this.estado == Estado.PRE_STARTED) {
            gc.setFill(Color.BROWN);
            gc.setStroke(Color.WHITE);
            gc.strokeText(Level.msg[0], 100, 200);
            gc.fillText(Level.msg[0], 100, 200);

        }
        
        for (int i=0; i<p.getLifes();i++){
            gc.drawImage(newImagen,105, 8, 50, 150,80*i,450,100,300);
        }
          gc.strokeText("SCORE " + this.p.getScore(), this.s.getWidth() / 2 - 100, 40);
    }

    private String getEnemyName(){
        float random = (float) Math.random();
        String nombre=null;
        int contador=0;
        while (nombre ==null){
            if (random <= this.probabilidadenemigos[contador]){
                nombre = FactoryEnemies.getKeyNames().get(contador);
            }
            contador ++;
        }
         return nombre;       
    }
    public void crearEnemigo(){
         AEnemy enemigo;
         
         if (Math.random()>0.98888){
              int y = (int)( Math.random() *s.getHeight());
              enemigo = FactoryEnemies.create(this.getEnemyName());
              enemigo.init(new Coordenada(s.getWidth() - 45, y), new Rectangle(new Coordenada(0, 0), new Coordenada(s.getWidth(), s.getHeight())));
           
              this.enemigos.add(enemigo);
              enemigo.setBalas(balasenemigas);
        }
    }
    @Override
    public void TicTac() {
        if (this.getEstado() == Estado.RUNNING) {
            //llamar a tictac de los hijos
             crearEnemigo();
            this.TicTacChildrens();
            //posicion en la que termina
            detectCollisions();
           // enemigos.add(FactoryEnemies.create("Enemigo1"));
            if (this.position < this.fin) {
                this.position += this.speed;
            } else {
               this.EndLevel();
            }
        }

    }

    private void TicTacChildrens() {
        //pintar el fondo
        this.background.TicTac();
        this.fighter.TicTac();
        enemigos.forEach( e -> {
            e.TicTac();
        });
        enemigos.forEach( e-> {
            e.shoot();
        });
        moveBullets();
        
    }

    private void detectCollisions() {

        enemigos.forEach(e -> {
            fighter.balas.forEach(b -> {
            if (e.isCollision(b)){
                e.setLive(false);
                b.setLive(false);
                this.p.setScore(this.p.getScore()+10);
            }
            });
        });
 
        this.balasenemigas.forEach( c -> {
            if (fighter.isCollision(c)){
                c.setLive(false);
                fighter.setLive(false);
                this.p.setLifes(this.p.getLifes()-1);
            }
          });
        /*enemigos.forEach(e -> {
          e.getBalas().forEach();
        }      
       );*/
          enemigos.removeIf((e->{return e.getState()==ICollision.State.DEAD;}) );
          enemigos.removeIf((e->{return e.getX()<=0+2;}) );
          balasenemigas.removeIf(e -> {
           return e.getX()<=0 || e.getState()==ICollision.State.DEAD ;
          });

    }

    private void moveBullets() {
       

        this.balasenemigas.forEach(b->b.move());
       /*enemigos.forEach(e -> {
           e.getBalas().forEach( b-> {
               b.move();
               //System.out.println(b.getInc());
           });
       });*/
    }

    public boolean isEnd() {
        return this.getEstado() == Estado.STOPPED;
    }

    private void EndLevel() {
        this.player.stop();
        this.setEstado(Estado.END);
    }

    /**
     * @return the estado
     */
    public Estado getEstado() {
        return estado;
    }

    /**
     * @return the p
     */
    public Player getP() {
        return p;
    }

    /**
     * @param p the p to set
     */
    public void setP(Player p) {
        this.p = p;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    @Override
    public void onKeyPressed(KeyCode code) {
        fighter.onKeyPressed(code);
        
        if (code==KeyCode.B){
            EndLevel();
        }
    }

    @Override
    public void onKeyReleased(KeyCode code) {

        if (this.getEstado() == Level.Estado.PRE_STARTED) {
            this.setEstado(Level.Estado.RUNNING);
        }
        if (this.getEstado() == Level.Estado.RUNNING) {
            fighter.onKeyReleased(code);
            if (player.getStatus() == MediaPlayer.Status.READY) {
                player.play();
            }
        }

    }
}
