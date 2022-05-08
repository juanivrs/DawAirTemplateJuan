/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pedro.ieslaencanta.com.dawairtemplate.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 *
 * @author DAM
 */
public class FireBullet extends Bullet {
    
    @Override
    public void init(Coordenada p, Rectangle Board,int inc1,int direccion) { 
        super.init(inc1, new Size(56,32), p, true, true, Board);
    }
    
    @Override
    public void draw(GraphicsContext gc) {
          gc.drawImage(new Image(getClass().getResourceAsStream("/" + "bullets/bolafuego.png")), 0, 0, this.getSize().getWidth()/2, this.getSize().getHeight()/2,
                        this.getPosicion().getX(), this.getPosicion().getY(),
                        this.getSize().getWidth(), this.getSize().getHeight());
}
}
