package com.simpletasker.ui;

import java.awt.DefaultKeyboardFocusManager;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.regex.Pattern;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Caret;

import com.simpletasker.lang.Executor;
import com.simpletasker.lang.commands.Command;
import com.simpletasker.ui.codearea.DownListener;
import com.simpletasker.ui.codearea.EnterListener;
import com.simpletasker.ui.codearea.EscapeListener;
import com.simpletasker.ui.codearea.SpaceListener;
import com.simpletasker.ui.codearea.UpListener;

/**
 * @author Sinius15
 */
public class CodePane extends JScrollPane implements DocumentListener, CaretListener, FocusListener{

	private static final long serialVersionUID = 3839348580827112332L;

	private JTextArea lineNumbers;
	private JTextArea codeArea = new JTextArea();

	private JWindow suggestionsFrame;
	private JPanel suggestionsPanel = new JPanel();

	private int caretDot = 0;
	private Point caretPos;
	private boolean codeIsChaningText = false;
	private int selectedSuggestion = 0;
	private ArrayList<SuggestionsLabel> suggestions = new ArrayList<>();

	/**
	 * Called when the user changes the content of the {@link #codeArea}
	 */
	private Runnable onTextChange = new Runnable() {
		@Override
		public void run() {
			setCodeChaningText(true);
			updateLineNumbers();
			updateDropdownMenu();
			setCodeChaningText(false);
		}
	};

	/**
	 * constructor
	 */
	public CodePane() {
		lineNumbers = new JTextArea("1");
		lineNumbers.setEditable(false);
		lineNumbers.setBackground(UIManager.getColor("Panel.background"));
		setRowHeaderView(lineNumbers);
		
		getCodeArea().setEditable(true);
		getCodeArea().addFocusListener(this);
		addFocusListener(this);
		setViewportView(getCodeArea());

		getCodeArea().getDocument().addDocumentListener(this);
		getCodeArea().addCaretListener(this);

		suggestionsFrame = new JWindow();
		suggestionsFrame.setVisible(false);
		suggestionsFrame.setAlwaysOnTop(true);
		suggestionsFrame.setContentPane(suggestionsPanel);
		suggestionsFrame.setFocusable(false);

		suggestionsPanel.setLayout(new GridBagLayout());
		suggestionsPanel.setFocusable(false);

		DefaultKeyboardFocusManager.getCurrentKeyboardFocusManager()
				.addKeyEventDispatcher(new DownListener(this));
		DefaultKeyboardFocusManager.getCurrentKeyboardFocusManager()
				.addKeyEventDispatcher(new UpListener(this));
		DefaultKeyboardFocusManager.getCurrentKeyboardFocusManager()
				.addKeyEventDispatcher(new EnterListener(this));
		DefaultKeyboardFocusManager.getCurrentKeyboardFocusManager()
				.addKeyEventDispatcher(new EscapeListener(this));
		DefaultKeyboardFocusManager.getCurrentKeyboardFocusManager()
				.addKeyEventDispatcher(new SpaceListener(this));
	}

