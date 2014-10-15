package udo.util.ui;

import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import udo.util.shared.Constants.UI;
import udo.util.shared.ItemData;

public class ListView extends JScrollPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel mView = new JPanel();
	
	public ListView() {

		//setOpaque(false);
		mView.setOpaque(false);
		setBackground(UI.MAIN_COLOR);
		//setBounds(20,20,UI.SUBVIEW_WIDTH,UI.SUBVIEW_HEIGHT);
	}

	public void init(ArrayList<ItemData> data) {
		for (int i = 0; i < data.size(); i++) {
			Entry entry = new Entry(data.get(i), data.get(i).getItemType());
			add(entry);
		}
	}

}
