package udo.main;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import udo.util.shared.Command;
import udo.util.shared.InputData;
import udo.util.shared.Constants.Keys;

/**
 * (Class description)
 * 
 * @author chongjiawei
 * 
 */

public class Parser {

	private String mTitle;
	private Calendar mStartDate;
	private Calendar mEndDate;
	private Calendar mStartTime;
	private Calendar mEndTime;
	private ArrayList<String> mTags;
	private int mDeleteIndex;

	public Parser() {

	}

	public InputData getInputData(String input) { // InputData inputData =
													// parser.getInputData(input);
		Command type = determineCommandType(input);
		InputData data = processCommandType(type, input);
		return data;
	}

	public Command determineCommandType(String input) {
		String parts[] = input.split(" ");
		String command = parts[0];
		switch (command) {
		case "add":
			return Command.ADD_EVENT;
		case "list":
			return Command.LIST;
		case "delete":
			return Command.DELETE;
		case "save":
			return Command.SAVE;
		case "exit":
			return Command.EXIT;
		default:
			return null;
		}
	}

	public InputData processCommandType(Command commandType, String details) {
		switch (commandType) {
		case ADD_EVENT:
			return add(commandType, details);
		case LIST:
			return list(commandType, details);
		case DELETE:
			return delete(commandType, details);
		case SAVE:
			return save(commandType, details);
		case EXIT:
			return exit(commandType, details);
		default:
			return null;
		}
	}

	// add <title> <hashTags, if any> on <date> from <start time> to <end time>
	// whether it is an event or a task
	public InputData add(Command type, String details) {
		if (isValidAdd(details)) { // to do: check whether it is an event or a
									// task
			// to do: make date and time into 1 calendar object
			ArrayList<Calendar> listOfDates = getDate(details);
			ArrayList<Calendar> listOfTime = getTime(details);
			
			mTitle = getTitle(details);
			mTags = getTags(details);

			//will be refactored to event, task, plan
			int hour, mins;
			if (listOfDates.size() == 1 && listOfTime.size() == 1) {
				hour = listOfTime.get(0).HOUR;
				mins = listOfTime.get(0).MINUTE;
				listOfDates.get(0).set(Calendar.HOUR, hour);
				listOfDates.get(0).set(Calendar.MINUTE, mins);
			} else if (listOfDates.size() == 1 && listOfTime.size() == 2) {
				hour = listOfTime.get(0).HOUR;
				mins = listOfTime.get(0).MINUTE;
				listOfDates.get(0).set(Calendar.HOUR, hour);
				listOfDates.get(0).set(Calendar.MINUTE, mins);
				hour = listOfTime.get(1).HOUR;
				mins = listOfTime.get(1).MINUTE;
				listOfDates.get(0).set(Calendar.HOUR, hour);
				listOfDates.get(0).set(Calendar.MINUTE, mins);
			}

			InputData addInputData = new InputData(type);
			addInputData.put(Keys.TITLE, mTitle);
			// addInputData.put(Keys.START, mStartDate);
			// addInputData.put(Keys.END, mEndDate);
			addInputData.put(Keys.HASHTAGS, mTags);

			return addInputData;
		} else {
			return null;
		}
	}

	public boolean isValidAdd(String input) {
		if (input.length() < 4) {
			return false;
		} else if (getTitle(input).isEmpty()) {
			return false;
		} else {
			return true;
		}
	}

	public boolean isValidEvent(String input) { // checks for events. Otherwise,
												// we tag it as task
		return false;
	}

	public String getTitle(String input) {
		String title = input;
		if (input.contains("#")) {
			int hashtagIndex = input.indexOf("#");
			title = input.substring(0, hashtagIndex);
		} else {
			ArrayList<Calendar> listOfDates = getDate(input);
			ArrayList<Calendar> listOfTime = getTime(input);
			if (listOfDates.size() > 0 || listOfTime.size() > 0) {
				// keywords
				int fromStringIndex = input.lastIndexOf("from");
				int byStringIndex = input.lastIndexOf("by");
				int onStringIndex = input.lastIndexOf("on");

				if (fromStringIndex != -1) {
					title = input.substring(0, fromStringIndex);
				} else if (byStringIndex != -1) {
					title = input.substring(0, byStringIndex);
				} else if (onStringIndex != -1) {
					title = input.substring(0, onStringIndex);
				}
			}
		}
		return title;
	}

	// does this catch 3/4/14 ?
	/**
	 * Stores all dates from user input in an ArrayList<Calendar>
	 * returns an empty ArrayList<Calendar> if no dates are found
	 * requires users to use "/" instead of "-" when typing in dates
	 * requires user to type in year in dates. 
	 * For instance, 9/10 will not be accepted. Instead, it should be 9/10/14
	 * @param input directly from user
	 * @return an ArrayList of Calendar object
	 */
	public ArrayList<Calendar> getDate(String input) {
		ArrayList<Calendar> listOfDates = new ArrayList<Calendar>();
		Calendar cal = Calendar.getInstance();
		if (input.contains("/")) {
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			format.setLenient(false);
			Date date;

			int dayMonthSlashIndex;
			int monthYearSlashIndex;
			String dateSubstring = input;

			while (dateSubstring.contains("/")) {
				dayMonthSlashIndex = dateSubstring.indexOf("/");
				monthYearSlashIndex = dateSubstring.indexOf("/",
						dayMonthSlashIndex + 1);

				if (dateSubstring.indexOf("/") == dayMonthSlashIndex
						&& dayMonthSlashIndex + 3 == monthYearSlashIndex) {
					// problematic area
					dateSubstring = dateSubstring.substring(
							dayMonthSlashIndex - 2, monthYearSlashIndex + 5);
					dateSubstring = formatDateSubstring(dateSubstring);
					try {
						date = format.parse(dateSubstring);
						date.setMonth(date.getMonth() - 1);
					} catch (ParseException pe) {
						// Should I throw exception for this? If I shouldn't
						// what should I do instead?
						throw new IllegalArgumentException("The date entered, "
								+ dateSubstring + " is invalid.", pe);
					}
					cal.setTime(date);
					listOfDates.add(cal);
				}
				dateSubstring = dateSubstring.substring(dayMonthSlashIndex + 1);
			}
		}
		return listOfDates;
	}

