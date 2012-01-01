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
