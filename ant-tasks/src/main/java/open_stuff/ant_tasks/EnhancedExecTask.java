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

import java.io.File;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.taskdefs.ExecTask;
import org.apache.tools.ant.taskdefs.Execute;
import org.apache.tools.ant.types.Environment;
import org.apache.tools.ant.types.Environment.Variable;

public class EnhancedExecTask extends ExecTask {

	private Environment env = new Environment();
	private File dir;
	private boolean vmLauncher;
	
	
	//@Override
	public void addEnv(Variable var) {
		super.addEnv(var);
		env.addVariable(var);
	}

	//@Override
	public void setDir(File d) {
		super.setDir(d);
		this.dir = d;
	}

	//@Override
	public void setVMLauncher(boolean vmLauncher) {
		super.setVMLauncher(vmLauncher);
		this.vmLauncher = vmLauncher;
	}

	public EnhancedExecTask() {
		super();
	}
	
	public EnhancedExecTask(Task owner) {
		super(owner);
	}

	//@Override
	protected Execute prepareExec() throws BuildException {
		// default directory to the project's base directory
		if (dir == null) {
			dir = getProject().getBaseDir();
		}
		if (redirectorElement != null) {
			redirectorElement.configure(redirector);
		}
		Execute exe = new EnhancedExecute(createHandler(), createWatchdog());
		exe.setAntRun(getProject());
		exe.setWorkingDirectory(dir);
		exe.setVMLauncher(vmLauncher);
		String[] environment = env.getVariables();
		if (environment != null) {
			for (int i = 0; i < environment.length; i++) {
				log("Setting environment variable: " + environment[i],
						Project.MSG_VERBOSE);
			}
		}
		exe.setNewenvironment(newEnvironment);
		exe.setEnvironment(environment);
		return exe;
	}
}
