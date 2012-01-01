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

import java.io.PrintStream;

import open_stuff.ant.CmdColorConfiguration.CmdColorConditionIterator;
import open_stuff.jna_win.Kernel32Ext;
import open_stuff.jna_win.types.CONSOLE_SCREEN_BUFFER_INFO;
import open_stuff.jna_win.types.Constants;

import org.apache.tools.ant.DefaultLogger;

import com.sun.jna.platform.win32.WinNT.HANDLE;

public class CmdCorlorLogger extends DefaultLogger {
	
	private static HANDLE stdOut = Kernel32Ext.INSTANCE.GetStdHandle(Constants.STD_OUTPUT_HANDLE);
	private static short originalAttribute;
	
	static {
		CONSOLE_SCREEN_BUFFER_INFO info = new CONSOLE_SCREEN_BUFFER_INFO();
		Kernel32Ext.INSTANCE.GetConsoleScreenBufferInfo(stdOut, info);
		originalAttribute = info.wAttributes;
	}
	
	private static CmdColorConfiguration colorConfiguration = new CmdColorConfiguration();
	
	synchronized protected void printMessage(String message, PrintStream stream, int priority) {
		CmdColorConditionIterator iterator = colorConfiguration.iterator();
		CmdColorCondition matchedCondition = null;
		
		while (matchedCondition == null && iterator.hasNext()) {
			CmdColorCondition currentCondition = iterator.next();
			if (currentCondition.condition.matches(message, priority))
				matchedCondition = currentCondition;
		}
		
		if (matchedCondition != null)
			Kernel32Ext.INSTANCE.SetConsoleTextAttribute(stdOut, matchedCondition.colorAttribute);
		super.printMessage(message, stream, priority);
		if (matchedCondition != null)
			Kernel32Ext.INSTANCE.SetConsoleTextAttribute(stdOut, originalAttribute);
	}

}
