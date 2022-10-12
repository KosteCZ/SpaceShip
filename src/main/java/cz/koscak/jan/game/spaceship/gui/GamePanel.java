package cz.koscak.jan.game.spaceship.gui;

import cz.koscak.jan.game.spaceship.model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.AdjustmentListener;
import java.io.Serial;

public class GamePanel extends JPanel {

    @Serial
    private static final long serialVersionUID = 1L;
    private static final int BUTTON_NEW_GAME_POSITION_X = 0;
    private static final int BUTTON_PAUSE_POSITION_X = 100;
    private static final int STRING_SPEED_POSITION_X = 200;
    private static final int SCROLL_BAR_SPEED_OF_GAME_POSITION_X = 270;
    private static final int STRING_SCROLL_BAR_SPEED_OF_GAME_POSITION_X = 338;
    private static final int STRING_TIME_POSITION_X = 600;

    private final Game game;
	private final Images images;

    private final JScrollBar scrollBarSpeedOfGame = new JScrollBar();
    private JButton buttonNewGame;
    private JButton buttonPause;

    //	private static final int HEIGHT = 800;
    //	private static final int WIDTH = 800;

	GamePanel(Game game) {
		this.game = game;
		//setPreferredSize(new Dimension(WIDTH, HEIGHT));
		//setBackground(Color.BLACK);

		//setPreferredSize(new Dimension(825, 825));
		setPreferredSize(new Dimension(800, 828));
		//setSize(790, 790);

		setDoubleBuffered(true);
		//setFocusable(true);
		setLayout(null);

		//System.out.println("Panel Size: " + getSize());

        addNewGameButton();
		addPauseButton();
        addSpeedOfGameScrollBar();

        /*JLabel label = new JLabel("Username");
        label.setToolTipText("Enter your username");
        label.setBounds(700, 1, 90, 28);
        add(label);*/

        images = new Images();
	}

    private void addSpeedOfGameScrollBar() {
        scrollBarSpeedOfGame.setBounds(SCROLL_BAR_SPEED_OF_GAME_POSITION_X, 1, 90, 28);
        scrollBarSpeedOfGame.setOrientation(JScrollBar.HORIZONTAL);
        scrollBarSpeedOfGame.setMinimum(1);
        scrollBarSpeedOfGame.setMaximum(7);
        scrollBarSpeedOfGame.setVisibleAmount(2);
        scrollBarSpeedOfGame.setValue(game.getSpeed());
        scrollBarSpeedOfGame.setUnitIncrement(1);
        scrollBarSpeedOfGame.setBlockIncrement(1);
        scrollBarSpeedOfGame.setEnabled(true);

        AdjustmentListener listener = event -> {
            Adjustable source = event.getAdjustable();
            if (event.getValueIsAdjusting()) {
                game.setSpeed(source.getValue());
                if (game.isDebugMode()) System.out.println("New scrollbar speed value: " + source.getValue());
                return;
            }
            game.setSpeed(source.getValue());
            if (game.isDebugMode()) System.out.println("New scrollbar speed value: " + source.getValue());
        };

        scrollBarSpeedOfGame.addAdjustmentListener(listener);
        add(scrollBarSpeedOfGame);
    }

    private void addNewGameButton() {
        buttonNewGame = new JButton("New game");
        buttonNewGame.setBounds(BUTTON_NEW_GAME_POSITION_X, 1, 100, 28);

        buttonNewGame.addActionListener(event -> {
            buttonPause.setEnabled(true);
            game.newGame();
        });

        add(buttonNewGame);
    }
    private void addPauseButton() {
        buttonPause = new JButton("Resume");
        buttonPause.setBounds(BUTTON_PAUSE_POSITION_X, 1, 90, 28);

		buttonPause.addActionListener(event -> {
            GameStatus gameStatus = game.getGameStatus();
            if (GameStatus.PLAY.equals(gameStatus)) {
                game.setGameStatus(GameStatus.PAUSED);
                buttonPause.setText("Resume");
            } else if (GameStatus.PAUSED.equals(gameStatus)) {
                game.setGameStatus(GameStatus.PLAY);
                buttonPause.setText("Pause");
            }
        });

		add(buttonPause);
	}

