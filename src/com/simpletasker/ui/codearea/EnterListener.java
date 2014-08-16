package com.simpletasker.ui.codearea;

import java.awt.event.KeyEvent;

import com.simpletasker.ui.CodePane;
import com.simpletasker.ui.SuggestionsLabel;

/**
 * @author Sinius15
 */
public class EnterListener extends CodeAreaListener {

	public EnterListener(CodePane parent) {
		super(parent, KeyEvent.VK_ENTER);
	}

	@Override
	public boolean onAction() {
		if (!parent.suggestionsIsVisable())
			return false;
		SuggestionsLabel selected = parent.getSelectedSuggestion();
		parent.setCodeChaningText(true);

		String txt = parent.getCodeArea().getText();
		String before = "", after = "";

		try {
			before = txt.substring(0, parent.getWordBegin());
		} catch (Exception iliketrains) {
		}
		try {
			after = txt.substring(parent.getWordEnd(), txt.length() - 1);
		} catch (Exception iliketrains) {
		}

		parent.getCodeArea().setText(before + selected.getText() + after);
		parent.setCodeChaningText(false);

		parent.removeDropdownMenu();

		return true;
	}

}
