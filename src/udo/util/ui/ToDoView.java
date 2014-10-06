package udo.util.ui;

import java.awt.Dimension;

import javax.swing.JPanel;

public class ToDoView extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private final static int VIEW_HEIGHT = 550;
	private final static int VIEW_WIDTH = 360;
	
	public ToDoView() {
		setPreferredSize(new Dimension(VIEW_WIDTH,VIEW_HEIGHT));
		setBounds(20,20,VIEW_WIDTH,VIEW_HEIGHT);
		setOpaque(false);
	}
	
	public void init() {
		initHeader();
		populateView();
	}
	
	public void initHeader() {
		
	}
}
