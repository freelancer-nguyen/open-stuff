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

import java.util.Iterator;

final class OrCondition extends CompositeCondition {

	public boolean matches(String message, int priority) {
		boolean matched = false;
		Iterator iterator = messageConditions.iterator();
		while (!matched && iterator.hasNext()) {
			matched = ((MessageCondition)iterator.next()).matches(message, priority);
		}
		return matched;
	}

}
