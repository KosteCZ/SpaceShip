package cz.koscak.jan.game.spaceship.gui;

import cz.koscak.jan.game.spaceship.model.Game;

import javax.swing.*;
import java.awt.*;
import java.io.Serial;

public class GameFrame extends JFrame {

	@Serial
	private static final long serialVersionUID = 1L;
	private static final String TITLE_SPACE_SHIP = "SpaceShip";

	public GameFrame(Game game) {
		
		super(TITLE_SPACE_SHIP);

		//setSize(825, 825);
		//setPreferredSize(new Dimension(825, 825));
		setResizable(false);

		//setLayout(null);
		//setLocationRelativeTo(null);

		GamePanel gamePanel = new GamePanel(game);
		add(gamePanel);

		pack();

		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width / 2 - getSize().width / 2, 0);

		setVisible(true);

		if (game.isDebugMode()) System.out.println("Frame Size: " + getSize());
	}

}
