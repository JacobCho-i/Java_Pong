package randomGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.*;
import java.awt.geom.Line2D;
import javax.swing.*;
import java.net.URL;
import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.Timer;
import java.util.TimerTask;

public class Pong extends GDV5{
	
	private Ball ball = new Ball(30,30,4,4);
	private Ball extraBall = new Ball(30,30,4,4);
	private Player p1 = new Player(1,4);
	private Player p2 = new Player(2,4);
	private int bounce = 0;
	private int p1Score = 0;
	private int p2Score = 0;
	private boolean intro = true;
	private boolean character = false;
	private boolean paused = false;
	private boolean game = false;
	private Indicator indic = new Indicator();
	private int gamemode = 1;
	private boolean onBackGround = true;
	private int p1CharSel = 1;
	private int p2CharSel = 5;
	private int p1Char = 1;
	private int p2Char = 1;
	private boolean pressed1 = true;
	private boolean pressed2 = true;
	private boolean pressed3 = true;
	private boolean pressed4 = true;
	private boolean howToPlay = false;
	private static Image image = null;
	private static Image image2 = null;
	private static Image image3 = null;
	private static Image image4 = null;
	private static Image image5 = null;
	private boolean p1Ready = false;
	private boolean p2Ready = false;
	private int p1Charge = 10;
	private int p2Charge = 10;
	private Ball p1wall1 = new Ball(50, 600, 0,0);
	private Ball p1wall2 = new Ball(50, 600, 0,0);
	private Ball p1wall3 = new Ball(50, 600, 0,0);
	private Ball p2wall1 = new Ball(50, 600, 0,0);
	private Ball p2wall2 = new Ball(50, 600, 0,0);
	private Ball p2wall3 = new Ball(50, 600, 0,0);
	private Timer p1timer;
	private Timer p2timer;
	private TimerTask p1timeTask;
	private TimerTask p2timeTask;
	private int p1time = 0;
	private int p2time = 0;
	private boolean p1Slowed = false;
	private boolean p2Slowed = false;
	private Ball p1Tor1 = new Ball(50, 50, 0, 5);
	private Ball p1Tor2 = new Ball(50, 50, 0, 5);
	private Ball p1Tor3 = new Ball(50, 50, 0, 5);
	private Ball p2Tor1 = new Ball(50, 50, 0, 5);
	private Ball p2Tor2 = new Ball(50, 50, 0, 5);
	private Ball p2Tor3 = new Ball(50, 50, 0, 5);
	private int p1NumTor = 0;
	private int p2NumTor = 0;
	
	public static void main(String[] args) {
		startGame();
		
		try {
			URL url = new URL("https://i.imgur.com/ecHe2MN.png");
			image = ImageIO.read(url);
			URL url2 = new URL("https://i.imgur.com/7lf4f7l.png");
			image2 = ImageIO.read(url2);
			URL url3 = new URL("https://i.imgur.com/6n03dRk.png");
			image3 = ImageIO.read(url3);
			URL url4 = new URL("https://i.imgur.com/7r2Lhhe.png");
			image4 = ImageIO.read(url4);
			URL url5 = new URL("https://i.imgur.com/f4GAOkL.png");
			image5 = ImageIO.read(url5);
		} catch (IOException e) {
            System.out.println("Something went wrong, sorry:" + e.toString());
            e.printStackTrace();
        }
	}
	
	public void backgroundAndTransitionObserver() {
		if (onBackGround && gamemode == 1) {
			indic.setLocation(GDV5.getMaxWindowX()/2 - 70, GDV5.getMaxWindowY() - 165);
		}
		if (onBackGround && gamemode == 2) {
			indic.setLocation(GDV5.getMaxWindowX()/2 - 78, GDV5.getMaxWindowY() - 110);
		}
		if (GDV5.KeysPressed[KeyEvent.VK_UP] && gamemode == 2 && onBackGround) {
			gamemode = 1;
			
		}
		if (GDV5.KeysPressed[KeyEvent.VK_DOWN] && gamemode == 1 && onBackGround) {
			gamemode = 2;
		}
		
		if (GDV5.KeysPressed[KeyEvent.VK_ENTER] && onBackGround && gamemode == 1) {
			//game = true;
			intro = false;
			character = true;
			onBackGround = false;
			p1Ready = false;
			p2Ready = false;
			p1CharSel = 1;
			p2CharSel = 5;
		}
		if (GDV5.KeysPressed[KeyEvent.VK_ENTER] && onBackGround && gamemode == 2) {
			howToPlay = true;
			onBackGround = false;
			intro = false;
			
		}
		if (GDV5.KeysPressed[KeyEvent.VK_BACK_SPACE] && (character || howToPlay)) {
			howToPlay = false;
			character = false;
			intro = true;
			onBackGround = true;
		}
	}
	
