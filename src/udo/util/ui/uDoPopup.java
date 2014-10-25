package udo.util.ui;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import udo.util.shared.Constants.UI;

public class uDoPopup extends JLabel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private float alpha;

	public uDoPopup() {

		setOpaque(false);
		setFont(UI.FONT_14);
		setBackground(UI.POPUP_BGCOLOR);
		setForeground(UI.POPUP_FGCOLOR);
		setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
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
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
				getAlpha()));
		super.paint(g2d);
		g2d.dispose();
	}

	protected void paintComponent(Graphics g) {
		g.setColor(getBackground());
		g.fillRect(0, 0, getWidth(), getHeight());
		super.paintComponent(g);
	}

}
