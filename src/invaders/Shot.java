package invaders;

import invaders.Enums.Direction;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Shot extends GameObject implements Runnable {
	
	private boolean _expired;
	private int _speed;
	public boolean getExpired() { return _expired; }
	
	private Direction _direction;
	
	public Direction getDirection() { return _direction; } 
	
	public Shot(GameObject gameObject, Direction direction, int speed)
	{
		_direction = direction;		
		_speed=speed;
		Rectangle sprite = gameObject.getPosition();		
		switch (direction)
		{
		case Up:			
			setPosition(new Rectangle(sprite.x+sprite.width/2,sprite.y+8,1,4));
			getSprite()[0] = new Sprite(new int[][] { 
					{1}, 
					{1},
					{1},
					{1}		
			});
			break;
		case Down:
			setPosition(new Rectangle(sprite.x+sprite.width/2,sprite.y,3,7));
			getSprite()[0] = new Sprite(new int[][] { 
					{1,0,0}, 
					{0,1,0},
					{0,0,1},
					{0,1,0},
					{1,0,0}, 
					{0,1,0},
					{0,0,1}
			});
			getSprite()[1] = new Sprite(new int[][] { 
					{0,0,1}, 
					{0,1,0},
					{1,0,0},
					{0,1,0},
					{0,0,1}, 
					{0,1,0},
					{1,0,0}
			});
			break;
		}
				
		
	}
		
	public void Draw(Graphics2D g) {
		// TODO Auto-generated method stub
		g.setColor(Color.white);
		super.Draw(g);
	}

	@Override
	public void run() {
		if (!_expired)
		{
			try {			
				Thread.sleep(_speed);
				if (Game.PAUSE)
					return;
				Rectangle sprite = getPosition();
				// TODO Auto-generated method stub
				switch (_direction)
				{
				case Up:
					sprite.y-=4;	
					break;
				case Down:
					sprite.y+=1;
					if (sprite.y % 3 == 0) 
						changeAnimFrame();
					break;
				}
				
				if (sprite.y < 20 || sprite.y > 230)
					_expired=true;
				
				setPosition(sprite);		
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}
	
	
	
}
