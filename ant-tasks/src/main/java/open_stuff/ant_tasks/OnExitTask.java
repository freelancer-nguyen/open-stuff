/*******************************************************************************
 * Copyright (c) 2011 freelancer.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl.html
 * 
 * Contributors:
 *     freelancer - initial API and implementation
 ******************************************************************************/
package open_stuff.ant_tasks;

import org.apache.tools.ant.BuildEvent;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.BuildListener;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.property.LocalProperties;
import org.apache.tools.ant.taskdefs.Sequential;

public class OnExitTask extends Task {

	private Sequential success = null;
	private Sequential fail = null;

	public OnExitTask() {
	}

	public Sequential createSuccess() {
		success = new Sequential();
		return success;
	}

	public Sequential createFail() {
		fail = new Sequential();
		return fail;
	}

	public void execute() throws BuildException {
		
		if (success == null) {
            log("No nested success element found.", Project.MSG_WARN);
        }

        if (fail == null) {
            log("No nested failure element found.", Project.MSG_WARN);
        }

        getProject().addBuildListener(new TaskRunner(success, fail));
	}

	private class TaskRunner implements BuildListener {

		private Task onSuccess;
		private Task onFail;

		public TaskRunner(Task onSuccess, Task onFail) {
			this.onSuccess = onSuccess;
			this.onFail = onFail;
		}

		public void buildFinished(BuildEvent event) {
			LocalProperties localProperties = LocalProperties.get(getProject());
			localProperties.enterScope();
			try {
				if (event.getException() == null) {
					if (onSuccess != null)
						onSuccess.perform();
				} else if (event.getException() != null) {
					if (onFail != null)
						onFail.perform();
				}
			} finally {
				localProperties.exitScope();
			}
		}

		public void buildStarted(BuildEvent arg0) {
		}

		public void messageLogged(BuildEvent arg0) {
		}

		public void targetFinished(BuildEvent arg0) {
		}

		public void targetStarted(BuildEvent arg0) {
		}

		public void taskFinished(BuildEvent arg0) {
		}

		public void taskStarted(BuildEvent arg0) {
		}
	}
}
