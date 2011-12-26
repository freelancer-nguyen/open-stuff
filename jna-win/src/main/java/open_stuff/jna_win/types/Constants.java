package open_stuff.jna_win.types;

public interface Constants {
	public static final int STD_INPUT_HANDLE = -10;
	public static final int STD_OUTPUT_HANDLE = -11;
	public static final int STD_ERROR_HANDLE = -12;

	public static final int FOREGROUND_BLUE = 0x0001;
	public static final int FOREGROUND_GREEN = 0x0002;
	public static final int FOREGROUND_RED = 0x0004;
	public static final int FOREGROUND_INTENSITY = 0x0008;
	public static final int BACKGROUND_BLUE = 0x0010;
	public static final int BACKGROUND_GREEN = 0x0020;
	public static final int BACKGROUND_RED = 0x0040;
	public static final int BACKGROUND_INTENSITY = 0x0080;
	public static final int COMMON_LVB_LEADING_BYTE = 0x0100;
	public static final int COMMON_LVB_TRAILING_BYTE = 0x0200;
	public static final int COMMON_LVB_GRID_HORIZONTAL = 0x0400;
	public static final int COMMON_LVB_GRID_LVERTICAL = 0x0800;
	public static final int COMMON_LVB_GRID_RVERTICAL = 0x1000;
	public static final int COMMON_LVB_REVERSE_VIDEO = 0x4000;
	public static final int COMMON_LVB_UNDERSCORE = 0x8000;

	public static final int COMMON_LVB_SBCSDBCS = 0x0300;
}
