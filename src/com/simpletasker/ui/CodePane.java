package com.simpletasker.ui;

import com.simpletasker.lang.Executor;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.util.ArrayList;
import java.util.regex.Pattern;

@SuppressWarnings("unused")
public class CodePane extends JScrollPane implements DocumentListener,
		CaretListener {

	private static final long serialVersionUID = 3839348580827112332L;

	private JTextArea lineNumbers;
	private JTextArea codeArea;

	private JFrame suggestionsFrame;
	private JPanel suggestionsPanel;

	// the current caret position
	private int caretDot = 0;

	// the current magic carret position.
	private Point caretPos;

	// tells if the code/program is chaning the text in the codeArea JTextArea.
	private boolean codeIsChaningText = false;
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
	private int selected = 0;
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

		suggestionsFrame = new JFrame();
		suggestionsFrame.setBounds(50, 50, 50, 50);
		suggestionsFrame.setVisible(false);
		suggestionsFrame.setAlwaysOnTop(true);
		//suggestionsFrame.setUndecorated(true);

		suggestionsPanel = new JPanel();
		suggestionsPanel.setBorder(BorderFactory.createRaisedBevelBorder());
		suggestionsPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

		suggestionsFrame.setContentPane(suggestionsPanel);

	}

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
		int sizeBefore = suggestions.size();
		updateSuggestions(word);
		if (suggestions.size() == 0) {
			if (suggestionsIsVisable())
				removeDropdownMenu();
			return;
		}
		boolean sizeChanged = suggestions.size() != sizeBefore;
		if (suggestionsIsVisable() && !sizeChanged) {
			updateSelected();
		}else{
			suggestionsPanel.removeAll();
			for(Suggestion s : suggestions){
				suggestionsPanel.add(s);
			}
			suggestionsFrame.setVisible(true);
			suggestionsFrame.setBounds(100, 100, 200, 30*suggestions.size());
		}
	}

	private void updateSelected() {
		int i = 0;
		for(Suggestion s : suggestions){
			if(i == selected)
				s.isSelected = true;
			else
				s.isSelected = false;
			s.revalidate();
			i++;
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
		Executor exe = Executor.getInstance();

		// Command[] cmds = exe.getCommands(word);
		// for (Command com : cmds) {
		// suggestions.add(new Suggestion(com.getFullName()));
		// }
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

	private class Suggestion extends JLabel {

		private static final long serialVersionUID = 3273864444932843439L;

		public boolean isSelected = false;

		public Suggestion(String name) {
			super(name);
			setPreferredSize(new Dimension(220, 30));
		}

		@Override
		public String toString() {
			return "[" + getText() + "]";
		}

	}
}
