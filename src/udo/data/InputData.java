//@author A0108358B
package udo.data;

import udo.enums.Command;
import udo.enums.ParsingStatus;


/**
 * This class carries information parsed from the input string. This class
 * should contain information that allows the Engine to decide what to do, and
 * any additional information that the Engine requires to carry it out.
 * 
 */
public class InputData extends DataHolder {

	private Command mCommand;
	private ParsingStatus mParsingStatus;

	public InputData(Command cmd) {
		mCommand = cmd;
	}
	
	public InputData(Command cmd, ParsingStatus status) {
		mCommand = cmd;
		mParsingStatus = status;
	}

	/**
	 * gets the command
	 * 
	 * @return the <code>Command</code>
	 */
	public Command getCommand() {
		return mCommand;
	}
	
	/**
	 * Returns the {@code ParsingStatus} value
	 * 
	 * @return the {@code ParsingStatus} value
	 */
	public ParsingStatus getStatus() {
		return mParsingStatus;
	}

	/**
	 * Sets the {@code ParsingStatus} value
	 * 
	 * @param status the status
	 * @return the {@code ParsingStatus} value
	 */
	public void setParsingStatus(ParsingStatus status) {
		mParsingStatus = status;
	}
}
