package udo.util.ui;

import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import udo.util.shared.Constants.UI;
import udo.util.shared.ItemData;

public class ToDoView extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JPanel mHeader = new JPanel();
	private ListView mEntryView = new ListView();
	private JScrollPane mScrollPane = new JScrollPane();
	
	private static final Logger logger =
	        Logger.getLogger(udo.util.ui.DayView.class.getName());
	
	public ToDoView() {
		setBounds(0,0,UI.SUBVIEW_WIDTH, UI.SUBVIEW_HEIGHT);
		setLayout(new WrapLayout());
		setOpaque(false);
		mScrollPane.getViewport().setBackground(UI.MAIN_COLOR);
	}
	
	public void init() {
		ArrayList<ItemData> data = new ArrayList<ItemData>();
		init(data);
	}
	
	public void init(ArrayList<ItemData> data) {
		initHeader();
		mHeader.setPreferredSize(new Dimension(UI.SUBVIEW_WIDTH, UI.TODOVIEW_HEADER_HEIGHT));
		try {
			logger.addHandler(new FileHandler("dayViewLog%u.txt", true));
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
			mScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			mScrollPane.setPreferredSize(new Dimension(UI.SUBVIEW_WIDTH,
					UI.SUBVIEW_HEIGHT - mHeader.getPreferredSize().height));
			mScrollPane.getViewport().add(mEntryView);
			mEntryView.populateView(data);
			if(mEntryView.getPreferredSize().width > UI.MAIN_WIDTH) {
				logger.info("TODO mEntryView's preferredSize: " + mEntryView.getPreferredSize() + "\nmEntryView's preferredSize is wider than mScrollPane");
			} else {
				logger.fine("TODO mEntryView's preferredSize is contained in mScrollPane");
			}
			
			add(mScrollPane);
		}
	}
	
	public JScrollPane getScrollPane() {
		return mScrollPane;
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.drawLine(UI.SUBVIEW_WIDTH/4, UI.SUBVIEW_HEADER_LINEY, UI.SUBVIEW_WIDTH, UI.SUBVIEW_HEADER_LINEY);
	}
}
