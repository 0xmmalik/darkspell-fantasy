package com.manav.dsf.gfx;

import com.manav.dsf.Game;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class SpriteSheet {
    public String path;
    public int width;
    public int height;

    public int[] pixelData;

    public SpriteSheet(String path) throws IOException {
        BufferedImage img = null;
        img = ImageIO.read(SpriteSheet.class.getResource(path));

        if (img == null) return;

        this.path = path;
        this.width = img.getWidth();
        this.height = img.getHeight();

        this.pixelData = img.getRGB(0, 0, width, height, null, 0, width);

        for (int i = 0; i < pixelData.length; i++) {
            pixelData[i] = (pixelData[i] & 0xff) / 64;
        }

        for (int i = 0; i < 8; i++) {
            System.out.println(pixelData[i]);
        }
    }
}