	/**
	 * updates the dropDownSuggestionsMenu. If the menu is invisible and should
	 * be visible, it will be shown. And if it was visible but should not be
	 * visible it is removed.
	 */
	public void updateDropdownMenu() {
		String word = getCurrentlyTypedWord();
		if (word.equals("")) {
			removeDropdownMenu();
			return;
		}
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
			// updateSelected();
		} else {
			suggestionsPanel.removeAll();
			int y = 0;
			for (SuggestionsLabel s : suggestions) {
				GridBagConstraints c = new GridBagConstraints();
				c.gridy = y;
				c.anchor = GridBagConstraints.WEST;
				suggestionsPanel.add(s, c);
				y++;
			}
			suggestionsFrame.setVisible(true);
			suggestionsFrame.pack();
			suggestionsFrame.setLocation(
					getCaretPos().x + getCodeArea().getLocationOnScreen().x, getCaretPos().y
							+ getCodeArea().getLocationOnScreen().y + 20);
		}
		updateSelected();
	}

	/**
	 * updates the {@link #suggestions} array values according to
	 * the {@link #selectedSuggestion} variable. It colors the selected
	 * value and resets the colors on the Suggestions that are not selected
	 * any more.
	 */
	public void updateSelected() {
		if (selectedSuggestion > suggestions.size() - 1)
			selectedSuggestion = suggestions.size() - 1;
		int i = 0;
		for (SuggestionsLabel s : suggestions) {
			if (i == selectedSuggestion)
				s.setSelected(true);
			else
				s.setSelected(false);
			s.revalidate();
			i++;
		}
		suggestionsFrame.pack();
	}

	/**
	 * @return whether or not the {@link #suggestionsFrame} is visable.
	 */
	public boolean suggestionsIsVisable() {
		return suggestionsFrame.isVisible();
	}

	/**
	 * finds the selected Suggestion. If the {@link #suggestionsFrame} is not
	 * visable, it will return null.
	 * 
	 * @return the selected Suggestion. null if there is none selected.
	 */
	public SuggestionsLabel getSelectedSuggestionLabel() {
		if (!suggestionsIsVisable())
			return null;
		for (SuggestionsLabel s : suggestions)
			if (s.isSelected())
				return s;
		return null;
	}

	/**
	 * removes the dropdown menu from the screen.
	 */
	public void removeDropdownMenu() {
		suggestions.clear();
		suggestionsPanel.removeAll();
		suggestionsFrame.setVisible(false);
		selectedSuggestion = 0;
	}

	/**
	 * updates the {@link #suggestions} array according to the specified word.
	 * 
	 * @param word
	 *            the specified word.
	 */
	private void updateSuggestions(String word) {
		suggestions.clear();
		Executor exe = Executor.getInstance();
		Command[] cmds = exe.getCommands(word);
		for (Command com : cmds) {
			suggestions.add(new SuggestionsLabel(com.getFullName()));
		}

	}

	/**
	 * Get the word that is currently being typed.
	 * 
	 * @return the word without spaces.
	 */
	public String getCurrentlyTypedWord() {
		if (getCaretDot() <= 0)
			return "";
		return getCodeArea().getText().substring(getWordBegin(), getWordEnd())
				.trim();
	}

	/**
	 * 
	 * @return the beginning of the word.
	 */
	public int getWordBegin() {
		int wordBegin = getCaretDot();
		String text = getCodeArea().getText();
		while (true) {
			if (wordBegin <= 0)
				break;
			String sub = text.substring(wordBegin, getCaretDot());
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
		return getCaretDot();
	}
	
	/**
	 * updates the line numbers.
	 */
	public void updateLineNumbers(){
		StringBuffer text = new StringBuffer("1" + System.lineSeparator());
		int lines = getCodeArea().getText().split("\\r?\\n", -1).length;
		for (int i = 2; i <= lines; i++) {
			text.append(i + "  " + System.lineSeparator());
		}
		lineNumbers.setText(text.toString());
	}

	// LISTENERS
	// .............................................
	@Override
	public void caretUpdate(CaretEvent e) {
		caretDot = e.getDot();
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		Point temp = getCodeArea().getCaret().getMagicCaretPosition();
		if (temp == null || isCodeChaningText())
			return;
		caretPos = temp;
		SwingUtilities.invokeLater(onTextChange);
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		Point temp = getCodeArea().getCaret().getMagicCaretPosition();
		if (temp == null || isCodeChaningText())
			return;
		caretPos = temp;

		SwingUtilities.invokeLater(onTextChange);
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		Point temp = getCodeArea().getCaret().getMagicCaretPosition();
		if (temp == null || isCodeChaningText())
			return;
		caretPos = temp;

		SwingUtilities.invokeLater(onTextChange);
	}
	
	@Override
	public void focusGained(FocusEvent e) {
		
	}

	@Override
	public void focusLost(FocusEvent e) {
		removeDropdownMenu();
	}
	
	// GETTERS AND SETTERS
	// .............................................
	
	/**
	 * @return wether or not the code is chaning the text inside the codeArea.
	 */
	public boolean isCodeChaningText(){
		return codeIsChaningText;
	}
	
	/**
	 * @param b set if the code is chaning the text or not.
	 */
	public void setCodeChaningText(boolean b){
		codeIsChaningText = b;
	}
	
	/**
	 * @return the position of the caret. 
	 * @see {@link Caret #getDot()}
	 */
	public int getCaretDot() {
		return caretDot;
	}
	
	/**
	 * @return the visual position of the caret. 
	 * @see {@link Caret #getMagicCaretPosition()}
	 */
	public Point getCaretPos() {
		return caretPos;
	}
	
	/**
	 * @return the CodeArea.
	 */
	public JTextArea getCodeArea(){
		return codeArea;
	}
	
	/**
	 * This value can never be bigger than the size of {@link #getSuggestions()}
	 * @return the selected suggestion value. If value is -1 than nothing is selected.
	 */
	public int getSelectedSuggestion() {
		return selectedSuggestion;
	}
	
	/**
	 * This value can never be bigger than the size of {@link #getSuggestions()}
	 * @param the selected suggestion value.
	 */
	public void setSelectedSuggestion(int selectedSuggestion) {
		this.selectedSuggestion = selectedSuggestion;
	}
	
	/**
	 * @return the array where all the {@link SuggestionsLabel}s are stored.
	 */
	public ArrayList<SuggestionsLabel> getSuggestions() {
		return suggestions;
	}
}
