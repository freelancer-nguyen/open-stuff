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

final class MessagePriorityCondition implements MessageCondition {

	static final int GREATER_THAN = 0;
	static final int LESSER_THAN = 1;
	static final int EQUAL = 2;
	
	private int value;
	private int type;
	
	public MessagePriorityCondition(int type, int value) {
		this.type = type;
		this.value = value;
	}
	
	public boolean matches(String message, int priority) {
		switch (type) {
		case GREATER_THAN:
			return priority > value;
		case LESSER_THAN:
			return priority < value;
		case EQUAL:
			return priority == value;
		default:
			throw new IllegalArgumentException();
		}
	}
	
}
