package udo.util.ui;

import java.util.ArrayList;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import udo.util.shared.ItemData;
import udo.util.shared.Constants.UI;

public class Screen extends JPanel {

	private static final long serialVersionUID = 1L;

	protected JPanel mHeader = new JPanel();
	protected JScrollPane mScrollPane = new JScrollPane();
	
	public Screen() {
		setOpaque(false);
		mHeader.setOpaque(false);
		setBounds(0,0,UI.SUBVIEW_WIDTH, UI.SUBVIEW_HEIGHT);
		setLayout(new WrapLayout());
		mScrollPane.getViewport().setOpaque(false);
		mScrollPane.setOpaque(false);
		mScrollPane.setBorder(BorderFactory.createEmptyBorder());
	}
	
	public void init(ArrayList<ItemData> data) {
		//TODO stub method to be overridden by subclasses. consider working around it
		System.out.println("parent");
	}
	
	public JScrollPane getScrollPane() {
		return mScrollPane;
	}

	public void init(Date date, ArrayList<ItemData> mData) {
		// TODO Auto-generated method stub
		// this is another stub to be overridden by subclass.
		
	}
}
