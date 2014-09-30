package udo.util.ui;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class DayView extends JPanel{

	public DayView(){
		
	}

	public void testFont(){
		
		Font cpgothic = new Font("Copperplate Gothic Bold", Font.PLAIN, 24);
		JLabel label = new JLabel("18 September 2014");
		label.setFont(cpgothic);
		label.setHorizontalAlignment( SwingConstants.LEFT );
		label.setBounds(0,10, 300,100);
		add(label);
		JLabel day = new JLabel("Wednesday");
		day.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 18));
		day.setHorizontalAlignment(SwingConstants.RIGHT);
		day.setBounds(100, 10, 100, 100);
		add(day);
	}
}