	public void deactivateTornados() {
		p1Tor1.deactivateTornado();
		p1Tor2.deactivateTornado();
		p1Tor3.deactivateTornado();
		p2Tor1.deactivateTornado();
		p2Tor2.deactivateTornado();
		p2Tor3.deactivateTornado();
	}
	
	public void characterSelectionObserver() {
		deactivateTornados();
		if (GDV5.KeysPressed[KeyEvent.VK_D] && p1CharSel != 5 && pressed1 && !p1Ready) {
			pressed1 = false;
			p1CharSel += 1;
			
		}
		if (GDV5.KeysPressed[KeyEvent.VK_A] && p1CharSel != 1 && pressed2 && !p1Ready) {
			pressed2 = false;
			p1CharSel -= 1;
			
		}
		if (GDV5.KeysPressed[KeyEvent.VK_RIGHT] && p2CharSel != 5 && pressed3 && !p2Ready) {
			pressed3 = false;
			p2CharSel += 1;
			
		}
		if (GDV5.KeysPressed[KeyEvent.VK_LEFT] && p2CharSel != 1 && pressed4 && !p2Ready) {
			pressed4 = false;
			p2CharSel -= 1;
			
		}
		if (GDV5.KeysReleased[KeyEvent.VK_D]) {
			pressed1 = true;
		}
		if (GDV5.KeysReleased[KeyEvent.VK_A]) {
			pressed2 = true;
		}
		if (GDV5.KeysReleased[KeyEvent.VK_RIGHT]) {
			pressed3 = true;
		}
		if (GDV5.KeysReleased[KeyEvent.VK_LEFT]) {
			pressed4 = true;
		}
		if (GDV5.KeysPressed[KeyEvent.VK_E]) {
			p1Ready = true;
		}
		if (GDV5.KeysPressed[KeyEvent.VK_SPACE]) {
			p2Ready = true;
		}
	}
		
	public void observingForPlayersReady() {
		if (p1Ready && p2Ready) {
			character = false;
			game = true;
			p1Char = p1CharSel;
			p2Char = p2CharSel;
			
		}
	}
	
	public void resetup() {
		ball.setBack();
		if (extraBall.getOn()) {
			extraBall.setBack();
			extraBall.off();
		}
		p1Slowed = false;
		p2Slowed = false;
		p1NumTor = 0;
		p2NumTor = 0;
		ball.normalSpeed();
		deactivateTornados();
	}
	
	public void playerAbilityActivation() {
		if (GDV5.KeysPressed[KeyEvent.VK_F] && p1Charge == 10) {
			p1Charge = 0;
			
			switch (p1Char) {
			case 1:
				p1wall1.wallActivated();
				p1wall2.wallActivated();
				p1wall3.wallActivated();
				p1wall1.locate(100,0);
				p1wall2.locate(150,0);
				p1wall3.locate(200,0);
				break;
			case 2:
				ball.baseBall();
				break;
			case 3:
				p1timer = new Timer();
				p2Slowed = true;
				p1timeTask = new TimerTask() {
				private final int MAX_SECOND = 10;
				
				@Override 
				public void run() {
					if (p1time < MAX_SECOND) {
						p1time++;
						p2Slowed = false;
					} else {
						cancel();
					}
				}
				};
				p1timer.schedule(p1timeTask, 5000);
				break;
			case 4:
				if (!(p1NumTor == 3)) {
					switch (p1NumTor) {
					case 0:
						p1Tor1.activateTornado();
						p1Tor1.x = p1.x + 80;
						p1Tor1.y = p1.y;
						p1NumTor += 1;
						break;
					case 1:
						p1Tor2.activateTornado();
						p1Tor2.x = p1.x + 80;
						p1Tor2.y = p1.y;
						p1NumTor += 1;
						break;
					case 2:
						p1Tor3.activateTornado();
						p1Tor3.x = p1.x + 80;
						p1Tor3.y = p1.y;
						break;
					}
				}
				break;
			case 5:
				System.out.println("p5");
				extraBall.on();
				extraBall.turnedOn(ball.getdx());
				extraBall.x = ball.x -4;
				extraBall.y = ball.y -4;
				break;
			}
		}
		if (GDV5.KeysPressed[KeyEvent.VK_SLASH] && p2Charge == 10) {
			p2Charge = 0;
			switch (p2Char) {
			case 1:
				p2wall1.wallActivated();
				p2wall2.wallActivated();
				p2wall3.wallActivated();
				p2wall1.locate(700,0);
				p2wall2.locate(650,0);
				p2wall3.locate(600,0);
				break;
			case 2:
				ball.baseBall();
				break;
			case 3:
				p2timer = new Timer();
				p1Slowed = true;
				p2timeTask = new TimerTask() {
				private final int MAX_SECOND = 10;
				
				@Override 
				public void run() {
					if (p2time < MAX_SECOND) {
						p2time++;
						p1Slowed = false;
					} else {
						cancel();
						}
					}
				};
				p2timer.schedule(p2timeTask, 5000);
				break;
			case 4:
				if (!(p2NumTor == 3)) {
					switch (p2NumTor) {
					case 0:
						p2Tor1.activateTornado();
						p2Tor1.x = p2.x - 80;
						p2Tor1.y = p2.y;
						p2NumTor += 1;
						break;
					case 1:
						System.out.println("bruh");
						p2Tor2.activateTornado();
						p2Tor2.x = p2.x - 80;
						p2Tor2.y = p2.y;
						p2NumTor += 1;
						break;
					case 2:
						p2Tor3.activateTornado();
						p2Tor3.x = p2.x - 80;
						p2Tor3.y = p2.y;
						break;
					}
				}
				break;
			case 5:
				extraBall.on();
				extraBall.turnedOn(ball.getdx());
				extraBall.x = ball.x -4;
				extraBall.y = ball.y -4;
				break;
			}
		}
	}
	
