package main;

import javax.swing.SwingUtilities;

/**
 * Main class
 * @author Blake
 *
 */
public class ScreenCapture {
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new ScreenFrame()); /* Lambda Java 8 version shortcutting an anonymous class, can leave out block statement due to single line expression */
	}
}
