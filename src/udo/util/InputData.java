package udo.util;

import java.util.HashMap;
import java.util.Set;

/**
 * This class carries information parsed from the input string.
 * This class should contain information that allows the Engine to decide
 * what to do, and any additional information that the Engine requires to
 * carry it out.
 * <p>
 * For example, if the input is about adding an event, then the class must
 * have information to tell the Engine that the job to execute is "add 
 * event", and also have the required information about the event, like 
 * the title, date, timings, and so on, so that the Engine can execute the
 * job properly.
 * 
 *
 */
public class InputData {
	
	private Command mCommand;
	private HashMap<String, Object> mInfo;
	
	public InputData(Command cmd) {
		mCommand = cmd;
		mInfo = new HashMap<String, Object>();
	}
	
	public Command getCommand() {
		return mCommand;
	}
	
	public boolean put(String name, Object info) {
		mInfo.put(name, info);
		return true;
	}
	
	public Object get(String name) {
		return mInfo.get(name);
	}
	
	public Set<String> getNames() {
		return mInfo.keySet();
	}
}
