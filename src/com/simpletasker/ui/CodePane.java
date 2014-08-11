package com.simpletasker.ui;

import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class CodePane extends JScrollPane implements DocumentListener{

	private static final long serialVersionUID = 3839348580827112332L;

	CodeTextArea textArea;
	JTextArea lineNrPane;
	
	boolean codeIsChaningText = false;
	
	public CodePane(){
		textArea = new CodeTextArea();
		textArea.getDocument().addDocumentListener(this);
		
		lineNrPane = new JTextArea();
		lineNrPane.setBackground(UIManager.getColor("Panel.background"));
		lineNrPane.setEditable(false);
		lineNrPane.setText("1");
		
		setViewportView(textArea);
		setRowHeaderView(lineNrPane);
		
	}

	public void updateLineNumbers() {
		StringBuffer text = new StringBuffer("1  " + "\n");
		int lines = textArea.getText().split("\\r?\\n", -1).length;
		for (int i = 2; i <= lines; i++) {
			text.append(i + "  " + "\n");
		}
		lineNrPane.setText(text.toString());
	}
	
	Runnable onChane = new Runnable() {
		@Override
		public synchronized void run() {
			codeIsChaningText = true;
			updateLineNumbers();
			textArea.colorErrors();
			
			textArea.showMenu();
			doFocusStuff();
			codeIsChaningText = false;
		}
	};
	
	@Override
	public void insertUpdate(DocumentEvent e) {
		Point temp = textArea.getCaret().getMagicCaretPosition();
		if(temp == null || textArea.codeIsChaningText)
			return;
		textArea.magicCaretPos = temp;
		SwingUtilities.invokeLater(onChane);
		
	}
	
	@Override
	public void removeUpdate(DocumentEvent e) {
		Point temp = textArea.getCaret().getMagicCaretPosition();
		if(temp == null || codeIsChaningText)
			return;
		textArea.magicCaretPos = temp;
		SwingUtilities.invokeLater(onChane);
	}
	
	@Override
	public void changedUpdate(DocumentEvent e) {
		Point temp = textArea.getCaret().getMagicCaretPosition();
		if(temp == null || codeIsChaningText)
			return;
		textArea.magicCaretPos = temp;
		SwingUtilities.invokeLater(onChane);
	}
	
	public void doFocusStuff(){
		System.out.println("adsfs");
		textArea.popupWindow.requestFocusInWindow();
		textArea.popupWindow.requestFocus();
	}
	
}
