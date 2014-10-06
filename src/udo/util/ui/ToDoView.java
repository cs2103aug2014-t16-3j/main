package udo.util.ui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

import udo.util.shared.ItemData;

public class ToDoView extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private final static int VIEW_HEIGHT = 550;
	private final static int VIEW_WIDTH = 360;
	private final static int LINE_Y = 33;
	
	public ToDoView() {
		setPreferredSize(new Dimension(VIEW_WIDTH,VIEW_HEIGHT));
		setBounds(20,20,VIEW_WIDTH,VIEW_HEIGHT);
		setOpaque(false);
	}
	
	public void init() {
		ArrayList<ItemData> data = new ArrayList<ItemData>();
		init(data);
	}
	
	public void init(ArrayList<ItemData> data) {
		initHeader();
		populateView(data);
	}
	
	public void initHeader() {
		JLabel title = new JLabel("To Do");
		title.setFont(new Font("Copperplate Gothic Light", Font.PLAIN, 24));
		FontMetrics fm = title.getFontMetrics(title.getFont());
		int height = fm.getHeight();
		title.setPreferredSize(new Dimension(VIEW_WIDTH, height));
		title.setHorizontalAlignment(JLabel.RIGHT);
		add(title);
	}
	
	public void populateView(ArrayList<ItemData> data) {
		if (data.size() == 0) {
			JLabel noItems = new JLabel("You have no upcoming tasks!");
			add(noItems);
		} else {
			for (int i = 0; i < data.size(); i++) {
				Entry entry = new Entry(data.get(i), "todo");
				add(entry);
			}
		}
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.drawLine(VIEW_WIDTH/4, LINE_Y, VIEW_WIDTH, LINE_Y);
	}
}
