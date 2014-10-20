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
		default :
			break;
		}
		addEntry(item);
	}

	private void initEdit(ItemData item) {
		// TODO Auto-generated method stub
		mMessage.setText("<html>You have edited: <br>" + item.get(Keys.FIELD) 
							+ "<br>from: " 
							+ "<br>to: </html>" 
							+ item.get(Keys.VALUE));
		add(mMessage);
	}

	private void initAdd() {
		mMessage.setText("You have successfully added: ");
		add(mMessage);
		// TODO Auto-generated method stub
		
	}

	private void initDelete() {
		mMessage.setText("You have successfully deleted: ");
		add(mMessage);
		// TODO Auto-generated method stub
		
	}

	private void initUndo() {
		// TODO Auto-generated method stub
		mMessage.setText("You have undone a command: ");
		add(mMessage);
	}
	
	private void addEntry(ItemData item) {
		Entry entry = new Entry(item, item.getItemType());
		add(entry);
	}
}
