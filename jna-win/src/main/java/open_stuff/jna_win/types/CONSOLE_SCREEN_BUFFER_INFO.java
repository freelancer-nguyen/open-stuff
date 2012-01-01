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
package open_stuff.jna_win.types;

import com.sun.jna.Structure;

public class CONSOLE_SCREEN_BUFFER_INFO extends Structure {
	public COORD dwSize;
	public COORD dwCursorPosition;
	public short wAttributes;
	public SMALL_RECT srWindow;
	public COORD dwMaximumWindowSize;
}
