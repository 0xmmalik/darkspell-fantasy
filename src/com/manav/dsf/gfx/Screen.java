package com.manav.dsf.gfx;

import java.awt.*;

public class Screen {
    public static final int MAP_WIDTH = 64;
    public static final int WIDTH_MASK = MAP_WIDTH - 1;
    public int[] tiles = new int[MAP_WIDTH * MAP_WIDTH];
    public int[] colors = new int[MAP_WIDTH * MAP_WIDTH * 4];
    public Rectangle viewport;
    public SpriteSheet tileSheet;

    public Screen(Dimension size, SpriteSheet tileSheet) {
        this.viewport = new Rectangle(new Point(0, 0), size);
        this.tileSheet = tileSheet;

        for (int i = 0; i < tiles.length; i++) {
            colors[i * 4 + 0] = 0xff00ff;
            colors[i * 4 + 1] = 0x00ffff;
            colors[i * 4 + 2] = 0xffff00;
            colors[i * 4 + 3] = 0xffffff;
        }
    }

    public void render(int[] pixels, int offset, int row) {
        for (int yTile = viewport.y; yTile <= (viewport.y + viewport.height); yTile += 8) {
            int yMin = yTile - viewport.y;
            int yMax = yMin + 8;

            if (yMin < 0) yMin = 0;
            if (yMax > viewport.height) yMax = viewport.height;

            for (int xTile = viewport.x; xTile <= (viewport.x + viewport.width); xTile += 8) {
                int xMin = xTile - viewport.x;
                int xMax = xMin + 8;

                if (xMin < 0) xMin = 0;
                if (xMax > viewport.width) xMax = viewport.width;

                int tileIndex = (((xTile >> 3) & WIDTH_MASK) + ((yTile >> 3) & WIDTH_MASK)) * MAP_WIDTH;

                for (int y = yMin; y < yMax; y++) {
                    int sheetPixel = ((y + viewport.y) & 7) * tileSheet.width + ((xMin + viewport.x) & 7);
                    int tilePixel = offset + xMin + y * row;

                    for (int x = xMin; x < xMax; x++) {
                        int colorIndex = tileIndex * 4 + tileSheet.pixelData[sheetPixel++];
                        pixels[tilePixel++] = colors[colorIndex];
                    }
                }
            }
        }
    }
}
