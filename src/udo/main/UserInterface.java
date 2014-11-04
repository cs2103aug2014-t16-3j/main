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
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.Border;

import com.sun.awt.AWTUtilities;

import udo.util.shared.Constants.UI;
import udo.util.shared.ItemData;
import udo.util.shared.OutputData;
import udo.util.ui.DropShadowPanel;
import udo.util.ui.Feedback;
import udo.util.ui.WrapLayout;
import udo.util.ui.uDoPopup;

public class UserInterface implements ActionListener {

	private static UserInterface mUserInterface;

	private JFrame mFrame = new JFrame("uDo");
	private JLayeredPane mMainViewLayer = new JLayeredPane();
	private JPanel mTextPanel = new JPanel(new GridBagLayout());
	private DropShadowPanel mShadowPanel = new DropShadowPanel(10);
	private JPanel mTopBar = new JPanel();
	private JPanel mMainView = new JPanel();
	private JPanel mRightView = new JPanel();
	private JPanel mLeftView = new JPanel();
	private JFormattedTextField mTextField = new JFormattedTextField();
	private uDoPopup mPopup = new uDoPopup();

	private BufferedImage mUdoImg;
	private JLabel mUdoLogo;
	private JButton mCloseButton = new JButton();

	private int mPosX = 0, mPosY = 0;

	private Timer mTimer;
	private Timer mExistingTimer;
	private volatile boolean mWaiting;
	private String mUserInput;

	private Feedback fb;

