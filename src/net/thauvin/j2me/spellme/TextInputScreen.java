/*
 * @(#)TextInputScreen.java
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

import javax.microedition.lcdui.*;


/**
 * The {@link TextBox} screen used to enter the text to spell.
 *
 * @author <a href="http://www.thauvin.net/erik/">Erik C. Thauvin</a>
 * @version $Revision$, $Date$
 *
 * @created January 15, 2003
 * @since 1.0
 */
class TextInputScreen extends TextBox implements CommandListener
{
	/**
	 * The <code>Exit</code> command.
	 */
	private final Command exitCommand;

	/**
	 * The <code>Start</code> command.
	 */
	private final Command startCommand;

	/**
	 * The MIDlet instance.
	 */
	private final SpellME midlet;

	/**
	 * Constructor for the TextInputScreen object.
	 *
	 * @param spellMidlet The MIDlet instance.
	 * @param str The default string, if any.
	 * @param exit <code>true</code> if the <code>Exit</code> command should be added.
	 */
	TextInputScreen(SpellME spellMidlet, String str, boolean exit)
	{
		super("Enter text: ", str, 256, TextField.ANY);
		this.midlet = spellMidlet;

		exitCommand = new Command("Exit", Command.EXIT, 2);
		startCommand = new Command("Start", Command.SCREEN, 1);

		if (exit)
		{
			addCommand(exitCommand);
		}

		addCommand(startCommand);
		setCommandListener(this);
	}

	/**
	 * Indicates that a command event has occurred.
	 *
	 * @param c Command object identifying the command.
	 * @param d The {@link Displayable} on which this event has occurred.
	 */
	public void commandAction(Command c, Displayable d)
	{
		if (c == exitCommand)
		{
			midlet.exitRequested();
		}
		else if (c == startCommand)
		{
			midlet.setInput(getString());
			midlet.nextCharacter();
		}
	}
}
