package cz.koscak.jan.game.spaceship.gui;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

class Images {

    Image spaceShip1;

    Images() {
        spaceShip1 = loadImage("SpaceShip-1.png");
    }

    private BufferedImage loadImage(String imageFileName) {
        try {
             return ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(imageFileName)));
        } catch (IOException exception) {
            System.err.println("Loading image error: " + exception);
            exception.printStackTrace();
            return new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        }
    }
}
