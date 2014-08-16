package com.simpletasker.ui.codearea;

import java.awt.KeyEventDispatcher;
import java.awt.event.KeyEvent;

import com.simpletasker.ui.CodePane;

public abstract class CodeAreaListener implements KeyEventDispatcher {

	private int keyCode;
	protected CodePane parent;

	// boolean so that every event is only called once.
	private boolean first = false;

	public CodeAreaListener(CodePane parent, int keyCode) {
		this.parent = parent;
		this.keyCode = keyCode;
	}

	public abstract boolean onAction();

	/**
	 * return true if the event is consumed, else return false.
	 */
	@Override
	public boolean dispatchKeyEvent(KeyEvent e) {
		first = !first;
		if (first)
			return false;
		if (e.getKeyCode() != getKeyCode())
			return false;
		return onAction();
	}

	public int getKeyCode() {
		return keyCode;
	}

}
