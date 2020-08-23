package com.manav.dsf;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

public class InputHandler implements KeyListener {

    public InputHandler(Game game) {
        game.addKeyListener(this);
    }

    public Key up = new Key();
    public Key down = new Key();
    public Key left = new Key();
    public Key right = new Key();

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        setPressed(e.getKeyCode(), true);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        setPressed(e.getKeyCode(), false);
    }

    public void setPressed(int keyCode, boolean pressed) {
        if (keyCode == KeyEvent.VK_W) up.setPressed(pressed);
        if (keyCode == KeyEvent.VK_S) down.setPressed(pressed);
        if (keyCode == KeyEvent.VK_A) left.setPressed(pressed);
        if (keyCode == KeyEvent.VK_D) right.setPressed(pressed);
    }

    public class Key {
        public boolean isPressed = false;

        public void setPressed(boolean pressed) {
            isPressed = pressed;
        }
    }
}
