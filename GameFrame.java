package SnakeGame;

import javax.swing.JFrame;

public class GameFrame extends JFrame{
//defines a GameFrame class that extends JFrame to create the game window. 
	private static final long serialVersionUID = 1L;

	GameFrame() {
//The GameFrame class is defined, and it has a default constructor.


		GamePanel panel = new GamePanel();
		this.add(panel);
//The GamePanel object is created to represent the game panel.
//The GamePanel object is added to the GameFrame using the add() method.

		this.setTitle("Bill the snake");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//The title of the game window is set to "snake".
//The default close operation is set to exit the application when the window is closed

		this.setResizable(false);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}
}
