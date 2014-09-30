package udo.util.shared;

public enum Command {
	ADD_EVENT, // have a start and end time/date
	ADD_TASK, // have a deadline
	ADD_PLAN, // have no deadlines
	LIST, DELETE, EDIT, SAVE, EXIT, UNDO
}
