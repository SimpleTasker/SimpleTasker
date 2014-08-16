package com.simpletasker.ui;

import java.awt.Color;

import javax.swing.JTextPane;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

/**
 * Created by Sinius.
 */
public class ConsoleArea extends JTextPane implements CaretListener {

	private static final long serialVersionUID = 1505389612102502013L;

	/**
	 * Constructor yay
	 */
	public ConsoleArea() {
		setEditable(false);
		addCaretListener(this);

	}

	/**
	 * Counts the amount of lines in this are.
	 * 
	 * @return the amount of lines.
	 */
	public int getLineCount() {
		return getText().split("\\n", -1).length;
	}

	/**
	 * This will hold the thread until the user enterd a line.
	 * 
	 * @return the typed line.
	 */
	public String getInputLine() {
		setEditable(true);
		int inputLine = getLineCount();
		int lineBeginOffset = getDocument().getLength();
		while (getLineCount() == inputLine) {

			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		setEditable(false);

		try {
			return getDocument().getText(lineBeginOffset,
					getDocument().getLength() - lineBeginOffset);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * add line to the console in the specified color.
	 * 
	 * @param text
	 *            the string to add to the console.
	 * @param color
	 *            the specified color
	 */
	public synchronized void println(String text, Color color) {
		SimpleAttributeSet set = new SimpleAttributeSet();
		StyleConstants.setForeground(set, color);
		try {
			getDocument().insertString(getDocument().getLength(), text + "\n",
					set);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Clears the console from all its text.
	 */
	public void clearConsole() {
		try {
			getDocument().remove(0, getDocument().getLength());
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}

	/**
	 * use {@link #println(String, Color)} and {@link #clearConsole()} for
	 * setting text.
	 */
	@Deprecated
	public void setText(String t) {
		throw new NullPointerException(
				"set-txt is not avalable for the class ConsoleArea.");
	}

	/**
	 * Make sure the caret is always on the end of the file.
	 */
	@Override
	public void caretUpdate(CaretEvent e) {
		if (getCaretPosition() != getDocument().getLength())
			setCaretPosition(getDocument().getLength());
	}

}
