package filter.gui;

import java.awt.EventQueue;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JSeparator;

import filter.filter.Filter;
import filter.other.Save;

import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Component;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Toolkit;

public class ThresholdFrame {

	public JFrame frame;
	private JFileChooser fileChooser;
	private File chooserDirectory;
	private String pathToPhotos;
	private Filter filter;

	private static List<BufferedImage> bufferedImageList;
	private static List<String> name;
	private static int index;

	private BufferedImage filteredPhoto;
	private List<BufferedImage> filteredImageList;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ThresholdFrame window = new ThresholdFrame(index, bufferedImageList, name);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ThresholdFrame(int index, List<BufferedImage> bufferedImageList, List<String> name) {
		ThresholdFrame.bufferedImageList = bufferedImageList;
		ThresholdFrame.name = name;
		ThresholdFrame.index = index;
		fileChooser = new JFileChooser();
		filteredImageList = new ArrayList<BufferedImage>();
		initialize();
		if (index != -1) {
			displayPhoto(ThresholdFrame.bufferedImageList.get(index));
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(ThresholdFrame.class.getResource("/icons/imageT.png")));
		frame.getContentPane().setBackground(new Color(219, 229, 245));
		frame.setBounds(100, 100, 424, 474);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		separator = new JSeparator();
		separator.setBounds(0, 37, 419, 2);
		frame.getContentPane().add(separator);

		buttonGroup = new ButtonGroup();

		imageLabel = new JLabel("");
		imageLabel.setBackground(Color.WHITE);
		imageLabel.setOpaque(true);
		imageLabel.setBounds(10, 50, 400, 249);
		frame.getContentPane().add(imageLabel);

		algorithmLabel = new JLabel("Algorithm:");
		algorithmLabel.setBounds(10, 310, 53, 14);
		frame.getContentPane().add(algorithmLabel);

		algorithmComboBox = new JComboBox<String>();
		algorithmComboBox.setToolTipText("Choose algorithm");
		algorithmComboBox.setModel(new DefaultComboBoxModel<String>(
				new String[] { "Compute otsu", "Compute entropy", "Local Gaussian", "Local Sauvola" }));
		algorithmComboBox.setBounds(73, 307, 115, 18);
		frame.getContentPane().add(algorithmComboBox);

		applyRadioButton = new JRadioButton("Apply on current image");
		applyRadioButton.setOpaque(false);
		applyRadioButton.setBounds(6, 336, 157, 23);
		frame.getContentPane().add(applyRadioButton);
		buttonGroup.add(applyRadioButton);
		applyRadioButton.setSelected(true);

		applyAllRadioButton = new JRadioButton("Apply on all images");
		applyAllRadioButton.setOpaque(false);
		applyAllRadioButton.setBounds(6, 362, 157, 23);
		frame.getContentPane().add(applyAllRadioButton);
		buttonGroup.add(applyAllRadioButton);

		saveAsButton = new JButton("Save as...");
		saveAsButton.setBounds(10, 400, 89, 23);
		frame.getContentPane().add(saveAsButton);

		applyButton = new JButton("Apply");
		applyButton.setBounds(113, 400, 89, 23);
		frame.getContentPane().add(applyButton);

		cancelButton = new JButton("Cancel");
		cancelButton.setBounds(219, 400, 89, 23);
		frame.getContentPane().add(cancelButton);

		okButton = new JButton("Ok");
		okButton.setBounds(321, 400, 89, 23);
		frame.getContentPane().add(okButton);

		lblThresholding = new JLabel("Thresholding");
		lblThresholding.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblThresholding.setBounds(10, 11, 103, 20);
		frame.getContentPane().add(lblThresholding);

		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (applyRadioButton.isSelected()) {
					frame.dispose();
				} else if (applyAllRadioButton.isSelected()) {
					frame.dispose();
				}
			}
		});

		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
			}
		});

		applyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				filter = new Filter(index, bufferedImageList, algorithmComboBox.getSelectedItem().toString(), 0);
				if (applyRadioButton.isSelected()) {
					filteredPhoto = filter.thresholdFilterImage();
					displayPhoto(filteredPhoto);
				} else if (applyAllRadioButton.isSelected()) {
					filteredImageList = filter.thresholdFilterImages();
				}
			}
		});

		saveAsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				fileChooser.showOpenDialog((Component) arg0.getSource());
				chooserDirectory = fileChooser.getSelectedFile();
				pathToPhotos = chooserDirectory.getPath();

				Save save = new Save(index, filteredPhoto, filteredImageList, name, pathToPhotos);
				if (chooserDirectory != null) {
					if (applyAllRadioButton.isSelected()) {
						save.saveImages();
					} else {
						save.saveImage();
					}
				}
			}
		});
	}

	/**
	 * Method for displaying selected image.
	 * 
	 * @param filteredPhoto
	 *            selected image
	 */
	private void displayPhoto(BufferedImage filteredPhoto) {
		ImageIcon image = new ImageIcon(filteredPhoto);
		Image img = image.getImage().getScaledInstance(imageLabel.getWidth(), imageLabel.getHeight(),
				Image.SCALE_SMOOTH);
		imageLabel.setIcon(new ImageIcon(img));
	}

	// Variables declaration - do not modify
	private ButtonGroup buttonGroup;
	private JSeparator separator;
	private JLabel imageLabel;
	private JLabel algorithmLabel;
	private JComboBox<String> algorithmComboBox;
	private JRadioButton applyRadioButton;
	private JRadioButton applyAllRadioButton;
	private JButton saveAsButton;
	private JButton applyButton;
	private JButton cancelButton;
	private JButton okButton;
	private JLabel lblThresholding;
	// End of variables declaration
}
