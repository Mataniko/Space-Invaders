package invaders;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;


public class Ship extends GameObject {

	public Ship(int x, int y, int width, int height)
	{
		super(x,y,width,height);
		getSprite()[0] = new Sprite(new int[][] {
		{0,0,0,0,0,0,1,0,0,0,0,0,0},
		{0,0,0,0,0,1,1,1,0,0,0,0,0},
		{0,0,0,0,0,1,1,1,0,0,0,0,0},
		{0,1,1,1,1,1,1,1,1,1,1,1,0},
		{1,1,1,1,1,1,1,1,1,1,1,1,1},
		{1,1,1,1,1,1,1,1,1,1,1,1,1},
		{1,1,1,1,1,1,1,1,1,1,1,1,1},
		{1,1,1,1,1,1,1,1,1,1,1,1,1}
		});
	}
	
	public boolean tick(boolean left, boolean right, boolean shoot)
	{	
		if (right) 
			Move(1);															
		
		
		if (left)
			Move(-1);					
		
				
		if (shoot)
			return true;
									
		return false;					
	}
	
	public void Move(int x)
	{		
		Rectangle sprite = super.getPosition();
		if (x > 0)
			sprite.x = Math.min(224-24, sprite.x+x); 
		
		if (x < 0)
			sprite.x = Math.max(18, sprite.x+x);
		
		super.setPosition(sprite);
	}
	
	public void Draw(Graphics2D g)
	{		
		g.setColor(Color.green);					
		super.Draw(g);
	
	}
}
