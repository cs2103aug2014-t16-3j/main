package udo.util.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
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

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import udo.util.shared.Constants.Keys;
import udo.util.shared.ItemData;

public class DayView extends JPanel{

	private static final long serialVersionUID = -1690972274647306472L;
	private SimpleDateFormat mDateFormat = new SimpleDateFormat("dd MMMM yyyy");
	private SimpleDateFormat mDayFormat = new SimpleDateFormat("EEEE");  
	private BufferedImage mTickerImg;
	private JLabel mTicker;
	private ArrayList<Point> mTickerCoordsXY; // stores x and y coords of the start of ticker
	private ArrayList<Point> mTickerCoordsWH; //stores width and height of each ticker
	
	private final static int VIEW_HEIGHT = 550;
	private final static int VIEW_WIDTH = 360;
	private final static int LINE_Y = 33;
	
	public DayView(){

		mTickerCoordsXY = new ArrayList<Point>();
		mTickerCoordsWH = new ArrayList<Point>();
		setBounds(20,20,VIEW_WIDTH,VIEW_HEIGHT);
		setOpaque(false);
		loadTicker();
	}

	public void init(ArrayList<ItemData> data) {
		Date date = new Date();
		init(date, data);
	}
	
	public void init(Date newDate, ArrayList<ItemData> data) {
		initHeader(newDate);
		add(mTicker);
		populateView(data);
	}
	
	private void loadTicker() {
		try {                
			mTickerImg = ImageIO.read(new File("dayViewTicker.png"));
		} catch (IOException ex) {
			// handle exception...
		}
		mTicker = new JLabel(new ImageIcon(mTickerImg));
	}
	
	private void populateView(ArrayList<ItemData> data) {
		int hour, min, total;
		Point xy;
		Point wh = new Point();
		for (int i = 0; i < data.size(); i++) {
			hour = ((Calendar) data.get(i).get(Keys.START)).get(Calendar.HOUR_OF_DAY) * 60;
			min = ((Calendar) data.get(i).get(Keys.START)).get(Calendar.MINUTE);
			total = hour+min;
			xy = new Point((int) Math.floor(total/4), 81);
			mTickerCoordsXY.add(xy);
			hour = ((Calendar) data.get(i).get(Keys.END)).get(Calendar.HOUR_OF_DAY) * 60;
			min = ((Calendar) data.get(i).get(Keys.END)).get(Calendar.MINUTE);
			total = hour+min;
			wh = new Point((int) Math.floor(total/4)-xy.x, 10);
			mTickerCoordsWH.add(wh);
			Entry entry = new Entry(data.get(i));
			add(entry);
		}
	}
	
	private void initHeader(Date newDate) {
		String dateString = mDateFormat.format(newDate);
		JLabel date = new JLabel(dateString);
		date.setFont(new Font("Copperplate Gothic Light", Font.PLAIN, 24));
		FontMetrics fm = date.getFontMetrics(date.getFont());
		int height = fm.getHeight();
		date.setPreferredSize(new Dimension(VIEW_WIDTH, height));
		date.setHorizontalAlignment(JLabel.LEFT);
		add(date);
		String dayString = mDayFormat.format(newDate);
		JLabel day = new JLabel(dayString);
		day.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 18));
		fm = day.getFontMetrics(day.getFont());
		height = fm.getHeight();
		day.setPreferredSize(new Dimension(VIEW_WIDTH, height));
		day.setHorizontalAlignment(JLabel.RIGHT);;
		add(day);
	}
	
	public void removeAll() {
		super.removeAll();
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
		g2.drawLine(VIEW_WIDTH/4, LINE_Y, VIEW_WIDTH, LINE_Y);
		g.setColor(Color.GREEN);
		for(int i=0; i < mTickerCoordsXY.size(); i++) {
			g.fillRect(mTickerCoordsXY.get(i).x, mTickerCoordsXY.get(i).y, mTickerCoordsWH.get(i).x, mTickerCoordsWH.get(i).y);
		}
	}
	
}

