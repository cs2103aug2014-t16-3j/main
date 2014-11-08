//@author A0108358B
package udo.util.shared;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.text.SimpleDateFormat;

import javax.swing.KeyStroke;

/**
 * 
 * This class holds all constants that the program uses. (the magic numbers) The
 * constants are further classified in their own subclasses. Add your own
 * subclass here to store all your strings and values and such.
 * 
 */
public class Constants {
	
	//@author A0108358B
	/**
	 * 
	 * This class holds all the keys used for the data storage objects. Please
	 * reference these values rather than typing your own key. This is to
	 * standardise across components so that one component can retrieve the data
	 * that another puts in.
	 * 
	 */
	public static final class Keys {
		public static final String UID = "uid";
		public static final String TITLE = "title";
		public static final String DUE = "due"; 			// for task
		public static final String DONE = "done"; 			// for task
		public static final String START = "start"; 		// events
		public static final String END = "end"; 			// event
		public static final String HASHTAG = "hashtag"; 	// for list
		public static final String HASHTAGS = "hashtags"; 	// for parser add
		public static final String ITEM = "item";
		public static final String ITEMS = "items";
		public static final String QUERY_TYPE = "query type";
		public static final String QUERY_VALUE = "query value";
		public static final String FIELD = "field";
		public static final String VALUE = "value";
		public static final String DATE = "date";
		public static final String OLD_VALUE = "Old value";
		public static final String SEARCH_QUERY = "search query";
	}

	//@author A0108358B
	/**
	 * 
	 * This class holds the array index values for the file manager usage. If you
	 * desire to put your own values inside here, make sure that the new
	 * variable name doesnt conflict with existing names. If there is potential
	 * confusion (e.g. your title index is different from the existing index)
	 * pls sound out first.
	 * 
	 */
	public static final class StorageIndices {
		// all
		public static final int UID = 0;
		public static final int TYPE = 1;
		public static final int TITLE = 2;
		// event
		public static final int START_DATE = 3;
		public static final int START_TIME = 4;
		public static final int END_DATE = 5;
		public static final int END_TIME = 6;
		public static final int EVENT_HASHTAGS = 7;
		// task
		public static final int DUE_DATE = 3;
		public static final int DUE_TIME = 4;
		public static final int TASK_DONE = 5;
		public static final int TASK_HASHTAGS = 6;
		//plan
		public static final int PLAN_DONE = 3;
		public static final int PLAN_HASHTAGS = 4;
	}
	
	//@author A0108358B
	/**
	 * 
	 * This class holds the magic strings for the storage and retrieving of data
	 * from the storage file. This includes the toString method of the ItemData
	 * class
	 * 
	 */
	public static final class StorageStrings {
		public static final String FILEPATH = "data/";
		public static final String FILENAME = "data/uDo_data.txt";
		public static final String FIELD_DELIMITER = "\\|\\|\\|";
		public static final String DATE_DELIMITER = "/";
		public static final String TIME_DELIMITER = ":";
		public static final String TAG_DELIMITER = ",";
		public static final String TYPE_EVENT = "EVENT";
		public static final String TYPE_TASK = "TASK";
		public static final String TYPE_PLAN = "PLAN";
		public static final String UNFORMATTED_STRING_EVENT = "%1$d|||%2$s|||%3$s|||%4$d/%5$d/%6$d|||%7$d:%8$d|||%9$d/%10$d/%11$d|||%12$d:%13$d|||%14$s|||;";
		public static final String UNFORMATTED_STRING_TASK = "%1$d|||%2$s|||%3$s|||%4$d/%5$d/%6$d|||%7$d:%8$d|||%9$b|||%10$s|||;";
		public static final String UNFORMATTED_STRING_PLAN = "%1$d|||%2$s|||%3$s|||%4$b|||%5$s|||;";
	}
	
	//@author A0108358B
	/**
	 * 
	 * This class holds the magic strings for the loggers.
	 * They include filenames and paths
	 * 
	 */
	public static final class LoggingStrings {
		public static final String LOGPATH_ENGINE = "logs/engine/";
		public static final String LOGFILE_ENGINE = LOGPATH_ENGINE + "engineLog.log";
		public static final String LOGPATH_UI = "logs/ui/";
		public static final String LOGFILE_UI = LOGPATH_UI + "uiLog.log";
	}
	
	//@author A0108358B
	/**
	 * 
	 * This class holds the magic numebrs for the main program.
	 * 
	 */
	public static final class MainVars {
		public static final int EXIT_STATUS_OK = 0;
		public static final int EXIT_STATUS_NOT_OK = -1;
		public static final int DAYS_IN_ADVANCE = 3;
	}

	//@author A0114088H
	/**
	 * 
	 * This class holds UI-related attributes such as fonts, measurements, types of entry and colours.
	 *
	 */
	public static final class UI {
		public static final KeyStroke ALT_Q = KeyStroke.getKeyStroke("alt Q");
		public static final KeyStroke ALT_A = KeyStroke.getKeyStroke("alt A");
		public static final KeyStroke ALT_W = KeyStroke.getKeyStroke("alt W");
		public static final KeyStroke ALT_S = KeyStroke.getKeyStroke("alt S");
		public static final KeyStroke ALT_E = KeyStroke.getKeyStroke("alt E");
		public static final KeyStroke ALT_D = KeyStroke.getKeyStroke("alt D");
		public static final KeyStroke ENTER = KeyStroke.getKeyStroke("ENTER");
		public static final KeyStroke UP = KeyStroke.getKeyStroke("UP");
		public static final KeyStroke DOWN = KeyStroke.getKeyStroke("DOWN");
		
