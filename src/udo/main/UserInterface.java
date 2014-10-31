package udo.main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import udo.util.shared.Constants.UI;
import udo.util.shared.ItemData;
import udo.util.shared.OutputData;
import udo.util.ui.Feedback;
import udo.util.ui.uDoPopup;

public class UserInterface implements ActionListener {

	private static UserInterface mUserInterface;
	
	private JFrame mFrame = new JFrame("uDo");
	private JLayeredPane mMainViewLayer = new JLayeredPane();
	private JPanel mTextPanel = new JPanel(new GridBagLayout());
	private JScrollPane mScrollPane = new JScrollPane();
	private JPanel mMainView = new JPanel();
	private JPanel mRightView = new JPanel();
	private JPanel mLeftView = new JPanel();
	private JFormattedTextField mTextField = new JFormattedTextField();
	private uDoPopup mPopup = new uDoPopup();
	
	private int mPosX = 0, mPosY = 0;

	private Timer mTimer;
	private Timer mExistingTimer;
	private volatile boolean mWaiting;
	private String mUserInput;

	private Feedback fb;

	public static UserInterface getInstance() {
		if(mUserInterface == null) {
			mUserInterface = new UserInterface();
		}
		return mUserInterface;
	}
	private UserInterface() {


		fb = new Feedback();
		initUI();
	}

