package udo.util.parser;

import java.util.ArrayList;

import udo.util.shared.Command;
import udo.util.shared.InputData;

public class ParserAddTask implements ParserAddCommand {

	public ParserAddTask() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void fill(Command type, String details, InputData data) {
		// TODO Auto-generated method stub
		
	}
	
	public InputData addTask(Command type, String details) {
		InputData taskInputData = new InputData(type);
		String title = getTaskTitle(details);
		ArrayList<String> tags = getTags(details);
		Calendar end = setFirstTimeAndDate(details);
		if (title.isEmpty()) {
			taskInputData.setParsingStatus(ParsingStatus.FAIL);
		} else {
			assert(end != null);
			taskInputData.put(Keys.TITLE, title);
			taskInputData.put(Keys.HASHTAGS, tags);
			taskInputData.put(Keys.DUE, end);
			taskInputData.setParsingStatus(ParsingStatus.SUCCESS);
		}
		return taskInputData;
	}

	@Override
	public String getTitle(String input) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<String> getTags(String input) {
		ArrayList<String> tagArrayList = new ArrayList<String>();
		String tag;
		boolean hashTagFilled;
		String words[] = input.split(" ");
		for (String word : words) {
			hashTagFilled = word.length() > 1;
			if (word.startsWith("#") && hashTagFilled) {
				tag = word.replaceFirst("#", "");
				tagArrayList.add(tag);
			}
		}
		return tagArrayList;
	}

}
