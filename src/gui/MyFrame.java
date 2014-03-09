package gui;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class MyFrame extends JFrame {
	public MyFrame() {
		super();
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
            	Tester2.close();
                System.exit(0);
            }
        });
    }
}
