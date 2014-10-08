package udo.util.ui;

import javax.swing.JLabel;
import javax.swing.JPanel;

import udo.util.shared.ItemData;

public class SingleView extends JPanel {

	private static final long serialVersionUID = 1L;
	private final static int VIEW_HEIGHT = 550;
	private final static int VIEW_WIDTH = 360;
	
	private JLabel mMessage = new JLabel();
	
	public SingleView() {
		
		setOpaque(false);
		setBounds(20,20,VIEW_WIDTH,VIEW_HEIGHT);
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
		Entry entry = new Entry(item, "allDetails");
		add(entry);
	}
}
