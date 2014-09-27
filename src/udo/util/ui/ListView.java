package udo.util.ui;

import java.util.ArrayList;

import javax.swing.JPanel;

import udo.util.shared.ItemData;

public class ListView extends JPanel {

	private ArrayList<ItemData> mData;
	public ListView() {
		
		super();
		setOpaque(false);
	}
	
	public void init(ArrayList<ItemData> data) {
		for (int i = 0; i < data.size(); i++){
			Entry entry = new Entry(data.get(i));
			entry.setBounds(20, 100*i, 300, 50);
			add(entry);
		}
		System.out.println("Entry added");
	}

	public ArrayList<ItemData> getmData() {
		return mData;
	}

	public void setmData(ArrayList<ItemData> mData) {
		this.mData = mData;
	}
	
}
