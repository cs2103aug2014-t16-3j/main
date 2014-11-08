//@author A0114088H
package udo.util.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import udo.language.LanguagePack;
import udo.util.shared.Constants.Keys;
import udo.util.shared.Constants.UI;
import udo.util.shared.ItemData;
import udo.util.shared.ItemType;

public class DayScreen extends Screen {

	private static final long serialVersionUID = -1690972274647306472L;
	
	private Image mTickerImg;
	private JLabel mTicker;
	private ArrayList<Point> mTickerCoordsXY; // stores x and y coords of the
												// start of ticker
	private ArrayList<Point> mTickerCoordsWH; // stores width and height of each
												// ticker
	private ArrayList<Point> mRedTickCoordsXY; // stores x and y coords of
												// overlapped start
	private ArrayList<Point> mRedTickCoordsWH; // stores width and height of red
												// ticks
	
	private Date mDateQuery;
	
	private LanguagePack mLang = LanguagePack.getInstance();

	// private static final Logger logger =
	// Logger.getLogger(udo.util.ui.DayView.class.getName());

	public DayScreen(int width, int height) throws IOException {

		super(width, height);
		mTickerCoordsXY = new ArrayList<Point>();
		mTickerCoordsWH = new ArrayList<Point>();
		mRedTickCoordsXY = new ArrayList<Point>();
		mRedTickCoordsWH = new ArrayList<Point>();
		loadTicker();
	}

	@Override
	public void init(ArrayList<ItemData> data) {
		Date date = new Date();
		init(date, data);
	}

	public void init() {
		Date date = new Date();
		ArrayList<ItemData> data = new ArrayList<ItemData>();
		init(date, data);
	}

	@Override
	public void init(Date newDate, ArrayList<ItemData> data) {
		removeAll();
		mDateQuery = newDate;
		initHeader(newDate);
		mHeader.setPreferredSize(new Dimension(mWidth,
				UI.DAYVIEW_HEADER_HEIGHT));
		// try {
		// logger.addHandler(new FileHandler("logs/dayViewLog%u.txt", true));
		// } catch (SecurityException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		populateView(data);
	}

	private void loadTicker() throws IOException {
		BufferedImage img = ImageIO.read(getClass().getResource(UI.TICKER_IMAGE_DIR));
		mTickerImg = img.getScaledInstance(UI.SUBVIEW_WIDTH,
											img.getHeight(), 
											Image.SCALE_SMOOTH);
		
		drawTicker();

	}

	private void populateView(ArrayList<ItemData> data) {
		if (data.size() == 0) {
			JLabel noItems = new JLabel(mLang.getFREE_TODAY());
			noItems.setFont(UI.FONT_14);
			FontMetrics fm = noItems.getFontMetrics(noItems.getFont());
			int height = fm.getHeight();
			noItems.setPreferredSize(new Dimension(UI.SUBVIEW_WIDTH, height));
			noItems.setHorizontalAlignment(JLabel.CENTER);
			add(noItems);
		} else {

			int eventCount = 0;
			for (int i = 0; i < data.size(); i++) {
				ItemData item = data.get(i);
				if (item.getItemType().equals(ItemType.EVENT)) {
					fillTicker(item, eventCount);
					eventCount++;
				}
			}
			super.init(data);

		}
	}

