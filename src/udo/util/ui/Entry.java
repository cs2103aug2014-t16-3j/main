package udo.util.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Entry extends JPanel{
	
	private int x;
	private int y;
	private String date; // date may be a Date object instead, or other data structures
	private String desc;
	private JTextArea textArea = new JTextArea();
	
	private static final Color ENTRY_COLOR = new Color(50,255,125);
	private static final Color ENTRY_BG = new Color(45,215,105);
	
	public Entry(String newDate, String newDesc) {
		date = newDate;
		desc = newDesc;
        setBorder(BorderFactory.createLineBorder(ENTRY_BG));
        setBackground(ENTRY_COLOR);
        textArea.setPreferredSize(new Dimension(280, 50));
        textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setEditable(false);
		textArea.setOpaque(false);
        textArea.append(newDate + "\n");
        textArea.append(newDesc);
        add(textArea);
    }

}
