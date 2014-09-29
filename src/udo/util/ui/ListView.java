package udo.util.ui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import udo.util.shared.ItemData;

public class ListView extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ListView() {
		
		// super.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		super(new FlowLayout());
		setOpaque(false);
	}
	
	public void init(ArrayList<ItemData> data) {
		for (int i = 0; i < data.size(); i++){
			Entry entry = new Entry(data.get(i));
			
			//entry.setPreferredSize(new Dimension(300,50));
			add(entry);
		}
	}

	
}
