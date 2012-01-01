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

import java.util.List;
import java.util.Vector;

abstract class CompositeCondition implements MessageCondition {
	protected List messageConditions = new Vector();
	void addMessageCondition(MessageCondition condition) {
		messageConditions.add(condition);
	}
}
