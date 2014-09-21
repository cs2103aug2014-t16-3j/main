package udo.uiItems;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

public class FadeLabel extends JLabel {

    private float alpha;

    public FadeLabel() {
    	setOpaque(true);
    	setFont(new Font("Georgia", Font.PLAIN, 14));
    	setBackground(Color.black);
    	setForeground(Color.white);
    	setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        setHorizontalAlignment(CENTER);
        setVerticalAlignment(CENTER);
        setAlpha(1f);
    }

    public void setAlpha(float value) {
        if (alpha != value) {
            float old = alpha;
            alpha = value;
            firePropertyChange("alpha", old, alpha);
            repaint();
        }
    }

    public float getAlpha() {
        return alpha;
    }

    @Override
    public void paint(Graphics g) {
        // This is one of the few times I would directly override paint
        // This makes sure that the entire paint chain is now using
        // the alpha composite, including borders and child components
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, getAlpha()));
        super.paint(g2d);
        g2d.dispose();
    }

    /*@Override
    protected void paintComponent(Graphics g) {
        // This is one of the few times that doing this before the super call
        // will work...
        if (background != null) {
            int x = (getWidth() - background.getWidth()) / 2;
            int y = (getHeight() - background.getHeight()) / 2;
            g.drawImage(background, x, y, this);
        }
        super.paintComponent(g);
    }*/
}
