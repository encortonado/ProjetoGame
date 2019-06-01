package components;

import utility.Resource;
import Main.GamePanel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.*;

import javax.imageio.ImageIO;

public class Ground {
  // classe puxa-imagem
  private class GroundImage {
    BufferedImage image;
    int x;
  }
  // seta valores do Y do chão
  public static int GROUND_Y;
  // cria uma imagem atraves de uma biblioteca
  private BufferedImage image;
  // cria um arraylist com as imagens do chao
  private ArrayList<GroundImage> groundImageSet;
  // metodo construtor iniciando variaveis e setando valores
  public Ground(int panelHeight) {
    GROUND_Y = (int)(panelHeight - 1 * panelHeight);
    // puxa imagem de um arquivo
    try{
      image = new Resource().getResourceImage("../images/back3.png");
    } catch(Exception e) {e.printStackTrace();}
    
    groundImageSet = new ArrayList<GroundImage>();
    
    //first ground image:
    for(int i=0; i<3; i++) {
      GroundImage obj = new GroundImage();
      obj.image = image;
      obj.x = 0;
      groundImageSet.add(obj);
    }
  }
  // metodo que atualiza as imagens do chao do jogo
  public void update() {
    Iterator<GroundImage> looper = groundImageSet.iterator();
    GroundImage first = looper.next();
    
    first.x -= 10;
    
    int previousX = first.x;
    while(looper.hasNext()) {
      GroundImage next = looper.next();
      next.x = previousX + image.getWidth();
      previousX = next.x;
    }
    
    if(first.x < -image.getWidth()) {
      groundImageSet.remove(first);
      first.x = previousX + image.getWidth();
      groundImageSet.add(first);
    }
    
  }
  // metodo que cria o chão
  public void create(Graphics g) {
		for(GroundImage img : groundImageSet) {
			
			g.setColor(Color.BLACK);
			g.setFont(new Font("Century Gothic", Font.PLAIN, 30));
			
			g.drawImage(img.image, (int) img.x, GROUND_Y, null);

			if(GamePanel.running) {
			g.drawString("Pontos: " + Integer.toString(GamePanel.score), 100, 100);
			g.drawString("Maiores Pontos: " + Integer.toString(GamePanel.highScore), 100, 130);
			}
		}
	}
}