package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * A frame that covers the entire screen, showing a bounding rectangle that is resizeable to capture screenshot
 * @author Blake
 *
 */
@SuppressWarnings("serial")
public class CapturePane extends JPanel {

	private Rectangle captureBounds;
	private Point clickPoint;
	private PreviewFrame preview;

	public CapturePane() {
		setOpaque(false);
		MouseAdapter mouseHandler = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (SwingUtilities.isLeftMouseButton(e)
						&& e.getClickCount() == 2) {
					System.exit(0);
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {
				clickPoint = e.getPoint();
				captureBounds = null;
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				clickPoint = null;
				takeScreenShot();
			}

			@Override
			public void mouseDragged(MouseEvent e) {
				Point dragPoint = e.getPoint();
				int x = Math.min(clickPoint.x, dragPoint.x);
				int y = Math.min(clickPoint.y, dragPoint.y);
				int width = Math.max(clickPoint.x - dragPoint.x, dragPoint.x
						- clickPoint.x);
				int height = Math.max(clickPoint.y - dragPoint.y, dragPoint.y
						- clickPoint.y);
				captureBounds = new Rectangle(x, y, width, height);
				repaint();
			}
		};
		addMouseListener(mouseHandler);
		addMouseMotionListener(mouseHandler);
	}

	/**
	 * Takes a screenshot using awt.robot to use native screenshot command
	 */
	private void takeScreenShot() {
		try {
			Robot r = new Robot();
			BufferedImage img = r.createScreenCapture(captureBounds);
			displayPreview(img);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * hides this frame and brings up the preview frame to display picture
	 * @param img
	 */
	private void displayPreview(BufferedImage img){
		if(preview == null){
			preview = new PreviewFrame(this);
		}
		setVisible(false);
		preview.setImage(new ImageIcon(img));
		preview.setVisible(true);
	}

	/**
	 * Overrides frame painting, draws the rectangle cut out frame to show area of screenshot
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setColor(new Color(255, 255, 255, 128));

		Area fill = new Area(new Rectangle(new Point(0, 0), getSize()));
		if (captureBounds != null) {
			fill.subtract(new Area(captureBounds));
		}
		g2d.fill(fill);
		if (captureBounds != null) {
			g2d.setColor(Color.RED);
			g2d.draw(captureBounds);
		}
		g2d.dispose();
	}
}
