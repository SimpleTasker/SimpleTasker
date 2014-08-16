package com.simpletasker.ui;

import java.awt.Color;

/**
 * Created by Sinius.
 */
public class GuiTester {

	EditorFrame frame;

	public GuiTester() {
		frame = new EditorFrame();
		frame.setVisible(true);
	}

	public ConsoleArea getConsole() {
		return frame.getConsoleArea();
	}

	public EditorFrame getEditorFrame() {
		return frame;
	}

	public static void main(String[] args) {
		GuiTester manager = new GuiTester();
		manager.getConsole().println("green", Color.green);
		manager.getConsole().println("red", Color.red);
		manager.getConsole().println("black", Color.black);

		System.out.println(manager.getConsole().getInputLine());
	}

}
