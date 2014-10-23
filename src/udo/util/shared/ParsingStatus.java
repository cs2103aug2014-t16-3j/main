package udo.util.shared;

/**
 * Communicates the success of parsing user's raw data
 * <p>
 * SUCCESS = No anomalies detected in input, successfully parsed input.
 * <br>
 * FAIL = Anomalies detected in input, failed to parse input.
 * 
 */
public enum ParsingStatus {
	SUCCESS, FAIL
}
