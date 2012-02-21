package invaders;

import invaders.Enums.Direction;
import invaders.Enums.InvaderType;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

public class Invader extends GameObject implements Comparable<Invader> {
	
	private boolean _canShoot;
	public boolean getCanShoot() { return _canShoot; }
	public void setCanShoot(boolean canShoot) { _canShoot=canShoot; }
	
	private Enums.InvaderType _invaderType;
	
	
	private Point _location;
	public Point getLocation() { return new Point(_location); }
	
	public Invader(int column, int row, InvaderType invaderType)
	{
		_location= new Point(column,row);
		_invaderType = invaderType;
		int width=0,height=0,x=0,y=0;
		x=(column*16);
		switch (invaderType)
		{
			case Front:
				width=12;
				height=8;
				if (row==5)
					y=128;
				else
					y=112;
				
				getSprite()[0] = new Sprite(new int[][] {
						{0,0,0,0,1,1,1,1,0,0,0,0},
						{0,1,1,1,1,1,1,1,1,1,1,0},
						{1,1,1,1,1,1,1,1,1,1,1,1},
						{1,1,1,0,0,1,1,0,0,1,1,1},
						{1,1,1,1,1,1,1,1,1,1,1,1},
						{0,0,1,1,1,0,0,1,1,1,0,0},
						{0,1,1,0,0,1,1,0,0,1,1,0},
						{0,0,1,1,0,0,0,0,1,1,0,0}
				});

				getSprite()[1] = new Sprite(new int[][] {
						{0,0,0,0,1,1,1,1,0,0,0,0},
						{0,1,1,1,1,1,1,1,1,1,1,0},
						{1,1,1,1,1,1,1,1,1,1,1,1},
						{1,1,1,0,0,1,1,0,0,1,1,1},
						{1,1,1,1,1,1,1,1,1,1,1,1},
						{0,0,0,1,1,0,0,1,1,0,0,0},
						{0,0,1,1,0,1,1,0,1,1,0,0},
						{1,1,0,0,0,0,0,0,0,0,1,1}
				});
				break;
			case Middle:
				width=11;
				height=8;
				if (row==3)
					y=96;
				else
					y=80;	
				x++;
				//invader2.1
				getSprite()[0] = new Sprite(new int[][] {
						{0,0,1,0,0,0,0,0,1,0,0},				
						{0,0,0,1,0,0,0,1,0,0,0},
						{0,0,1,1,1,1,1,1,1,0,0},
						{0,1,1,0,1,1,1,0,1,1,0},
						{1,1,1,1,1,1,1,1,1,1,1},
						{1,0,1,1,1,1,1,1,1,0,1},
						{1,0,1,0,0,0,0,0,1,0,1},
						{0,0,0,1,1,0,1,1,0,0,0}
				});

				getSprite()[1] = new Sprite(new int[][] {
						{0,0,1,0,0,0,0,0,1,0,0},
						{1,0,0,1,0,0,0,1,0,0,1},
						{1,0,1,1,1,1,1,1,1,0,1},
						{1,1,1,0,1,1,1,0,1,1,1},
						{1,1,1,1,1,1,1,1,1,1,1},
						{0,1,1,1,1,1,1,1,1,0,1},
						{0,0,1,0,0,0,0,0,1,0,0},
						{0,1,0,0,0,0,0,0,0,1,0}
				});
				
				break;
			case Back:
				width=8;
				height=8;
				y=64;
				x+=2;
				
				getSprite()[0] = new Sprite(new int[][] {
						{0,0,0,1,1,0,0,0},
						{0,0,1,1,1,1,0,0},
						{0,1,1,1,1,1,1,0},
						{1,1,0,1,1,0,1,1},
						{1,1,1,1,1,1,1,1},
						{0,1,0,1,1,0,1,0},
						{1,0,0,0,0,0,0,1},
						{0,1,0,0,0,0,1,0}
				});

				getSprite()[1] = new Sprite(new int[][] {
						{0,0,0,1,1,0,0,0},
						{0,0,1,1,1,1,0,0},
						{0,1,1,1,1,1,1,0},
						{1,1,0,1,1,0,1,1},
						{1,1,1,1,1,1,1,1},
						{0,0,1,0,0,1,0,0},
						{0,1,0,1,1,0,1,0},
						{1,0,1,0,0,1,0,1}
				});
				break;
		}
		
		super.setPosition(new Rectangle(x,y,width,height));
	}
	
	public int getScore()
	{
		switch (_invaderType)
		{
		case Back:
			return 30;
		case Front:
			return 10;
		case Middle:
			return 20;
		}
		return 0;
	}
	public void MoveInvader(Direction direction)
	{
		Rectangle sprite = getPosition();
		
		switch (direction)
		{
			case Down:
				sprite.y+=4;		
				break;
			case Left:			
				sprite.x-=4;
				break;
			case Right:			
				sprite.x+=4;		
				break;
		}
		setPosition(sprite);		
	}
		
	public void Draw(Graphics2D g)
	{		
		switch (_invaderType)
		{
			case Front:
				g.setColor(Color.pink);
				break;
			case Middle:
				g.setColor(Color.cyan);
				break;
			case Back:
				g.setColor(Color.green);
				break;
		}		
		super.Draw(g);
	}
		
	public int compareTo(Invader invader)
	{
		final int BEFORE = -1;
	    final int EQUAL = 0;
	    final int AFTER = 1;
	    
	    if (this == invader)
	    	return EQUAL;
	    
	    if (this.getLocation().x == invader.getLocation().x)
	    {
	    	if (this.getLocation().y > invader.getLocation().y)
	    		return BEFORE;
	    	
	    	if (this.getLocation().y < invader.getLocation().y)
	    		return AFTER;
	    	
	    	if (this.getLocation().y == invader.getLocation().y)
	    		return EQUAL;
	    }
		
	    return EQUAL;
	}
	
	public void tick(Direction direction, int amount)
	{
		
	}
}
