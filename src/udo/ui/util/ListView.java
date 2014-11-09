//@author A0114088H
package udo.ui.util;

import java.awt.Dimension;

import udo.constants.Constants.UI;

public class ListView extends View {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ListView() {
		setLayout(new WrapLayout(WrapLayout.LEADING, 0, 0));
		setSize(new Dimension(UI.SUBVIEW_WIDTH, 1));
	}

}
