package invaders;

import java.awt.Graphics2D;
import java.awt.Rectangle;

public class GameObject {
		
	private Rectangle _position;
	private boolean _isHit=false;	
	private Sprite[] sprite=new Sprite[2];
	
	public boolean getIsHit() { return _isHit; }
	private boolean _animFrame;

	public void changeAnimFrame() { _animFrame = !_animFrame; }
	
	public Sprite[] getSprite() { return sprite; }
	public Rectangle getPosition() { return 
			//new Rectangle(_position);
			_position;
			}
	public void setPosition(Rectangle sprite) { _position = sprite; }
	public boolean getExpired()
	{		
		return false;
	}
	
	
	public GameObject() { }
	
	public GameObject(int x, int y, int width, int height)
	{		
		_position = new Rectangle(x,y,width,height);
	}
	
	public GameObject(Rectangle object)
	{		
		_position = object;
	}
	
	public boolean CheckCollision(Shot shot)
	{		
		if (shot == null)
			return false;
		
		if (_position.contains(shot.getPosition())) 
			_isHit=true;
			
		return _isHit;
	}
	public void Draw(Graphics2D g)
	{
		int[][] activeSprite = sprite[0].getArray();
		if (_animFrame)
			activeSprite = sprite[1].getArray();
		
		for (int i=0; i < activeSprite.length; i++)
		{
			for (int j=0; j < activeSprite[i].length; j++)
			{
				if (activeSprite[i][j]==1)
					g.fillRect(getPosition().x+j, getPosition().y+i, 1,1);
			}
		}
		
	}
	
	
}
