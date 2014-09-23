package udo.ui;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

public class uDoPopup extends JLabel {
	
	private float alpha;
	
	public uDoPopup() {
		
		setOpaque(false);
		setFont(new Font("Sans", Font.PLAIN, 14));
		setBackground(Color.BLACK);
	    setForeground(Color.WHITE);
	    //setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
	    //setAlpha(0f);
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
         g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, getAlpha()));
         super.paint(g2d);
         g2d.dispose();
     }
     
     protected void paintComponent(Graphics g)
     {
         g.setColor( getBackground() );
         g.fillRect(0, 0, getWidth(), getHeight());
         super.paintComponent(g);
     }
     
}
