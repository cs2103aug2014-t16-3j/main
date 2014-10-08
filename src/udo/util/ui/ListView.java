package udo.util.ui;

import java.util.ArrayList;

import javax.swing.JPanel;

import udo.util.shared.ItemData;

public class ListView extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final static int VIEW_HEIGHT = 550;
	private final static int VIEW_WIDTH = 360;
	
	public ListView() {

		setOpaque(false);
		setBounds(20,20,VIEW_WIDTH,VIEW_HEIGHT);
	}

	public void init(ArrayList<ItemData> data) {
		for (int i = 0; i < data.size(); i++) {
			Entry entry = new Entry(data.get(i), "allDetails");
			add(entry);
		}
	}

}