	public void update() {
		backgroundAndTransitionObserver();
		if (character) {
			characterSelectionObserver();
		}
		observingForPlayersReady();
		if (game) {
		if (paused) {
			resetup();
		}
		if ((GDV5.KeysPressed[KeyEvent.VK_S] || GDV5.KeysPressed[KeyEvent.VK_W] || GDV5.KeysPressed[KeyEvent.VK_UP] || GDV5.KeysPressed[KeyEvent.VK_DOWN]) && game) {
			ball.ready();
			paused = false;
		}
		if (ball.y >= GDV5.getMaxWindowY()-30) {
			ball.changeY();
		} 
		if (p1Score >= 3) {
			game = false;
		}
		if (p2Score >= 3) {
			game = false;
		}
		if (ball.x >= GDV5.getMaxWindowX()-30) {
			ball.scored();
			if (extraBall.getOn()) {
				extraBall.scored();
			}
			paused = true;
			p1Score += 1;
			bounce = 0;
			
			
			p1.getBack();
			p2.getBack();
		} 
		if (ball.y <= 0) {
			ball.changeY();
		} 
		if (ball.x <= 0) {
			ball.scored();
			if (extraBall.getOn()) {
				extraBall.scored();
			}
			paused = true;
			p2Score += 1;
			bounce = 0;
			
			p1.getBack();
			p2.getBack();
		} 
		if (ball.intersects(p1)) {
			ball.changeX();
			ball.x += 4;
			bounce += 1;
			if (!(p1Charge == 10)) {
				p1Charge += 1;
			}
			if (!(p2Charge == 10)) {
				p2Charge += 1;
			}
			if (bounce % 8 == 0 && bounce != 0) {
				ball.nextStage();
				}
			
		}
		if (ball.intersects(p2)) {
			if (true) { 
			ball.changeX();
			ball.x -= 4;
			bounce += 1;
			if (!(p1Charge == 10)) {
				p1Charge += 1;
			}
			if (!(p2Charge == 10)) {
				p2Charge += 1;
			}
			if (bounce % 8 == 0 && bounce != 0) {
				ball.nextStage();
				}
			}
		}
		if (p1wall1.getWall()) {
			if (p1wall1.intersects(ball)) {
				ball.changeX();
				p1wall1.wallDeactivated();
			}
			if (p1wall1.intersects(extraBall)) {
				extraBall.changeX();
				p1wall1.wallDeactivated();
			}
		}
		if (p1wall2.getWall()) {
			if (p1wall2.intersects(ball)) {
				ball.changeX();
				p1wall2.wallDeactivated();
			}
			if (p1wall2.intersects(extraBall)) {
				extraBall.changeX();
				p1wall2.wallDeactivated();
			}
		}
		if (p1wall3.getWall()) {
			if (p1wall3.intersects(ball)) {
				ball.changeX();
				p1wall3.wallDeactivated();
			}
			if (p1wall3.intersects(extraBall)) {
				extraBall.changeX();
				p1wall3.wallDeactivated();
			}
		}
		if (p2wall1.getWall()) {
			if (p2wall1.intersects(ball)) {
				ball.changeX();
				p2wall1.wallDeactivated();
			}
			if (p2wall1.intersects(extraBall)) {
				extraBall.changeX();
				p2wall1.wallDeactivated();
			}
		}
		if (p2wall2.getWall()) {
			if (p2wall2.intersects(ball)) {
				ball.changeX();
				p2wall2.wallDeactivated();
			}
			if (p2wall2.intersects(extraBall)) {
				extraBall.changeX();
				p2wall2.wallDeactivated();
			}
		}
		if (p2wall3.getWall()) {
			if (p2wall3.intersects(ball)) {
				ball.changeX();
				p2wall3.wallDeactivated();
			}
			if (p2wall3.intersects(extraBall)) {
				extraBall.changeX();
				p2wall3.wallDeactivated();
			}
		}
		if (extraBall.getOn()) {
			if (extraBall.y >= GDV5.getMaxWindowY()-30) {
				extraBall.changeY();
				
			} 
			if (extraBall.x >= GDV5.getMaxWindowX()-30) {
				extraBall.scored();
				ball.scored();
				paused = true;
				p1Score += 1;
				bounce = 0;
				
				if (p1Score == 3) {
					game = false;
				}
				p1.getBack();
				p2.getBack();
			} 
			if (extraBall.y <= 0) {
				extraBall.changeY();
			} 
			if (extraBall.x <= 0) {
				extraBall.scored();
				ball.scored();
				paused = true;
				p2Score += 1;
				bounce = 0;
				if (p2Score == 3) {
					game = false;
				}
				p1.getBack();
				p2.getBack();
			} 
			if (extraBall.intersects(p1)) {
				extraBall.changeX();
				bounce += 1;
				if (!(p1Charge == 10)) {
					p1Charge += 1;
				}
				if (!(p2Charge == 10)) {
					p2Charge += 1;
				}
				if (bounce % 8 == 0 && bounce != 0) {
					extraBall.nextStage();
					}
			}
			if (extraBall.intersects(p2)) { 
				extraBall.changeX();
				bounce += 1;
				if (!(p1Charge == 10)) {
					p1Charge += 1;
				}
				if (!(p2Charge == 10)) {
					p2Charge += 1;
				}
				
			}
		}
		ball.move();
		//if (extraBall.getOn()) {
			extraBall.move();
//		//}
			if (p1Tor1.getTor()) {
				
			
			if (p1Tor1.intersects(ball)) { 
				ball.positiveX();
				bounce += 1;
				if (!(p1Charge == 10)) {
					p1Charge += 1;
				}
				if (!(p2Charge == 10)) {
					p2Charge += 1;
				}
				
			}
			if (p1Tor2.intersects(ball)) { 
				ball.positiveX();
				bounce += 1;
				if (!(p1Charge == 10)) {
					p1Charge += 1;
				}
				if (!(p2Charge == 10)) {
					p2Charge += 1;
				}
				
			}
			if (p1Tor3.intersects(ball)) { 
				ball.positiveX();
				bounce += 1;
				if (!(p1Charge == 10)) {
					p1Charge += 1;
				}
				if (!(p2Charge == 10)) {
					p2Charge += 1;
				}
				
			}
			}
			if (p2Tor1.getTor()) {
			if (p2Tor1.intersects(ball)) { 
				ball.negativeX();
				bounce += 1;
				if (!(p1Charge == 10)) {
					p1Charge += 1;
				}
				if (!(p2Charge == 10)) {
					p2Charge += 1;
				}
				
			}
			if (p2Tor2.intersects(ball)) { 
				ball.negativeX();
				bounce += 1;
				if (!(p1Charge == 10)) {
					p1Charge += 1;
				}
				if (!(p2Charge == 10)) {
					p2Charge += 1;
				}
				
			}
			if (p2Tor3.intersects(ball)) { 
				ball.negativeX();
				bounce += 1;
				if (!(p1Charge == 10)) {
					p1Charge += 1;
				}
				if (!(p2Charge == 10)) {
					p2Charge += 1;
				}
				}
			}
			
		if (GDV5.KeysPressed[KeyEvent.VK_W] && p1.y >= 0) {
			p1.moveUp();
		}
		if (GDV5.KeysPressed[KeyEvent.VK_S] && p1.y <= GDV5.getMaxWindowY()-80) {
			p1.moveDown();
		}
		
		playerAbilityActivation();
		
		if (p1Tor1.getTor()) {
			p1Tor1.ready();
			p1Tor1.move();
			if (p1Tor1.y <= 0) {
				p1Tor1.changeY();
			}
			if (p1Tor1.y + 50 >= GDV5.getMaxWindowY()) {
				p1Tor1.changeY();
			}
		}
		if (p1Tor2.getTor()) {
			p1Tor2.ready();
			p1Tor2.move();
			if (p1Tor2.y <= 0) {
				p1Tor2.changeY();
			}
			if (p1Tor2.y + 50 >= GDV5.getMaxWindowY()) {
				p1Tor2.changeY();
			}
		}
		if (p1Tor3.getTor()) {
			p1Tor3.ready();
			p1Tor3.move();
			if (p1Tor3.y <= 0) {
				p1Tor3.changeY();
			}
			if (p1Tor3.y + 50 >= GDV5.getMaxWindowY()) {
				p1Tor3.changeY();
			}
		}
		if (p2Tor1.getTor()) {
			p2Tor1.ready();
			p2Tor1.move();
			if (p2Tor1.y <= 0) {
				p2Tor1.changeY();
			}
			if (p2Tor1.y + 50 >= GDV5.getMaxWindowY()) {
				p2Tor1.changeY();
			}
		}
		if (p2Tor2.getTor()) {
			p2Tor2.ready();
			p2Tor2.move();
			if (p2Tor2.y <= 0) {
				p2Tor2.changeY();
			}
			if (p2Tor2.y + 50 >= GDV5.getMaxWindowY()) {
				p2Tor2.changeY();
			}
		}
		if (p2Tor3.getTor()) {
			p2Tor3.ready();
			p2Tor3.move();
			if (p2Tor3.y <= 0) {
				p2Tor3.changeY();
			}
			if (p2Tor3.y + 50 >= GDV5.getMaxWindowY()) {
				p2Tor3.changeY();
			}
		}
		
		
		//p1.y = ball.y-40;
//		if (p2.y > ball.y) {
//			p2.moveUp();
//		} else if (p2.y + 80 < ball.y + 30) {
//			p2.moveDown();
//		}
		//p2.y = ball.y-40;
		
		if (GDV5.KeysPressed[KeyEvent.VK_UP] && p2.y >= 0) {
			p2.moveUp();
		}
		if (GDV5.KeysPressed[KeyEvent.VK_DOWN] && p2.y <= GDV5.getMaxWindowY()-80) {
			p2.moveDown();
		}
		if (GDV5.KeysPressed[KeyEvent.VK_5]) {
			ball.nextStage();
			p2.x = 730;
			p1.x = 50;
			
		}
		if (GDV5.KeysPressed[KeyEvent.VK_1]) {
			startGame();
			}
		if (p2Slowed) {
			p2.slow();
		} else {
			p2.back();
		}
		if (p1Slowed) {
			p1.slow();
		} else {
			p1.back();
			}
		}
		if (!game) {
			if (!character) {
			p1Ready = false;
			p2Ready = false;
			}
			if (GDV5.KeysPressed[KeyEvent.VK_R]) {
				p1Score = 0;
				p2Score = 0;
				p1Charge = 0;
				p2Charge = 0;
				onBackGround = true;
				intro = true;
			}
		}
		
	}
	
