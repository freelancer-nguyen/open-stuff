package open_stuff.ant;

interface MessageCondition {
	boolean matches(String message, int priority);
}
