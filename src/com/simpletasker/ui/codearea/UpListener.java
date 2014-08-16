package com.simpletasker.ui.codearea;

import java.awt.event.KeyEvent;

import com.simpletasker.ui.CodePane;

/**
 * @author Sinius15
 */
public class UpListener extends CodeAreaListener {

	public UpListener(CodePane parent) {
		super(parent, KeyEvent.VK_UP);
	}

	@Override
	public boolean onAction() {
		
		parent.setSelectedSuggestion(parent.getSelectedSuggestion()-1);
		if (parent.getSelectedSuggestion() < 0)
			parent.setSelectedSuggestion(parent.getSuggestions().size()-1);
		parent.updateSelected();
		return true;
	}

}