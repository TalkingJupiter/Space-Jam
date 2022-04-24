
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;


/**
 *
 * @author Batuhan Sencer
 */

class Shoot{
    
    private int x;
    private int y;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Shoot(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
}



public class Game extends JPanel implements KeyListener,ActionListener{
    Timer timer = new Timer(5, this); 
    
    private int time_passed = 0;
    private int shootsUser = 0;
    
    private BufferedImage image;
    
    private ArrayList<Shoot> shoots = new ArrayList<Shoot>();
    
    private int shootdirY = 1;
    
    private int ballX = 0;
    
    private int balldirX = 2;
    
    private int spaceShipX = 0;
    
    private int dirSpaceX = 20;
    
    public boolean check() {
        for (Shoot shoot : shoots){
            
            if (new Rectangle(shoot.getX(), shoot.getY(), 10, 20).intersects(new Rectangle(ballX, 0, 20, 20))){
                return true;
            }
            
            
        }
        return false;
    }
    
    public Game(){
        
        try {
            image = ImageIO.read(new FileImageInputStream(new File("uzaygemisi.png")));
        } catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
        setBackground(Color.BLACK);
        
        
        timer.start();
    
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        time_passed += 5;
        g.setColor(Color.red);
        
        g.fillOval(ballX, 0, 20, 20);
        
        
        g.drawImage(image, spaceShipX, 490, image.getWidth() / 10, image.getHeight() / 10, this);
        
        for(Shoot shoot : shoots){
            if(shoot.getY()< 0){
                shoots.remove(shoot);
            }
        }
        
        g.setColor(Color.BLUE);
        
        for(Shoot shoot : shoots){
            
            g.fillRect(shoot.getX(), shoot.getY(), 10, 20);
        }
        
        if (check()){
            timer.stop();
            String message = "Victory!!\n"+
                             "Shoots: " + shootsUser + 
                             "\nTime: " + time_passed / 1000.0 +" Seconds";
            JOptionPane.showMessageDialog(this, message);
            System.exit(0);
        }
        
    }

    @Override
    public void repaint() {
        super.repaint(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }
    
    
    @Override
    public void keyTyped(KeyEvent e) {
        
        
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int c = e.getKeyCode();
        
        if(c == KeyEvent.VK_A || c == KeyEvent.VK_LEFT){
           if(spaceShipX <= 0){
               spaceShipX = 0;
           } 
           else{
               spaceShipX -= dirSpaceX;
           }
        }
        else if(c == KeyEvent.VK_D || c == KeyEvent.VK_RIGHT){
            if(spaceShipX >= 745){
                spaceShipX = 745;
            }
            else{
                spaceShipX += dirSpaceX;
            }
        }
        else if(c == KeyEvent.VK_SPACE){
            shoots.add(new Shoot(spaceShipX + 15, 480 ));
            
            shootsUser++;
            
        }
    
    }

    @Override
    public void keyReleased(KeyEvent e) {
    
    
    
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        for(Shoot shoot : shoots){
            shoot.setY(shoot.getY() - shootdirY);
        }
        
        ballX += balldirX;
        
        if (ballX >= 750){
            balldirX = -balldirX;
            
        }
        if (ballX <= 0){
            balldirX = -balldirX;
        }
        repaint();
    }
    
    
    
}
