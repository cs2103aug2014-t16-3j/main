package udo.util.ui;

import java.awt.Dimension;
import java.util.Calendar;

import javax.swing.JLabel;
import javax.swing.JPanel;

import udo.util.shared.Command;
import udo.util.shared.Constants.Keys;
import udo.util.shared.Constants.UI;
import udo.util.shared.EditField;
import udo.util.shared.ItemData;
import udo.util.shared.ItemType;
import udo.util.shared.OutputData;

public class SingleView extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JLabel mMessage = new JLabel();
	
	public SingleView() {
		
		setOpaque(false);
		setPreferredSize(new Dimension(UI.SUBVIEW_WIDTH, UI.SUBVIEW_HEIGHT));
		setLayout(new WrapLayout(WrapLayout.LEADING, 5,5));
		mMessage.setFont(UI.FONT_14);
	}
	
	public void init(OutputData output, Command type) {
		switch (type) {
		case ADD_EVENT: 
		case ADD_PLAN:
		case ADD_TASK:
			initAdd();
			break;
		case DELETE :
			initDelete();
			break;
		case EDIT :
			initEdit(output);
			break;
		case UNDO :
			initUndo();
			break;
		case MARK_DONE:
			initMarkDone();
			break;
		case TOGGLE_DONE:
			initToggleDone();
			break;
		default :
			break;
		}
		ItemData item = (ItemData) output.get(Keys.ITEM);
		addEntry(item);
	}

	private void initToggleDone() {
		mMessage.setText("You have toggled the completion status of: ");
		add(mMessage);
		
	}

	private void initMarkDone() {
		mMessage.setText("You have marked the following as completed: ");
		add(mMessage);
		
	}

	private void initEdit(OutputData output) {
		// TODO Auto-generated method stub
		ItemData item = (ItemData) output.get(Keys.ITEM);
		EditField field = (EditField) output.get(Keys.FIELD);
		String fieldString = "";
		String oldValue = "";
		String newValue = "";
		switch(field) {
			case DUE_DATE:
				fieldString = "Due date";
				oldValue = UI.DD_MM_YY.format(((Calendar) output.get(Keys.OLD_VALUE)).getTime());
				newValue = UI.DD_MM_YY.format(((Calendar)item.get(Keys.DUE)).getTime());
				break;
			case DUE_TIME:
				fieldString = "Due time";
				oldValue = UI.HOUR_12.format(((Calendar) output.get(Keys.OLD_VALUE)).getTime());
				newValue = UI.HOUR_12.format(((Calendar)item.get(Keys.DUE)).getTime());
				break;
			case END_DATE:
				break;
			case END_TIME:
				break;
			case START_DATE:
				break;
			case START_TIME:
				break;
			case TITLE:
				fieldString = "Title";
				oldValue = (String) output.get(Keys.OLD_VALUE);
				newValue = (String) item.get(Keys.TITLE);
				break;
			default:
				break;
			
		}
		mMessage.setText("<html>"
							+ "<br><br>"
							+ "You have edited: <font color=\"blue\">" + fieldString
							+ "</font><br>from: <font color=\"blue\">" + "\"" + oldValue + "\""
							+ "</font><br>to: <font color=\"blue\">" + "\"" + newValue + "\""
							+ "</font>. <br>This is the edited item: "
							+ "</html>");
		add(mMessage);
	}

	private void initAdd() {
		mMessage.setText("<html>"
							+"<br><br>"
							+ "You have successfully added: "
							+ "</html>");
		add(mMessage);
		// TODO Auto-generated method stub
		
	}

	private void initDelete() {
		mMessage.setText("<html>"
							+"<br><br>"
							+ "You have successfully deleted: "
							+ "</html>");
		add(mMessage);
		// TODO Auto-generated method stub
		
	}

	private void initUndo() {
		// TODO Auto-generated method stub
		mMessage.setText("<html>"
							+"<br><br>"
							+ "You have successfully added: "
							+ "</html>");
		add(mMessage);
	}
	
	private void addEntry(ItemData item) {
		Entry entry = new Entry(item, item.getItemType());
		add(entry);
	}
}
