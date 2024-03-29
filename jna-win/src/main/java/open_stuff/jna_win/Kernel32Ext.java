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
package open_stuff.jna_win;

import open_stuff.jna_win.types.CONSOLE_SCREEN_BUFFER_INFO;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.WinNT.HANDLE;

public interface Kernel32Ext extends Kernel32 {
	public static Kernel32Ext INSTANCE = (Kernel32Ext) Native.loadLibrary("kernel32", Kernel32Ext.class);

	public HANDLE GetStdHandle(int nStdHandle);
	public boolean GetConsoleScreenBufferInfo(HANDLE hConsoleOutput, CONSOLE_SCREEN_BUFFER_INFO lpConsoleScreenBufferInfo);
	public boolean SetConsoleTextAttribute(HANDLE hConsoleOutput, short wAttributes);
}
