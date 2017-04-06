package sk.ics.upjs.matusikova.gui;

import java.awt.EventQueue;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JSeparator;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import sk.ics.upjs.matusikova.filter.Threshold;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JSlider;
import java.awt.Font;

public class ThresholdFrame {

	public JFrame frame;
	private Threshold threshold;
	
	//inputs
	private static List<BufferedImage> bufferedImageList;
	private static BufferedImage photo;
	private static List<String> name;
	
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
					ThresholdFrame window = new ThresholdFrame(photo, bufferedImageList, name);
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
	public ThresholdFrame(BufferedImage photo, List<BufferedImage> bufferedImageList, List<String> name) {
		ThresholdFrame.photo = photo;
		ThresholdFrame.bufferedImageList = bufferedImageList;
		ThresholdFrame.name = name;
		filteredImageList = new ArrayList<BufferedImage>();
		initialize();
		
		if(photo != null) {
			displayPhoto(photo);
		}	
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 435, 490);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		separator = new JSeparator();
		separator.setBounds(0, 37, 466, 2);
		frame.getContentPane().add(separator);
		
		buttonGroup = new ButtonGroup();
		
		imageLabel = new JLabel("");
		imageLabel.setBackground(Color.WHITE);
		imageLabel.setOpaque(true);
		imageLabel.setBounds(10, 50, 400, 249);
		frame.getContentPane().add(imageLabel);
		
		minSpinner = new JSpinner();
		minSpinner.setModel(new SpinnerNumberModel(new Integer(0), null, null, new Integer(1)));
		minSpinner.setBounds(73, 335, 40, 18);
		frame.getContentPane().add(minSpinner);
		
		maxSpinner = new JSpinner();
		maxSpinner.setBounds(73, 363, 40, 18);
		frame.getContentPane().add(maxSpinner);
		
		algorithmLabel = new JLabel("Algorithm:");
		algorithmLabel.setBounds(10, 310, 53, 14);
		frame.getContentPane().add(algorithmLabel);
		
		algorithmComboBox = new JComboBox();
		algorithmComboBox.setModel(new DefaultComboBoxModel(new String[] {"Compute otsu", "Compute entropy", "Local Gaussian", "Local Sauvola"}));
		algorithmComboBox.setBounds(73, 307, 115, 18);
		frame.getContentPane().add(algorithmComboBox);
		
		applyRadioButton = new JRadioButton("Apply on current photo");
		applyRadioButton.setBounds(165, 387, 157, 23);
		frame.getContentPane().add(applyRadioButton);
		buttonGroup.add(applyRadioButton);
		applyRadioButton.setSelected(true);
		
		applyAllRadioButton = new JRadioButton("Apply on all photos");
		applyAllRadioButton.setBounds(6, 387, 141, 23);
		frame.getContentPane().add(applyAllRadioButton);
		buttonGroup.add(applyAllRadioButton);
		
		saveAsButton = new JButton("Save as...");
		saveAsButton.setBounds(10, 417, 89, 23);
		frame.getContentPane().add(saveAsButton);
		
		applyButton = new JButton("Apply");
		applyButton.setBounds(109, 417, 89, 23);
		frame.getContentPane().add(applyButton);
		
		cancelButton = new JButton("Cancel");
		cancelButton.setBounds(222, 417, 89, 23);
		frame.getContentPane().add(cancelButton);
		
		okButton = new JButton("Ok");
		okButton.setBounds(321, 417, 89, 23);
		frame.getContentPane().add(okButton);
		
		lblThresholding = new JLabel("Thresholding");
		lblThresholding.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblThresholding.setBounds(10, 11, 103, 20);
		frame.getContentPane().add(lblThresholding);
		
		lblMin = new JLabel("Min:");
		lblMin.setBounds(10, 338, 46, 14);
		frame.getContentPane().add(lblMin);
		
		lblMax = new JLabel("Max:");
		lblMax.setBounds(10, 366, 46, 14);
		frame.getContentPane().add(lblMax);
		
		lblRadius = new JLabel("Radius:");
		lblRadius.setBounds(195, 338, 46, 14);
		frame.getContentPane().add(lblRadius);
		
		lblScale = new JLabel("Scale:");
		lblScale.setBounds(195, 366, 46, 14);
		frame.getContentPane().add(lblScale);
		
		JSlider slider = new JSlider();
		slider.setBounds(258, 335, 100, 18);
		frame.getContentPane().add(slider);
		
		JSlider slider_1 = new JSlider();
		slider_1.setBounds(258, 363, 100, 18);
		frame.getContentPane().add(slider_1);
		
		JSpinner spinner = new JSpinner();
		spinner.setBounds(368, 335, 40, 18);
		frame.getContentPane().add(spinner);
		
