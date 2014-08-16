package com.simpletasker.ui;

import com.simpletasker.lang.Executor;
import com.simpletasker.lang.commands.Command;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import sun.net.www.protocol.gopher.GopherClient;

import com.simpletasker.lang.Executor;
import com.simpletasker.lang.commands.Command;

import java.awt.Color;
import java.awt.DefaultKeyboardFocusManager;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.KeyEventDispatcher;
import java.awt.Point;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.regex.Pattern;

@SuppressWarnings("unused")
public class CodePane extends JScrollPane implements DocumentListener, CaretListener, FocusListener{

	private static final long serialVersionUID = 3839348580827112332L;

	private JTextArea lineNumbers;
	private JTextArea codeArea;

	private JWindow suggestionsFrame;
	private JPanel suggestionsPanel = new JPanel();

	// the current caret position
	private int caretDot = 0;

	// the current magic carret position.
	private Point caretPos;

	// tells if the code/program is chaning the text in the codeArea JTextArea.
	private boolean codeIsChaningText = false;

	// the height of a suggestion label.
	private static final int suggestionHeight = 30;

	// borders for the SuggestionLabel
	private final Border redBorder = BorderFactory.createLineBorder(Color.red, 1);
	private final Border blackBorder = BorderFactory.createLineBorder(UIManager.getColor("Panel.background"), 1);

	/**
	 * The sellected suggestion. If value is -1 than there is no suggestion
	 * selected.
	 */
	private int selectedSuggestion = 0;
	private ArrayList<Suggestion> suggestions = new ArrayList<>();

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