	private void drawTicker() {
		mTicker = new JLabel(new ImageIcon(mTickerImg)) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.setColor(Color.GREEN);
				for (int i = 0; i < mTickerCoordsXY.size(); i++) {
					g.fillRect(mTickerCoordsXY.get(i).x,
							mTickerCoordsXY.get(i).y, mTickerCoordsWH.get(i).x,
							mTickerCoordsWH.get(i).y);
				}
				g.setColor(Color.RED);
				for (int i = 0; i < mRedTickCoordsXY.size(); i++) {
					g.fillRect(mRedTickCoordsXY.get(i).x,
							mRedTickCoordsXY.get(i).y,
							mRedTickCoordsWH.get(i).x,
							mRedTickCoordsWH.get(i).y);
				}
				g.setFont(UI.FONT_18);
				g.setColor(UI.EVENT_COLOR);
				g.drawString("AM", mTickerImg.getWidth(null)/4, 30);
				g.drawString("PM", mTickerImg.getWidth(null)*3/4, 30);
			}

		};
	}

	private void fillTicker(ItemData item, int i) {
		int hour, min, total;
		Point xy, wh;
		Point redXY, redWH;
		xy = new Point();
		wh = new Point();
		double minPerPixel = (24d * 60d) / mTickerImg.getWidth(null);
		int currItem_startX = 0;
		int lastItem_endX = 0;
		Calendar date = dateToCalendar(mDateQuery);
		Calendar start = (Calendar) item.get(Keys.START);
		if(date.get(Calendar.DAY_OF_MONTH) == start.get(Calendar.DAY_OF_MONTH)) {
			hour = (start).get(Calendar.HOUR_OF_DAY) * 60;
			min = (start).get(Calendar.MINUTE);
		} else {
			hour = 0;
			min = 0;
		}
		total = hour + min;
		xy = new Point(Math.max(1, (int) Math.floor(total / minPerPixel)),
				UI.TICKER_Y); // minimum x pixel is 1
		mTickerCoordsXY.add(xy);
		if (i > 0) {
			currItem_startX = xy.x;
			lastItem_endX = mTickerCoordsXY.get(i - 1).x
					+ mTickerCoordsWH.get(i - 1).x;
		}
		Calendar end = (Calendar) item.get(Keys.END);
		if(date.get(Calendar.DAY_OF_MONTH) == end.get(Calendar.DAY_OF_MONTH)) {
			hour = (end).get(Calendar.HOUR_OF_DAY) * 60;
			min = (end).get(Calendar.MINUTE);
		} else {
			hour = 24*60;
			min = 0;
		}
		
		total = hour + min;
		wh = new Point((int) (Math.ceil(total / minPerPixel)) - xy.x, 10);
		mTickerCoordsWH.add(wh);
		int currItem_endX = xy.x + wh.x;
		if (currItem_startX < lastItem_endX) {
			redXY = new Point(xy.x, xy.y);
			mRedTickCoordsXY.add(redXY);
			if (currItem_endX > lastItem_endX) {
				redWH = new Point(lastItem_endX - currItem_startX, 10);
			} else {
				redWH = new Point(currItem_endX - currItem_startX, 10);
			}
			mRedTickCoordsWH.add(redWH);
		}
		drawTicker();
	}

	private void initHeader(Date newDate) {
		String dateString = UI.DD.format(newDate) + " " + mLang.convertMonthToLanguage(UI.MMMM.format(newDate))
							+ " " + UI.YYYY.format(newDate);
		JLabel date = new JLabel(dateString);
		date.setFont(UI.FONT_24);
		FontMetrics fm = date.getFontMetrics(date.getFont());
		int height = fm.getHeight();
		date.setPreferredSize(new Dimension(UI.SUBVIEW_WIDTH, height));
		date.setHorizontalAlignment(JLabel.LEFT);

		String dayString = mLang.convertDayToLanguage(UI.DAY_NAME.format(newDate));
		JLabel day = new JLabel(dayString);
		day.setFont(UI.FONT_18);
		fm = day.getFontMetrics(day.getFont());
		height = fm.getHeight();
		day.setPreferredSize(new Dimension(UI.SUBVIEW_WIDTH, height));
		day.setHorizontalAlignment(SwingConstants.RIGHT);

		mHeader.add(date);
		mHeader.add(day);
		mHeader.add(mTicker);

		add(mHeader);
	}

	@Override
	public void removeAll() {
		super.removeAll();
		mHeader.removeAll();
		mTickerCoordsXY.clear();
		mTickerCoordsWH.clear();
		mRedTickCoordsXY.clear();
		mRedTickCoordsWH.clear();
	}
	
	public Calendar dateToCalendar(Date date){ 
	  Calendar cal = Calendar.getInstance();
	  cal.setTime(date);
	  return cal;
	}

	/**
	 * The following override of paint method is to add the line separator
	 * between the date and the day
	 */
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.drawLine(UI.SUBVIEW_WIDTH / 4, UI.SUBVIEW_HEADER_LINEY,
				UI.SUBVIEW_WIDTH, UI.SUBVIEW_HEADER_LINEY);

	}

}