		JSpinner spinner_1 = new JSpinner();
		spinner_1.setBounds(368, 363, 40, 18);
		frame.getContentPane().add(spinner_1);
		
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (applyRadioButton.isSelected()) {
					returnImage();
			    }
				else if (applyAllRadioButton.isSelected()) {
			    	returnAllImages();
			    }
			}
		});
		
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		
		applyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (applyRadioButton.isSelected()) {
					filterImage();
		        } 
				else if (applyAllRadioButton.isSelected()) {
		           filterAllImages();
		        }	
			}
		});
		
		saveAsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				
			}
		});
	}
	
	public int getMaxSpinner() {
        return (Integer) maxSpinner.getValue();
    }

    public void setMaxSpinner(JSpinner maxSpinner) {
       maxSpinner.setModel(new SpinnerNumberModel(255, 0, 255, 1));
    }
    
    public int getMinSpinner() {
        return (Integer) minSpinner.getValue();
    }

    public void setMinSpinner(JSpinner minSpinner) {
        minSpinner.setModel(new SpinnerNumberModel(0, -255, 0, 1));
    }
	
    private void displayPhoto(BufferedImage filteredPhoto) {
    	ImageIcon image = new ImageIcon(filteredPhoto);
		Image img = image.getImage().getScaledInstance(imageLabel.getWidth(), imageLabel.getHeight(),
				Image.SCALE_SMOOTH);
		imageLabel.setIcon(new ImageIcon(img));
    }
    
	private void filterImage() {  
		filteredPhoto = photo;
		
        if("Compute otsu".equals(algorithmComboBox.getSelectedItem().toString())) {
            filteredPhoto = threshold.computeOtsu(filteredPhoto, getMinSpinner(), getMaxSpinner());
        }
        
        else if("Compute entropy".equals(algorithmComboBox.getSelectedItem().toString())) {
           filteredPhoto = threshold.computeEntropy(filteredPhoto, getMinSpinner(), getMaxSpinner());
        }
        
        else if("Local Gaussian".equals(algorithmComboBox.getSelectedItem().toString())) {
           filteredPhoto = threshold.localGaussian(filteredPhoto, getMinSpinner(), getMaxSpinner());
        }
        
        else if("Local Sauvola".equals(algorithmComboBox.getSelectedItem().toString())) {
        	filteredPhoto = threshold.localSauvola(filteredPhoto, getMinSpinner(), getMaxSpinner());
        }
        
        displayPhoto(filteredPhoto);
   }
    
    private void filterAllImages() {
         if("Compute otsu".equals(algorithmComboBox.getSelectedItem().toString())) {
            for (BufferedImage photo : bufferedImageList) {
            	filteredPhoto = photo;
                filteredPhoto = threshold.computeOtsu(filteredPhoto, getMinSpinner(), getMaxSpinner());
                filteredImageList.add(filteredPhoto);
            }
         }
        
         else if("Compute entropy".equals(algorithmComboBox.getSelectedItem().toString())) {
            for (BufferedImage photo : bufferedImageList) {
            	filteredPhoto = photo;
            	filteredPhoto = threshold.computeEntropy(filteredPhoto, getMinSpinner(), getMaxSpinner());
            	filteredImageList.add(filteredPhoto);
            }
         }
         
         else if("Local Gaussian".equals(algorithmComboBox.getSelectedItem().toString())) {
            for (BufferedImage photo : bufferedImageList) {
            	filteredPhoto = photo;
            	filteredPhoto = threshold.localGaussian(filteredPhoto, getMinSpinner(), getMaxSpinner());
            	filteredImageList.add(filteredPhoto);
            }
         }
         
         else if("Local Sauvola".equals(algorithmComboBox.getSelectedItem().toString())) {
            for (BufferedImage photo : bufferedImageList) {
            	filteredPhoto = photo;
            	filteredPhoto = threshold.localSauvola(filteredPhoto, getMinSpinner(), getMaxSpinner());
            	filteredImageList.add(filteredPhoto);
            }
        }
    }
    
    private BufferedImage returnImage() {
        return filteredPhoto;
    }
    
    private List<BufferedImage> returnAllImages() {
        return filteredImageList;
    }
    
    // Variables declaration - do not modify 
    private ButtonGroup buttonGroup;
	private JSeparator separator;
	private JLabel imageLabel;
	private JSpinner minSpinner;
	private JSpinner maxSpinner;
	private JLabel algorithmLabel;
	private JComboBox algorithmComboBox;
	private JRadioButton applyRadioButton;
	private JRadioButton applyAllRadioButton;
	private JButton saveAsButton;
	private JButton applyButton;
	private JButton cancelButton;
	private JButton okButton;
	private JLabel lblThresholding;
	private JLabel lblMin;
	private JLabel lblMax;
	private JLabel lblRadius;
	private JLabel lblScale;
}

