package com.simpletasker.ui;

import java.awt.Color;

public class GuiManager {
	
	EditorFrame frame;
	
	public GuiManager(){
		frame = new EditorFrame();
		frame.setVisible(true);
	}
	
	public ConsoleArea getConsole(){
		return frame.getConsoleArea();
	}
	
	public EditorFrame getEditorFrame(){
		return frame;
	}
	
	public static void main(String[] args) {
		GuiManager manager = new GuiManager();
		manager.getConsole().println("green", Color.green);
		manager.getConsole().println("red", Color.red);
		manager.getConsole().println("black", Color.black);
		
		System.out.println(manager.getConsole().getInputLine());
	}
	
}
