package sk.ics.upjs.matusikova.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JSeparator;

import sk.ics.upjs.matusikova.filter.Filter;
import sk.ics.upjs.matusikova.other.Save;

import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Image;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;

import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;

public class EdgeFrame {

	public JFrame frame;
	private JFileChooser fileChooser;
	private File chooserDirectory;
	private String pathToPhotos;
	private Filter filter;
	
	//inputs
	private static List<BufferedImage> edgeBufferedImageList;
	private static List<String> edgeNameList;
	private static int edgeIndex;
		
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
					EdgeFrame window = new EdgeFrame(edgeIndex, edgeBufferedImageList, edgeNameList);
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
	public EdgeFrame(int index, List<BufferedImage> bufferedImageList, List<String> name) {
		EdgeFrame.edgeBufferedImageList = bufferedImageList;
		EdgeFrame.edgeNameList = name;
		EdgeFrame.edgeIndex = index;
		fileChooser = new JFileChooser();
		filteredImageList = new ArrayList<BufferedImage>();
		initialize();
	   	
		if(index != -1) {
			displayPhoto(EdgeFrame.edgeBufferedImageList.get(index));
		}	
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(219, 229, 245));
		frame.setBounds(100, 100, 435, 440);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		separator = new JSeparator();
		separator.setBounds(0, 37, 419, 2);
		frame.getContentPane().add(separator);
		
		buttonGroup = new ButtonGroup();
		
		edgeDetectionLabel = new JLabel("Edge detection");
		edgeDetectionLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		edgeDetectionLabel.setBounds(10, 11, 119, 20);
		frame.getContentPane().add(edgeDetectionLabel);
		
		imageLabel = new JLabel("");
		imageLabel.setOpaque(true);
		imageLabel.setBackground(Color.WHITE);
		imageLabel.setBounds(10, 50, 400, 249);
		frame.getContentPane().add(imageLabel);
		
		operatorComboBox = new JComboBox<String>();
		operatorComboBox.setModel(new DefaultComboBoxModel(new String[] {"Prewitt operator", "Sobel operator", "Roberts operator", "Laplace operator"}));
		operatorComboBox.setBounds(73, 307, 129, 18);
		frame.getContentPane().add(operatorComboBox);
		
		operatorLabel = new JLabel("Operator:");
		operatorLabel.setBounds(10, 310, 53, 14);
		frame.getContentPane().add(operatorLabel);
		
		applyRadioButton = new JRadioButton("Apply on current image");
		applyRadioButton.setOpaque(false);
		applyRadioButton.setBounds(10, 336, 157, 23);
		frame.getContentPane().add(applyRadioButton);
		buttonGroup.add(applyRadioButton);
		applyRadioButton.setSelected(true);
		
		applyAllRadioButton = new JRadioButton("Apply on all images");
		applyAllRadioButton.setOpaque(false);
		applyAllRadioButton.setBounds(195, 336, 157, 23);
		frame.getContentPane().add(applyAllRadioButton);
		buttonGroup.add(applyAllRadioButton);
		
		okButton = new JButton("Ok");
		okButton.setBounds(321, 371, 89, 23);
		frame.getContentPane().add(okButton);
		
		cancelButton = new JButton("Cancel");
		cancelButton.setBounds(219, 371, 89, 23);
		frame.getContentPane().add(cancelButton);
		
		applyButton = new JButton("Apply");
		applyButton.setBounds(113, 371, 89, 23);
		frame.getContentPane().add(applyButton);
		
		saveAsButton = new JButton("Save as...");
		saveAsButton.setBounds(10, 371, 89, 23);
		frame.getContentPane().add(saveAsButton);
		
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
		
		applyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				filter = new Filter(edgeIndex, edgeBufferedImageList, operatorComboBox.getSelectedItem().toString());
				if (applyRadioButton.isSelected()) {
					filteredPhoto = filter.edgeFilterImage();
					displayPhoto(filteredPhoto);
		        } 
				else if (applyAllRadioButton.isSelected()) {
					 filteredImageList = filter.edgeFilterImages();
		        }	
			}
		});
		
		saveAsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				fileChooser.showOpenDialog((Component) e.getSource());
				chooserDirectory = fileChooser.getSelectedFile();
				pathToPhotos = chooserDirectory.getPath();

				Save save = new Save(edgeIndex, filteredPhoto, filteredImageList, edgeNameList, pathToPhotos);
				if(chooserDirectory != null) {
					if(applyAllRadioButton.isSelected()) {
						save.saveImages();
					} else {
						save.saveImage();
					}
				}
			}
		});
		
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		
	}
	
	/**
	 * Other methods.
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
	private JLabel edgeDetectionLabel;
	private JLabel imageLabel;
	private JComboBox operatorComboBox;
	private JLabel operatorLabel;
	private JRadioButton applyRadioButton;
	private JRadioButton applyAllRadioButton;
	private JButton okButton;
	private JButton cancelButton;
	private JButton applyButton;
	private JButton saveAsButton;
	// End of variables declaration 
}
