package invaders;

import java.awt.*;
import java.awt.image.*;
import javax.swing.*;

public class Main extends Canvas implements Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6759551502528417185L;
	private static final int WIDTH = 224;
	private static final int HEIGHT = 260;
	private static final int SCALE = 3;
	
	private boolean running;
	private Thread thread;
	private BufferedImage bufferedImage;
	private Game game;
	private Screen screen;
	private Input inputHandler;	
	private boolean hadFocus = false;
	
	/**
	 * @param args
	 */
	public Main()
	{
		Dimension size = new Dimension(WIDTH * SCALE, HEIGHT * SCALE);
		setSize(size);
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);

		game = new Game();
		screen = new Screen(WIDTH, HEIGHT);
		Sound.LoadResources();

		bufferedImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		
		inputHandler = new Input();

		addKeyListener(inputHandler);
		addFocusListener(inputHandler);
	}
	
	public synchronized void start() {
		if (running) return;
		running = true;
		thread = new Thread(this);
		thread.start();
	}

	public synchronized void stop() {
		if (!running) return;
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
		
	@Override
	public void run() {
		int frames = 0;

		double unprocessedSeconds = 0;
		long lastTime = System.nanoTime();
		double secondsPerTick = 1 / 60.0;
		int tickCount = 0;

		requestFocus();

		while (running) {
			

			long now = System.nanoTime();
			long passedTime = now - lastTime;
			lastTime = now;
			if (passedTime < 0) passedTime = 0;
			if (passedTime > 100000000) passedTime = 100000000;

			unprocessedSeconds += passedTime / 1000000000.0;

			boolean ticked = false;
			while (unprocessedSeconds > secondsPerTick) {
				tick();
				unprocessedSeconds -= secondsPerTick;
				ticked = true;

				tickCount++;
				if (tickCount % 60 == 0) {
					System.out.println(frames + " fps");
					lastTime += 1000;
					frames = 0;
				}
			}

			if (ticked) {
				render(game.getStarted());
				frames++;
			} else {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		}
	}

	private void tick() {
						
		if (hasFocus()) {
			game.tick(inputHandler.keys);
		}
	}

	private void render(boolean started) {
		if (hadFocus != hasFocus()) {
			hadFocus = !hadFocus;			
		}
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		
		if (started)			
			bufferedImage = screen.render(game, hasFocus());		
		else
			bufferedImage = screen.renderMenu(game, hasFocus());

		Graphics g = bs.getDrawGraphics();
		g.fillRect(0, 0, getWidth(), getHeight());
		g.drawImage(bufferedImage, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
		g.dispose();
		bs.show();
	}

	public static void main(String[] args) {		
		Main game = new Main();

		JFrame frame = new JFrame("Space Invaders");

		JPanel panel = new JPanel(new BorderLayout());
		panel.add(game, BorderLayout.CENTER);

		frame.setContentPane(panel);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		game.start();
	}
	
}
