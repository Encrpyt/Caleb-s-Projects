package SnakeGame;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

import javax.swing.JPanel;
//Defines a GamePanel class that extends JPanel and implements 
//ActionListener to handle game logic and drawing. 
public class GamePanel extends JPanel implements ActionListener{

// Serial version UID for serialization
private static final long serialVersionUID = 1L;

// Constants for defining the dimensions of the game panel
static final int WIDTH = 500;
static final int HEIGHT = 500;
static final int UNIT_SIZE = 20;
static final int NUMBER_OF_UNITS = (WIDTH * HEIGHT) / (UNIT_SIZE * UNIT_SIZE);

// Arrays to hold the x and y coordinates for the body parts of the snake
final int x[] = new int[NUMBER_OF_UNITS];
final int y[] = new int[NUMBER_OF_UNITS];

// Variables to store the initial length of the snake, 
//food eaten, food position, direction, running status, and random object
int length = 5;
int foodEaten;
int foodX;
int foodY;
char direction = 'D';
boolean running = false;
Random random;
Timer timer;

// Constructor for the GamePanel class
GamePanel() {
	random = new Random();
	
	// Setting the preferred size and background color of the panel
	this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
	this.setBackground(Color.DARK_GRAY);
	
	// Setting the panel as focusable and adding a key listener
	this.setFocusable(true);
	this.addKeyListener(new MyKeyAdapter());
	
	// Starting the game
	play();
}	

// Method to start the game
public void play() {
	// Adding food and setting the running status to true
	addFood();
	running = true;
	
	// Creating a timer to control the game speed
	timer = new Timer(80, this);
	timer.start();	
}

// Overriding the paintComponent method to draw graphics on the panel
@Override
public void paintComponent(Graphics graphics) {
	super.paintComponent(graphics);
	draw(graphics);
}

// Method to move the snake
public void move() {
	// Shifting the snake's body parts to create movement
	for (int i = length; i > 0; i--) {
		x[i] = x[i-1];
		y[i] = y[i-1];
	}
	
	// Moving the head of the snake based on the current direction
	if (direction == 'L') {
		x[0] = x[0] - UNIT_SIZE;
	} else if (direction == 'R') {
		x[0] = x[0] + UNIT_SIZE;
	} else if (direction == 'U') {
		y[0] = y[0] - UNIT_SIZE;
	} else {
		y[0] = y[0] + UNIT_SIZE;
	}	
}

// Method to check if the snake has eaten the food
public void checkFood() {
	if(x[0] == foodX && y[0] == foodY) {
		length++;
		foodEaten++;
		addFood();
	}
}

// Method to draw graphics on the panel
public void draw(Graphics graphics) {
	if (running) {
		// Drawing the food
		graphics.setColor(new Color(210, 115, 90));
		graphics.fillOval(foodX, foodY, UNIT_SIZE, UNIT_SIZE);
		
		// Drawing the snake's head
		graphics.setColor(Color.white);
		graphics.fillRect(x[0], y[0], UNIT_SIZE, UNIT_SIZE);
		
		// Drawing the snake's body
		for (int i = 1; i < length; i++) {
			graphics.setColor(new Color(40, 200, 150));
			graphics.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
		}
		
		// Drawing the score
		graphics.setColor(Color.white);
		graphics.setFont(new Font("Sans serif", Font.ROMAN_BASELINE, 25));
		FontMetrics metrics = getFontMetrics(graphics.getFont());
		graphics.drawString("Score: " + foodEaten, (WIDTH - metrics.stringWidth("Score: " + foodEaten)) / 2, graphics.getFont().getSize());
	
	} else {
		// Displaying game over message
		gameOver(graphics);
	}
}

// Method to add food at a random position
public void addFood() {
	foodX = random.nextInt((int)(WIDTH / UNIT_SIZE))*UNIT_SIZE;
	foodY = random.nextInt((int)(HEIGHT / UNIT_SIZE))*UNIT_SIZE;
}

// Method to check if the snake has hit itself or the walls
public void checkHit() {
	// Checking if the head of the snake has collided with its body
	for (int i = length; i > 0; i--) {
		if (x[0] == x[i] && y[0] == y[i]) {
			running = false;
		}
	}
	
	// Checking if the head of the snake has collided with the walls
	if (x[0] < 0 || x[0] > WIDTH || y[0] < 0 || y[0] > HEIGHT) {
		running = false;
	}
	
	// Stopping the timer if the game is over
	if(!running) {
		timer.stop();
	}
}

// Method to display the game over message
public void gameOver(Graphics graphics) {
	graphics.setColor(Color.red);
	graphics.setFont(new Font("Sans serif", Font.ROMAN_BASELINE, 50));
	FontMetrics metrics = getFontMetrics(graphics.getFont());
	graphics.drawString("Game Over", (WIDTH - metrics.stringWidth("Game Over")) / 2, HEIGHT / 2);
	
	graphics.setColor(Color.white);
	graphics.setFont(new Font("Sans serif", Font.ROMAN_BASELINE, 25));
	metrics = getFontMetrics(graphics.getFont());
	graphics.drawString("Score: " + foodEaten, (WIDTH - metrics.stringWidth("Score: " + foodEaten)) / 2, graphics.getFont().getSize());

}

// Overriding the actionPerformed method from ActionListener
@Override
public void actionPerformed(ActionEvent arg0) {
	if (running) {
		move();
		checkFood();
		checkHit();
	}
	repaint();
}

// Inner class for handling key events
//The MyKeyAdapter class is an inner class that handles key 
//events to change the direction of the snake.
public class MyKeyAdapter extends KeyAdapter {
	@Override
	public void keyPressed(KeyEvent e) {
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

