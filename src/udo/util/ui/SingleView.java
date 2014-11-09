//@author A0114088H
package udo.util.ui;

import java.awt.Dimension;
import java.util.Calendar;

import javax.swing.JLabel;

import udo.language.LanguagePack;
import udo.util.shared.Command;
import udo.util.shared.Constants.Keys;
import udo.util.shared.Constants.UI;
import udo.util.shared.EditField;
import udo.util.shared.ItemData;
import udo.util.shared.OutputData;

public class SingleView extends View {

	private static final long serialVersionUID = 1L;

	private JLabel mMessage = new JLabel();
	
	private LanguagePack mLang = LanguagePack.getInstance();

	public SingleView() {
		setPreferredSize(new Dimension(UI.SUBVIEW_WIDTH, UI.SUBVIEW_HEIGHT));
		setLayout(new WrapLayout(WrapLayout.LEADING, 5, 5));
		mMessage.setFont(UI.FONT_14);
	}

	@Override
	public void init(OutputData output, Command type) {
		switch (type) {
			case ADD_EVENT :
			case ADD_PLAN :
			case ADD_TASK :
				initAdd();
				break;
				
			case DELETE :
				initDelete();
				break;
				
			case EDIT :
				initEdit(output);
				break;
				
			case MARK_DONE :
				initMarkDone();
				break;
				
			case TOGGLE_DONE :
				initToggleDone();
				break;
				
			default :
				break;
		}
		ItemData item = (ItemData) output.get(Keys.ITEM);
		addEntry(item);
	}

	private void initToggleDone() {
		mMessage.setText("<html>" + "<br><br>"
				+ mLang.getSINGLE_TOGGLE_DONE() + "</html>");
		add(mMessage);

	}

	private void initMarkDone() {
		mMessage.setText("<html>" + "<br><br>"
				+ mLang.getSINGLE_MARK_AS_DONE() + "</html>");
		add(mMessage);

	}

	private void initEdit(OutputData output) {
		ItemData item = (ItemData) output.get(Keys.ITEM);
		EditField field = (EditField) output.get(Keys.FIELD);
		String fieldString = "";
		String oldValue = "";
		String newValue = "";
		switch (field) {
			case DUE_DATE :
				fieldString = mLang.getDUE_DATE();
				oldValue = UI.DD_MM_YY.format(((Calendar) output
						.get(Keys.OLD_VALUE)).getTime());
				newValue = UI.DD_MM_YY.format(((Calendar) item.get(Keys.DUE))
						.getTime());
				break;
				
			case DUE_TIME :
				fieldString = mLang.getDUE_TIME();
				oldValue = UI.HOUR_12.format(((Calendar) output
						.get(Keys.OLD_VALUE)).getTime());
				newValue = UI.HOUR_12.format(((Calendar) item.get(Keys.DUE))
						.getTime());
				break;
				
			case END_DATE :
				fieldString = mLang.getEND_DATE();
				oldValue = UI.DD_MM_YY.format(((Calendar) output
						.get(Keys.OLD_VALUE)).getTime());
				newValue = UI.DD_MM_YY.format(((Calendar) item.get(Keys.END))
						.getTime());
				break;
				
			case END_TIME :
				fieldString = mLang.getEND_TIME();
				oldValue = UI.HOUR_12.format(((Calendar) output
						.get(Keys.OLD_VALUE)).getTime());
				newValue = UI.HOUR_12.format(((Calendar) item.get(Keys.END))
						.getTime());
				break;
				
			case START_DATE :
				fieldString = mLang.getSTART_DATE();
				oldValue = UI.DD_MM_YY.format(((Calendar) output
						.get(Keys.OLD_VALUE)).getTime());
				newValue = UI.DD_MM_YY.format(((Calendar) item.get(Keys.START))
						.getTime());
				break;
				
			case START_TIME :
				fieldString = mLang.getSTART_TIME();
				oldValue = UI.HOUR_12.format(((Calendar) output
						.get(Keys.OLD_VALUE)).getTime());
				newValue = UI.HOUR_12.format(((Calendar) item.get(Keys.START))
						.getTime());
				break;
				
			case TITLE :
				fieldString = mLang.getTITLE();
				oldValue = (String) output.get(Keys.OLD_VALUE);
				newValue = (String) item.get(Keys.TITLE);
				break;
				
			default :
				break;

		}
		mMessage.setText("<html>" + "<br><br>"
				+ mLang.getSINGLE_EDITED() + "<font color=\"blue\">" + fieldString
				+ "</font><br>" + mLang.getSINGLE_FROM() + "<font color=\"blue\">" + "\"" + oldValue
				+ "\"" + "</font><br>" + mLang.getSINGLE_TO() + "<font color=\"blue\">" + "\""
				+ newValue + "\"" + "</font>. <br>" + mLang.getSINGLE_THIS_IS_EDITED_ITEM()
				+ "</html>");
		add(mMessage);
	}

	private void initAdd() {
		mMessage.setText("<html>" + "<br><br>"
				+ mLang.getSINGLE_SUCCESFULLY_ADDED() + "</html>");
		add(mMessage);

	}

	private void initDelete() {
		mMessage.setText("<html>" + "<br><br>"
				+ mLang.getSINGLE_SUCCESSFULLY_DELETED() + "</html>");
		add(mMessage);

	}

}
