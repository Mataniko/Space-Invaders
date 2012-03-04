package invaders;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;


public class Screen  {

	private final static Font FONT = new Font("Arial",Font.PLAIN, 12);
	private BufferedImage bufferedImage;
	private Game game;
	
	public Screen(int width, int height)
	{
		bufferedImage = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
	}
	
	public BufferedImage render(Game game, boolean hasFocus)
	{
		this.game=game;
		Graphics2D g2 = (Graphics2D) bufferedImage.getGraphics();
		g2.setColor(Color.black);
		g2.fillRect(0, 0, bufferedImage.getWidth(),bufferedImage.getHeight());
		//Paint the overlay
		DrawOverlay(g2);
				
		for (GameObject gameObject : game.getGameObjects())
		{
			if (gameObject != null && !gameObject.getExpired())
				gameObject.Draw(g2);
		}
		
		return bufferedImage;
		
	}	
	
	
	private void DrawOverlay(Graphics2D g)
	{		
		g.setColor(Color.cyan);
		g.setFont(FONT);	
		g.drawString("SCORE",8,13);
		g.drawString(String.valueOf(game.getScore()),8,24);		
		g.drawString(String.valueOf(game.getLives()), 4, 260-10);
		g.drawString("CS565", 180, 260-10);
		g.drawString("BAREKET", 165, 13);
		
		g.setColor(Color.green);		
		g.drawLine(0, 260-21, 224, 260-21);
		
		g.drawString("HI-SCORE", 80, 13);
		g.drawString(String.valueOf(game.getHightScore()),90,24);
		
		for (int i=1; i < game.getLives(); i++)
		{
			Ship ship = new Ship(16*i+4, 260-20, 13, 8);
			ship.Draw(g);
		}
	
	}
	public BufferedImage renderMenu(Game game, boolean hasFocus)
	{
			Game.PAUSE=true;
			this.game=game;
			Graphics2D g2 = (Graphics2D) bufferedImage.getGraphics();
			g2.setColor(Color.black);
			g2.fillRect(0, 0, bufferedImage.getWidth(),bufferedImage.getHeight());
			
			//Paint the overlay
			DrawOverlay(g2);
			
			g2.drawString("PRESS RETURN TO BEGIN", 40, 100);
			
			return bufferedImage;		
		
	}
			
}
