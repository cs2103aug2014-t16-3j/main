package udo.util.ui;

import javax.swing.JLabel;
import javax.swing.JPanel;

import udo.util.shared.Constants.UI;
import udo.util.shared.ItemData;

public class SingleView extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JLabel mMessage = new JLabel();
	
	public SingleView() {
		
		setOpaque(false);
		setBounds(20,20,UI.SUBVIEW_WIDTH,UI.SUBVIEW_HEIGHT);
		mMessage.setFont(UI.FONT_14);
	}
	
	public void init(ItemData item, String cmdType) {
		switch (cmdType) {
		case "addEvent" : 
			initAdd();
			break;
			// TODO dont forget add task/plan
		case "delete" :
			initDelete();
			break;
		case "edit" :
			// TODO get edit itemdata
			break;
		case "undo" :
			initUndo();
			break;
		default :
			break;
		}
		addEntry(item);
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
		Entry entry = new Entry(item, UI.ENTRY_EVENT);
		add(entry);
	}
}
