package com.simpletasker.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JWindow;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

public class CodeTextArea extends JTextArea implements CaretListener{

	private static final long serialVersionUID = -7018375408410070748L;

	public final JWindow popupWindow;
	public final JPanel suggestionsPanel;
	
	public ArrayList<SuggestionLabel> labels = new ArrayList<>();
	public Point magicCaretPos = null;
	
	public boolean codeIsChaningText = false;
	public float opacity = 0.8f;
	public int carotPos = 0;
	
	/**
	 * fired when button down is released when the focus is on the textField.
	 */
	private AbstractAction onButtonDownInTextfield = new AbstractAction() {
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent ae) {
			
			if(isMenuVisable()){
				for (SuggestionLabel label : labels) {
					// only the first label should be focused.
					label.setFocused(true);
					label.requestFocusInWindow();
					break;
				}
			}
			
		}
	};
	
	/**
	 * fired when button down is released when the focus is on the drop down
	 * thingy.
	 */
	private AbstractAction onButtonDwonInDownPanel = new AbstractAction() {
		
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent ae) {

			if (labels.size() > 1) {
				
				int i = 0;
				int newFocused = 0;
				for(SuggestionLabel label : labels){
					if(label.isFocused()){
						label.setFocused(false);
						newFocused = i+1;
					}
					i++;
				}
				if(newFocused > labels.size()-1)
					setFocusToTextField();
				else
					labels.get(newFocused).setFocused(true);

			} else {
				// if there is just 1 suggestion, a button press down always
				// result in setting the focus to the text field.
				setFocusToTextField();
			}
		}
		
	};
	
	SimpleAttributeSet red = new SimpleAttributeSet();
	SimpleAttributeSet black = new SimpleAttributeSet();
	
	public CodeTextArea() {
		
		addCaretListener(this);
		
		StyleConstants.setForeground(red, Color.RED);
		StyleConstants.setForeground(black, Color.BLACK);
		
		popupWindow = new JWindow();
		popupWindow.setOpacity(this.opacity);
		
		suggestionsPanel = new JPanel();
		suggestionsPanel.setLayout(new GridLayout(0, 1));
		suggestionsPanel.setBackground(Color.white);
		
		popupWindow.getContentPane().add(suggestionsPanel);
		
		getInputMap(JComponent.WHEN_FOCUSED).put(
				KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, true), "Down released");
		getActionMap().put("Down released", onButtonDownInTextfield);
		
		suggestionsPanel.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(
				KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, true), "Down released");
		suggestionsPanel.getActionMap().put("Down released", onButtonDwonInDownPanel);
	}
	
	protected boolean isMenuVisable() {
		return popupWindow.isVisible();
	}

	@Override
	public String getText() {
		try {
			return getDocument().getText(0, getDocument().getLength());
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public void setText(String t) {
		super.setText(t);
	}
	
	@Override
	public void caretUpdate(CaretEvent e) {
		carotPos = e.getDot();
	}
	
	public void showMenu() {

		addSuggestions(getCurrentlyTypedWord());
		
		if(labels.size() == 0){
			removeDropdownMenu();
			return;
		}
		
		suggestionsPanel.removeAll();
		
		for(SuggestionLabel label : labels){
			suggestionsPanel.add(label);
		}
		
		popupWindow.setMinimumSize(new Dimension(getWidth(), 30));
		popupWindow.pack();
		popupWindow.setVisible(true);
		
		int windowX = (int) magicCaretPos.x + getLocationOnScreen().x;
		int windowY = (int) magicCaretPos.y + getLocationOnScreen().y+20;
		
		popupWindow.setLocation(windowX, windowY);
		popupWindow.setMinimumSize(new Dimension(getWidth(), 30));
		popupWindow.revalidate();
		popupWindow.repaint();
		
	}
	
	/**
	 * Adds all the suggestions to the {@link #labels} arraylist.
	 * 
	 * @param typedWord the wordt to check
	 * @return wether or not a new suggestion is added.
	 */
	public void addSuggestions(String typedWord) {
		labels.clear();
		if (typedWord.isEmpty()) {
			return;
		}
		
		//TODO: add new suggestions to the labels array.
	}
	
	/**
	 * Colors the lines that contain an error. 
	 */
	public void colorErrors() {
		//TODO: color.
	}
	
	/**
	 * Get the word that is currently being typed.
	 * 
	 * @return the word without spaces.
	 */
	public String getCurrentlyTypedWord() {
		if (carotPos <= 0)
			return "";
		return getText().substring(getWordBegin(), getWordEnd());
	}
	
	/**
	 * 
	 * @return the beginning of the word.
	 */
	public int getWordBegin(){
		int wordBegin = carotPos;
		String text = getText();
		while (true) {
			if (wordBegin <= 0)
				break;
			String sub = text.substring(wordBegin, carotPos);
			if (sub.startsWith(" ") || sub.startsWith(";")) {
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
	public int getWordEnd(){
		return carotPos;
	}
	
	/**
	 * Sets the focus to the current textPane.
	 */
	public void setFocusToTextField() {
		//parent.toFront();
		//parent.requestFocusInWindow();
		requestFocusInWindow();
		requestFocus();
	}
	
	/**
	 * Removes the dropdown menu from the screen.
	 */
	public void removeDropdownMenu(){
		labels.clear();
		popupWindow.setVisible(false);
	}
}
