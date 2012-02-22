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
		g.setColor(Color.white);
		super.Draw(g);
	}
	
	@Override
	public boolean CheckCollision(Shot shot) {
		
		if (shot == null)
			return false;
				
		Rectangle shotPos = shot.getPosition();
		Rectangle bunkerPos = super.getPosition();				
				
		int[][] activeSprite = getSprite()[0].getArray();
		
	
		if (shot.getDirection() == Direction.Up)
		{		
			if (!(shotPos.x >= bunkerPos.x && shotPos.x < bunkerPos.x+bunkerPos.width))
				return false;
			
			//From the bottom, see if there's an active pixel in the bunker
			for (int y=bunkerPos.height-1; y >= 0; y--)
			{													
				if (activeSprite[y][shotPos.x-bunkerPos.x]==1) //found an active pixel
				{
					if (!bunkerPos.intersects(shotPos))
						return false;					
					//delete the next 4 pixels going up starting from the active one
					for (int y2=3; y2>=0; y2--)
					{		
							activeSprite[Math.max(0,y-y2)][Math.max(shotPos.x-bunkerPos.x-1,0)]=0;
							activeSprite[Math.max(0,y-y2)][shotPos.x-bunkerPos.x]=0;
							activeSprite[Math.max(0,y-y2)][Math.min(shotPos.x-bunkerPos.x+1,bunkerPos.width-1)]=0;
					}
					return true;
				}
				
			}
					
		}
		else
		{
			if (!(shotPos.x >= bunkerPos.x && shotPos.x < bunkerPos.x+bunkerPos.width))
				return false;
			
			//From the top, see if there's an active pixel in the bunker
			for (int y=0; y < bunkerPos.height; y++)
			{													
				if (activeSprite[y][shotPos.x-bunkerPos.x]==1) //found an active pixel
				{
					if (!bunkerPos.contains(shotPos.x,shotPos.y))
						return false;					
					//delete the next 4 pixels going down starting from the active one
					for (int y2=0; y2<4; y2++)
					{		
							activeSprite[Math.max(0,y+y2)][Math.max(shotPos.x-bunkerPos.x-1,0)]=0;
							activeSprite[Math.max(0,y+y2)][shotPos.x-bunkerPos.x]=0;
							activeSprite[Math.max(0,y+y2)][Math.min(shotPos.x-bunkerPos.x+1,bunkerPos.width-1)]=0;
					}
					return true;
				}
				
			}
		}

		return false;
	}
}