	private void initUI() {
		/**
		 * Sets up font
		 */
		try {
		     GraphicsEnvironment ge = 
		         GraphicsEnvironment.getLocalGraphicsEnvironment();
		     ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("fonts/Ubuntu-R.TTF")));
		} catch (IOException|FontFormatException e) {
		     //Handle exception
		}
		
		/**
		 * Sets up layer
		 */
		mMainViewLayer.setPreferredSize(new Dimension(UI.MAIN_WIDTH, UI.MAIN_HEIGHT));

		/**
		 * Sets up textArea
		 */
		mMainView.setOpaque(false);
		mScrollPane.getViewport().setBackground(UI.MAIN_COLOR);
		mScrollPane.getViewport().add(mMainView);

		/**
		 * Sets up textField
		 */
		mTextField.setColumns(20);
		mTextField.addActionListener(this);
		mTextField.setBackground(UI.MAIN_COLOR);
		mTextField.setFont(UI.FONT_16);
		setKeyBinds();
		mTextField.requestFocus();

		/**
		 * Sets up textPanel
		 */
		mTextPanel.setBounds(0, 0, UI.MAIN_WIDTH, UI.MAIN_HEIGHT);

		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1;
		c.weighty = 0.5;

		mTextPanel.add(mScrollPane, c);

		c.gridy = 1;
		c.weighty = 0;

		mTextPanel.add(mTextField, c);
		mTextPanel.setBackground(UI.MAIN_COLOR);

		mMainViewLayer.add(mTextPanel, new Integer(0));

		/**
		 * Sets up popup
		 */

		mMainViewLayer.add(mPopup, new Integer(2));
		

		/**
		 * Sets up LeftView
		 */
		mLeftView.setPreferredSize(new Dimension(UI.MAIN_WIDTH - UI.SIDEVIEW_PADDING, UI.MAIN_HEIGHT));
		mLeftView.setBackground(UI.SUB_COLOR);

		/**
		 * Sets up RightView
		 */
		mRightView.setPreferredSize(new Dimension(UI.MAIN_WIDTH - UI.SIDEVIEW_PADDING, UI.MAIN_HEIGHT));
		mRightView.setBackground(UI.SUB_COLOR);
		
		/**
		 * Sets up the frame
		 */
		mFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mFrame.setUndecorated(true);
		mFrame.addMouseListener(new MouseAdapter() {
		   public void mousePressed(MouseEvent e) {
		      mPosX=e.getX();
		      mPosY=e.getY();
		   }
		});
		mFrame.addMouseMotionListener(new MouseAdapter() {
		     public void mouseDragged(MouseEvent evt) {
				//sets frame position when mouse dragged			
				mFrame.setLocation (evt.getXOnScreen()-mPosX,evt.getYOnScreen()-mPosY);
		     }
		});
		
		List<Image> icons = new ArrayList<Image>();
		icons.add(new ImageIcon("img/uDoLogo_16x16.png").getImage());
		icons.add(new ImageIcon("img/uDoLogo_32x32.png").getImage());
		icons.add(new ImageIcon("img/uDoLogo_64x64.png").getImage());
		icons.add(new ImageIcon("img/uDoLogo_256x256.png").getImage());
		mFrame.setIconImages(icons);
		mFrame.add(mLeftView, BorderLayout.WEST);
		mFrame.add(mMainViewLayer, BorderLayout.CENTER);
		mFrame.add(mRightView, BorderLayout.EAST);
		mFrame.pack();
		mFrame.setLocationRelativeTo(null);
		mFrame.setVisible(true);
	}
	
	private void setKeyBinds() {
		mTextField.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put( UI.ALT_Q,
                "altQ" );
		mTextField.getActionMap().put("altQ", new AbstractAction() {
			private static final long serialVersionUID = 1L;
			
			public void actionPerformed(ActionEvent e) {
				final JScrollBar bar = getLeftSPane().getVerticalScrollBar();
				int currentValue = bar.getValue();
				bar.setValue(currentValue - UI.SCROLLBAR_INCREMENT);
			}
		});
		
		mTextField.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put( UI.ALT_A,
				"altA" );
		mTextField.getActionMap().put("altA", new AbstractAction() {
			private static final long serialVersionUID = 1L;
			
			public void actionPerformed(ActionEvent e) {
				final JScrollBar bar = getLeftSPane().getVerticalScrollBar();
				int currentValue = bar.getValue();
				bar.setValue(currentValue + UI.SCROLLBAR_INCREMENT);
			}
		});
		
		mTextField.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put( UI.ALT_W,
                "altW" );
		mTextField.getActionMap().put("altW", new AbstractAction() {
			private static final long serialVersionUID = 1L;
			
			public void actionPerformed(ActionEvent e) {
				final JScrollBar bar = getMainSPane().getVerticalScrollBar();
				int currentValue = bar.getValue();
				bar.setValue(currentValue - UI.SCROLLBAR_INCREMENT);
			}
		});
		
		mTextField.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put( UI.ALT_S,
                "altS" );
		mTextField.getActionMap().put("altS", new AbstractAction() {
			private static final long serialVersionUID = 1L;
			
			public void actionPerformed(ActionEvent e) {
				final JScrollBar bar = getMainSPane().getVerticalScrollBar();
				int currentValue = bar.getValue();
				bar.setValue(currentValue + UI.SCROLLBAR_INCREMENT);
			}
		});
		
		mTextField.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put( UI.ALT_E,
                "altE" );
		mTextField.getActionMap().put("altE", new AbstractAction() {
			private static final long serialVersionUID = 1L;
			
			public void actionPerformed(ActionEvent e) {
				final JScrollBar bar = getRightSPane().getVerticalScrollBar();
				int currentValue = bar.getValue();
				bar.setValue(currentValue - UI.SCROLLBAR_INCREMENT);
			}
		});
		
		mTextField.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put( UI.ALT_D,
                "altD" );
		mTextField.getActionMap().put("altD", new AbstractAction() {
			private static final long serialVersionUID = 1L;
			
			public void actionPerformed(ActionEvent e) {
				final JScrollBar bar = getRightSPane().getVerticalScrollBar();
				int currentValue = bar.getValue();
				bar.setValue(currentValue + UI.SCROLLBAR_INCREMENT);
			}
		});
	}
	
	@Override
	/**
	 * actionPerformed when user press enter on textField.
	 * Instead of the following, it should return input to main.java
	 */
	public void actionPerformed(ActionEvent arg0) {

		String text = mTextField.getText();
		mTextField.setText("");

		mUserInput = text;
		mWaiting = false;
	}

	/**
	 * getInput takes the user input and returns it to main
	 * 
	 * @return the String userInput
	 */
	public String getInput() {
		mWaiting = true;
		while (mWaiting) {
			
		}
		return mUserInput;
	}

	public void updateTodayScreen(ArrayList<ItemData> data) {
		mFrame.remove(mRightView);
		mRightView.add(fb.getTodayView(data), BorderLayout.CENTER);
		mFrame.add(mRightView, BorderLayout.EAST);
		mFrame.revalidate();
	}
	
	public void updateTodoScreen(ArrayList<ItemData> data) {
		mFrame.remove(mLeftView);
		mLeftView.add(fb.getToDoView(data), BorderLayout.CENTER);
		mFrame.add(mLeftView, BorderLayout.WEST);
		mFrame.revalidate();
	}
	
	/**
	 * ui.show is to show the output sent by engine
	 */
	public void show(OutputData output) {
		fb.process(output);
		String outputString = fb.getCommand();
		
		mScrollPane.getViewport().removeAll();
		mMainView = fb.getFinalView();
		mScrollPane.getViewport().add(mMainView);

		showPopup(outputString);
	}

	/**
	 * show popup as feedback to user.
	 * 
	 * @param text
	 *            it is the text to be shown to user (from FeedBack class)
	 */
	private void showPopup(String text) {

		FontMetrics fm = mPopup.getFontMetrics(mPopup.getFont());
		int padding = 5;
		int height = fm.getHeight() + padding;
		int width = fm.stringWidth(text) + padding;
		int x = UI.MAIN_WIDTH / 2 - width / 2;
		int y = UI.MAIN_HEIGHT - mTextField.getHeight() - height - padding;
		mPopup.setText(text);
		mPopup.setHorizontalAlignment(SwingConstants.CENTER);
		mPopup.setBounds(x, y, width, height);
		fadePopup();
	}

	private void fadePopup() {
		if (mExistingTimer != null)
			mExistingTimer.stop();
		mTimer = new Timer(10, new ActionListener() {
			int fade = -1;

			@Override
			public void actionPerformed(ActionEvent e) {
				float alpha = mPopup.getAlpha();
				if (fade < 0) {
					alpha += 0.05f;
					if (alpha < 1) {
						mPopup.setAlpha(alpha);
					} else {
						fade++;
					}
				} else if (fade == 0) {
					mTimer.setDelay(3000);
					fade++;
				} else {
					mTimer.setDelay(10);
					alpha -= 0.05f;
					if (alpha > 0) {
						mPopup.setAlpha(alpha);
					} else {
						mExistingTimer = null;
						mTimer.stop();
					}
				}
			}

		});
		mExistingTimer = mTimer;
		mTimer.start();

	}
	
	private JScrollPane getLeftSPane() {
		return fb.getLeftScrollPane();
	}
	
	private JScrollPane getRightSPane() {
		return fb.getRightScrollPane();
	}
	
	private JScrollPane getMainSPane() {
		if(fb.getMainScrollPane() == null) {
			return mScrollPane;
		} else {
			return fb.getMainScrollPane();
		}
	}

}
