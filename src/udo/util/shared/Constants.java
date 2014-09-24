package udo.util.shared;

public class Constants {
	
	public static class Keys {
		public static final String UID = "uid";
		public static final String TITLE = "title";
		public static final String DUE = "due";
		public static final String DONE = "done";
		public static final String START = "start";
		public static final String END = "end";
		public static final String HASHTAGS = "hashtags";
		public static final String ITEM = "item";
	}
	
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
	
	public static class StorageStrings {
		public static final String FILENAME = "uDo_data.txt";
		public static final String FIELD_DELIMITER = "\\|\\|\\|";
		public static final String DATE_DELIMITER = "/";
		public static final String TIME_DELIMITER = ":";
		public static final String TAG_DELIMITER= ",";
		public static final String TYPE_EVENT = "EVENT";
		public static final String TYPE_TASK = "TASK";
		public static final String TYPE_PLAN = "PLAN";
		public static final String TIME_AM = "AM";
		public static final String TIME_PM = "PM";
	}
}
