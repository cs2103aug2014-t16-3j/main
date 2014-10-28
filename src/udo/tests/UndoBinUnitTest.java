//@author A0108358B
package udo.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import udo.util.engine.UndoBin;
import udo.util.shared.Command;
import udo.util.shared.InputData;

public class UndoBinUnitTest {

	@Test
	public void testUBPutItem() {
		// put something
		UndoBin u = new UndoBin();
		u.putInputData(new InputData(Command.EDIT));
		// cannot fail. no exception thrown as well.
	}
	
	@Test
	public void testUBGetItem() {
		// put something
		// then get it back
		// make sure its the same item
		UndoBin u = new UndoBin();
		u.putInputData(new InputData(Command.EDIT));
		
		InputData getInput = u.getUndoInputData();
		
		assertNotEquals("get input cannot be null",
				null,
				getInput);
		
		assertEquals("get input is the same as putted",
				Command.EDIT,
				getInput.getCommand());
	}
	
	@Test
	public void testUBClearItem() {
		// put something
		// then clear
		// then try to get back.
		// get back should be null
		UndoBin u = new UndoBin();
		u.putInputData(new InputData(Command.EDIT));
		
		u.clear();
		
		InputData getInput = u.getUndoInputData();
		
		assertEquals("get input must be null",
				null,
				getInput);
	}

}
