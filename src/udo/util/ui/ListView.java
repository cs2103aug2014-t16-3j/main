package udo.util.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import udo.util.shared.Constants.UI;
import udo.util.shared.ItemData;

public class ListView extends JScrollPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel mView;
	
	public ListView() {
		mView = new JPanel();
		setOpaque(true);
		setBackground(Color.RED);
		mView.setLayout(new WrapLayout(WrapLayout.LEADING, 0 ,0));
		getViewport().setLayout(new WrapLayout(WrapLayout.LEADING, 0 ,0));
		mView.setOpaque(true);
		mView.setBackground(Color.BLUE);
		setPreferredSize(new Dimension(UI.SUBVIEW_WIDTH, UI.SUBVIEW_HEIGHT));
		setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
	    setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	    setViewportView(mView);
	}

	public void init(ArrayList<ItemData> data) {
		for (int i = 0; i < data.size(); i++) {
			Entry entry = new Entry(data.get(i), data.get(i).getItemType());
			mView.add(entry);
			mView.revalidate();
		}
	}

}
