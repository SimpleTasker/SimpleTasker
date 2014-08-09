package com.simpletasker.ui;

import java.awt.Color;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

public class ConsoleArea extends JTextPane {

	private static final long serialVersionUID = 1505389612102502013L;

	public ConsoleArea(){
		setEditable(false);
	}
	
	/**
	 * add line to the console in the specified color.
	 * @param text the string to add to the console.
	 * @param color the specified color
	 */
	public synchronized void println(String text, Color color){
		SimpleAttributeSet set = new SimpleAttributeSet();
		StyleConstants.setForeground(set, color);
		try {
			getDocument().insertString(getDocument().getLength(), text + System.lineSeparator(), set);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Clears the console from all its text.
	 */
	public void clearConsole(){
		try {
			getDocument().remove(0, getDocument().getLength());
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * {@link #println(String, Color)} and {@link #clearConsole()} for setting text.
	 */
	@Deprecated
	public void setText(String t) {
		throw new NullPointerException("set-txt is not avalable for the class ConsoleArea.");
	}

}
