package udo.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class UserInterface implements ActionListener {
	
	private JFrame frame = new JFrame("uDo");
	private JLayeredPane layer = new JLayeredPane();
	private JPanel textPanel = new JPanel(new GridBagLayout());
	private JTextArea textArea = new JTextArea(20,40);
	private JScrollPane scrollPane = new JScrollPane(textArea);
	private JFormattedTextField textField = new JFormattedTextField();
	private JLabel popup = new JLabel();
	
	private static final int HEIGHT = 400;
	private static final int WIDTH = 400;
	
	private Timer t;
	private int alpha;
	
	public UserInterface(){
		
		initUI();
	}
	
	public void initUI(){
		
		/**
		 * Sets up layer
		 */
		layer.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		
		/**
		 * Sets up textArea
		 */
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
		textArea.setEditable(false);
		
		/**
		 * Sets up textField
		 */
		textField.setColumns(20);
		textField.addActionListener(this);
		
		/**
		 * Sets up textPanel
		 */
		textPanel.setBounds(0, 0, WIDTH, HEIGHT);
		
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0.5;
		c.weighty = 0.5;
		
		scrollPane.getViewport().add(textArea);
		textPanel.add(scrollPane, c);
		
		c.gridy = 1;
		c.weighty = 0;
		
		textPanel.add(textField, c);
		
		layer.add(textPanel, new Integer(0));
		
		/**
		 * Sets up popup
		 */
		popup.setOpaque(true);
		popup.setFont(new Font("Georgia", Font.PLAIN, 14));
        popup.setBackground(Color.black);
        popup.setForeground(Color.white);
        popup.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        
        layer.add(popup, new Integer(1));
		
		/**
		 * Sets up the frame
		 */
		frame.setSize(new Dimension(WIDTH, HEIGHT));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(layer);
		frame.setLocationRelativeTo(null);
		frame.pack();
		frame.setVisible(true);
	}

	@Override
	/**
	 * actionPerformed when user press enter on textField.
	 */
	public void actionPerformed(ActionEvent arg0) {
		
		String text = textField.getText();
		
		showPopup(text);
	}
	
	
	/**
	 * show popup as feedback to user.
	 * @param text it is the text to be shown to user (from FeedBack class)
	 */
	public void showPopup(String text){

		FontMetrics fm = popup.getFontMetrics(popup.getFont());
		int padding = 5;
		int height = fm.getHeight() + padding;
		int width = fm.stringWidth(text) + padding;
		int x = WIDTH/2 - width/2;
		int y = HEIGHT - textField.getHeight() - height - padding;
		popup.setText(text);
		popup.setHorizontalAlignment(SwingConstants.CENTER);
		popup.setBounds(x, y, width, height);
		fadePopup();
	}
	
	public void fadePopup(){

		t = new Timer(10, new ActionListener(){
			boolean fadeIn = true;
			@Override
			public void actionPerformed(ActionEvent e){
				popup.setForeground(new Color(255,255,255,alpha++));
                popup.setBackground(new Color(255,255,255,alpha));
                popup.setBorder(BorderFactory.createLineBorder(new Color(255,255,255,alpha)));
                if(alpha==255) t.stop();
			}
			
		});
		t.setInitialDelay(2000);
		t.start();
		
	}
	
	/**
	 * The following main method is to test and see the UI.
	 * It will be deleted/ commented out when
	 * the uDo main method is run.
	 */
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				UserInterface newUI = new UserInterface();
			}
		});
	}

	
}
