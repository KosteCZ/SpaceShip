package cz.koscak.jan.game.spaceship.gui;

import cz.koscak.jan.game.spaceship.model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.AdjustmentListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
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
        buttonNewGame.setFocusable(false);

        buttonNewGame.addActionListener(event -> {
            buttonPause.setEnabled(true);
            game.newGame();
        });

        add(buttonNewGame);
    }
    private void addPauseButton() {
        buttonPause = new JButton("Resume");
        buttonPause.setBounds(BUTTON_PAUSE_POSITION_X, 1, 90, 28);
        buttonNewGame.setFocusable(false);

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
        if (game.isDebugMode()) {
            g.setColor(Color.BLACK);
            g.drawLine(300, 300, 300, 350);
            g.drawLine(300, 300, 350, 350);
            /*System.out.println("Sin 0:" + Math.sin(0));
            System.out.println("Sin 90:" + Math.sin(90));
            System.out.println("Sin 180:" + Math.sin(180));
            System.out.println("Sin PI:" + Math.sin(Math.PI));
            System.out.println("Sin PI/6:" + Math.sin(Math.PI/6));
            System.out.println("Sin PI/2:" + Math.sin(Math.PI/2));*/

            for (int i = 0; i <= 120; i++) {
                g.drawLine(200,
                        300,
                        200 + (int) Math.floor(Math.cos(Math.toRadians(i)) * 50 + 0.5d),
                        300 + (int) Math.floor(Math.sin(Math.toRadians(i)) * 50 + 0.5d));
            }
        }
        for (SpaceShip spaceShip: game.getListOfSpaceShips()) {
            //g.drawImage(images.spaceShip1, spaceShip.getIntX(), spaceShip.getIntY(), this);

            BufferedImage bufferedImage = new BufferedImage(images.spaceShip1.getWidth(this), images.spaceShip1.getHeight(this), BufferedImage.TYPE_INT_ARGB);
            Graphics2D bufferedImageGraphics = bufferedImage.createGraphics();
            bufferedImageGraphics.drawImage(images.spaceShip1, 0, 0, null);
            bufferedImageGraphics.dispose();
            //g.drawImage(bufferedImage, spaceShip.getIntX() + 50, spaceShip.getIntY(), this);
            g.drawImage(rotate(bufferedImage, spaceShip.getRotation()), spaceShip.getIntX(), spaceShip.getIntY(), this);
        }
	}

    public static BufferedImage rotate(BufferedImage bufferedImage, Double angle) {
        double sin = Math.abs(Math.sin(Math.toRadians(angle)));
        double cos = Math.abs(Math.cos(Math.toRadians(angle)));
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();
        int newWidth = (int) Math.floor((width * cos) + (height * sin));
        int newHeight = (int) Math.floor((height * cos) + (width * sin));
        BufferedImage rotated = new BufferedImage(newWidth, newHeight, bufferedImage.getType());
        Graphics2D graphic = rotated.createGraphics();
        graphic.translate((newWidth-width) / 2, (newHeight-height) / 2);
        graphic.rotate(Math.toRadians(angle), width / 2, height / 2);
        graphic.drawRenderedImage(bufferedImage, null);
        graphic.dispose();
        return rotated;
    }

	/*private void paintArena(Graphics g) {
		//g.drawRect(40, 10, WIDTH, HEIGHT);
	}*/

}
