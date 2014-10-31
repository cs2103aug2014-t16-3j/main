package udo.util.ui;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;

import javax.swing.border.AbstractBorder;

public class ShadowBorder extends AbstractBorder {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int RADIUS = 30;

    @Override
    public boolean isBorderOpaque() {
        return false;
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(RADIUS, RADIUS, RADIUS, RADIUS);
    }

    @Override
    public Insets getBorderInsets(Component c, Insets insets) {
        insets.top = RADIUS;
        insets.left = RADIUS;
        insets.bottom = RADIUS;
        insets.right = RADIUS;
        return insets;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(new Color(66, 0, 0));
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.DST_ATOP, 0.5f));
        g2d.fillRect(0, 0, width - RADIUS, RADIUS);
        g2d.fillRect(width - RADIUS, 0, RADIUS, height - RADIUS);
        g2d.fillRect(0, RADIUS, RADIUS, height - RADIUS);
        g2d.fillRect(RADIUS, height - RADIUS, width - RADIUS, RADIUS);
    }
}
