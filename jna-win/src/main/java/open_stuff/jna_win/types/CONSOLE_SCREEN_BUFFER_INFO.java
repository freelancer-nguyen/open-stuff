package open_stuff.jna_win.types;

import com.sun.jna.Structure;

public class CONSOLE_SCREEN_BUFFER_INFO extends Structure {
	public COORD dwSize;
	public COORD dwCursorPosition;
	public short wAttributes;
	public SMALL_RECT srWindow;
	public COORD dwMaximumWindowSize;
}
