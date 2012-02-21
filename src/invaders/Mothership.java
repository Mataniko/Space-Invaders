package invaders;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Mothership extends GameObject implements Runnable {

	public Mothership(int x, int y, int width, int height)
	{
		super(x,y,width,height);
		getSprite()[0] = new Sprite(new int[][] {
		{0,0,0,0,0,1,1,1,1,1,1,0,0,0,0,0},
		{0,0,0,1,1,1,1,1,1,1,1,1,1,0,0,0},
		{0,0,1,1,1,1,1,1,1,1,1,1,1,1,0,0},
		{0,1,1,0,1,1,0,1,1,0,1,1,0,1,1,0},
		{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
		{0,0,1,1,1,0,0,1,1,0,0,1,1,1,0,0},
		{0,0,0,1,0,0,0,0,0,0,0,0,1,0,0,0}		
		});
	}
	
	public boolean getExpired()
	{
		if (getPosition().x < -16 || getPosition().x > 260)
			return true;
		
		return false;
	}
	
	public int getScore() { return 100; }
	public void Move(int x)
	{		
		Rectangle sprite = super.getPosition();		
			sprite.x-=x; 					
		super.setPosition(sprite);
	}
	
	public void Draw(Graphics2D g)
	{		
		g.setColor(Color.RED);					
		super.Draw(g);
	
	}

	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
			Move(+1);		
						
		
		}
	}
}
