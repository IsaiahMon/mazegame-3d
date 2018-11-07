package downloads.gamecreate.game.graphics;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class MapAddition {
    public BufferedImage MapAddition(JFrame frame) throws IOException {
        String path = "worldMap.jpg";
        File file = new File(path);
        BufferedImage image = ImageIO.read(file);
        JLabel label = new JLabel(new ImageIcon(image));
        return image;
        
    }
}