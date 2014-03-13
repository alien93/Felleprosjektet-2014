package guiTest;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JTextField;

import gui.LogInPanel;
import junit.extensions.jfcunit.JFCTestCase;
import junit.extensions.jfcunit.JFCTestHelper;
import junit.extensions.jfcunit.TestHelper;
import junit.extensions.jfcunit.finder.NamedComponentFinder;

public class LogInPanelTest extends JFCTestCase {
	
	private LogInPanel loginPanel = null;
	
	public LogInPanelTest(String name) {
		super(name);
	}
	
	protected void setUp() throws Exception {
		super.setUp();
		setHelper(new JFCTestHelper());
		JFrame frame = new JFrame();
		frame.add(new LogInPanel());
		frame.setVisible(true);
	}
	
	protected void tearDown() throws Exception {
		loginPanel = null;
		getHelper();
		TestHelper.cleanUp(this);
		super.tearDown();
		
	}
	
	public void TestUserAndPasswordEmpty() {
		//det andre parameteret er navnet på komponentet som skal finnes
		NamedComponentFinder finder = new NamedComponentFinder(JComponent.class, "UsernameField" );
		JTextField usernameField = (JTextField) finder.find(loginPanel, 0);
		assertNotNull( "Could not find the username textfield", usernameField );
	}
}
