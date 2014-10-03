package udo.util.ui;

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
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import udo.util.shared.ItemData;

public class DayView extends JPanel{

	private static final long serialVersionUID = -1690972274647306472L;
	private SimpleDateFormat mDateFormat = new SimpleDateFormat("dd MMMM yyyy");
	private SimpleDateFormat mDayFormat = new SimpleDateFormat("EEEE");  
	private BufferedImage mTickerImg;
	private JLabel mTicker;
	private ArrayList<Point> mTickerCoordsX;
	private ArrayList<Point> mTickerCoordsY;
	
	private final static int VIEW_HEIGHT = 550;
	private final static int VIEW_WIDTH = 360;
	private final static int LINE_Y = 33;
	
	public DayView(){

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
		fillTicker();
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
	
	private void fillTicker() {
		add(mTicker);
	}
	
	private void populateView(ArrayList<ItemData> data) {
		
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
	
	public void removeAllButTicker() {
		super.removeAll();
		add(mTicker);
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
	}
	
}

