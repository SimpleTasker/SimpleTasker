package com.simpletasker.ui;

import java.awt.Color;
import java.io.IOException;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import com.simpletasker.Lib;

/**
 * Created by Sinius.
 */
public class GuiTester {

	EditorFrame frame;

	public GuiTester() {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				frame = new EditorFrame();
				frame.setVisible(true);
				
				getConsole().println("green", Color.green);
				getConsole().println("red", Color.red);
				getConsole().println("black", Color.black);

				System.out.println(getConsole().getInputLine());
			}
		});
		
	}

	public ConsoleArea getConsole() {
		return frame.getConsoleArea();
	}

	public EditorFrame getEditorFrame() {
		return frame;
	}

	public static void main(String[] args) {
		try {
			Lib.initLanguage("english");
		} catch (IOException e) {
			//this is the only hard coded string in the whole project.
			JOptionPane.showMessageDialog(null, "Could not load language file.", "Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		
		GuiTester manager = new GuiTester();

	}

}
