package com.simpletasker.ui.codearea;

import java.awt.event.KeyEvent;

import com.simpletasker.ui.CodePane;

/**
 * Created by Sinius.
 */
public class EscapeListener extends CodeAreaListener {

	public EscapeListener(CodePane parent) {
		super(parent, KeyEvent.VK_ESCAPE);
	}

	@Override
	public boolean onAction() {
		parent.removeDropdownMenu();
		return true;
	}

}