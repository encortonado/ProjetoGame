package components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import Main.GamePanel;
import utility.Resource;

public class Obstacles {
  private class Obstacle {
    BufferedImage image;
    int x;
    int y;
    // cria uma area limite para o retangulo na colisao
    Rectangle getObstacle() {
      Rectangle obstacle = new Rectangle();
      obstacle.x = x;
      obstacle.y = y;
      obstacle.width = image.getWidth();
      obstacle.height = image.getHeight();

      return obstacle;
    }
  }
  
  private int firstX;
  private int obstacleInterval;
  private int auxObstacle;
  private int movementSpeed;
  
  private ArrayList<BufferedImage> imageList;
  private ArrayList<Obstacle> obList;
  // objeto que serve na hora de criar a colisao
  private Obstacle blockedAt;
  Random random = new Random();
  
  // metodo construtor iniciando valores e colocando as imagens no codigo 
  public Obstacles(int firstPos) {
    obList = new ArrayList<Obstacle>();
    imageList = new ArrayList<BufferedImage>();
    
    firstX = firstPos;
    obstacleInterval = 600;
    auxObstacle = 50;
    movementSpeed = 10;
    
    imageList.add(new Resource().getResourceImage("../images/tree01.png"));
    imageList.add(new Resource().getResourceImage("../images/trunk01.png"));
    imageList.add(new Resource().getResourceImage("../images/tree02.png"));
    imageList.add(new Resource().getResourceImage("../images/tree03.png"));
    imageList.add(new Resource().getResourceImage("../images/trunk02.png"));
    
    int x = firstX;
    // loop das imagens
    for(BufferedImage bi : imageList) {
      
      Obstacle ob = new Obstacle();
      
      ob.image = bi;
      ob.x = x;
      ob.y = Ground.GROUND_Y - bi.getHeight() + 380;
      x += obstacleInterval;
      
      obList.add(ob);
    }
  }
  // atualiza os obstaculos do jogo
  public void update() {
    Iterator<Obstacle> looper = obList.iterator();
    
    Obstacle firstOb = looper.next();
    firstOb.x -= movementSpeed;
    
    while(looper.hasNext()) {
      Obstacle ob = looper.next();
      ob.x -= movementSpeed + 1;
    }
    
    Obstacle lastOb = obList.get(obList.size() - 1);
    		// deixando a distancia entre obstaculos mais aleatoria
    		auxObstacle = random.nextInt(800);
    		int calc = auxObstacle - obstacleInterval;
			if (calc > 100) {
				obstacleInterval = auxObstacle;
			}
				
		
		
	
    if(firstOb.x < -firstOb.image.getWidth()) {
      obList.remove(firstOb);
      firstOb.x = obList.get(obList.size() - 1).x + obstacleInterval;
      obList.add(firstOb);
    }
  }
  // metodo que cria as imagens no codigo
  public void create(Graphics g) {
    for(Obstacle ob : obList) {
      g.setColor(Color.black);
     //g.drawRect(ob.getObstacle().x, ob.getObstacle().y, ob.getObstacle().width, ob.getObstacle().height);
      g.drawImage(ob.image, ob.x, ob.y, null);
    }
  }
  // metodo que calcula a colisao e diz se morre ou se ganha ponto
  public boolean hasCollided() {
    for(Obstacle ob : obList) {
      if(Player.getPlayer().intersects(ob.getObstacle())) {
        System.out.println("Player = " + Player.getPlayer() + "\nObstacle = " + ob.getObstacle() + "\n\n");
        GamePanel.score += 50; // guaradnao
       // ob.y = - 1000;
        blockedAt = ob;
        return true;
      }   
    }
    return false;
  }
  // metodo que retorna o jogo apos um gameover
  public void resume() {
    // this.obList = null;
    int x = firstX/2;   
    obList = new ArrayList<Obstacle>();
    
    for(BufferedImage bi : imageList) {
      
      Obstacle ob = new Obstacle();
      
      ob.image = bi;
      ob.x = x;
      ob.y = Ground.GROUND_Y - bi.getHeight() + 380;
      x += obstacleInterval;
      
      obList.add(ob);
    }
  }
  
}