	//called when the BUTTON_DOWN key is pressed
	private KeyEventDispatcher onButtonDown = new KeyEventDispatcher() {
		boolean b = false;

		@Override
		public boolean dispatchKeyEvent(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				b = !b;
				if (b)
					return true;
				selectedSuggestion++;
				if (selectedSuggestion >= suggestions.size())
					selectedSuggestion = 0;
				updateSelected();
				return true;
			}
			return false;
		}
	};

	//called when the BUTTON_UP key is pressed
	private KeyEventDispatcher onButtonUp = new KeyEventDispatcher() {
		boolean b = false;

		@Override
		public boolean dispatchKeyEvent(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_UP) {
				b = !b;
				if (b)
					return true;
				selectedSuggestion--;
				if (selectedSuggestion < 0)
					selectedSuggestion = suggestions.size() - 1;
				updateSelected();
				return true;
			}
			return false;
		}
	};

	//called when the ENTER key is pressed.
	private KeyEventDispatcher onButtonEnter = new KeyEventDispatcher() {
		boolean b = false;

		@Override
		public boolean dispatchKeyEvent(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				if (!suggestionsIsVisable())
					return false;
				b = !b;
				if (b)
					return true;
				if (!suggestionsIsVisable())
					return false;
				Suggestion selected = getSelectedSuggestion();
				codeIsChaningText = true;

				String txt = codeArea.getText();
				String before = "", after = "";

				try {
					before = txt.substring(0, getWordBegin());
				} catch (Exception iliketrains) {
				}

				try {
					after = txt.substring(getWordEnd(), txt.length() - 1);
				} catch (Exception iliketrains) {
				}

				codeArea.setText(before + selected.getText() + after);
				codeIsChaningText = false;

				removeDropdownMenu();

				return true;
			}
			return false;
		}
	};
	
	private KeyEventDispatcher onButtonEsc = new KeyEventDispatcher() {
		
		@Override
		public boolean dispatchKeyEvent(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
				removeDropdownMenu();
			return false;
		}
	};
	
	private KeyEventDispatcher onButtonSpace = new KeyEventDispatcher() {
		
		@Override
		public boolean dispatchKeyEvent(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_SPACE && e.isControlDown())
				updateDropdownMenu();
			return false;
		}
	};

	/**
	 * constructor
	 */
	public CodePane() {
		lineNumbers = new JTextArea("1");
		lineNumbers.setEditable(false);
		setRowHeaderView(lineNumbers);

		codeArea = new JTextArea();
		codeArea.setEditable(true);
		codeArea.addFocusListener(this);
		setViewportView(codeArea);

		codeArea.getDocument().addDocumentListener(this);
		codeArea.addCaretListener(this);

		suggestionsFrame = new JWindow();
		suggestionsFrame.setVisible(false);
		suggestionsFrame.setAlwaysOnTop(true);
		suggestionsFrame.setContentPane(suggestionsPanel);
		suggestionsFrame.setFocusable(false);

		suggestionsPanel.setLayout(new GridBagLayout());
		suggestionsPanel.setFocusable(false);

		DefaultKeyboardFocusManager.getCurrentKeyboardFocusManager()
				.addKeyEventDispatcher(onButtonDown);
		DefaultKeyboardFocusManager.getCurrentKeyboardFocusManager()
				.addKeyEventDispatcher(onButtonUp);
		DefaultKeyboardFocusManager.getCurrentKeyboardFocusManager()
				.addKeyEventDispatcher(onButtonEnter);
		DefaultKeyboardFocusManager.getCurrentKeyboardFocusManager()
				.addKeyEventDispatcher(onButtonEsc);
		DefaultKeyboardFocusManager.getCurrentKeyboardFocusManager()
				.addKeyEventDispatcher(onButtonSpace);
	}

	/**
	 * updates the dropDownSuggestionsMenu. If the menu is invisible and should
	 * be visible, it will be shown. And if it was visible but should not be
	 * visible it is removed.
	 */
	private void updateDropdownMenu() {
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
			for (Suggestion s : suggestions) {
				GridBagConstraints c = new GridBagConstraints();
				c.gridy = y;
				c.anchor = GridBagConstraints.WEST;
				suggestionsPanel.add(s, c);
				y++;
			}
			suggestionsFrame.setVisible(true);
			suggestionsFrame.pack();
			suggestionsFrame.setLocation(
					caretPos.x + codeArea.getLocationOnScreen().x, caretPos.y
							+ codeArea.getLocationOnScreen().y + 20);
		}
		updateSelected();
	}

	/**
	 * updates the {@link #suggestions} array values according to
	 * the {@link #selectedSuggestion} variable. It colors the selected
	 * value and resets the colors on the Suggestions that are not selected
	 * any more.
	 */
	private void updateSelected() {
		if (selectedSuggestion > suggestions.size() - 1)
			selectedSuggestion = suggestions.size() - 1;
		int i = 0;
		for (Suggestion s : suggestions) {
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
	private boolean suggestionsIsVisable() {
		return suggestionsFrame.isVisible();
	}

	/**
	 * finds the selected Suggestion. If the {@link #suggestionsFrame} is not
	 * visable, it will return null.
	 * 
	 * @return the selected Suggestion. null if there is none selected.
	 */
	private Suggestion getSelectedSuggestion() {
		if (!suggestionsIsVisable())
			return null;
		for (Suggestion s : suggestions)
			if (s.isSelected)
				return s;
		return null;
	}

	/**
	 * removes the dropdown menu from the screen.
	 */
	private void removeDropdownMenu() {
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
			suggestions.add(new Suggestion(com.getFullName()));
		}

	}

	/**
	 * Get the word that is currently being typed.
	 * 
	 * @return the word without spaces.
	 */
	public String getCurrentlyTypedWord() {
		if (caretDot <= 0)
			return "";
		return codeArea.getText().substring(getWordBegin(), getWordEnd())
				.trim();
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
		caretPos = temp;
		SwingUtilities.invokeLater(onTextChange);
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		Point temp = codeArea.getCaret().getMagicCaretPosition();
		if (temp == null || codeIsChaningText)
			return;
		caretPos = temp;

		SwingUtilities.invokeLater(onTextChange);
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		Point temp = codeArea.getCaret().getMagicCaretPosition();
		if (temp == null || codeIsChaningText)
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
	
	// CLASSES
	// .............................................

	public class Suggestion extends JLabel {

		private static final long serialVersionUID = 3273864444932843439L;

		public Suggestion(String name) {
			super(name);
			setBorder(blackBorder);
			setFocusable(false);
		}

		private boolean isSelected = false;

		public void setSelected(boolean isSelected) {
			this.isSelected = isSelected;
			if (isSelected)
				setBorder(redBorder);
			else
				setBorder(blackBorder);
		}

		public boolean isSelected() {
			return isSelected;
		}

		@Override
		public String toString() {
			return "[" + getText() + "]";
		}

	}
}
