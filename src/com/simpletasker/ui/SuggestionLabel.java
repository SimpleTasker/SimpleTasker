package com.simpletasker.ui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.KeyStroke;
import javax.swing.border.LineBorder;

public class SuggestionLabel extends JLabel {

	private static final long serialVersionUID = 6009853525002186427L;

	private boolean focused = false;

	private final CodeTextArea parent;

	private static final Color suggestionsTextColor = Color.BLACK; 
	private static final Color suggestionBorderColor = Color.RED;

	private Action replaceAction = new AbstractAction() {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent ae) {
			replaceWithSuggestedText();
			parent.removeDropdownMenu();
		}
	};

	public SuggestionLabel(String txt, CodeTextArea parent) {
		super(txt);

		this.parent = parent;

		initComponent();
	}

	private void initComponent() {
		setFocusable(true);
		setForeground(suggestionsTextColor);

		getInputMap(JComponent.WHEN_FOCUSED).put(
				KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true),
				"Enter released");
		getActionMap().put("Enter released", replaceAction);
	}

	public void setFocused(boolean focused) {
		if (focused) {
			setBorder(new LineBorder(suggestionBorderColor));
			requestFocusInWindow();
			requestFocus();
		} else {
			setBorder(null);
		}
		repaint();
		this.focused = focused;
	}

	public boolean isFocused() {
		return focused;
	}

	private void replaceWithSuggestedText() {
		parent.codeIsChaningText = true;
		String txt = parent.getText();
		String before = "", after = "";

		try {
			before = txt.substring(0, parent.getWordBegin());
		} catch (Exception e) {
		}

		try {
			after = txt.substring(parent.getWordEnd(), txt.length() - 1);
		} catch (Exception e) {
		}
		parent.setText(before + getText() + after);
		parent.removeDropdownMenu();
		parent.codeIsChaningText = false;
	}

}
