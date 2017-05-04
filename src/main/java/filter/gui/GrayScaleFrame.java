package filter.gui;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JSeparator;

import filter.filter.Filter;
import filter.other.Save;

import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;

public class GrayScaleFrame {

	public JFrame frame;
	private JFileChooser fileChooser;
	private File chooserDirectory;
	private String pathToPhotos;
	private Filter filter;
	
	//inputs
	private static List<BufferedImage> grayBufferedImageList;
	private static List<String> grayNameList;
	private static int index;
		
	//outputs
	private BufferedImage filteredPhoto;
	private List<BufferedImage> filteredImageList;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GrayScaleFrame window = new GrayScaleFrame();
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
	public GrayScaleFrame() {
		initialize();
		
	}
	
	public void init(int index, List<BufferedImage> bufferedImageList, List<String> name) {
		GrayScaleFrame.grayBufferedImageList = bufferedImageList;
		GrayScaleFrame.grayNameList = name;
		GrayScaleFrame.index = index;
		fileChooser = new JFileChooser();
		filteredImageList = new ArrayList<BufferedImage>();
		initialize();
		if(index != -1) {
			displayPhoto(bufferedImageList.get(index));
		}
	}
	

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(GrayScaleFrame.class.getResource("/icons/imageG.png")));
		frame.getContentPane().setBackground(new Color(219, 229, 245));
		frame.setBounds(100, 100, 425, 409);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		buttonGroup = new ButtonGroup();
		
		separator = new JSeparator();
		separator.setBounds(0, 37, 420, 2);
		frame.getContentPane().add(separator);
		
		grayscaleLabel = new JLabel("Grayscale");
		grayscaleLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		grayscaleLabel.setBounds(10, 11, 92, 20);
		frame.getContentPane().add(grayscaleLabel);
		
		imageLabel = new JLabel("");
		imageLabel.setOpaque(true);
		imageLabel.setBackground(Color.WHITE);
		imageLabel.setBounds(10, 50, 400, 249);
		frame.getContentPane().add(imageLabel);
		
		applyRadioButton = new JRadioButton("Apply on current image");
		applyRadioButton.setOpaque(false);
		applyRadioButton.setBounds(7, 306, 145, 23);
		frame.getContentPane().add(applyRadioButton);
		buttonGroup.add(applyRadioButton);
		applyRadioButton.setSelected(true);
		
		applyAllRadioButton = new JRadioButton("Apply on all images");
		applyAllRadioButton.setOpaque(false);
		applyAllRadioButton.setBounds(173, 306, 145, 23);
		frame.getContentPane().add(applyAllRadioButton);
		buttonGroup.add(applyAllRadioButton);
		
		saveAsButton = new JButton("Save as...");
		saveAsButton.setBounds(10, 336, 89, 23);
		frame.getContentPane().add(saveAsButton);
		
		applyButton = new JButton("Apply");
		applyButton.setBounds(113, 336, 89, 23);
		frame.getContentPane().add(applyButton);
		
		cancelButton = new JButton("Cancel");
		cancelButton.setBounds(219, 336, 89, 23);
		frame.getContentPane().add(cancelButton);
		
		okButton = new JButton("Ok");
		okButton.setBounds(321, 336, 89, 23);
		frame.getContentPane().add(okButton);
		
		/**
		 * Component actions.
		 */
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (applyRadioButton.isSelected()) {
						frame.dispose();
				    }
					else if (applyAllRadioButton.isSelected()) {
				    	frame.dispose();
				    }
			}
		});
		
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
						frame.dispose();
			}
		});
		
		applyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (applyRadioButton.isSelected()) {
					filter = new Filter(index, grayBufferedImageList);
					filteredPhoto = filter.grayscaleFilterImage();
					System.out.println(filteredPhoto.toString());
					displayPhoto(filteredPhoto);
		        } 
				else if (applyAllRadioButton.isSelected()) {
					filteredImageList = new Filter(index, grayBufferedImageList).grayscaleFilterImages();
		        }
			}
		});
		
		saveAsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				fileChooser.showOpenDialog((Component) arg0.getSource());
				chooserDirectory = fileChooser.getSelectedFile();
				pathToPhotos = chooserDirectory.getPath();

				Save save = new Save(index, filteredPhoto, filteredImageList, grayNameList, pathToPhotos);
				
				if(chooserDirectory != null) {
					if(applyAllRadioButton.isSelected()) {
						save.saveImages();
					} else {
						save.saveImage();
					}
				}
			}
		});
		
	}
	
	private void displayPhoto(BufferedImage filteredPhoto) {
    	ImageIcon image = new ImageIcon(filteredPhoto);
		Image img = image.getImage().getScaledInstance(imageLabel.getWidth(), imageLabel.getHeight(),
				Image.SCALE_SMOOTH);
		imageLabel.setIcon(new ImageIcon(img));
    }
	
	// Variables declaration - do not modify 
	private ButtonGroup buttonGroup;
	private JSeparator separator;
	private JLabel grayscaleLabel;
	private JRadioButton applyRadioButton;
	private JLabel imageLabel;
	private JButton saveAsButton;
	private JRadioButton applyAllRadioButton;
	private JButton applyButton;
	private JButton cancelButton;
	private JButton okButton;
	// End of variables declaration 
}
