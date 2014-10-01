package udo.util.shared;

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
	public static class Keys {
		public static final String UID = "uid";
		public static final String TITLE = "title";
		public static final String DUE = "due"; // for task
		public static final String DONE = "done"; // for task
		public static final String START = "start"; // events
		public static final String END = "end"; // event
		public static final String HASHTAG = "hashtag"; // for list
		public static final String HASHTAGS = "hashtags"; // for parser add
		public static final String ITEM = "item";
		public static final String ITEMS = "items";
		public static final String QUERY = "query";
		public static final String DELETE = "delete";
	}

	/**
	 * 
	 * This class holds the array index values for the file manager. If you
	 * desire to put your own values inside here, make sure that the new
	 * variable name doesnt conflict with existing names. If there is potential
	 * confusion (e.g. your title index is different from the existing index)
	 * pls sound out first.
	 * 
	 */
	public static class Indices {
		public static final int UID = 0;
		public static final int TYPE = 1;
		public static final int TITLE = 2;
		public static final int START_DATE = 3;
		public static final int START_TIME = 4;
		public static final int END_DATE = 5;
		public static final int END_TIME = 6;
		public static final int HASHTAGS = 7;
	}

	/**
	 * 
	 * This class holds the magic strings for the storage and retrieving of data
	 * from the storage file. This includes the toString method of the ItemData
	 * class
	 * 
	 */
	public static class StorageStrings {
		public static final String FILENAME = "uDo_data.txt";
		public static final String FIELD_DELIMITER = "\\|\\|\\|";
		public static final String DATE_DELIMITER = "/";
		public static final String TIME_DELIMITER = ":";
		public static final String TAG_DELIMITER = ",";
		public static final String TYPE_EVENT = "EVENT";
		public static final String TYPE_TASK = "TASK";
		public static final String TYPE_PLAN = "PLAN";
	}
}
