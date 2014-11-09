//@author A0108358B
package udo.data;

import udo.enums.Command;
import udo.enums.ExecutionStatus;
import udo.enums.ParsingStatus;


/**
 * This class carries information from the engine to the UI. The information
 * contained in this class is all the info that the UI needs to display a
 * message that corresponds to the execution of the command.
 * <p>
 * For example, if the engine executed the addEvent() method, the OutputData
 * object will contain the command executed (ADD_EVENT), the status of the
 * execution (SUCCESS or FAIL), and any additional information regarding the
 * event that was added.
 * <p>
 * The status and command are accessed directly but any other additional details
 * will be stored in a hash table and only accessed by a key.
 * 
 */
public class OutputData extends DataHolder {

	private Command mCommand;
	private ParsingStatus mParsingStatus;
	private ExecutionStatus mExecutionStatus;
	
	public OutputData(Command cmd) {
		mCommand = cmd;
	}
	
	public OutputData(Command cmd, ParsingStatus pStatus) {
		mCommand = cmd;
		mParsingStatus = pStatus;
	}

	public OutputData(Command cmd, ParsingStatus pStatus, ExecutionStatus eStatus) {
		mCommand = cmd;
		mParsingStatus = pStatus;
		mExecutionStatus = eStatus;
	}

	/**
	 * Retrieves the {@code Command} value
	 * 
	 * @return the {@code Command} value
	 */
	public Command getCommand() {
		return mCommand;
	}
	
	/**
	 * Retrieves the {@code ParsingStatus} value
	 * 
	 * @return the {@code ParsingStatus} value
	 */
	public ParsingStatus getParsingStatus() {
		return mParsingStatus;
	}

	/**
	 * Retrieves the {@code ExecutionStatus} value
	 * 
	 * @return the {@code ExecutionStatus} value
	 */
	public ExecutionStatus getExecutionStatus() {
		return mExecutionStatus;
	}

	/**
	 * sets the {@code ExecutionStatus} value
	 * @param status the status
	 * 
	 * @return the {@code ExecutionStatus} value
	 */
	public void setParsingStatus(ParsingStatus status) {
		mParsingStatus = status;
	}
	
	/**
	 * Retrieves the {@code ExecutionStatus} value
	 * @param status the status
	 * 
	 * @return the {@code ExecutionStatus} value
	 */
	public void setExecutionStatus(ExecutionStatus status) {
		mExecutionStatus = status;
	}
	
}
