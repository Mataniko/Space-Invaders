package invaders;

import invaders.Enums.Direction;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;


public class Bunker extends GameObject {

	public Bunker(int x, int y, int width, int height)
	{
		super(x,y,width,height);
		getSprite()[0] = new Sprite(new int[][] {
		{0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0},
		{0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0},
		{0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0},
		{0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0},				
		{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
		{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
		{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
		{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
		{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
		{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
		{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
		{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
		{1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1},
		{1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1},
		{1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1},
		{1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1}
		});
	}
	
	@Override
	public void Draw(Graphics2D g) {
		// TODO Auto-generated method stub
		g.setColor(Color.white);
		super.Draw(g);
	}
	
	@Override
	public boolean CheckCollision(Shot shot) {
		
		if (shot == null)
			return false;
				
		Rectangle shotPos = shot.getPosition();
		Rectangle bunkerPos = super.getPosition();
		
		if (!(shotPos.x >= bunkerPos.x && shotPos.x <= bunkerPos.x+bunkerPos.width))
			return false;
				
		int[][] activeSprite = getSprite()[0].getArray();
		
	
		if (shot.getDirection() == Direction.Up)
		{
			for (int y=activeSprite.length-1; y >= 0; y--)
			{
				for (int x=0; x < activeSprite[y].length; x++)
				{												
						if (bunkerPos.x+x==shotPos.x && bunkerPos.y+y==shotPos.y)
						{	
							if (activeSprite[y][x]==1)
							{												
								for (int y2=Math.min(y, 3); y2>=0; y2--)
								{													
									activeSprite[y-y2][x] = 0;
									if (x-1 >= 0)
										activeSprite[y-y2][x-1] = 0;
									if (x+1 < activeSprite[y-y2].length)
										activeSprite[y-y2][x+1] = 0;														
								}
							return true;
							}
						}
				}
			}
		}
		else
		{
			for (int y=0; y < activeSprite.length; y++)
			{
				for (int x=0; x < activeSprite[y].length; x++)
				{												
						if (bunkerPos.x+x==shotPos.x && bunkerPos.y+y==shotPos.y)
						{	
							if (activeSprite[y][x]==1)
							{												
								for (int y2=0; y2<4; y2++)
								{				
									if (y+y2 > activeSprite.length-1)
										continue;
									activeSprite[y+y2][x] = 0;
									if (x-1 >= 0)
										activeSprite[y+y2][x-1] = 0;
									if (x+1 < activeSprite[y+y2].length)
										activeSprite[y+y2][x+1] = 0;														
								}
							return true;
							}
						}
				}
			}
		}

		return false;
	}
}
