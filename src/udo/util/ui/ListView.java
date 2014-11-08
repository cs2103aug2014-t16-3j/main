//@author A0114088H
package udo.util.ui;

import java.awt.Dimension;

import udo.util.shared.Constants.UI;

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
