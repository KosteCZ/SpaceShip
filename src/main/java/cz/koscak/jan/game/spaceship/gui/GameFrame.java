package cz.koscak.jan.game.spaceship.gui;

import cz.koscak.jan.game.spaceship.model.Game;
import cz.koscak.jan.game.spaceship.model.SpaceShip;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.Serial;

public class GameFrame extends JFrame {

	@Serial
	private static final long serialVersionUID = 1L;
	private static final String TITLE_SPACE_SHIP = "SpaceShip";

	public GameFrame(Game game) {
		
		super(TITLE_SPACE_SHIP);

		setDefaultCloseOperation(EXIT_ON_CLOSE);

		//setSize(825, 825);
		//setPreferredSize(new Dimension(825, 825));
		setResizable(false);

		//setLayout(null);
		//setLocationRelativeTo(null);

		addKeyListener(new MyKeyListener(game));
		setFocusable(true);

		GamePanel gamePanel = new GamePanel(game);
		add(gamePanel);

		pack();

		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width / 2 - getSize().width / 2, 0);

		setVisible(true);

		if (game.isDebugMode()) System.out.println("Frame Size: " + getSize());
	}

	class MyKeyListener extends KeyAdapter {

		SpaceShip spaceShip;
		public MyKeyListener(Game game) {
			spaceShip = game.getListOfSpaceShips().get(0);
		}

		public void keyPressed(KeyEvent event) {
			if (event.getKeyChar() == 'a') {
				System.out.println("Check for key characters: " + event.getKeyChar());
				spaceShip.turnLeft();
			}
			if (event.getKeyChar() == 's') {
				System.out.println("Check for key characters: " + event.getKeyChar());
				spaceShip.decreaseForce();
			}
			if (event.getKeyChar() == 'd') {
				System.out.println("Check for key characters: " + event.getKeyChar());
				spaceShip.turnRight();
			}
			if (event.getKeyChar() == 'w') {
				System.out.println("Check for key characters: " + event.getKeyChar());
				spaceShip.increaseForce();
			}
			if (event.getKeyCode() == KeyEvent.VK_HOME) {
				System.out.println("Check for key codes: " + event.getKeyCode());
			}
		}
	}

}
