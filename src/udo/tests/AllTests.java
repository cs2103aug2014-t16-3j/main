//@author A0108358B
package udo.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ 
	MainUnitTest.class, 
	ParserUnitTest.class, 
	EngineUnitTest.class, 
	CacheUnitTest.class, 
	FileManagerUnitTest.class, 
	UndoBinUnitTest.class
	})

public class AllTests {

}
