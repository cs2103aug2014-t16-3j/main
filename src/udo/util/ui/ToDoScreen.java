//@author A0114088H
package udo.util.ui;

import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import udo.util.shared.Constants.UI;
import udo.util.shared.ItemData;

public class ToDoScreen extends Screen {

	private static final long serialVersionUID = 1L;
	
//	private static final Logger logger =
//	        Logger.getLogger(udo.util.ui.DayView.class.getName());
	
	public ToDoScreen() {
		super();
		setBounds(0,0,UI.SUBVIEW_WIDTH, UI.SUBVIEW_HEIGHT);
	}
	
	public void init() {
		ArrayList<ItemData> data = new ArrayList<ItemData>();
		init(data);
	}
	
	@Override
	public void init(ArrayList<ItemData> data) {
		initHeader();
		mHeader.setPreferredSize(new Dimension(UI.SUBVIEW_WIDTH, UI.TODOVIEW_HEADER_HEIGHT));
//		try {
//			logger.addHandler(new FileHandler("dayViewLog%u.txt", true));
//		} catch (SecurityException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		populateView(data);
	}
	
	private void initHeader() {
		JLabel title = new JLabel("To Do");
		title.setFont(UI.FONT_24);
		FontMetrics fm = title.getFontMetrics(title.getFont());
		int height = fm.getHeight();
		title.setPreferredSize(new Dimension(UI.SUBVIEW_WIDTH, height));
		title.setHorizontalAlignment(JLabel.RIGHT);
		title.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		mHeader.add(title);
		add(mHeader);
	}
	
	private void populateView(ArrayList<ItemData> data) {
		if (data.size() == 0) {
			JLabel noItems = new JLabel("You have no upcoming tasks!");
			noItems.setFont(UI.FONT_14);
			FontMetrics fm = noItems.getFontMetrics(noItems.getFont());
			int height = fm.getHeight();
			noItems.setPreferredSize(new Dimension(UI.SUBVIEW_WIDTH, height));
			noItems.setHorizontalAlignment(JLabel.CENTER);
			add(noItems);
		} else {
			super.init(data);
		}
	}

	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.drawLine(UI.SUBVIEW_WIDTH/4, UI.SUBVIEW_HEADER_LINEY, UI.SUBVIEW_WIDTH, UI.SUBVIEW_HEADER_LINEY);
	}
}
