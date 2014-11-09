//@author A0114088H
package udo.ui.util;

import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import udo.constants.Constants.UI;
import udo.data.ItemData;
import udo.language.LanguagePack;

public class ToDoScreen extends Screen {

	private static final long serialVersionUID = 1L;
	
	private LanguagePack mLang = LanguagePack.getInstance();

	public ToDoScreen(int width, int height) {
		super(width, height);
	}

	public void init() {
		ArrayList<ItemData> data = new ArrayList<ItemData>();
		init(data);
	}

	@Override
	public void init(ArrayList<ItemData> data) {
		initHeader();
		mHeader.setPreferredSize(new Dimension(mWidth,
				UI.TODOVIEW_HEADER_HEIGHT));
		populateView(data);
	}

	private void initHeader() {
		JLabel title = new JLabel(mLang.getTO_DO_TITLE());
		title.setFont(UI.FONT_24);
		FontMetrics fm = title.getFontMetrics(title.getFont());
		int height = fm.getHeight();
		title.setPreferredSize(new Dimension(mWidth, height));
		title.setHorizontalAlignment(JLabel.RIGHT);
		title.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		mHeader.add(title);
		add(mHeader);
	}

	private void populateView(ArrayList<ItemData> data) {
		if (data.size() == 0) {
			JLabel noItems = new JLabel(mLang.getNO_UPCOMING_TASKS());
			noItems.setFont(UI.FONT_14);
			FontMetrics fm = noItems.getFontMetrics(noItems.getFont());
			int height = fm.getHeight();
			noItems.setPreferredSize(new Dimension(mWidth, height));
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
		int fromQuarterWidth = mWidth/4;
		g2.drawLine(fromQuarterWidth, UI.SUBVIEW_HEADER_LINEY,
				mWidth, UI.SUBVIEW_HEADER_LINEY);
	}
}
