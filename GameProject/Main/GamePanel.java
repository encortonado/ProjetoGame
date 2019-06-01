package Main;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;


import components.Ground;
import components.Obstacles;
import components.Player;
import components.Trash;

public class GamePanel extends JPanel implements KeyListener, Runnable {
  // entrada do tamanho da tela
  public static int WIDTH;
  public static int HEIGHT;
  private Thread animator;
  // teste logico para saber se o jogo ta rodando ou parado
  public static boolean running = false;
  private boolean gameOver = false;
  
  
  // objetos provenientes das classes
  Ground ground;
  Player player;
  Obstacles obstacles;
  Trash trash;


  private Font font;
  
  public static int score;
  public static int highScore;
  Graphics g;

  // Construtor iniciando variaveis e istanciando objetos
  public GamePanel() {
    WIDTH = UserInterface.WIDTH;
    HEIGHT = UserInterface.HEIGHT;
    
    ground = new Ground(HEIGHT);
    player = new Player();
    obstacles = new Obstacles((int)(WIDTH * 1.5));
  
    trash = new Trash((int) (WIDTH * 2.0));
    
    
    score = 0;
    highScore = 0;
    
    font = new Font("Arial", Font.PLAIN, 18);
    
    setSize(WIDTH, HEIGHT);
    setVisible(true);
  }
  // metodo que 'pinta' os elementos na tela
  public void paint(Graphics g) {
    super.paint(g);
   
    
    
    ground.create(g); 
    if (!running  && gameOver == false) {
    	g.setColor(Color.WHITE);
    	g.setFont(font);
		g.drawString("Comece apertando espaço.", 150, 200);
		g.drawString("Colete todo o lixo do parque!", 150, 220);
		g.drawString("Mas não retire nada da natureza!!!", 150, 240);
		
	}
    
    if (gameOver) {
    	g.drawString("Você Perdeu", 200, 200);
    	g.drawString("Pontos: " + Integer.toString(GamePanel.score), 200, 230);
	}
    
    player.create(g);
    obstacles.create(g);
    trash.create(g);
  }
  
  public void draw(Graphics g) {
	  g.drawString("Começe apertando ESPAÇO", 200, 200);
  }
  // metodo que faz o loop do jogo, fazendo-o rodar 
  public void run() {
    running = true;
   
    while(running) {
    	
		
      updateGame();
      repaint();      
      try {
        Thread.sleep(50);
      } catch(InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
  // metodo que atualiza os status do jogo
  public void updateGame() {
    

    ground.update();
    
    // player.update();
    obstacles.update();
    trash.update();
    

    hasColided();
    
    
    
    if (score >= highScore) {
		highScore = score;
	}
    
    
    // game complete condition
  }

  
  public void hasColided() {
	  if(obstacles.hasCollided()) {
      player.die();
      repaint();
      running = false;
      gameOver = true;
      System.out.println("colisão");
     
    }
	  
	if (trash.hasCollided()) {
		System.out.println("score: " + score);
		
	}
    
    
  }
  // reinicia o jogo do zero
  public void reset() {
	  score = 0;
	  
    System.out.println("reinicio");
    obstacles.resume();
    trash.resume();

    gameOver = false;
  }
  // metodos abaixo vem da classe keyListener
  // utilizada para entrada de teclado no java
  public void keyTyped(KeyEvent e) {
    // System.out.println(e);
    if(e.getKeyChar() == ' ') {    
      if(gameOver) reset();
      if (animator == null || !running) {
        System.out.println("começou");        
        animator = new Thread(this);
        animator.start();     
        player.startRunning();   
      } else {
        player.jump();
      }
    }
  }
  
  public void keyPressed(KeyEvent e) {
    // System.out.println("keyPressed: "+e);
	  
  }
  
  public void keyReleased(KeyEvent e) {
    // System.out.println("keyReleased: "+e);
  }
}