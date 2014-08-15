package com.simpletasker.ui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Point;
import java.util.ArrayList;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.simpletasker.lang.Executor;
import com.simpletasker.lang.commands.Command;

@SuppressWarnings("unused")
public class CodePane extends JScrollPane implements DocumentListener,
		CaretListener {

	private static final long serialVersionUID = 3839348580827112332L;

	private JTextArea lineNumbers;
	private JTextArea codeArea;

	private JWindow suggestionsFrame;
	private JPanel suggestionsPanel;

	// the current caret position
	private int caretDot = 0;

	// the current magic carret position.
	private Point caretPos;

	// tells if the code/program is chaning the text in the codeArea JTextArea.
	private boolean codeIsChaningText = false;

	/**
	 * The sellected suggestion. If value is -1 than there is no suggestion
	 * selected.
	 */
	private int slectedSuggestion = -1;

	private ArrayList<Suggestion> suggestions = new ArrayList<>();

	public CodePane() {
		lineNumbers = new JTextArea("1");
		lineNumbers.setEditable(false);
		this.setRowHeaderView(lineNumbers);

		codeArea = new JTextArea();
		codeArea.setEditable(true);
		this.setViewportView(codeArea);

		codeArea.getDocument().addDocumentListener(this);
		codeArea.addCaretListener(this);

		suggestionsFrame = new JWindow();
		suggestionsFrame.setBounds(50, 50, 50, 50);
		suggestionsFrame.setVisible(false);
		suggestionsFrame.setAlwaysOnTop(true);    
		
		//suggestionsFrame.getRootPane().setBorder(BorderFactory.create);
		
		suggestionsPanel = new JPanel();
		suggestionsPanel.setBorder(BorderFactory.createRaisedBevelBorder());
		suggestionsPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

		suggestionsFrame.setContentPane(suggestionsPanel);

	}

	/**
	 * Called when the user changes the content of the {@link #codeArea}
	 */
	private Runnable onTextChange = new Runnable() {
		@Override
		public void run() {
			codeIsChaningText = true;
			updateDropdownMenu();
			codeIsChaningText = false;
		}
	};

	/**
	 * updates the dropDownSuggestionsMenu. If the menu is invisible and should
	 * be visible, it will be shown. And if it was visible but should not be
	 * visible it is removed.
	 */
	private void updateDropdownMenu() {
		String word = getCurrentlyTypedWord();
		if (word.equals(""))
			return;
		// give the argument word so we do not have to call
		// getCurrentlytypedWord() again.
		updateSuggestions(word);
		if (suggestions.size() == 0) {
			if (suggestionsIsVisable())
				removeDropdownMenu();
			return;
		}
		if (suggestionsIsVisable()) {

		} else {
			suggestionsFrame.setVisible(true);
		}
	}

	private boolean suggestionsIsVisable() {
		return suggestionsFrame.isVisible();
	}

	private void removeDropdownMenu() {
		suggestions.clear();
		suggestionsPanel.removeAll();
		suggestionsFrame.setVisible(false);
	}

	private void updateSuggestions(String word) {
		suggestions.clear();
		Executor exe = new Executor();

		 Command[] cmds = exe.getCommands(word);
		 for (Command com : cmds) {
		 suggestions.add(new Suggestion(com.getFullName()));
		 }
		suggestions.add(new Suggestion("aaa"));
		suggestions.add(new Suggestion("bbb"));
		suggestions.add(new Suggestion("ccc"));
	}

	/**
	 * Get the word that is currently being typed.
	 * 
	 * @return the word without spaces.
	 */
	public String getCurrentlyTypedWord() {
		if (caretDot <= 0)
			return "";
		return codeArea.getText().substring(getWordBegin(), getWordEnd());
	}

	/**
	 * 
	 * @return the beginning of the word.
	 */
	public int getWordBegin() {
		int wordBegin = caretDot;
		String text = codeArea.getText();
		while (true) {
			if (wordBegin <= 0)
				break;
			String sub = text.substring(wordBegin, caretDot);
			if (Pattern.compile("\\s").matcher(sub).find()) {
				wordBegin++;
				break;
			}
			wordBegin--;
		}
		return wordBegin;
	}

	/**
	 * @return the end of the word.
	 */
	public int getWordEnd() {
		return caretDot;
	}

	// CLASSES
	// .............................................

	private class Suggestion extends JLabel {

		private static final long serialVersionUID = 3273864444932843439L;

		public Suggestion(String name) {
			super(name);
		}

		@Override
		public String toString() {
			return "[" + getText() + "]";
		}

	}

	// LISTENERS
	// .............................................
	@Override
	public void caretUpdate(CaretEvent e) {
		caretDot = e.getDot();
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		Point temp = codeArea.getCaret().getMagicCaretPosition();
		if (temp == null || codeIsChaningText)
			return;
		if (!suggestionsIsVisable())
			caretPos = temp;
		SwingUtilities.invokeLater(onTextChange);
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		Point temp = codeArea.getCaret().getMagicCaretPosition();
		if (temp == null || codeIsChaningText)
			return;
		if (!suggestionsIsVisable())
			caretPos = temp;
		SwingUtilities.invokeLater(onTextChange);
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		Point temp = codeArea.getCaret().getMagicCaretPosition();
		if (temp == null || codeIsChaningText)
			return;
		if (!suggestionsIsVisable())
			caretPos = temp;
		SwingUtilities.invokeLater(onTextChange);
	}
}
