package invaders;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Timer;
import java.util.TimerTask;

public class Explosion extends GameObject {

	private boolean _expired;
	public boolean getExpired() { return _expired; }
	
	public Explosion(Rectangle location)
	{
		this.setPosition(location);
		Timer timer = new Timer();
		timer.schedule(timerTask,300,5);
		getSprite()[0] = new Sprite(new int[][] { 
				{0,0,0,0,1,0,0,0,1,0,0,0,0}, 
				{0,1,0,0,0,1,0,1,0,0,0,1,0},
				{0,0,1,0,0,0,0,0,0,0,1,0,0},
				{0,0,0,1,0,0,0,0,0,1,0,0,0},
				{1,1,0,0,0,0,0,0,0,0,0,1,1}, 
				{0,0,1,0,0,0,0,0,0,0,1,0,0},
				{0,1,0,0,0,1,0,1,0,0,0,1,0},
				{0,0,0,0,1,0,0,0,1,0,0,0,0}
		});
		
	}
	
	TimerTask timerTask = new TimerTask() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub		
			if (Game.getPause())
				return;
				_expired=true;
		}
	};
	
	public void Draw(Graphics2D g)
	{
		g.setColor(Color.red);	
		super.Draw(g);
	}
	
}
