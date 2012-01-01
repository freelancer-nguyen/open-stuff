package open_stuff.ant;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

final class MessageContentCondition implements MessageCondition {

	private Pattern pattern;
	
	public MessageContentCondition(String pattern) {
		this.pattern = Pattern.compile(pattern);
	}
	
	public boolean matches(String message, int priority) {
		Matcher matcher = pattern.matcher(message);
		return matcher.find();
	}

}
