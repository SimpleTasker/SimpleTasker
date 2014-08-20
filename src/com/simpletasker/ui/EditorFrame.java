package com.simpletasker.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JScrollPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.simpletasker.Lib;

/**
 * Created by Sinius.
 */
public class EditorFrame extends JFrame {

	private static final long serialVersionUID = 4513395576031795608L;
	private JPanel contentPane;
	private ConsoleArea consoleArea;
	private JTabbedPane tabbedPane;
	
	private EditorFrame thiss = this;
	
	public EditorFrame() {

		setTitle(Lib.getLang("main.title"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 730, 658);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnFile = new JMenu(Lib.getLang("main.file"));
		menuBar.add(mnFile);

		JMenuItem mntmSave = new JMenuItem(Lib.getLang("file.save"));
		mnFile.add(mntmSave);

		JMenuItem mntmSaveAs = new JMenuItem(Lib.getLang("file.saveas"));
		mnFile.add(mntmSaveAs);

		JMenuItem mntmLoad = new JMenuItem(Lib.getLang("file.load"));
		mnFile.add(mntmLoad);

		JMenu mnSource = new JMenu(Lib.getLang("main.source"));
		menuBar.add(mnSource);

		JMenu mnHelp = new JMenu(Lib.getLang("main.help"));
		menuBar.add(mnHelp);

		JMenuItem mntmAbout = new JMenuItem(Lib.getLang("help.about"));
		mnHelp.add(mntmAbout);
		contentPane = new JPanel();
		contentPane.setBorder(null);
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JSplitPane splitPane = new JSplitPane();
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane.setResizeWeight(0.8);
		contentPane.add(splitPane, BorderLayout.CENTER);

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		
		splitPane.setLeftComponent(tabbedPane);
		
		tabbedPane.addTab(Lib.getLang("file.new"), new CodePane());
		tabbedPane.addTab(Lib.getLang("main.plus"), new JPanel());
		tabbedPane.setSelectedIndex(0);
		tabbedPane.addChangeListener(onTabSelect);
		
		JPanel panel_2 = new JPanel();
		splitPane.setRightComponent(panel_2);
		panel_2.setLayout(new BorderLayout(0, 0));

		JPanel panel_3 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_3.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		panel_2.add(panel_3, BorderLayout.NORTH);

		JButton btnRun = new JButton(Lib.getLang("main.run"));
		panel_3.add(btnRun);

		JButton btnStop = new JButton(Lib.getLang("main.stop"));
		panel_3.add(btnStop);

		JScrollPane scrollPane = new JScrollPane();
		panel_2.add(scrollPane, BorderLayout.CENTER);

		consoleArea = new ConsoleArea();
		scrollPane.setViewportView(consoleArea);
	}
	
	private ChangeListener onTabSelect = new ChangeListener() {
		@Override
		public void stateChanged(ChangeEvent e) {
			JTabbedPane pane = (JTabbedPane) e.getSource();
			int index = pane.getSelectedIndex();
			if(index == -1){
				return;
			}
			if(pane.getTitleAt(index).equals(Lib.getLang("main.plus"))){
				
				pane.removeTabAt(index);
				
				String name = JOptionPane.showInputDialog(thiss, Lib.getLang("question.newFileName"), Lib.getLang("question.newFileNameTitle"), JOptionPane.QUESTION_MESSAGE);
				
				CodePane codePane = new CodePane();
				tabbedPane.addTab(name, codePane);
				
				CodePane plusTab = new CodePane();
				tabbedPane.addTab(Lib.getLang("main.plus"), plusTab);
				
				tabbedPane.setSelectedComponent(codePane);
				
				return;
			}
		}
	};

	public ConsoleArea getConsoleArea() {
		return consoleArea;
	}
}
