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

import java.io.IOException;
import java.lang.reflect.Field;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Execute;
import org.apache.tools.ant.taskdefs.ExecuteStreamHandler;
import org.apache.tools.ant.taskdefs.ExecuteWatchdog;

import com.sun.jna.Library;
import com.sun.jna.Native;

class WindowsExecute extends Execute
{

	static private interface Kernel32 extends Library
	{

		public static Kernel32 INSTANCE = (Kernel32) Native.loadLibrary(
				"kernel32", Kernel32.class);

		public int GetProcessId(Long hProcess);
	}

	static private Kernel32 nativeBridge = Kernel32.INSTANCE;

	private Project project;
	private boolean vmLauncher;
	private ExecuteStreamHandler streamHandler;
	private ExecuteWatchdog watchdog;

	// @Override
	public void setAntRun(Project project) throws BuildException
	{
		super.setAntRun(project);
		this.project = project;
	}

	// @Override
	public void setStreamHandler(ExecuteStreamHandler streamHandler)
	{
		super.setStreamHandler(streamHandler);
		this.streamHandler = streamHandler;
	}

	// @Override
	public void setVMLauncher(boolean useVMLauncher)
	{
		super.setVMLauncher(useVMLauncher);
		this.vmLauncher = useVMLauncher;
	}

	public void setVmLauncher(boolean vmLauncher)
	{
		this.vmLauncher = vmLauncher;
	}

	public WindowsExecute()
	{
		super();
	}

	public WindowsExecute(ExecuteStreamHandler streamHandler,
			ExecuteWatchdog watchdog)
	{
		super(streamHandler, watchdog);
		this.streamHandler = streamHandler;
		this.watchdog = watchdog;
	}

	public WindowsExecute(ExecuteStreamHandler streamHandler)
	{
		super(streamHandler);
		this.streamHandler = streamHandler;
	}

	public void setProject(Project project)
	{
		this.project = project;
	}

	// @Override
	public int execute() throws IOException
	{
		if (getWorkingDirectory() != null && !getWorkingDirectory().exists())
		{
			throw new BuildException(getWorkingDirectory() + " doesn't exist.");
		}
		final Process process = launch(project, getCommandline(),
				getEnvironment(), getWorkingDirectory(), vmLauncher);
		ProcessKiller killer = new ProcessKiller(process);
		try
		{
			streamHandler.setProcessInputStream(process.getOutputStream());
			streamHandler.setProcessOutputStream(process.getInputStream());
			streamHandler.setProcessErrorStream(process.getErrorStream());
		}
		catch (IOException e)
		{
			process.destroy();
			throw e;
		}
		streamHandler.start();

		try
		{
			// add the process to the list of those to destroy if the VM exits
			//
			Runtime.getRuntime().addShutdownHook(killer);

			if (watchdog != null)
			{
				watchdog.start(process);
			}
			waitFor(process);

			if (watchdog != null)
			{
				watchdog.stop();
			}
			streamHandler.stop();
			closeStreams(process);

			if (watchdog != null)
			{
				watchdog.checkException();
			}
			return getExitValue();
		}
		catch (ThreadDeath t)
		{
			// #31928: forcibly kill it before continuing.
			killer.execute();
			throw t;
		}
		finally
		{
			// remove the process to the list of those to destroy if
			// the VM exits
			//
			Runtime.getRuntime().removeShutdownHook(killer);
		}
	}

	private static int getPid(Process p)
	{

		Field f;
		try
		{
			f = p.getClass().getDeclaredField("handle");
			f.setAccessible(true);
			int pid = nativeBridge.GetProcessId((Long) f.get(p));
			return pid;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return 0;
	}

	private class ProcessKiller extends Thread
	{
		private int pid;

		public ProcessKiller(Process process)
		{
			pid = getPid(process);
		}

		public void run()
		{
			execute();
		}

		public void execute()
		{
			String killTree = "taskkill /F /T /PID " + pid;
			try
			{
				Runtime.getRuntime().exec(killTree);
				pid = -1;
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

}
