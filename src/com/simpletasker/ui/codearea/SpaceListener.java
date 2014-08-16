package com.simpletasker.ui.codearea;

import java.awt.event.KeyEvent;

import com.simpletasker.ui.CodePane;

/**
 * @author Sinius15
 */
public class SpaceListener extends CodeAreaListener {

	public SpaceListener(CodePane parent) {
		super(parent, KeyEvent.VK_SPACE);
	}

	@Override
	public boolean onAction() {
		parent.updateDropdownMenu();
		return true;
	}

}
