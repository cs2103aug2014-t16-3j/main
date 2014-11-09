//@author A0114088H-reused

// this was taken from http://stackoverflow.com/questions/13368103/jpanel-drop-shadow
// I do not take credit from this.

package udo.ui.util;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class DropShadowPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private int pixels;

	public DropShadowPanel(int pix) {
		this.pixels = pix;
		Border border = BorderFactory.createEmptyBorder(pixels, pixels, pixels,
				pixels);
		this.setBorder(BorderFactory.createCompoundBorder(this.getBorder(),
				border));
		setOpaque(false);
		setLayout(new BorderLayout());
	}

	@Override
	protected void paintComponent(Graphics g) {
		int shade = 0;
		int topOpacity = 80;
		for (int i = 0; i < pixels; i++) {
			g.setColor(new Color(shade, shade, shade,
					((topOpacity / pixels) * i)));
			g.drawRect(i, i, this.getWidth() - ((i * 2) + 1), this.getHeight()
					- ((i * 2) + 1));
		}
	}
}