		public static final int FONTSIZE_24 = 24;
		public static final int FONTSIZE_20 = 20;
		public static final int FONTSIZE_18 = 18;
		public static final int FONTSIZE_16 = 16;
		public static final int FONTSIZE_14 = 14;
		public static final int FONTSIZE_12 = 12;
		public static final Font FONT_24 = new Font("Ubuntu", Font.PLAIN, FONTSIZE_24);
		public static final Font FONT_20 = new Font("Ubuntu", Font.PLAIN, FONTSIZE_20);
		public static final Font FONT_18 = new Font("Ubuntu", Font.PLAIN, FONTSIZE_18);
		public static final Font FONT_16 = new Font("Ubuntu", Font.PLAIN, FONTSIZE_16);
		public static final Font FONT_14 = new Font("Ubuntu", Font.PLAIN, FONTSIZE_14);
		public static final Font FONT_12 = new Font("Ubuntu", Font.PLAIN, FONTSIZE_12);
		public static final Font FONT_20_BOLD = new Font("Ubuntu", Font.BOLD, FONTSIZE_20);
		public static final Font FONT_12_BOLD = new Font("Ubuntu", Font.BOLD, FONTSIZE_12);
		
		public static final SimpleDateFormat DD_MM_YY = new SimpleDateFormat("dd/MM/yy");
		public static final SimpleDateFormat DD_MMMM_YYYY = new SimpleDateFormat("dd MMMM yyyy");
		public static final SimpleDateFormat DD_MMMM = new SimpleDateFormat("dd MMMM");
		public static final SimpleDateFormat DD_MMM = new SimpleDateFormat(" dd MMM ");
		public static final SimpleDateFormat DD = new SimpleDateFormat("dd");
		public static final SimpleDateFormat MMM = new SimpleDateFormat("MMM");
		public static final SimpleDateFormat MMMM = new SimpleDateFormat("MMMM");
		public static final SimpleDateFormat YYYY = new SimpleDateFormat("yyyy");
		public static final SimpleDateFormat DAY_NAME = new SimpleDateFormat("EEEE");
		public static final SimpleDateFormat HOUR_12 = new SimpleDateFormat("hh:mm a");
		
//		public static final int MAIN_WIDTH = 400; // or 360
		public static final int MAIN_WIDTH = 350; // for presentation purposes
		public static final int MAIN_HEIGHT = 600;
		public static final int MAIN_PADDING = 8;
		public static final int TEXTFIELD_HEIGHT = 32;
//		public static final int SUBVIEW_WIDTH = 360; // or 300
		public static final int SUBVIEW_WIDTH = 290; // This is only for presentation
		public static final int SUBVIEW_HEIGHT = 550;
		public static final int SUBVIEW_HEADER_LINEY = 40;
		public static final int SIDEVIEW_PADDING = 30;
		
		public static final String UDO_LOGO_IMG_DIR_256 = "/uDoLogo_256x256.png";
		public static final String UDO_LOGO_IMG_DIR_64 = "/uDoLogo_64x64.png";
		public static final String UDO_LOGO_IMG_DIR_32 = "/uDoLogo_32x32.png";
		public static final String UDO_LOGO_IMG_DIR_16 = "/uDoLogo_16x16.png";
		
		public static final String CLOSE_BUTTON = "/closeButton.png";
		public static final String CLOSE_BUTTON_HOVER = "/closeButtonHover.png";
		
		public static final int DAYVIEW_HEADER_HEIGHT = 100;
		public static final String TICKER_IMAGE_DIR = "/dayViewTicker.png";
		public static final int TICKER_Y = 18;

		public static final int TODOVIEW_HEADER_HEIGHT = 40;
		
		public static final int ENTRY_SEPARATOR_HEIGHT = 40;
		public static final int ENTRY_SEPARATOR_WIDTH = -5;
		public static final int ENTRY_TIMEPANEL_WIDTH = 58;
		public static final Dimension EMPTY_DATE = new Dimension(28,44);
		public static final Dimension DONE_PANEL = new Dimension(28,28);
		
		public static final String ENTRY_ALL = "ALL";
		public static final String ENTRY_EVENT = "EVENT";
		public static final String ENTRY_TASK = "TASK";
		public static final String ENTRY_PLAN = "PLAN";
		
		public static final Color MAIN_COLOR = Color.decode("#FFFFFF");
		public static final Color MAIN_BORDER_COLOR = Color.decode("#E3E3E3");
		public static final Color SUB_COLOR = Color.decode("#F4F4FF");
		public static final Color ENTRY_DONE_COLOR = Color.decode("#46FF82");
		public static final Color ENTRY_BORDERCOLOR = new Color(0,0,0);
		public static final Color ENTRY_TITLE_COLOR = new Color(0,0,0);
		public static final Color ENTRY_HASHTAGS_COLOR = new Color(150,150,150);
		public static final Color ENTRY_DATE_COLOR = new Color(100,100,0);
		
		public static final Color UID_COLOR = Color.decode("#F4F4FF");
		public static final Color EVENT_COLOR = Color.decode("#6A828A");
		public static final Color PLAN_COLOR = Color.decode("#CDFF00");
		public static final Color TASK_COLOR = Color.decode("#FF3B77");
		
		public static final Color POPUP_BGCOLOR = new Color(100,100,100);
		public static final Color POPUP_FGCOLOR = new Color(255,255,255);
		
		public static final int SCROLLBAR_INCREMENT = 135;
		
	}
}