	public static void startGame() {
		Pong p = new Pong();
		p.start();
	}
	
	
	public void draw(Graphics2D win) {
		
		win.setColor(Color.WHITE);
		if (intro) {
			
			win.setFont(new Font("Helvetica", Font.PLAIN, 50));
			win.drawString("Power", GDV5.getMaxWindowX()/2 - win.getFontMetrics().stringWidth("Power")/2, GDV5.getMaxWindowY()/2 - 50);
			win.drawString("Pong", GDV5.getMaxWindowX()/2 - win.getFontMetrics().stringWidth("Pong")/2, GDV5.getMaxWindowY()/2);
			win.setFont(new Font("Helvetica", Font.PLAIN, 20));
			win.drawString("Play game", GDV5.getMaxWindowX()/2 - win.getFontMetrics().stringWidth("Play game")/2, GDV5.getMaxWindowY() - 150);
			win.drawString("How to Play", GDV5.getMaxWindowX()/2 - win.getFontMetrics().stringWidth("How to play")/2, GDV5.getMaxWindowY() - 100);
			win.fill(indic);
		}
		if (howToPlay) {
			win.setFont(new Font("Helvetica", Font.PLAIN, 20));
			win.drawString("How to Play", GDV5.getMaxWindowX()/2 - win.getFontMetrics().stringWidth("How to play")/2, 50);
			win.setFont(new Font("Helvetica", Font.PLAIN, 30));
			win.drawString("P1", 125, 150);
			win.drawString("P2", GDV5.getMaxWindowX()/2 - win.getFontMetrics().stringWidth("P2")/2, 150);
			win.drawString("Win", GDV5.getMaxWindowX() - win.getFontMetrics().stringWidth("Win") - 125, 150);
			win.setStroke(new BasicStroke(4));
			win.drawRect(125, 250, 50, 50);
			win.drawRect(125, 325, 50, 50);
			win.drawRect(GDV5.getMaxWindowX()/2 - 25, 250, 50, 50);
			win.drawRect(GDV5.getMaxWindowX()/2 - 25, 325, 50, 50);
	        win.fillRect(125,250,50,50);
	        win.fillRect(125,325,50,50);
	        win.fillRect(200,275,50,50);
	        win.fillRect(GDV5.getMaxWindowX()/2 - 25,250,50,50);
	        win.fillRect(GDV5.getMaxWindowX()/2 - 25,325,50,50);
	        win.fillRect(GDV5.getMaxWindowX()/2 - 25 + 75,275,50,50);
	        win.fillRect(50,500,100,50);
	        win.setColor(Color.BLACK);
	        win.drawString("W", 145, 275);
			win.drawString("S", 145, 350);
			win.drawString("↑", GDV5.getMaxWindowX()/2 - 25, 275);
			win.drawString("F", 225, 300);
			win.drawString("/", GDV5.getMaxWindowX()/2 - 25 + 85, 300);
			win.drawString("↓", GDV5.getMaxWindowX()/2 - 25, 350);
			win.setFont(new Font("Helvetica", Font.PLAIN, 10));
			win.drawString("Backspace", 55, 540);
			win.setFont(new Font("Helvetica", Font.PLAIN, 20));
			win.drawString("Go Back", 55, 520);
			win.setColor(Color.WHITE);
			win.drawString("  First one who gets  ", GDV5.getMaxWindowX() - 225, 250);
			win.drawString("3 points wins the game", GDV5.getMaxWindowX() - 225, 280);
			
			
		}
		if (character) {
				win.drawImage(image, getMaxWindowX()/2 - 237, 450, 75, 75, getBackground(), getFocusCycleRootAncestor());
				win.drawImage(image2, getMaxWindowX()/2 - 137, 450, 75, 75, getBackground(), getFocusCycleRootAncestor());
				win.drawImage(image3, getMaxWindowX()/2 - 37, 450, 75, 75, getBackground(), getFocusCycleRootAncestor());
				win.drawImage(image4, getMaxWindowX()/2 + 63, 450, 75, 75, getBackground(), getFocusCycleRootAncestor());
				win.drawImage(image5, getMaxWindowX()/2 + 163, 450, 75, 75, getBackground(), getFocusCycleRootAncestor());
				
				if (p1CharSel == 1) {
					win.drawImage(image, 70, 120, 200, 200, getBackground(), getFocusCycleRootAncestor());
					win.drawString("   Creates 3 layers of shield  ", 70, 360);
					win.drawString(" that will block incoming ball ", 70, 380);
				}
				if (p1CharSel == 2) {
					win.drawImage(image2, 70, 120, 200, 200, getBackground(), getFocusCycleRootAncestor());
					win.drawString("   Makes the ball go straight  ", 70, 360);
				}
				if (p1CharSel == 3) {
					win.drawImage(image3, 70, 120, 200, 200, getBackground(), getFocusCycleRootAncestor());
					win.drawString("     slows opponent     ", 70, 360);
					win.drawString("       down for 4s      ", 70, 380);
				}
				if (p1CharSel == 4) {
					win.drawImage(image4, 70, 120, 200, 200, getBackground(), getFocusCycleRootAncestor());
					win.drawString("  Creates tornado that   ", 70, 360);
					win.drawString("  move around and block  ", 70, 380);
				}
				if (p1CharSel == 5) {
					win.drawImage(image5, 70, 120, 200, 200, getBackground(), getFocusCycleRootAncestor());
					win.drawString("   Make another copy of   ", 70, 360);
					win.drawString("  the ball that can score ", 70, 380);
				}
				if (p2CharSel == 1) {
					win.drawImage(image, getMaxWindowX() - 270, 120, 200, 200, getBackground(), getFocusCycleRootAncestor());
					win.drawString("   Creates 3 layers of shield  ", getMaxWindowX() - 270, 360);
					win.drawString(" that will block incoming ball ", getMaxWindowX() - 270, 380);
				}
				if (p2CharSel == 2) {
					win.drawImage(image2, getMaxWindowX() - 270, 120, 200, 200, getBackground(), getFocusCycleRootAncestor());
					win.drawString("   Makes the ball go straight  ", getMaxWindowX() - 270, 360);
				}
				if (p2CharSel == 3) {
					win.drawImage(image3, getMaxWindowX() - 270, 120, 200, 200, getBackground(), getFocusCycleRootAncestor());
					win.drawString("     slows opponent     ", getMaxWindowX() - 270, 360);
					win.drawString("       down for 4s      ", getMaxWindowX() - 270, 380);
				}
				if (p2CharSel == 4) {
					win.drawImage(image4, getMaxWindowX() - 270, 120, 200, 200, getBackground(), getFocusCycleRootAncestor());
					win.drawString("  Creates tornado that   ", getMaxWindowX() - 270, 360);
					win.drawString("  move around and block  ", getMaxWindowX() - 270, 380);
				}
				if (p2CharSel == 5) {
					win.drawString("   Make another copy of   ", getMaxWindowX() - 270, 360);
					win.drawString("  the ball that can score ", getMaxWindowX() - 270, 380);
					win.drawImage(image5, getMaxWindowX() - 270, 120, 200, 200, getBackground(), getFocusCycleRootAncestor());
					}
				
			
			win.setColor(Color.WHITE);
			win.setFont(new Font("Helvetica", Font.PLAIN, 40));
			win.drawString("Select Ability", 50, 70);
			win.drawString("VS", getMaxWindowX()/2 - win.getFontMetrics().stringWidth("VS")/2, 250);
			win.setStroke(new BasicStroke(4));
			win.drawRect(getMaxWindowX()/2 - 237, 450, 75, 75);
			win.drawRect(getMaxWindowX()/2 - 137, 450, 75, 75);
			win.drawRect(getMaxWindowX()/2 - 37, 450, 75, 75);
			win.drawRect(getMaxWindowX()/2 + 63, 450, 75, 75);
			win.drawRect(getMaxWindowX()/2 + 163, 450, 75, 75);
			win.setFont(new Font("Helvetica", Font.BOLD, 20));
			
			if (!p2Ready) {
				win.setColor(Color.GRAY);
			} else {
				win.setColor(Color.WHITE);
			}
			win.drawString("READY", getMaxWindowX() - 220, 420);
			if (!p1Ready) {
				win.setColor(Color.GRAY);
			} else {
				win.setColor(Color.WHITE);
			}
			win.drawString("READY", 120, 420);
			
			win.setStroke(new BasicStroke(8));
			win.setColor(Color.GREEN);
			//Image image1 = new Image();
			//win.drawImage(image1, p1CharSel, p1Char, gamemode, bounce, getFocusCycleRootAncestor());
			win.drawImage(null, p1CharSel, p1Char, gamemode, bounce, getFocusCycleRootAncestor());
			win.drawRect(getMaxWindowX()/2 - 237 + (100 * (p1CharSel - 1)), 450, 75, 75);
		
			win.setColor(Color.BLUE);
			win.drawRect(getMaxWindowX()/2 - 237 + (100 * (p2CharSel - 1)), 450, 75, 75);
			
		
			
		}
		if (!intro && !character && !howToPlay) {
		win.fill(ball);
		
		
		win.setFont(new Font("Helvetica", Font.PLAIN, 50));
		win.drawString("" + p1Score, 100, 75);
		win.drawString("" + p2Score, 650, 75);
		if (p1wall1.getWall()) {
			win.fill(p1wall1);
		}
		if (p1wall2.getWall()) {
			win.fill(p1wall2);
		}
		if (p1wall3.getWall()) {
			win.fill(p1wall3);
		}
		if (p2wall1.getWall()) {
			win.fill(p2wall1);
		}
		if (p2wall2.getWall()) {
			win.fill(p2wall2);
		}
		if (p2wall3.getWall()) {
			win.fill(p2wall3);
		}
		
		if (extraBall.getOn()) {
			win.setColor(Color.gray);
			win.fill(extraBall);
		}
		win.setColor(Color.white);
		if (p1Score == 3) {
			win.drawString("P1 Won!", GDV5.getMaxWindowX()/2 - 100, GDV5.getMaxWindowY()/2 - 50);
			win.setFont(new Font("Helvetica", Font.PLAIN, 20));
			win.drawString("Press R to restart", GDV5.getMaxWindowX()/2 - win.getFontMetrics().stringWidth("Press R to restart")/2, GDV5.getMaxWindowY()/2);
		} else if (p2Score == 3) {
			
			win.drawString("P2 Won!", GDV5.getMaxWindowX()/2 - 100, GDV5.getMaxWindowY()/2 - 50);
			win.drawString("Press R to restart", GDV5.getMaxWindowX()/2 - win.getFontMetrics().stringWidth("Press R to restart")/2, GDV5.getMaxWindowY()/2);
		} else if (bounce % 8 == 0 && bounce != 0) {
			win.drawString("Speed up!", GDV5.getMaxWindowX()/2 - 100, GDV5.getMaxWindowY()/2 - 50);
		}
		win.setStroke(new BasicStroke(8));
		win.setColor(Color.GREEN);
		win.drawRect(70, 500, 75, 75);
		win.setStroke(new BasicStroke(4));
		win.drawRect(145, 545, 100, 30);
		win.fillRect(145, 545, 10*p1Charge, 30);
		win.setColor(Color.BLUE);
		win.setStroke(new BasicStroke(8));
		win.drawRect(getMaxWindowX() - 70 - 75, 500, 75, 75);
		win.setStroke(new BasicStroke(4));
		win.drawRect(getMaxWindowX() - 70 - 75 - 100, 545, 100, 30);
		win.fillRect(getMaxWindowX() - 70 - 75 - 10*p2Charge, 545, 10*p2Charge, 30);
		if (p1Slowed) {
			win.setColor(Color.CYAN);
		} else {
			win.setColor(Color.WHITE);
		}
		win.fill(p1);
		if (p2Slowed) {
			win.setColor(Color.CYAN);
		} else {
			win.setColor(Color.WHITE);
		}
		win.fill(p2);
		
		if (p1Tor1.getTor()) {
			win.drawImage(image4, p1Tor1.x, p1Tor1.y, p1Tor1.width, p1Tor1.height, getBackground(), getFocusCycleRootAncestor());
		}
		if (p1Tor2.getTor()) {
			win.drawImage(image4, p1Tor2.x, p1Tor2.y, p2Tor1.width, p2Tor1.height, getBackground(), getFocusCycleRootAncestor());
		}
		if (p1Tor3.getTor()) {
			win.drawImage(image4, p1Tor3.x, p1Tor3.y, p2Tor1.width, p2Tor1.height, getBackground(), getFocusCycleRootAncestor());
		}
		if (p2Tor1.getTor()) {
			win.drawImage(image4, p2Tor1.x, p2Tor1.y, p2Tor1.width, p2Tor1.height, getBackground(), getFocusCycleRootAncestor());
		}
		if (p2Tor2.getTor()) {
			win.drawImage(image4, p2Tor2.x, p2Tor2.y, p2Tor1.width, p2Tor1.height, getBackground(), getFocusCycleRootAncestor());
		}
		if (p2Tor3.getTor()) {
			win.drawImage(image4, p2Tor3.x, p2Tor3.y, p2Tor1.width, p2Tor1.height, getBackground(), getFocusCycleRootAncestor());
		}
		
		if (p1CharSel == 1) {
			win.drawImage(image, 70, 500, 75, 75, getBackground(), getFocusCycleRootAncestor());
		}
		if (p1CharSel == 2) {
			win.drawImage(image2, 70, 500, 75, 75, getBackground(), getFocusCycleRootAncestor());
		}
		if (p1CharSel == 3) {
			win.drawImage(image3, 70, 500, 75, 75, getBackground(), getFocusCycleRootAncestor());
		}
		if (p1CharSel == 4) {
			win.drawImage(image4, 70, 500, 75, 75, getBackground(), getFocusCycleRootAncestor());
		}
		if (p1CharSel == 5) {
			win.drawImage(image5, 70, 500, 75, 75, getBackground(), getFocusCycleRootAncestor());
		}
		if (p2CharSel == 1) {
			win.drawImage(image, getMaxWindowX() - 70 - 75, 500, 75, 75, getBackground(), getFocusCycleRootAncestor());
		}
		if (p2CharSel == 2) {
			win.drawImage(image2, getMaxWindowX() - 70 - 75, 500, 75, 75, getBackground(), getFocusCycleRootAncestor());
		}
		if (p2CharSel == 3) {
			win.drawImage(image3, getMaxWindowX() - 70 - 75, 500, 75, 75, getBackground(), getFocusCycleRootAncestor());
		}
		if (p2CharSel == 4) {
			win.drawImage(image4, getMaxWindowX() - 70 - 75, 500, 75, 75, getBackground(), getFocusCycleRootAncestor());
		}
		if (p2CharSel == 5) {
			win.drawImage(image5, getMaxWindowX() - 70 - 75, 500, 75, 75, getBackground(), getFocusCycleRootAncestor());
			}
			
		}
		
	}
	
	
}
