package invaders;


import invaders.Enums.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



public class Game {
	
	
	//Game Objects
	private Ship _playerShip;
	private List<Invader> _invaders;
	private List<Explosion> _explosions;
	private List<Bunker> _bunkers;
	private Shot _playerShot = null;
	private Shot _enemyShot = null;
	private Mothership _motherShip = null;
	public boolean _started = false;
	
	
	public boolean getStarted() { return _started; }	
	public int getLives() { return _lives;}
	public int getScore() { return _score;}
	public int getHightScore() { return _highScore;}
	public Ship getPlayerShip() {return _playerShip;}	
	public List<GameObject> getGameObjects() {
		List<GameObject> gameObjects = new ArrayList<GameObject>(); 
		gameObjects.addAll(_bunkers);
		gameObjects.addAll(_explosions);
		gameObjects.addAll(_invaders);
		gameObjects.add(_playerShip);
		gameObjects.add(_enemyShot);
		gameObjects.add(_playerShot);
		gameObjects.add(_motherShip);
		return gameObjects;
	}
	
	private Direction _direction = Direction.Right;	
	private int _lives = 3;
	private int moveSound=4;
	private boolean isInitalized = false;
	public static boolean PAUSE;
	private int _score = 0;	

	
	
	private int currentTick=0;
	private int _highScore=0;
	private boolean _start=false;
	private long _lastShot= System.nanoTime();
	private long _lastMotherShip=System.nanoTime();
	private long lastFrameNs=System.nanoTime();
	
	private static int SCALE = 1;
	public static int getScale() { return SCALE; }
		

	public Game()
	{				
		isInitalized = false;		
		_highScore = Highscore.ReadHighscore();			
		isInitalized=initialize(true);				
	}
	
	private boolean initialize(boolean resetScoreAndLives)
	{		
			
		_started=false;
		moveSound=4;
		if (resetScoreAndLives)
		{
			
			Highscore.WriteHighscore(_score);
			_score = 0;
			_lives = 3;			
		}
		_lastShot = System.nanoTime();
		_playerShip = new Ship(18,260-44,13,8);
		
		_explosions = new ArrayList<Explosion>();
		
		//Initialize invaders		
		CreateInvaders();
		CreateBunkers();
						
		
		return true;
	}
	
	
	private void MoveInvaders()
	{
		for (Invader invader : _invaders)
			{
				invader.MoveInvader(_direction);
				invader.changeAnimFrame();
			}
			Sound.PlaySound(moveSound++);
			if (moveSound==8)
				moveSound=4;	
	}

	
	public void tick(boolean[] keys, int tickCount, boolean hasFocus)
	{
		currentTick=tickCount;
		PAUSE = !hasFocus;
		
		
		
		long start = System.nanoTime();
		long now = start;
				
		boolean left = keys[KeyEvent.VK_A] || keys[KeyEvent.VK_LEFT] || keys[KeyEvent.VK_NUMPAD4];
		boolean right = keys[KeyEvent.VK_D] || keys[KeyEvent.VK_RIGHT] || keys[KeyEvent.VK_NUMPAD6];
		boolean shoot  = keys[KeyEvent.VK_SPACE];
		
		if (keys[KeyEvent.VK_W])
			_lives = 0;
		
		_started = keys[KeyEvent.VK_ENTER];			
		
		if (PAUSE || ! getStarted())
			return;
		
		if (_playerShip.tick(left, right, shoot))
		{
			if (_playerShot == null) {
				_playerShot = new Shot(_playerShip,Direction.Up, 0);				
				Sound.PlaySound(1);
			}
		}			
		
		if (_playerShot != null)
		{
			_playerShot.run();
		}
		
		if (_enemyShot != null)
		{
			_enemyShot.run();
		}
		
		_highScore = Math.max(_score, _highScore);					
		
		if (_invaders.size() == 0)		
			initialize(false);					
		else if (_lives == 0)
		{
			initialize(true);
			keys[KeyEvent.VK_ENTER] = false;
		}
		
		
				
			CheckPlayerHit();
			CheckForInvaderHits();
			CheckBunkers();
			SpawnMothership();
			InvaderShoot();			
			CheckShots();
			_direction = GetInvaderDirection();		
			if (currentTick % (4*(Math.ceil(_invaders.size()/3)+1)) == 0)
				MoveInvaders();
		
		
	}
	
	private void SpawnMothership()
	{
		if (_motherShip != null && _motherShip.getExpired())
			_motherShip = null;
			
		if ((System.nanoTime() - _lastMotherShip > 100000000L*160) && _motherShip == null)
		{									
				_motherShip = new Mothership(224,20, 16, 7);
				_lastMotherShip = System.nanoTime();
				Thread msThread = new Thread(_motherShip);
				msThread.start();	
				Sound.PlaySound(8);
		}
	}
	
