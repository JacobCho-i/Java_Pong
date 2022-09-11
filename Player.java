package randomGame;

import java.awt.Rectangle;
public class Player extends Rectangle{
	private int dy = 0;
	
	
	public Player(int player, int dy) {
		super(0,0,20,80);
		this.dy = dy;
		if (player == 1) {
			this.x = 50;
		} else {
			this.x = 730;
		}
		
		this.y = GDV5.getMaxWindowY()/2 - 40;
	}
	
	public Player() {
		super(0,0,20,20);
	}
	
	public void moveUp() {
		this.translate(0, -dy);
	}
	public void moveDown() {
		this.translate(0, dy);
	}
	
	public void slow() {
		dy = 2;
	}
	public void back() {
		dy = 4;
	}
	
	public void getBack() {
		this.y = GDV5.getMaxWindowY()/2 - 40;
	}
	
	
}
