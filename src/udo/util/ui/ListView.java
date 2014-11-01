//@author A0114088H
package udo.util.ui;

import java.awt.Dimension;
import java.util.ArrayList;

import udo.util.shared.Constants.UI;
import udo.util.shared.ItemData;


public class ListView extends View {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ListView() {
		setLayout(new WrapLayout(WrapLayout.LEADING, 0, 0));
		setSize(new Dimension(UI.SUBVIEW_WIDTH, 1));
	}

	public void populateView(ArrayList<ItemData> data) {
		removeAll();
		for (int i = 0; i < data.size(); i++) {
			Entry entry = new Entry(data.get(i), data.get(i).getItemType());
			add(entry);
		}
	}
	


}
