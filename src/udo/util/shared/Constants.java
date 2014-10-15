package udo.util.shared;

import java.awt.Color;
import java.awt.Font;

/**
 * 
 * This class holds all constants that the program uses. (the magic numbers) The
 * constants are further classified in their own subclasses. Add your own
 * subclass here to store all your strings and values and such.
 * 
 */
public class Constants {

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
		public static final String QUERY = "query string";
		public static final String FIELD = "field";
		public static final String VALUE = "value";
	}

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
		public static final int TASK_HASHTAGS = 5;
		//plan
		public static final int PLAN_HASHTAGS = 3;
	}

	/**
	 * 
	 * This class holds the magic strings for the storage and retrieving of data
	 * from the storage file. This includes the toString method of the ItemData
	 * class
	 * 
	 */
	public static final class StorageStrings {
		public static final String FILENAME = "uDo_data.txt";
		public static final String FIELD_DELIMITER = "\\|\\|\\|";
		public static final String DATE_DELIMITER = "/";
		public static final String TIME_DELIMITER = ":";
		public static final String TAG_DELIMITER = ",";
		public static final String TYPE_EVENT = "EVENT";
		public static final String TYPE_TASK = "TASK";
		public static final String TYPE_PLAN = "PLAN";
		public static final String UNFORMATTED_STRING_EVENT = "%1$d|||%2$s|||%3$s|||%4$d/%5$d/%6$d|||%7$d:%8$d|||%9$d/%10$d/%11$d|||%12$d:%13$d|||%14$s";
		public static final String UNFORMATTED_STRING_TASK = "%1$d|||%2$s|||%3$s|||%4$d/%5$d/%6$d|||%7$d:%8$d|||%9$b|||%10$s";
		public static final String UNFORMATTED_STRING_PLAN = "%1$d|||%2$s|||%3$s|||%5$b|||%5$s";
	}
	
	/**
	 * 
	 * This class holds UI-related attributes such as fonts, measurements, types of entry and colours.
	 *
	 */
	public static final class UI {
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
		
		public static final int MAIN_WIDTH = 400;
		public static final int MAIN_HEIGHT = 600;
		public static final int SUBVIEW_WIDTH = 360;
		public static final int SUBVIEW_HEIGHT = 550;
		public static final int SUBVIEW_HEADER_LINEY = 33;
		public static final int SIDEVIEW_PADDING = 30;
		
		public static final int DAYVIEW_HEADER_HEIGHT = 100;

		public static final int TODOVIEW_HEADER_HEIGHT = 40;
		
		public static final int ENTRY_SEPARATOR_HEIGHT = 40;
		public static final int ENTRY_SEPARATOR_WIDTH = -5;
		
		public static final String ENTRY_ALL = "ALL";
		public static final String ENTRY_EVENT = "EVENT";
		public static final String ENTRY_TASK = "TASK";
		public static final String ENTRY_PLAN = "PLAN";
		
		public static final Color MAIN_COLOR = new Color(255,255,255);
		//public static final Color SUBVIEW_COLOR = new Color();
		public static final Color ENTRY_BGCOLOR = new Color(255,255,255);
		public static final Color ENTRY_BORDERCOLOR = new Color(0,0,0);
		
		public static final Color UID_COLOR = new Color(238,238,238);
		public static final Color SEPARATOR_COLOR = new Color(100,100,100);
		
		public static final Color POPUP_BGCOLOR = new Color(100,100,100);
		public static final Color POPUP_FGCOLOR = new Color(255,255,255);
		
	}
}
