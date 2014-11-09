//@author A0114088H
package udo.ui.util;

import java.util.ArrayList;

import javax.swing.JPanel;

import udo.data.ItemData;
import udo.data.OutputData;
import udo.enums.Command;

public class View extends JPanel {

	private static final long serialVersionUID = 1L;

	public View() {
		setOpaque(false);
	}

	public void populateView(ArrayList<ItemData> data) {
		removeAll();
		for (int i = 0; i < data.size(); i++) {
			ItemData item = data.get(i);
			addEntry(item);
		}
	}

	protected void addEntry(ItemData item) {
		Entry entry = new Entry(item, item.getItemType());
		add(entry);
	}

	protected void init(OutputData output, Command type) {
		// TODO also a stub
	}

}
