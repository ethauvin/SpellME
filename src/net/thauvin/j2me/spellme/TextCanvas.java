/*
 * @(#)TextCanvas.java
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
 * The {@link Canvas} used to display the spelled characters.
 *
 * @author <a href="http://www.thauvin.net/erik/">Erik C. Thauvin</a>
 * @version $Revision$, $Date$
 *
 * @created January 15, 2003
 * @since 1.0
 */
class TextCanvas extends Canvas implements CommandListener
{
	/**
	 * The backgroud color.
	 */
	private static final int BG_COLOR = 0xffffff;

	/**
	 * The forground color.
	 */
	private static final int FG_COLOR = 0x000000;

	/**
	 * The <code>Exit</code> command.
	 */
	private final Command exitCommand;

	/**
	 * The <code>Next</code> command.
	 */
	private final Command nextCommand;

	/**
	 * The MIDlet instance.
	 */
	private final SpellME midlet;
	private String output;

	/**
	 * Constructor for the TextCanvas object.
	 *
	 * @param spellMidlet The MIDlet instance.
	 * @param exit <code>true</code> if the <code>Exit</code> command should be added.
	 */
	TextCanvas(SpellME spellMidlet, boolean exit)
	{
		this.midlet = spellMidlet;

		nextCommand = new Command("Next", Command.SCREEN, 1);
		exitCommand = new Command("Exit", Command.EXIT, 2);

		if (exit)
		{
			addCommand(exitCommand);
		}
		
		if (spellMidlet.hasNextCharacter())
		{
			output = spellMidlet.getNextCharacter();

			if (spellMidlet.hasNextCharacter())
			{
				addCommand(nextCommand);
			}

			setCommandListener(this);
		}
		else
		{
			spellMidlet.exitRequested();
		}
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
		else if (c == nextCommand)
		{
			dismiss();
		}
	}

	/**
	 * Called when a key is pressed.
	 *
	 * @param keyCode The key code of the key that was pressed.
	 */
	public void keyPressed(int keyCode)
	{
		dismiss();
	}

	/**
	 * Renders the Canvas.
	 *
	 * @param g The {@link Graphics} object to be used for rendering the Canvas
	 */
	public void paint(Graphics g)
	{
		final Font font = g.getFont();
		g.setFont(Font.getFont(font.getFace(), Font.STYLE_BOLD, Font.SIZE_LARGE));

		g.setColor(BG_COLOR);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(FG_COLOR);

		int height = getHeight() >> 1;
		final int width = getWidth() >> 1;
		final int align = Graphics.HCENTER | Graphics.BASELINE;

		if (output.length() > 1)
		{
			height -= font.getHeight();
			g.drawString(output.substring(0, 1), width, height, align);
			g.setFont(Font.getFont(font.getFace(), Font.STYLE_PLAIN, Font.SIZE_SMALL));
			height += font.getHeight();
			g.drawString("as in", width, height, align);
			height += font.getHeight();
			g.setFont(Font.getFont(font.getFace(), Font.STYLE_BOLD, Font.SIZE_LARGE));
		}

		g.drawString(output, width, height, align);
	}

	/**
	 * Dissmisses the current screen.
	 */
	private void dismiss()
	{
		if (midlet.hasNextCharacter())
		{
			midlet.nextCharacter();
		}
		else
		{
			midlet.exitRequested();
		}
	}
}