	public String formatDateSubstring(String input) {
		String date = input.trim();
		int lastLetterIndex = date.length() - 1;
		String lastLetter = date.substring(lastLetterIndex);

		while (!isInteger(lastLetter)) {
			date = date.substring(0, lastLetterIndex);
			lastLetterIndex = lastLetterIndex - 1;
			lastLetter = date.substring(lastLetterIndex);
		}
		return date;
	}

	public boolean isInteger(String input) {
		try {
			Integer.parseInt(input);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	// time format: hh:mm a(12 hours)
	// does it catch 9:00 AM
	// does it catch 9 AM / 9am?
	public ArrayList<Calendar> getTime(String input) {
		ArrayList<Calendar> listOfTime = new ArrayList<Calendar>();
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("hh:mma");
		format.setLenient(false);
		Date time;

		int timeSessionIndex;
		int colonIndex;
		String timeInt;
		String timeSubstring = input.toUpperCase();

		while (timeSubstring.contains("AM") || timeSubstring.contains("PM")) {
			timeSessionIndex = timeSubstring.indexOf("M");
			colonIndex = timeSubstring.indexOf(":");
			if (colonIndex != -1) {
				if (timeSessionIndex - 4 == colonIndex) {
					timeInt = timeSubstring.substring(colonIndex - 2,
							timeSessionIndex + 1);
					timeInt = formatTimeSubstring(timeInt);
					try {
						time = format.parse(timeInt);
					} catch (ParseException pe) {
						throw new IllegalArgumentException("The date entered, "
								+ timeInt + " is invalid.", pe);
					}
					cal.setTime(time);
					listOfTime.add(cal);
				}
			} else {
				// check whether this is the right "M" by checking whether there
				// is an "A"
				// or "P" in front of it
				// 12AM etc
				if (timeSessionIndex - 2 > -1) {
					timeInt = timeSubstring.substring(timeSessionIndex - 2,
							timeSessionIndex - 1);
					if (isInteger(timeInt)) {
						timeInt = timeSubstring.substring(timeSessionIndex - 3,
								timeSessionIndex + 1);
						timeInt = formatTimeSubstring(timeInt);
						try {
							time = format.parse(timeInt);
						} catch (ParseException pe) {
							throw new IllegalArgumentException(
									"The date entered, " + timeInt
											+ " is invalid.", pe);
						}
						cal.setTime(time);
						listOfTime.add(cal);
					}
				}
			}
			timeSubstring = timeSubstring.substring(timeSessionIndex + 1);
		}
		return listOfTime;
	}

	public String formatTimeSubstring(String input) {
		if (input.contains(":")) {
			return input.trim();
		} else {
			String amPmHolder = input.substring(2);
			String timeHolder = input.substring(0, 2);
			String formatedTimeString = timeHolder.concat(":").concat(
					amPmHolder);
			return formatedTimeString.trim();
		}
	}

	/**
	 * Returns an ArrayList of tags. Tags do not contain "#"
	 * If no tags are found, retun an empty ArrayList
	 * Tags require a space after them. Eg. #2013<space>
	 * @param input that is directly retrieved from user
	 * @return an ArrayList<String> of tags
	 */
	public ArrayList<String> getTags(String input) {
		ArrayList<String> tagArrayList = new ArrayList<String>();
		if (input.contains("#")) {
			int indexOfSeparator;
			String tag;
			int indexOfHash = input.indexOf("#");

			while (indexOfHash != -1) {
				indexOfSeparator = input.indexOf(" ", indexOfHash);
				tag = input.substring(indexOfHash + 1, indexOfSeparator);
				tagArrayList.add(tag);
				indexOfHash = input.indexOf("#", indexOfSeparator);
			}
		}
		return tagArrayList;
	}

	public InputData list(Command type, String details) {
		if (details.contains("#")) {
			ArrayList<String> tags = getTags(details);
			InputData listInputData = new InputData(type);
			listInputData.put(Keys.HASHTAG, tags.get(0));
			return listInputData;
		} else {
			return new InputData(type);
		}
	}

	public InputData delete(Command type, String details) {
		if (isValidDelete(details)) {
			mDeleteIndex = Integer.parseInt(details);
			InputData deleteInputData = new InputData(type);
			deleteInputData.put("deleteIndex", mDeleteIndex);
			return deleteInputData;
		} else {
			return null;
		}
	}

	public boolean isValidDelete(String input) {
		if (!input.isEmpty() || !isInteger(input)) {
			return false;
		} else {
			return true;
		}
	}

	public InputData undo(Command type, String details) {
		return new InputData(type);
	}

	public InputData save(Command type, String details) {
		return new InputData(type);
	}

	public InputData exit(Command type, String details) {
		return new InputData(type);
	}
}
