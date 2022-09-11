package randomGame;

import java.awt.Rectangle;
public class Ball extends Rectangle{
	
	private int dx = 0;
	private int dy = 0;
	private boolean stop = true;
	private boolean extraOn = false;
	private boolean wall = false;
	private boolean tornado = false;
	
	public Ball(int x, int y, int dx, int dy) {
		super(0,0,x,y);
		this.dx = dx;
		this.dy = dy;
		this.x = GDV5.getMaxWindowX()/2;
		this.y = GDV5.getMaxWindowY()/2;
	}
	
	public void setBack() {
		this.x = GDV5.getMaxWindowX()/2;
		this.y = GDV5.getMaxWindowY()/2;
	}
	
	public int getdx() {
		return dx;
	}
	public void normalSpeed() {
		dx = 4;
		dy = 4;
	}
	
	public Ball() {
		super(0,0,20,20);
	}
	
	public void move() {
		if (!stop) {
		this.translate(dx, dy);
		}
	}
	
	public void turnedOff() {
		dx = 0;
		dy = 0;
	}
	public void turnedOn(int dx) {
		this.dx = dx;
		dy = -dx;
		stop = false;
	}
	public void baseBall() {
		dx = 2 * dx;
		dy = 0;
	}
	public void on() {
		extraOn = true;
	}
	
	public void off() {
		extraOn = false;
	}
	
	public boolean getOn() {
		return extraOn;
	}
	public boolean iszeroY() {
		return dy == 0;
	}
	public void changeX() {
		if (dy == 0) {
			dx /= 2;
			dy = dx;
		} else {
		dx = -dx;
		}
	}
	public void changeY() {
		dy = -dy;
	}
	public void positiveX() {
		dx = Math.abs(dx);
	}
	public void negativeX() {
		dx = -Math.abs(dx);
	}
	public void normalX() {
		dx = -dx;
	}
	public void normalY() {
		dy = -dy;
	}
	public void scored() {
		stop = true;
	}
	public void ready() {
		stop = false;
	}
	public void nextStage() {
		dx += 1;
		if (dy < 0) {
			dy += -1;
		} else {
			dy += 1;
		}

	}
	public void locate(int newx, int newy) {
		x = newx;
		y = newy;
	}
	
	public void wallActivated() {
		wall = true;
	}
	public void wallDeactivated() {
		wall = false;
	}
	public boolean getWall() {
		return wall;
	}
	public void activateTornado() {
		tornado = true;
	}
	public void deactivateTornado() {
		tornado = false;
		x = 10000;
		y = 10000;
	}
	public boolean getTor() {
		return tornado;
	}
	
}
