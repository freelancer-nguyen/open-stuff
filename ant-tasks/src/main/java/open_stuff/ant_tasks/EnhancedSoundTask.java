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
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

public class EnhancedSoundTask extends Task implements LineListener
{
	private File source = null;
	private int loops = 0;
	private Long duration = null;

	public void setSource(File source)
	{
		this.source = source;
	}

	public void setLoops(int loops)
	{
		this.loops = loops;
	}

	public void setDuration(Long duration)
	{
		this.duration = duration;
	}

	public EnhancedSoundTask()
	{
		super();
	}

	public void execute() throws BuildException
	{
		Clip audioClip = null;

		AudioInputStream audioInputStream = null;

		try
		{
			audioInputStream = AudioSystem.getAudioInputStream(source);
		}
		catch (UnsupportedAudioFileException uafe)
		{
			log("Audio format is not yet supported: " + uafe.getMessage());
		}
		catch (IOException ioe)
		{
			ioe.printStackTrace();
		}

		if (audioInputStream != null)
		{
			AudioFormat format = audioInputStream.getFormat();
			DataLine.Info info = new DataLine.Info(Clip.class, format,
					AudioSystem.NOT_SPECIFIED);
			try
			{
				audioClip = (Clip) AudioSystem.getLine(info);
				audioClip.addLineListener(this);
				audioClip.open(audioInputStream);
			}
			catch (LineUnavailableException e)
			{
				log("The sound device is currently unavailable");
				return;
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}

			if (duration != null)
			{
				playClip(audioClip, duration.longValue());
			}
			else
			{
				playClip(audioClip, loops);
			}
			audioClip.drain();
			audioClip.close();
		}
		else
		{
			log("Can't get data from file " + source.getName());
		}
	}

	private void playClip(Clip clip, int loops)
	{

		clip.loop(loops);
		do
		{
			try
			{
				long timeLeft = (clip.getMicrosecondLength() - clip
						.getMicrosecondPosition()) / 1000;
				if (timeLeft > 0)
				{
					Thread.sleep(timeLeft);
				}
			}
			catch (InterruptedException e)
			{
				break;
			}
		}
		while (clip.isRunning());

		if (clip.isRunning())
		{
			clip.stop();
		}
	}

	private void playClip(Clip clip, long duration)
	{
		clip.loop(Clip.LOOP_CONTINUOUSLY);
		try
		{
			Thread.sleep(duration);
		}
		catch (InterruptedException e)
		{
			// Ignore Exception
		}
		clip.stop();
	}

	public void update(LineEvent event)
	{
		if (event.getType().equals(LineEvent.Type.STOP))
		{
			Line line = event.getLine();
			line.close();
		}
	}

}
