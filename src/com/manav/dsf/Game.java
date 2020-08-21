package com.manav.dsf;

import com.manav.dsf.gfx.SpriteSheet;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.IOException;

public class Game extends Canvas implements Runnable {

    public static final int WIDTH = 160;
    public static final int HEIGHT = WIDTH / 12 * 9; // game area is 12 x 9
    public static final float SCALE = 3.0f;
    public static final String NAME = "Darkspell Fantasy";
    private static final long serialVersionUID = 1L;
    public boolean running = false;
    public int tickCount = 0;
    private JFrame frame;
    private BufferedImage img = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    private int[] pixels = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();
    private SpriteSheet tileSheet = new SpriteSheet("/sprite_sheet.png");

    public Game() throws IOException {
        setMinimumSize(new Dimension((int) (WIDTH * SCALE), (int) (HEIGHT * SCALE)));
        setMaximumSize(new Dimension((int) (WIDTH * SCALE), (int) (HEIGHT * SCALE)));
        setPreferredSize(new Dimension((int) (WIDTH * SCALE), (int) (HEIGHT * SCALE)));

        frame = new JFrame(NAME);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setLayout(new BorderLayout());
        frame.add(this, BorderLayout.CENTER);
        frame.pack();

        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) throws IOException {
        new Game().start();
    }

    public synchronized void start() {
        running = true;
        new Thread(this).start();
    }

    public synchronized void stop() {
        running = false;
    }

    @Override
    public void run() {
        long prevTime = System.nanoTime();
        double nsPerTick = 1000000000 / 60d;

        int frames = 0;
        int ticks = 0;

        long lastTimer = System.currentTimeMillis();
        double unprocessedNS = 0;
        while (running) {
            long currTime = System.nanoTime();
            unprocessedNS += (currTime - prevTime) / nsPerTick;
            prevTime = currTime;
            boolean render = true;

            while (unprocessedNS >= 1) {
                ticks++;
                tick();
                unprocessedNS--;
                render = true;
            }

            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (render) {
                frames++;
                render();
            }

            if (System.currentTimeMillis() - lastTimer >= 1000) {
                lastTimer += 1000;
                System.out.println(frames + " | " + ticks);
                frames = 0;
                ticks = 0;
            }
        }
    }

    public void tick() {
        tickCount++;

        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = i + tickCount;
        }
    }

    public void render() {
        BufferStrategy bufferStrategy = getBufferStrategy();
        if (bufferStrategy == null) {
            createBufferStrategy(5);
            return;
        }

        Graphics graphics = bufferStrategy.getDrawGraphics();
        graphics.drawImage(img, 0, 0, getWidth(), getHeight(), null);
        graphics.dispose();
        bufferStrategy.show();
    }
}
