package com.simpletasker.ui;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.UIManager;
import javax.swing.border.Border;

public class SuggestionsLabel extends JLabel {

	private static final long serialVersionUID = 3273864444932843439L;

	private static final Border redBorder = BorderFactory.createLineBorder(Color.red, 1);
	private static final Border blackBorder = BorderFactory.createLineBorder(UIManager.getColor("Panel.background"), 1);
	
	private boolean isSelected = false;
	
	public SuggestionsLabel(String name) {
		super(name);
		setBorder(blackBorder);
		setFocusable(false);
	}

	

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

}