	public static UserInterface getInstance() {
		if (mUserInterface == null) {
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
			GraphicsEnvironment ge = GraphicsEnvironment
					.getLocalGraphicsEnvironment();
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File(
					"fonts/Ubuntu-R.TTF")));
		} catch (IOException | FontFormatException e) {
			// Handle exception
		}

		/**
		 * Sets up the bar at the top
		 */
		mTopBar.setLayout(new BorderLayout());
		mTopBar.setBackground(UI.SUB_COLOR);
		initUdoLogo();
		initCloseButton();

		/**
		 * Sets up layer
		 */
		mMainViewLayer.setPreferredSize(new Dimension(UI.MAIN_WIDTH,
				UI.MAIN_HEIGHT));

		/**
		 * Sets up textArea
		 */
		mMainView.setOpaque(false);
		WrapLayout wl = new WrapLayout();
		wl.setVgap(0);
		mMainView.setLayout(wl);
		Border lineBorder = BorderFactory
				.createLineBorder(UI.MAIN_BORDER_COLOR);
		Border padding = BorderFactory.createEmptyBorder(0, UI.MAIN_PADDING, 0,
				0);
		mMainView.setBorder(BorderFactory.createCompoundBorder(lineBorder,
				padding));
		setWelcomeScreen();

		/**
		 * Sets up textField
		 */
		mTextField.setColumns(20);
		mTextField.addActionListener(this);
		mTextField.setBackground(UI.MAIN_COLOR);
		mTextField.setFont(UI.FONT_16);
		setKeyBinds();

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

		mTextPanel.add(mMainView, c);

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
		mLeftView.setPreferredSize(new Dimension(UI.MAIN_WIDTH
				- UI.SIDEVIEW_PADDING, UI.MAIN_HEIGHT));
		mLeftView.setBackground(UI.SUB_COLOR);

		/**
		 * Sets up RightView
		 */
		mRightView.setPreferredSize(new Dimension(UI.MAIN_WIDTH
				- UI.SIDEVIEW_PADDING, UI.MAIN_HEIGHT));
		mRightView.setBackground(UI.SUB_COLOR);

		/**
		 * Sets up the frame
		 */
		mFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mFrame.setUndecorated(true);
		mFrame.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				mPosX = e.getX();
				mPosY = e.getY();
			}
		});
		mFrame.addMouseMotionListener(new MouseAdapter() {
			public void mouseDragged(MouseEvent evt) {
				// sets frame position when mouse dragged
				mFrame.setLocation(evt.getXOnScreen() - mPosX,
						evt.getYOnScreen() - mPosY);
			}
		});

		List<Image> icons = new ArrayList<Image>();
		icons.add(new ImageIcon(UI.UDO_LOGO_IMG_DIR_256).getImage());
		icons.add(new ImageIcon(UI.UDO_LOGO_IMG_DIR_64).getImage());
		icons.add(new ImageIcon(UI.UDO_LOGO_IMG_DIR_32).getImage());
		icons.add(new ImageIcon(UI.UDO_LOGO_IMG_DIR_16).getImage());
		mFrame.setIconImages(icons);
		mShadowPanel.add(mTopBar, BorderLayout.NORTH);
		mShadowPanel.add(mLeftView, BorderLayout.WEST);
		mShadowPanel.add(mMainViewLayer, BorderLayout.CENTER);
		mShadowPanel.add(mRightView, BorderLayout.EAST);
		mFrame.add(mShadowPanel);
		AWTUtilities.setWindowOpaque(mFrame, false);
		mFrame.pack();
		mTextField.requestFocus();
		mFrame.setLocationRelativeTo(null);
		mFrame.setVisible(true);
	}

	private void initCloseButton() {
		mCloseButton.setBorderPainted(false);
		mCloseButton.setContentAreaFilled(false);
		mCloseButton.setFocusPainted(false);
		mCloseButton.setOpaque(false);
		mCloseButton.setPreferredSize(new Dimension(32, 32));
		mCloseButton.setIcon(new ImageIcon(UI.CLOSE_BUTTON));
		mCloseButton.setRolloverIcon(new ImageIcon(UI.CLOSE_BUTTON_HOVER));
		mCloseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mFrame.dispose();
				System.exit(0);
			}
		});
		mTopBar.add(mCloseButton, BorderLayout.EAST);

	}

	private void initUdoLogo() {
		try {
			mUdoImg = ImageIO.read(new File(UI.UDO_LOGO_IMG_DIR_32));
		} catch (IOException ex) {
			// handle exception...
		}
		mUdoLogo = new JLabel(new ImageIcon(mUdoImg));
		int padding = 4;
		mUdoLogo.setPreferredSize(new Dimension(mUdoImg.getWidth(), mUdoImg
				.getHeight() + padding));
		mTopBar.add(mUdoLogo);

	}

	private void setKeyBinds() {
		mTextField.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(UI.ALT_Q,
				"altQ");
		mTextField.getActionMap().put("altQ", new AbstractAction() {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				final JScrollBar bar = getLeftSPane().getVerticalScrollBar();
				int currentValue = bar.getValue();
				bar.setValue(currentValue - UI.SCROLLBAR_INCREMENT);
			}
		});

		mTextField.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(UI.ALT_A,
				"altA");
		mTextField.getActionMap().put("altA", new AbstractAction() {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				final JScrollBar bar = getLeftSPane().getVerticalScrollBar();
				int currentValue = bar.getValue();
				bar.setValue(currentValue + UI.SCROLLBAR_INCREMENT);
			}
		});

		mTextField.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(UI.ALT_W,
				"altW");
		mTextField.getActionMap().put("altW", new AbstractAction() {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				final JScrollBar bar = getMainSPane().getVerticalScrollBar();
				int currentValue = bar.getValue();
				bar.setValue(currentValue - UI.SCROLLBAR_INCREMENT);
			}
		});

		mTextField.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(UI.ALT_S,
				"altS");
		mTextField.getActionMap().put("altS", new AbstractAction() {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				final JScrollBar bar = getMainSPane().getVerticalScrollBar();
				int currentValue = bar.getValue();
				bar.setValue(currentValue + UI.SCROLLBAR_INCREMENT);
			}
		});

		mTextField.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(UI.ALT_E,
				"altE");
		mTextField.getActionMap().put("altE", new AbstractAction() {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				final JScrollBar bar = getRightSPane().getVerticalScrollBar();
				int currentValue = bar.getValue();
				bar.setValue(currentValue - UI.SCROLLBAR_INCREMENT);
			}
		});

		mTextField.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(UI.ALT_D,
				"altD");
		mTextField.getActionMap().put("altD", new AbstractAction() {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				final JScrollBar bar = getRightSPane().getVerticalScrollBar();
				int currentValue = bar.getValue();
				bar.setValue(currentValue + UI.SCROLLBAR_INCREMENT);
			}
		});

		mFrame.getRootPane()
				.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT)
				.put(UI.ENTER, "EnterPressed");
		mFrame.getRootPane().getActionMap()
				.put("EnterPressed", new AbstractAction() {
					private static final long serialVersionUID = 1L;

					public void actionPerformed(ActionEvent e) {
						mTextField.requestFocus();
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
		mRightView.removeAll();
		mRightView.add(fb.getTodayView(data), BorderLayout.CENTER);
		mFrame.revalidate();
	}

	public void updateTodoScreen(ArrayList<ItemData> data) {
		mLeftView.removeAll();
		mLeftView.add(fb.getToDoView(data), BorderLayout.CENTER);
		mFrame.revalidate();
	}

	/**
	 * ui.show is to show the output sent by engine
	 */
	public void show(OutputData output) {
		fb.process(output);
		String outputString = fb.getCommand();

		mMainView.removeAll();
		mMainView.add(fb.getFinalView(), BorderLayout.CENTER);
		mFrame.revalidate();

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
		return fb.getMainScrollPane();
	}

	private void setWelcomeScreen() {
		BufferedImage logoImg = null;
		try {
			logoImg = ImageIO.read(new File(UI.UDO_LOGO_IMG_DIR_256));
		} catch (IOException ex) {
			// handle exception...
		}
		JLabel logoLabel = new JLabel(new ImageIcon(logoImg));
		JLabel welcome = new JLabel();
		welcome.setFont(UI.FONT_20_BOLD);
		welcome.setText("<html>" + "<br><br>" + "Welcome to uDo!" + "</html>");
		welcome.setOpaque(false);
		JLabel welcome2 = new JLabel();
		welcome2.setFont(UI.FONT_16);
		welcome2.setForeground(UI.POPUP_BGCOLOR);
		welcome2.setText("<html>" + "<br>"
				+ "Start by typing instruction in the textbox below:"
				+ "</html>");
		welcome2.setOpaque(false);
		mMainView.add(logoLabel);
		mMainView.add(welcome);
		mMainView.add(welcome2);
	}

}
