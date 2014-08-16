package com.simpletasker.ui.codearea;

import java.awt.event.KeyEvent;

import com.simpletasker.ui.CodePane;

/**
 * Created by Sinius.
 */
public class DownListener extends CodeAreaListener {

	public DownListener(CodePane parent) {
		super(parent, KeyEvent.VK_DOWN);
	}

	@Override
	public boolean onAction() {
		parent.setSelectedSuggestion(parent.getSelectedSuggestion() + 1);
		if (parent.getSelectedSuggestion() >= parent.getSuggestions().size())
			parent.setSelectedSuggestion(0);
		parent.updateSelected();
		return true;
	}

}