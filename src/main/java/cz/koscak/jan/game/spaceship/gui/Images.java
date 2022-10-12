package cz.koscak.jan.game.spaceship.gui;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

class Images {

    Image spaceShip1;

    Images() {
        try {
             spaceShip1 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("SpaceShip-1.png")));
        } catch (IOException exception) {
            System.err.println("Loading image error: " + exception);
            exception.printStackTrace();
        }
    }
}
