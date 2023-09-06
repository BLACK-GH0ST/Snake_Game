
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;


import javax.swing.JPanel;

	public abstract class GamePanel extends JPanel implements ActionListener{
		private static final long serialVersionUID = 1l;
		
		static final int WIDTH = 1280;
		static final int HEIGHT = 720;
		static final int UNIT_SIZE = 20;
		static final int NUMBER_OF_UNITS = (WIDTH * HEIGHT ) / (UNIT_SIZE * UNIT_SIZE);
		
		//a and y coordinate for the snake body parts.
		final int x[] = new int[NUMBER_OF_UNITS];
		final int y[] = new int[NUMBER_OF_UNITS];
		// initials length of the snake.
		
		int length = 5;
		int foodEaten;
		int foodX;
		int foodY;
		char direction = 'D';
		boolean running = false;
		Random random;
		Timer timertween;
		
		GamePanel(){
			random = new Random();
			this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
			this.setBackground(Color.DARK_GRAY);
			this.setFocusable(true);
			this.addKeyListener(new MyKeyAdapter());		
			play();
		}
		
		public void play() { 
			addFood();
			running = true;
			
			timertween = new Timer(80, this);
			timertween.start();
		}
		
		@Override
		public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		draw(graphics);
		}
		
		public void move() {
			for (int i = length; i > 0; i--) {
				// shift the snake one unit to the desired direction to create a move
				x[i] = x[i-1];
				y[i] = y[i-1];
			}
			//Initialising the move of the snake.
			if (direction == 'L') {
				x[0] = x[0] - UNIT_SIZE;
			} else if (direction == 'R') {
			x[0] = x[0] + UNIT_SIZE;
			} else if (direction == 'U') {
		          y[0] = y[0] - UNIT_SIZE;
		    } else {
		    	y[0] = y[0] - UNIT_SIZE;
		    }
		}
		// the interaction between the snake and its food...
		public void checkFood() {
			if(x[0] == foodX &&  y[0] == foodY) {
				length++;
				foodEaten++;
				addFood();
			}
		}
		
		public void  draw(Graphics graphics) {
			
			if (running) {
				graphics.setColor(new Color(210, 115, 90)); 
				graphics.fillOval(foodX, foodY, UNIT_SIZE, UNIT_SIZE);
				
				graphics.setColor(Color.white);
				graphics.setFont(new Font("San serif", Font.ROMAN_BASELINE, 25));
				FontMetrics metrics = getFontMetrics(graphics.getFont());
				graphics.drawString("Score: ",+ foodEaten, (WIDTH - metrics.stringWidth("Score: " + foodEaten)) / 2, graphics.getFont().getSize());
			}else {
				gameOver(graphics);
			}
		}
		// coordinate the x, y of the food on update.
		public void addFood() {
			foodX = random.nextInt((int) (WIDTH / UNIT_SIZE))*UNIT_SIZE;
			foodY = random.nextInt((int) (WIDTH / UNIT_SIZE))*UNIT_SIZE;
		}
		
		public void checkHit() {
			//check  if head run its body
			for (int i = length; i > 0; i--) {
				if (x[0] == x[i] && y[0] == y[i]) {
					running = false;
				}
			}
			//check if head hits walls
			if (x[0] < 0 || x[0] > WIDTH || y[0] <0 || y[0] > HEIGHT) {
				running = false;
			}
			if(!running) {
				timertween.stop();
			}
		}
		// The Game Over display when the object (snake) hit the wall and its self.  
		public void gameOver(Graphics graphics) {
			graphics.setColor(Color.red);
			graphics.setFont(new Font("Sans serif", Font.ROMAN_BASELINE, 50));
			FontMetrics metrics = getFontMetrics(graphics.getFont());
			graphics.drawString("Game Over", (WIDTH - metrics.stringWidth("Game Over")) / 2, HEIGHT / 2);
			
		graphics.setColor(Color.white);
		graphics.setFont(new Font("Sans serif" , Font.ROMAN_BASELINE, 25));
		metrics = getFontMetrics(graphics.getFont());
		graphics.drawString("Score: ", foodEaten, (WIDTH - metrics.stringWidth("Score: " + foodEaten)) / 2, graphics.getFont().getSize());
		}
		
			@Override
			public void actionPerformed(ActionEvent args0) {
				if (running) {
				move();
				checkFood();
				checkHit();
			}
			repaint();
		}
		
		public class MyKeyAdapter extends KeyAdapter {
			
		@Override
			public void KeyPressed (KeyEvent e) {
				switch(e.getKeyCode()) {
				case KeyEvent.VK_LEFT:
					if (direction != 'R') {
						direction = 'L';
					}
					break;
				case KeyEvent.VK_RIGHT:
					if (direction != 'L') {
						direction = 'R';
					}
					break;
				case KeyEvent.VK_UP:
					if (direction != 'D') {
						direction = 'U';
					}
					break;
				case KeyEvent.VK_DOWN:
					if (direction != 'U') {
						direction = 'D';
					}
					break;
				}
			}
		}
	}
