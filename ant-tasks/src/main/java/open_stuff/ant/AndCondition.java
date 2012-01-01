package open_stuff.ant;

import java.util.Iterator;

final class AndCondition extends CompositeCondition {

	public boolean matches(String message, int priority) {
		boolean matched = true;
		Iterator iterator = messageConditions.iterator();
		while (matched && iterator.hasNext()) {
			matched = ((MessageCondition)iterator.next()).matches(message, priority);
		}
		return matched;
	}

}
