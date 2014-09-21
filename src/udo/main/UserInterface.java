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

import udo.uiItems.FadeLabel;

public class UserInterface implements ActionListener {
	
	private JFrame mFrame = new JFrame("uDo");
	private JLayeredPane mLayer = new JLayeredPane();
	private JPanel mTextPanel = new JPanel(new GridBagLayout());
	private JTextArea mTextArea = new JTextArea(20,40);
	private JScrollPane mScrollPane = new JScrollPane(mTextArea);
	private JFormattedTextField mTextField = new JFormattedTextField();
	private FadeLabel mPopup = new FadeLabel();
	
	private static final int HEIGHT = 400;
	private static final int WIDTH = 400;
	
	private Timer mTimer;
	private float mDirection = -0.05f;
	
	public UserInterface(){
		
		initUI();
	}
	
	public void initUI(){
		
		/**
		 * Sets up layer
		 */
		mLayer.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		
		/**
		 * Sets up textArea
		 */
		mTextArea.setLineWrap(true);
		mTextArea.setWrapStyleWord(true);
		mTextArea.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
		mTextArea.setEditable(false);
		
		/**
		 * Sets up textField
		 */
		mTextField.setColumns(20);
		mTextField.addActionListener(this);
		
		/**
		 * Sets up textPanel
		 */
		mTextPanel.setBounds(0, 0, WIDTH, HEIGHT);
		
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0.5;
		c.weighty = 0.5;
		
		mScrollPane.getViewport().add(mTextArea);
		mTextPanel.add(mScrollPane, c);
		
		c.gridy = 1;
		c.weighty = 0;
		
		mTextPanel.add(mTextField, c);
		
		mLayer.add(mTextPanel, new Integer(0));
		
		/**
		 * Sets up popup
		 */
        
        mLayer.add(mPopup, new Integer(1));
		
		/**
		 * Sets up the frame
		 */
		mFrame.setSize(new Dimension(WIDTH, HEIGHT));
		mFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mFrame.add(mLayer);
		mFrame.setLocationRelativeTo(null);
		mFrame.pack();
		mFrame.setVisible(true);
	}

	@Override
	/**
	 * actionPerformed when user press enter on textField.
	 */
	public void actionPerformed(ActionEvent arg0) {
		
		String text = mTextField.getText();
		
		showPopup(text);
	}
	
	
	/**
	 * show popup as feedback to user.
	 * @param text it is the text to be shown to user (from FeedBack class)
	 */
	public void showPopup(String text){

		FontMetrics fm = mPopup.getFontMetrics(mPopup.getFont());
		int padding = 5;
		int height = fm.getHeight() + padding;
		int width = fm.stringWidth(text) + padding;
		int x = WIDTH/2 - width/2;
		int y = HEIGHT - mTextField.getHeight() - height - padding;
		mPopup.setText(text);
		mPopup.setHorizontalAlignment(SwingConstants.CENTER);
		mPopup.setBounds(x, y, width, height);
		fadePopup();
	}
	
	public void fadePopup(){

		mTimer = new Timer(100, new ActionListener(){
			boolean fadeIn = true;
			@Override
			public void actionPerformed(ActionEvent e){
				 float alpha = mPopup.getAlpha();
                 alpha += mDirection;
                 if (alpha < 0) {
                     alpha = 0;
                     mDirection = 0.05f;
                 } else if (alpha > 1) {
                     alpha = 1;
                     mDirection = -0.05f;
                 }
                 mPopup.setAlpha(alpha);
			}
			
		});
		mTimer.setInitialDelay(2000);
		mTimer.start();
		
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
