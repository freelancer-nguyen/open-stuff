package open_stuff.ant;

import java.util.List;
import java.util.Vector;

abstract class CompositeCondition implements MessageCondition {
	protected List messageConditions = new Vector();
	void addMessageCondition(MessageCondition condition) {
		messageConditions.add(condition);
	}
}
