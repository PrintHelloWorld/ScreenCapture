package main;

import java.awt.Color;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;

import javax.swing.JFrame;

/**
 * A frame that holds the capture panel
 * @author Blake
 *
 */
@SuppressWarnings("serial")
public class ScreenFrame extends JFrame {

	public ScreenFrame() {
		initLayout();
		initComponents();
	}

	private void initComponents() {
		add(new CapturePane());
	}

	private void initLayout() {
		setUndecorated(true);
		setBackground(new Color(0, 0, 0, 0));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Rectangle bounds = getVirtualBounds();
		setLocation(bounds.getLocation());
		setSize(bounds.getSize());
		setAlwaysOnTop(true);
		setVisible(true);
	}

	public static Rectangle getVirtualBounds() {
		Rectangle bounds = new Rectangle(0, 0, 0, 0);
		GraphicsEnvironment ge = GraphicsEnvironment
				.getLocalGraphicsEnvironment();
		GraphicsDevice lstGDs[] = ge.getScreenDevices();
		for (GraphicsDevice gd : lstGDs) {
			bounds.add(gd.getDefaultConfiguration().getBounds());
		}
		return bounds;
	}
}
