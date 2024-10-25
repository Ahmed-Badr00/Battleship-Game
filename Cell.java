// Cell.java
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Cell extends JButton {
    private boolean hasShip;
    private boolean idle;
    private static final ImageIcon idleIcon = new ImageIcon("data/tile-01.png");
    private static final ImageIcon missIcon = new ImageIcon("data/tile-02.png");
    private static final ImageIcon hitIcon = new ImageIcon("data/tile-03.png");

    public Cell(ActionListener listener, boolean hasShip) {
        this.hasShip = hasShip;
        this.idle = true;
        addActionListener(listener);
        setIcon(idleIcon);
        
        setPreferredSize(new Dimension(40, 40));
        setBorder(BorderFactory.createLineBorder(Color.GRAY));
        setFocusPainted(false);
    }

    public boolean isIdle() {
        return idle;
    }

    public boolean hasShip() {
        return hasShip;
    }

    public void setHasShip(boolean value) {
        this.hasShip = value;
    }

    public void fire(boolean hit) {
        idle = false;
        setIcon(hit ? hitIcon : missIcon);
    }

    public void reveal() {
        if (hasShip) {
            setIcon(hitIcon);
            idle = false;
        }
    }

    public void reset() {
        hasShip = false;
        idle = true;
        setIcon(idleIcon);
    }
}