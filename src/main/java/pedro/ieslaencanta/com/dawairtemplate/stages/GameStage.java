/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pedro.ieslaencanta.com.dawairtemplate.stages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import pedro.ieslaencanta.com.dawairtemplate.model.Coordenada;
import pedro.ieslaencanta.com.dawairtemplate.model.Level;
import pedro.ieslaencanta.com.dawairtemplate.model.Player;
import pedro.ieslaencanta.com.dawairtemplate.model.Size;


/**
 *
 * @author Pedro
 */
public class GameStage extends AbstractScene {

   
    private Level actual;
    private Size s;
    private static Font font;//, Font.ITALIC, 50);
    private Player player;
    private Level[] lvls = new Level[3];
    private int nivel=1;
    private static float[][] probabilidades = {
        {0.2f,0.5f, 1f},
        {0.6f, 1f},
        {0.5f, 1f}
    };

    static {
        font = new Font("8BIT WONDER Nominal", 30);//, Font.ITALIC, 50);
    }

    //0 delante, 1 detras,2 arriba, 3 abajo, 4 disparo
    //private boolean[] keys_presed;
    public GameStage(GraphicsContext context, GraphicsContext bg_context, Size s) {
        super(context, bg_context, s, null);
        this.s=s;
        this.state = SceneState.PRE_STARTED;
  
        lvls[0]=new Level("/level1/bg2.png", "/level1/music.mp3", s, 2, new Coordenada(0, 0), bg_context, GameStage.probabilidades[0],5000);
        lvls[1]= new Level("/level3/bg3.png", "/level3/music.mp3", s, 2, new Coordenada(0, 0), bg_context, GameStage.probabilidades[0],5000);
        lvls[2]= new Level("/level2/bg3.png", "/level2/music.mp3", s, 2, new Coordenada(0, 0), bg_context, GameStage.probabilidades[0],5000);
        this.initFactory();
        this.actual=lvls[0];
        this.player = new Player();
       

    }

    //se inicializan la factoria de enemigos
    private void initFactory() {
      
    }

    @Override
    public void TicTac() {
        if (this.state == SceneState.STARTED) {
            this.actual.TicTac();
            //para pasar de nivel
            if (this.actual.getEstado() == Level.Estado.END) {
                  if (this.actual==lvls[2] && this.actual.getEstado() == Level.Estado.END){
                   this.stop();
                      }
                  else{
                    changeLvl();
                  }
            }
        }
        if (this.actual.getP().getLifes()<=0){
            stop();
            System.exit(0);
        }
                
    }
    public void changeLvl(){
     if (lvls.length<4){
     this.actual=lvls[nivel];
     nivel++;
     }
    }
    @Override
    public void onKeyPressed(KeyCode code) {
        if (this.state == SceneState.STARTED) {
            this.actual.onKeyPressed(code);
        }
    }

    @Override
    public void onKeyReleased(KeyCode code) {
        if (GameStage.SceneState.PRE_STARTED == this.state) {
            this.state = GameStage.SceneState.STARTED;
        }

        if (this.state == SceneState.STARTED) {
            this.actual.onKeyReleased(code);
        }

    }

    @Override
    public void draw(GraphicsContext gc) {
        this.actual.draw(gc);
        gc.setFill(Color.BLUE);
        gc.setStroke(Color.BROWN);
        gc.setFont(GameStage.font);
      

    }

}
