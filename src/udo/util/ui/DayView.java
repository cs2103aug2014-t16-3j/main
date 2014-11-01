//@author A0114088H
package udo.util.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import udo.util.shared.Constants.Keys;
import udo.util.shared.Constants.UI;
import udo.util.shared.ItemData;
import udo.util.shared.ItemType;

public class DayView extends Screen{

	private static final long serialVersionUID = -1690972274647306472L;
	private BufferedImage mTickerImg;
	private JLabel mTicker;
	private ListView mEntryView = new ListView();
	private ArrayList<Point> mTickerCoordsXY; // stores x and y coords of the start of ticker
	private ArrayList<Point> mTickerCoordsWH; // stores width and height of each ticker
	private ArrayList<Point> mRedTickCoordsXY; // stores x and y coords of overlapped start
	private ArrayList<Point> mRedTickCoordsWH; // stores width and height of red ticks
	
//	private static final Logger logger =
//	        Logger.getLogger(udo.util.ui.DayView.class.getName());
	
	public DayView(){
		
		super();
		mTickerCoordsXY = new ArrayList<Point>();
		mTickerCoordsWH = new ArrayList<Point>();
		mRedTickCoordsXY = new ArrayList<Point>();
		mRedTickCoordsWH = new ArrayList<Point>();
		loadTicker();
	}

	public void init(ArrayList<ItemData> data) {
		Date date = new Date();
		init(date, data);
	}
	
	public void init() {
		Date date = new Date();
		ArrayList<ItemData> data = new ArrayList<ItemData>();
		init(date,data);
	}
	
	public void init(Date newDate, ArrayList<ItemData> data) {
		removeAll();
		initHeader(newDate);
		mHeader.setPreferredSize(new Dimension(UI.SUBVIEW_WIDTH, UI.DAYVIEW_HEADER_HEIGHT));
//		try {
//			logger.addHandler(new FileHandler("logs/dayViewLog%u.txt", true));
//		} catch (SecurityException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		populateView(data);
	}
	
	private void loadTicker() {
		try {                
			mTickerImg = ImageIO.read(new File(UI.TICKER_IMAGE_DIR));
		} catch (IOException ex) {
			// handle exception...
		}
		mTicker = new JLabel(new ImageIcon(mTickerImg)){
			private static final long serialVersionUID = 1L;
			
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.setColor(Color.GREEN);
				for(int i=0; i < mTickerCoordsXY.size(); i++) {
					g.fillRect(mTickerCoordsXY.get(i).x, 
								mTickerCoordsXY.get(i).y, 
								mTickerCoordsWH.get(i).x, 
								mTickerCoordsWH.get(i).y);
				}
				g.setColor(Color.RED);
				for(int i=0; i< mRedTickCoordsXY.size(); i++) {
					g.fillRect(mRedTickCoordsXY.get(i).x, 
								mRedTickCoordsXY.get(i).y, 
								mRedTickCoordsWH.get(i).x, 
								mRedTickCoordsWH.get(i).y);
				}
				g.setFont(UI.FONT_18);
				g.setColor(UI.EVENT_COLOR);
				g.drawString("AM", 76, 30);
				g.drawString("PM", 257, 30);
			}
			
		};
		mTicker.setPreferredSize(new Dimension(mTickerImg.getWidth(),mTickerImg.getHeight()));
	}
	
	private void populateView(ArrayList<ItemData> data) {
		//logger.entering(getClass().getName(), "populateView");
		if (data.size() == 0) {
			JLabel noItems = new JLabel("You are free today!");
			noItems.setFont(UI.FONT_14);
			FontMetrics fm = noItems.getFontMetrics(noItems.getFont());
			int height = fm.getHeight();
			noItems.setPreferredSize(new Dimension(UI.SUBVIEW_WIDTH, height));
			noItems.setHorizontalAlignment(JLabel.CENTER);
			add(noItems);
		} else {
			
			int eventCount = 0;
			for(int i = 0; i<data.size(); i++) {
				ItemData item = data.get(i);
				if(item.getItemType().equals(ItemType.EVENT)) {
					fillTicker(item, eventCount);
					eventCount++;
				}
			}
			mScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			mScrollPane.setPreferredSize(new Dimension(UI.SUBVIEW_WIDTH,
					UI.SUBVIEW_HEIGHT - mHeader.getPreferredSize().height));
			mScrollPane.getViewport().add(mEntryView);
			mEntryView.populateView(data);
			if(mEntryView.getPreferredSize().width > UI.MAIN_WIDTH) {
//				logger.info("TODAY mEntryView's preferredSize: " + mEntryView.getPreferredSize() + "\nmEntryView's preferredSize is wider than mScrollPane");
			} else {
//				logger.fine("TODAY mEntryView's preferredSize is contained in mScrollPane");
			}
			
			add(mScrollPane);
			
		}
//		logger.exiting(getClass().getName(), "populateView");
	}

	private void fillTicker(ItemData item, int i) {
		int hour, min, total;
		Point xy,wh;
		Point redXY, redWH;
		xy = new Point();
		wh = new Point();
		int currItem_startX = 0;
		int lastItem_endX = 0;
		hour = ((Calendar) item.get(Keys.START)).get(Calendar.HOUR_OF_DAY) * 60;
		min = ((Calendar) item.get(Keys.START)).get(Calendar.MINUTE);
		total = hour+min;
		xy = new Point(Math.max (1, (int) Math.floor(total/4d)), UI.TICKER_Y); //minimum x pixel is 1
		mTickerCoordsXY.add(xy);
		if(i>0) {
			currItem_startX = xy.x;
			lastItem_endX = mTickerCoordsXY.get(i-1).x + mTickerCoordsWH.get(i-1).x;
		}
		hour = ((Calendar) item.get(Keys.END)).get(Calendar.HOUR_OF_DAY) * 60;
		min = ((Calendar) item.get(Keys.END)).get(Calendar.MINUTE);
		total = hour+min;
		wh = new Point((int) (Math.ceil(total/4d)) - xy.x, 10);
		mTickerCoordsWH.add(wh);
		int currItem_endX = xy.x + wh.x;
		if(currItem_startX < lastItem_endX) {
			redXY = new Point(xy.x, xy.y);
			mRedTickCoordsXY.add(redXY);
			if(currItem_endX > lastItem_endX) {
				redWH = new Point(lastItem_endX - currItem_startX, 10);
			}else{
				redWH = new Point(currItem_endX - currItem_startX, 10);
			}
			mRedTickCoordsWH.add(redWH);
		}
	}
	
	private void initHeader(Date newDate) {
		String dateString = UI.DD_MMMM_YYYY.format(newDate);
		JLabel date = new JLabel(dateString);
		date.setFont(UI.FONT_24);
		FontMetrics fm = date.getFontMetrics(date.getFont());
		int height = fm.getHeight();
		date.setPreferredSize(new Dimension(UI.SUBVIEW_WIDTH , height));
		date.setHorizontalAlignment(JLabel.LEFT);

		String dayString = UI.DAY_NAME.format(newDate);
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
	}
	
	/**
	 * The following override of paint method is to add the line separator
	 * between the date and the day
	 */
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.drawLine(UI.SUBVIEW_WIDTH/4, UI.SUBVIEW_HEADER_LINEY, UI.SUBVIEW_WIDTH, UI.SUBVIEW_HEADER_LINEY);
		
	}
	

	
}

