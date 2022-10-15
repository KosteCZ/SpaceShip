package cz.koscak.jan.game.spaceship.gui;

import cz.koscak.jan.game.spaceship.model.Bullet;
import cz.koscak.jan.game.spaceship.model.Game;
import cz.koscak.jan.game.spaceship.model.GameStatus;
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

	private GamePanel gamePanel;

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

		gamePanel = new GamePanel(game);
		add(gamePanel);

		pack();

		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width / 2 - getSize().width / 2, 0);

		setVisible(true);

		if (game.isDebugMode()) System.out.println("Frame Size: " + getSize());
	}

	class MyKeyListener extends KeyAdapter {

		SpaceShip spaceShip;
		Game game;
		public MyKeyListener(Game game) {
			this.game = game;
			spaceShip = game.getListOfSpaceShips().get(0);
		}

		public void keyPressed(KeyEvent event) {
			if (event.getKeyChar() == 'a') {
				if (game.isDebugMode()) System.out.println("Check for key characters: " + event.getKeyChar());
				spaceShip.turnLeft();
			}
			if (event.getKeyChar() == 's') {
				if (game.isDebugMode()) System.out.println("Check for key characters: " + event.getKeyChar());
				spaceShip.decreaseForce();
			}
			if (event.getKeyChar() == 'd') {
				if (game.isDebugMode()) System.out.println("Check for key characters: " + event.getKeyChar());
				spaceShip.turnRight();
			}
			if (event.getKeyChar() == 'w') {
				if (game.isDebugMode()) System.out.println("Check for key characters: " + event.getKeyChar());
				spaceShip.increaseForce();
			}
			if (event.getKeyChar() == 'c') {
				if (game.isDebugMode()) System.out.println("Check for key characters: " + event.getKeyChar());
				if (spaceShip.getTimeToReloadBullet() == 0) {
					int x = spaceShip.getIntX()
							+ (gamePanel.getImages().spaceShip1.getWidth(null) / 2)
							+ (int) Math.floor(Math.cos(Math.toRadians(spaceShip.getRotation())) * (gamePanel.getImages().spaceShip1.getWidth(null) / 2 + 2) + 0.5d);
					int y = spaceShip.getIntY()
							+ gamePanel.getImages().spaceShip1.getHeight(null) / 2
							+ (int) Math.floor(Math.sin(Math.toRadians(spaceShip.getRotation())) * (gamePanel.getImages().spaceShip1.getHeight(null) / 2 + 2) + 0.5d);
					game.getListOfBullets().add(new Bullet(x, y, spaceShip.getRotation(), Bullet.TIME_TO_DEAD_MEDIUM));
					spaceShip.setReloadingTimeForBullet();
				}
			}
			if (event.getKeyCode() == KeyEvent.VK_F2) {
				if (game.isDebugMode()) System.out.println("Check for key codes: " + event.getKeyCode() + " (F2)");
				game.newGame();
			}
			if (event.getKeyCode() == KeyEvent.VK_F3) {
				if (game.isDebugMode()) System.out.println("Check for key codes: " + event.getKeyCode() + " (F3)");
				gamePanel.togglePauseAndResume();
			}
			if (event.getKeyCode() == KeyEvent.VK_HOME) {
				if (game.isDebugMode()) System.out.println("Check for key codes: " + event.getKeyCode()+ " (Home)");
			}
		}
	}

}
