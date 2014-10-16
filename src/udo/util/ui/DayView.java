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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import udo.util.shared.Constants.Keys;
import udo.util.shared.Constants.UI;
import udo.util.shared.ItemData;

public class DayView extends JPanel{

	private static final long serialVersionUID = -1690972274647306472L;
	private JPanel mHeader = new JPanel();
	private SimpleDateFormat mDateFormat = new SimpleDateFormat("dd MMMM yyyy");
	private SimpleDateFormat mDayFormat = new SimpleDateFormat("EEEE");  
	private BufferedImage mTickerImg;
	private JLabel mTicker;
	private JScrollPane mScrollPane = new JScrollPane();
	private ListView mEntryView = new ListView();
	private ArrayList<Point> mTickerCoordsXY; // stores x and y coords of the start of ticker
	private ArrayList<Point> mTickerCoordsWH; //stores width and height of each ticker
	
	private static final Logger logger =
	        Logger.getLogger(udo.util.ui.DayView.class.getName());
	
	public DayView(){

		mTickerCoordsXY = new ArrayList<Point>();
		mTickerCoordsWH = new ArrayList<Point>();
		setLayout(new WrapLayout());
		setOpaque(false);
		loadTicker();
		mScrollPane.getViewport().setBackground(UI.MAIN_COLOR);
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
		initHeader(newDate);
		mHeader.setPreferredSize(new Dimension(UI.SUBVIEW_WIDTH, UI.DAYVIEW_HEADER_HEIGHT));
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
	
	private void loadTicker() {
		try {                
			mTickerImg = ImageIO.read(new File("dayViewTicker.png"));
		} catch (IOException ex) {
			// handle exception...
		}
		mTicker = new JLabel(new ImageIcon(mTickerImg));
		mTicker.setPreferredSize(new Dimension(mTickerImg.getWidth(),mTickerImg.getHeight()));
	}
	
	private void populateView(ArrayList<ItemData> data) {
		logger.entering(getClass().getName(), "populateView");
		if (data.size() == 0) {
			JLabel noItems = new JLabel("You are free today!");
			noItems.setFont(UI.FONT_14);
			FontMetrics fm = noItems.getFontMetrics(noItems.getFont());
			int height = fm.getHeight();
			noItems.setPreferredSize(new Dimension(UI.SUBVIEW_WIDTH, height));
			noItems.setHorizontalAlignment(JLabel.CENTER);
			add(noItems);
		} else {
			int hour, min, total;
			Point xy,wh;
			for(int i = 0; i<data.size(); i++) {
				hour = ((Calendar) data.get(i).get(Keys.START)).get(Calendar.HOUR_OF_DAY) * 60;
				min = ((Calendar) data.get(i).get(Keys.START)).get(Calendar.MINUTE);
				total = hour+min;
				xy = new Point((int) Math.floor(total/4d), UI.TICKER_Y);
				mTickerCoordsXY.add(xy);
				hour = ((Calendar) data.get(i).get(Keys.END)).get(Calendar.HOUR_OF_DAY) * 60;
				min = ((Calendar) data.get(i).get(Keys.END)).get(Calendar.MINUTE);
				total = hour+min;
				wh = new Point((int) (Math.ceil(total/4d)) - xy.x, 10);
				mTickerCoordsWH.add(wh);
			}
			mScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			mScrollPane.setPreferredSize(new Dimension(UI.SUBVIEW_WIDTH,
					UI.SUBVIEW_HEIGHT - mHeader.getPreferredSize().height));
			mScrollPane.getViewport().add(mEntryView);
			mEntryView.populateView(data);
			if(mEntryView.getPreferredSize().width > UI.MAIN_WIDTH) {
				logger.info("TODAY mEntryView's preferredSize: " + mEntryView.getPreferredSize() + "\nmEntryView's preferredSize is wider than mScrollPane");
			} else {
				logger.fine("TODAY mEntryView's preferredSize is contained in mScrollPane");
			}
			
			add(mScrollPane);
			
		}
		
		logger.exiting(getClass().getName(), "populateView");
	}
	
	private void initHeader(Date newDate) {
		String dateString = mDateFormat.format(newDate);
		JLabel date = new JLabel(dateString);
		date.setFont(UI.FONT_24);
		FontMetrics fm = date.getFontMetrics(date.getFont());
		int height = fm.getHeight();
		date.setPreferredSize(new Dimension(UI.SUBVIEW_WIDTH , height));
		date.setHorizontalAlignment(JLabel.LEFT);

		String dayString = mDayFormat.format(newDate);
		JLabel day = new JLabel(dayString);
		day.setFont(UI.FONT_18);
		fm = day.getFontMetrics(day.getFont());
		height = fm.getHeight();
		day.setPreferredSize(new Dimension(UI.SUBVIEW_WIDTH, height));
		day.setHorizontalAlignment(SwingConstants.RIGHT);
		day.setOpaque(true);
		
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
		g.setColor(Color.GREEN);
		for(int i=0; i < mTickerCoordsXY.size(); i++) {
			g.fillRect(mTickerCoordsXY.get(i).x, mTickerCoordsXY.get(i).y, mTickerCoordsWH.get(i).x, mTickerCoordsWH.get(i).y);
		}
	}
	

	
}

