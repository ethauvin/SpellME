/*
 * @(#)SpellME.java
 *
 * Copyright (c) 2003-2004, Erik C. Thauvin (http://www.thauvin.net/erik/)
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 *
 * Neither the name of the authors nor the names of its contributors may be
 * used to endorse or promote products derived from this software without
 * specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * $Id$
 *
 */
package net.thauvin.j2me.spellme;

import javax.microedition.lcdui.Display;
import javax.microedition.midlet.*;


/**
 * Spells the given text using a phonetic alphabet.
 *
 * @author <a href="http://www.thauvin.net/erik/">Erik C. Thauvin</a>
 * @version $Revision$, $Date$
 *
 * @created January 15, 2003
 * @since 1.0
 */
public class SpellME extends MIDlet
{
	private static final String[] ALPHABET =
		new String[]
		{
			"Adam", "Boy", "Charlie", "David", "Edward", "Frank", "George", "Henry", "Ida", "John", "King", "Lincoln",
			"Mary", "Nora", "Ocean", "Paul", "Queen", "Robert", "Sam", "Tom", "Union", "Victor", "William", "X-Ray",
			"Young", "Zebra"
		};
	private static final String[] NATO_ALPHABET =
		new String[]
		{
			"Alpha", "Bravo", "Charlie", "Delta", "Echo", "Foxtrot", "Golf", "Hotel", "India", "Juliet", "Kilo", "Lima",
			"Mike", "November", "Oscar", "Papa", "Quebec", "Romeo", "Sierra", "Tango", "Uniform", "Victor", "Whiskey",
			"X-Ray", "Yankee", "Zulu"
		};
	private String init;
	private String input;
	private boolean exit = true;
	private boolean nato = false;
	private int index = -1;

	/**
	 * Constructor for the SpellME object.
	 */
	public SpellME()
	{
		init = getAppProperty("SpellME-Default");

		if (init == null)
		{
			init = "";
		}
		else if ("false".equals(init))
		{
			init = "";
		}

		String prop = getAppProperty("SpellME-NATO");

		if ((prop != null) && "true".equals(prop))
		{
			this.nato = true;
		}

		prop = getAppProperty("SpellME-Exit");

		if ((prop != null) && "false".equals(prop))
		{
			this.exit = false;
		}
	}

	/**
	 * Signals the MIDlet to terminate and enter the Destroyed state.
	 *
	 * @param ignore Ignored.
	 */
	protected void destroyApp(boolean ignore)
	{
	}

	/**
	 * Signals the MIDlet to enter the Paused state.
	 */
	protected void pauseApp()
	{
	}

	/**
	 * Signals the MIDlet that it has entered the Active state.
	 */
	protected void startApp()
	{
		Display.getDisplay(this).setCurrent(new TextInputScreen(this, init, exit));
	}

	/**
	 * Sets the Input attribute of the SpellME object.
	 *
	 * @param str The new Input value.
	 */
	void setInput(String str)
	{
		this.input = str;
	}

	/**
	 * Returns the nextCharacter attribute of the SpellME object.
	 *
	 * @return The NextCharacter value.
	 */
	String getNextCharacter()
	{
		index++;

		if (index < input.length())
		{
			final char c = input.charAt(index);

			if ((c < 91) && (c > 64))
			{
				if (nato)
				{
					return NATO_ALPHABET[(int) c - 65];
				}

				return ALPHABET[(int) c - 65];
			}

			else if ((c < 123) && (c > 96))
			{
				if (nato)
				{
					return NATO_ALPHABET[(int) c - 97];
				}

				return ALPHABET[(int) c - 97];
			}

			else
			{
				return String.valueOf(c);
			}
		}

		return "";
	}

	/**
	 * Exit request.
	 */
	void exitRequested()
	{
		destroyApp(false);
		notifyDestroyed();
	}

	/**
	 * Returns <code>true</code> if there is a next character to display.
	 *
	 * @return <code>true</code> if there is a next character, <code>false</code> otherwise.
	 */
	boolean hasNextCharacter()
	{
		if (input != null)
		{
			if ((index + 1) < input.length())
			{
				return true;
			}
		}

		return false;
	}

	/**
	 * Displays the next character.
	 */
	void nextCharacter()
	{
		Display.getDisplay(this).setCurrent(new TextCanvas(this, exit));
	}
}
