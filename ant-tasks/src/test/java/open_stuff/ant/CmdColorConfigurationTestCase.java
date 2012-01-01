/*******************************************************************************
 * Copyright (c) 2012 freelancer.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl.html
 * 
 * Contributors:
 *     freelancer - initial API and implementation
 ******************************************************************************/
package open_stuff.ant;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

import open_stuff.ant.CmdColorConfiguration.CmdColorConditionIterator;
import junit.framework.TestCase;

public class CmdColorConfigurationTestCase extends TestCase {
	public void testDefaultConfiguration() throws Exception {
		CmdColorConfiguration configuration = new CmdColorConfiguration();
		CmdColorConditionIterator iteration = configuration.iterator();
		CmdColorCondition colorCondition = iteration.next();
		assertTrue(colorCondition.condition.matches(null, 0));
		assertFalse(colorCondition.condition.matches(null, 1));
	}
	
	public void testConfigurationCase1() throws Exception {
		setConfigurationFile();
		CmdColorConfiguration configuration = new CmdColorConfiguration();
		CmdColorConditionIterator iteration = configuration.iterator();
		CmdColorCondition colorCondition = iteration.next();
		assertTrue(colorCondition.condition.matches(null, 3));
		assertTrue(colorCondition.condition.matches(null, 4));
		assertFalse(colorCondition.condition.matches(null, 2));
		assertFalse(colorCondition.condition.matches(null, 1));
	}
	
	public void testConfigurationCase2() throws Exception {
		setConfigurationFile();
		CmdColorConfiguration configuration = new CmdColorConfiguration();
		CmdColorConditionIterator iteration = configuration.iterator();
		CmdColorCondition colorCondition = iteration.next();
		assertTrue(colorCondition.condition.matches(null, 2));
		assertTrue(colorCondition.condition.matches(null, 1));
		assertFalse(colorCondition.condition.matches(null, 3));
		assertFalse(colorCondition.condition.matches(null, 4));
	}
	
	public void testConfigurationCase3() throws Exception {
		setConfigurationFile();
		CmdColorConfiguration configuration = new CmdColorConfiguration();
		CmdColorConditionIterator iteration = configuration.iterator();
		CmdColorCondition colorCondition = iteration.next();
		assertTrue(colorCondition.condition.matches("[test] # mkmk-ERROR", 0));
		assertTrue(colorCondition.condition.matches("[test] # mkmk-FATAL", 0));
		assertTrue(colorCondition.condition.matches("[test] # syst-ERROR", 0));
		assertTrue(colorCondition.condition.matches("[test] # syst-FATAL", 0));
		assertFalse(colorCondition.condition.matches("test", 1));
	}
	
	private File getConfigurationFile() throws URISyntaxException {
		
		URL fileUrl = CmdColorConfigurationTestCase.class.getClassLoader().getResource("open_stuff/ant/"+getName()+".xml");
		return new File(fileUrl.toURI());
	}
	
	private void setConfigurationFile() throws Exception {
		File confFile = getConfigurationFile();
		// Absolute path
		String absolutePath = confFile.getAbsolutePath();
		System.setProperty("CmdColorLogger.confFile", absolutePath);
	}
}