	public void paintComponent(Graphics g) {

		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;

		paintGame(g2);

		/*paintPlaces(g2);

		paintHumans(g2);*/

		g.setColor(Color.BLACK);

		if (game.isDebugMode()) {
            g.drawString("Scrollbar: " + scrollBarSpeedOfGame.getValue(),
                    STRING_SCROLL_BAR_SPEED_OF_GAME_POSITION_X, 28 + 15);
        }

        checkStateOfGame(g2);
	}

    private void checkStateOfGame(Graphics2D g2) {
        paintEndOfGame(g2);
        modifyUIBasedOnGameState();
    }

    private void modifyUIBasedOnGameState() {
        if (game.getGameStatus().equals(GameStatus.PLAY) || game.getGameStatus().equals(GameStatus.PAUSED)) {
            buttonPause.setEnabled(true);
        } else {
            buttonPause.setEnabled(false);
        }
    }

    private void paintEndOfGame(Graphics2D g) {
	    if (game.getGameStatus().equals(GameStatus.VICTORY) || game.getGameStatus().equals(GameStatus.DEFEAT)) {
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(320, 28 + 370, 160, 60);
            g.setColor(Color.BLACK);
            g.drawRect(320, 28 + 370, 160, 60);
            g.setColor(Color.BLACK);
            Font originalFont = g.getFont();
            Font newFont = originalFont.deriveFont(originalFont.getSize() * 2.0F);
            g.setFont(newFont);
            if (game.getGameStatus().equals(GameStatus.VICTORY)) {
                g.drawString("VICTORY!!!", 338, 28 + 409);
            } else if (game.getGameStatus().equals(GameStatus.DEFEAT)) {
                g.drawString("DEFEAT!!!", 342, 28 + 409);
            }
            g.setFont(originalFont);
        }
    }

    /*private void paintPlaces(Graphics g) {
        for(Place place: game.getListOfPlaces()) {
            //g.drawImage(images.humanHealthy, place.getXForPainting(), place.getYForPainting(), this);
            g.setColor(Color.BLACK);

            if (game.isDebugMode()) {
                g.drawOval(place.getXForPainting(), place.getYForPainting(), 15, 15);
                g.drawRect(place.getXForPainting(), place.getYForPainting(), 15, 15);
                g.setColor(Color.RED);
                g.drawString(String.valueOf(place.getPosition()), place.getXForPainting() + 1, place.getYForPainting() + 12);
            }
        }
    }*/

    /*g.setColor(Color.RED);
    ListIterator<Virus> iteratorVirus = game.getListOfViruses().listIterator();
    try {
        while(iteratorVirus.hasNext()) {
                Virus virus = iteratorVirus.next();
                g.drawRect(virus.getIntX(), virus.getIntY(), 3, 3);
        }
    } catch (ConcurrentModificationException exception) {
        // Ignore
    }*/


	private void paintGame(Graphics g) {

		//paintArena(g);

		//g.setColor(Color.LIGHT_GRAY);
		//g.fillRect(7, 30, 786, 763);

		g.setColor(Color.BLACK);
		g.drawString("Speed: " + game.getSpeedForUI(), STRING_SPEED_POSITION_X, 21);
        g.drawString("Time: " + game.getTime(), STRING_TIME_POSITION_X, 21);
		g.setColor(Color.BLACK);
		g.drawRect(-1, 28, 801, 801);


        if (game.isDebugMode()) {
            g.setColor(Color.BLUE);
            g.drawRect(0, 28 + 0, 399, 399);
            g.setColor(Color.MAGENTA);
            g.drawRect(0, 28 + 400, 399, 399);
            g.setColor(Color.GREEN);
            g.drawRect(400, 28 + 0, 399, 399);
            g.setColor(Color.YELLOW);
            g.drawRect(400, 28 + 400, 399, 399);
        }
        if (game.isDebugMode()) {
            g.drawImage(images.spaceShip1, 370, 225, this);
        }
		/*for (Human human: game.getListOfHumans()) {
			human.paint(g);
		}*/
	}

	/*private void paintArena(Graphics g) {
		//g.drawRect(40, 10, WIDTH, HEIGHT);
	}*/

}
