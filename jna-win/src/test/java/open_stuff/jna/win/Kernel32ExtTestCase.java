package open_stuff.jna.win;

import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.WinNT.HANDLE;

import open_stuff.jna_win.Kernel32Ext;
import open_stuff.jna_win.types.CONSOLE_SCREEN_BUFFER_INFO;
import open_stuff.jna_win.types.Constants;
import junit.framework.TestCase;

public class Kernel32ExtTestCase extends TestCase {
	
	public void testGetStdHandle() throws Exception {
		HANDLE h = Kernel32Ext.INSTANCE.GetStdHandle(Constants.STD_INPUT_HANDLE);
		assertTrue(Pointer.nativeValue(h.getPointer())>0);
		h = Kernel32Ext.INSTANCE.GetStdHandle(Constants.STD_OUTPUT_HANDLE);
		assertTrue(Pointer.nativeValue(h.getPointer())>0);
		h = Kernel32Ext.INSTANCE.GetStdHandle(Constants.STD_ERROR_HANDLE);
		assertTrue(Pointer.nativeValue(h.getPointer())>0);
	}
	
	public void testGetConsoleScreenBufferInfo() throws Exception {
		HANDLE stdHandle = Kernel32Ext.INSTANCE.GetStdHandle(Constants.STD_OUTPUT_HANDLE);
		CONSOLE_SCREEN_BUFFER_INFO bufferInfo = new CONSOLE_SCREEN_BUFFER_INFO();
		assertFalse(Kernel32Ext.INSTANCE.GetConsoleScreenBufferInfo(stdHandle, bufferInfo));
	}
	
	public void testSetConsoleTextAttribute() throws Exception {
		HANDLE stdHandle = Kernel32Ext.INSTANCE.GetStdHandle(Constants.STD_ERROR_HANDLE);
		CONSOLE_SCREEN_BUFFER_INFO bufferInfo = new CONSOLE_SCREEN_BUFFER_INFO();
		assertFalse(Kernel32Ext.INSTANCE.GetConsoleScreenBufferInfo(stdHandle, bufferInfo));
		assertFalse(Kernel32Ext.INSTANCE.SetConsoleTextAttribute(stdHandle, (short)(bufferInfo.wAttributes+1)));
		System.out.println("test");
		assertFalse(Kernel32Ext.INSTANCE.SetConsoleTextAttribute(stdHandle, bufferInfo.wAttributes));
	}
}
