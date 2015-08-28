package main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * A frame to show a preview of the last picture taken and give options to save or retake picture
 * 
 * @author Blake
 *
 */
@SuppressWarnings("serial")
public class PreviewFrame extends JFrame {

	private static final String TITLE = "Preview";
	
	private CapturePane pane;

	private ImageIcon image;

	private JLabel imageDisplay;
	private JButton saveButton;
	private JButton redoButton;

	public PreviewFrame(CapturePane pane) {
		this.pane = pane;
		initComponents();
		initLayout();
	}

	/**
	 * intialize frame and sets button listeners
	 */
	private void initComponents() {
		setLayout(new BorderLayout());
		imageDisplay = new JLabel();
		imageDisplay.setHorizontalAlignment(JLabel.CENTER);
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		redoButton = new JButton("Redo");
		saveButton = new JButton("Save");
		buttonPanel.add(redoButton);
		buttonPanel.add(saveButton);
		add(imageDisplay, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					saveImage();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		redoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				redoPicture();
			}
		});
	}

	private void initLayout() {
		setTitle(TITLE);
		setMinimumSize(new Dimension(500, 500));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(pane);
		setVisible(true);
	}
	
	/**
	 * reopens captureFrame and closes preview frame
	 */
	private void redoPicture(){
		setVisible(false);
		pane.setVisible(true);
	}

	/**
	 * Converts the image to a buffered image to save to a file
	 * @throws IOException
	 */
	public void saveImage() throws IOException {
		Image img = image.getImage();
		BufferedImage buffered = (BufferedImage) img;
		File outputFile = new File("test" + ".png");
		ImageIO.write(buffered, "png", outputFile);
	}

	/**
	 * sets image to preview and packs the components to fit
	 * @param image
	 */
	public void setImage(ImageIcon image) {
		this.image = image;
		imageDisplay.setIcon(image);
		pack();
	}
}
