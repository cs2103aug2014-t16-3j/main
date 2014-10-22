package udo.util.ui;

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

import udo.util.shared.Command;
import udo.util.shared.Constants.Keys;
import udo.util.shared.Constants.UI;
import udo.util.shared.ItemData;
import udo.util.shared.ItemType;

public class SingleView extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JLabel mMessage = new JLabel();
	
	public SingleView() {
		
		setOpaque(false);
		setPreferredSize(new Dimension(UI.SUBVIEW_WIDTH, UI.SUBVIEW_HEIGHT));
		setLayout(new WrapLayout(WrapLayout.LEADING, 5,5));
		mMessage.setFont(UI.FONT_14);
	}
	
	public void init(ItemData item, Command type) {
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
			initEdit(item);
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

	private void initEdit(ItemData item) {
		// TODO Auto-generated method stub
		mMessage.setText("<html>"
							+ "<br><br>"
							+ "You have edited: <font color=\"blue\">" + item.get(Keys.FIELD) 
							+ "</font><br>from: <font color=\"blue\">" + "\"" + item.get(Keys.OLD_VALUE) + "\""
							+ "</font><br>to: <font color=\"blue\">" + "\"" + item.get((String) item.get(Keys.FIELD)) + "\""
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
