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

public class Trash {
	private class Trashs {
	    BufferedImage image;
	    int x;
	    int y;

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
	  private ArrayList<Trashs> obList;

	  private Trashs blockedAt;
	  Random random = new Random();
	  
	  
	  public Trash(int firstPos) {
	    obList = new ArrayList<Trashs>();
	    imageList = new ArrayList<BufferedImage>();
	    
	    firstX = firstPos;
	    obstacleInterval = 800;
	    auxObstacle = 50;
	    movementSpeed = 10;
	    
	    imageList.add(new Resource().getResourceImage("../images/trash.png"));
	     imageList.add(new Resource().getResourceImage("../images/trash-2.png"));
	    imageList.add(new Resource().getResourceImage("../images/trash-3.png"));
	    imageList.add(new Resource().getResourceImage("../images/trash-4.png"));
	    imageList.add(new Resource().getResourceImage("../images/trash-5.png")); 
	    
	    int x = firstX;
	    
	    for(BufferedImage bi : imageList) {
	      
	      Trashs ob = new Trashs();
	      
	      ob.image = bi;
	      ob.x = x;
	      ob.y = Ground.GROUND_Y - bi.getHeight() + 380;
	      x += obstacleInterval;
	      
	      obList.add(ob);
	    }
	  }
	  
	  public void update() {
	    Iterator<Trashs> looper = obList.iterator();
	    
	    Trashs firstOb = looper.next();
	    firstOb.x -= movementSpeed;
	    
	    while(looper.hasNext()) {
	    	Trashs ob = looper.next();
	      ob.x -= movementSpeed + 1;
	    }
	    
	    Trashs lastOb = obList.get(obList.size() - 1);
	    
	 
					
			
			
		
	    if(firstOb.x < -firstOb.image.getWidth()) {
	      obList.remove(firstOb);
	      firstOb.x = obList.get(obList.size() - 1).x + obstacleInterval;
	      obList.add(firstOb);
	    }
	  }
	  
	  public void create(Graphics g) {
	    for(Trashs ob : obList) {
	      g.setColor(Color.black);
	     //g.drawRect(ob.getObstacle().x, ob.getObstacle().y, ob.getObstacle().width, ob.getObstacle().height);
	      g.drawImage(ob.image, ob.x, ob.y, null);
	    }
	  }
	  // calcula a colisao e diz quantos pontos ganha
	  public boolean hasCollided() {
	    for(Trashs ob : obList) {
	      if(Player.getPlayer().intersects(ob.getObstacle())) {
	        System.out.println("Player = " + Player.getPlayer() + "\nObstacle = " + ob.getObstacle() + "\n\n");
	        GamePanel.score += 50; // guaradnao
	        ob.y = - 1000;
	        blockedAt = ob;
	        return true;
	      }   
	    }
	    return false;
	  }

	  public void resume() {
	    // this.obList = null;
	    int x = firstX/2;   
	    obList = new ArrayList<Trashs>();
	    
	    for(BufferedImage bi : imageList) {
	      
	    	Trashs ob = new Trashs();
	      
	      ob.image = bi;
	      ob.x = x;
	      ob.y = Ground.GROUND_Y - bi.getHeight() + 380;
	      x += obstacleInterval;
	      
	      obList.add(ob);
	    }
	  }
	  
}