	private Direction GetInvaderDirection()
	{	
		for (Invader invader : _invaders)
		{
			Rectangle invaderPos = invader.getPosition();
			if (_direction == Direction.Left && invaderPos.x <= 14)
			{
				MoveInvadersDown();
				return Direction.Right;
			}
			if (_direction == Direction.Right && invaderPos.x+invaderPos.width >= 214)
			{
				MoveInvadersDown();
				return Direction.Left;
			}
		}		
		return _direction;
	}
	private void MoveInvadersDown()
	{
		for (Invader invader : _invaders)
		{
			invader.MoveInvader(Direction.Down);
		}		
	}	
	private void InvaderShoot()
	{
		//Sort the collection to have the bottom most invaders at the beginning of the list
		Collections.sort(_invaders);		
		//Make a list of just the bottom most invaders so we can decide if we want to shoot or not.
		List<Invader> bottomInvaders = new ArrayList<Invader>();
		
		Invader prevInvader=null;
		for (Invader invader : _invaders)
		{			
			if (prevInvader==null)
			{
				bottomInvaders.add(invader);
				prevInvader=invader;
				continue;				
			}
					
			if (invader.getLocation().x == prevInvader.getLocation().x)
				continue;
			
			bottomInvaders.add(invader);
		
			prevInvader=invader;
		}	
		
		//find an invader in line with the player
		for (Invader invader : bottomInvaders)
		{		
			int dist = Math.abs(invader.getPosition().x - _playerShip.getPosition().x);
			
			if (dist <= 15 && _enemyShot == null && (System.nanoTime() - _lastShot > 100000000L*30) )
			{									
					_enemyShot = new Shot(invader,Direction.Down,0);		
					_lastShot = System.nanoTime();
					break;
			}
		}
	}
	
	private void CheckBunkers()
	{
		
		if (_playerShot == null && _enemyShot == null)
			return;
		
		if (_playerShot != null)
		{
			for (Bunker bunker : _bunkers)
			{
				if (bunker.CheckCollision(_playerShot))
				{
					_playerShot=null;
				}
			}
		}
		
		if (_enemyShot != null)
		{
			for (Bunker bunker : _bunkers)
			{
				if (bunker.CheckCollision(_enemyShot))
				{
					_enemyShot=null;
				}
			}
		}
	}
	
	private void CheckForInvaderHits() {
		Invader hitInvader=null;
		boolean invadersReachedBottom=false;
		for (Invader invader : _invaders)
		{													
				if (_playerShot != null && invader.CheckCollision(_playerShot))
				{
					hitInvader=invader;
					_playerShot=null;
				}
				
				if (invader.getPosition().intersects(_playerShip.getPosition()) || (invader.getPosition().y >= 260-21))				
					_lives=0;		
				
				for (Bunker bunker : _bunkers)
				{									
					if (bunker.getPosition().intersects(invader.getPosition()))
					{
						Rectangle invaderPos = invader.getPosition();
						Rectangle bunkerPos = bunker.getPosition();
						int[][] activeSprite = bunker.getSprite()[0].getArray();
						for (int y=invaderPos.y; y < invaderPos.y+invaderPos.height; y++)
						{
							for (int x=invaderPos.x; x < invaderPos.x+invaderPos.width; x++)
							{
								if (bunker.getPosition().contains(x, y))
								{
									activeSprite[y-bunkerPos.y][x-bunkerPos.x]=0;									
								}
							}
						}
						
					}
				}
				
				if (_motherShip != null && _playerShot != null)
				{
					if (_motherShip.getPosition().intersects(_playerShot.getPosition()))
					{
						_explosions.add(new Explosion(_motherShip.getPosition()));
						_score+=_motherShip.getScore();
						Sound.PlaySound(3);
						_motherShip=null;
					}
				}
		}
		
		if (hitInvader != null)
		{					
			_explosions.add(new Explosion(hitInvader.getPosition()));
			_score+=hitInvader.getScore();
			Sound.PlaySound(3);
			_invaders.remove(hitInvader);			
		}
		
		if (invadersReachedBottom)
		{
			for (Invader invader : _invaders)
			{													
				Rectangle sprite = invader.getPosition();
				sprite.y-=100;
				invader.setPosition(sprite);
			}
		}
		
	}	
	private void CheckPlayerHit()
	{
		if (_playerShip == null || _enemyShot == null)
			return;
		
		if (_playerShip.CheckCollision(_enemyShot)) //player hit
		{
			HandlePlayerDeath();
			Sound.PlaySound(2);			
		}
				
	}
	private void HandlePlayerDeath() {
		_explosions.add(new Explosion(_playerShip.getPosition()));
		_enemyShot = null;
		_lives--;		
		_playerShip = new Ship(18,260-44,13,8);		
	}
	private void CreateInvaders() {
		_invaders = new ArrayList<Invader>();
		for (int i=1; i <= 11; i++)
		{
			for (int j=1; j <= 5; j++)
			{
				InvaderType invaderType;
				if (j >= 4)
					invaderType = InvaderType.Front;
				else if (j != 1)
					invaderType = InvaderType.Middle;
				else
					invaderType = InvaderType.Back;
				
				_invaders.add(new Invader(i,j,invaderType));
			}
		}
	}
	
	private void CreateBunkers()
	{
		_bunkers = new ArrayList<Bunker>();
		for (int i=0; i < 4; i++)
		{
			_bunkers.add(new Bunker(32+(22+23)*i,192,22,16));
		}
	}
		
	private void CheckShots()
	{
		if (_playerShot != null)
		{			
			if (_playerShot.getExpired())
				_playerShot=null;
		}
		
		if (_enemyShot != null)
		{		
			if (_enemyShot.getExpired())
				_enemyShot=null;
		}
		
		if (_playerShot != null && _enemyShot != null)
		{
			if (_enemyShot.CheckCollision(_playerShot))
			{
				_enemyShot=null;
				_playerShot=null;
			}
		}
	}
}
