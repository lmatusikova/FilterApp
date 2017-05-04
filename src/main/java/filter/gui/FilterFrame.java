package filter.gui;

import java.awt.EventQueue;
import java.awt.Image;
import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.JList;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JSeparator;
import javax.swing.JToolBar;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.Font;

public class FilterFrame {

	private JFrame frmFilterApplication;
	private JFileChooser fileChooser;
    private File chooserFile;
	private DefaultListModel<String> photoListModel;
	private BufferedImage bufferedImage;
	private String photoPath;
	private String photoDirPath;
	public List<BufferedImage> bufferedImageList;
	public List<String> name;
	private BufferedImage labelImage;
	private int index = -1;
    
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FilterFrame window = new FilterFrame();
					window.frmFilterApplication.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public FilterFrame() {
		initialize();
		init();
	}
	
	public final void init() {
        fileChooser = new JFileChooser();
        photoListModel = new DefaultListModel<String>();
        bufferedImageList = new ArrayList<BufferedImage>();
        name = new ArrayList<String>();
    }
	
	public String getPhotoDirPath() {
		return photoDirPath;
	}

	public void setPhotoDirPath(String photoDirPath) {
		this.photoDirPath = photoDirPath;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmFilterApplication = new JFrame();
		frmFilterApplication.setResizable(false);
		frmFilterApplication.getContentPane().setBackground(new Color(219, 229, 245));
		frmFilterApplication.setTitle("Filter tool");
		frmFilterApplication.setBounds(100, 100, 686, 478);
		frmFilterApplication.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmFilterApplication.getContentPane().setLayout(null);
		
		menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 682, 21);
		frmFilterApplication.getContentPane().add(menuBar);
		
		fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		
		openMenuItem = new JMenuItem("Open");
		openMenuItem.setIcon(new ImageIcon(FilterFrame.class.getResource("/icons/dir.png")));
		fileMenu.add(openMenuItem);
		
		exitMenuItem = new JMenuItem("Exit");
		fileMenu.add(exitMenuItem);
		
		filterMenu = new JMenu("Filter");
		menuBar.add(filterMenu);
		
		grayscaleMenuItem = new JMenuItem("Grayscale");
		grayscaleMenuItem.setIcon(new ImageIcon(FilterFrame.class.getResource("/icons/imageG.png")));
		filterMenu.add(grayscaleMenuItem);
		
		thresholdMenuItem = new JMenuItem("Threshold");
		thresholdMenuItem.setIcon(new ImageIcon(FilterFrame.class.getResource("/icons/imageT.png")));
		filterMenu.add(thresholdMenuItem);
		
		edgeDetectionMenuItem = new JMenuItem("Edge detection");
		edgeDetectionMenuItem.setIcon(new ImageIcon(FilterFrame.class.getResource("/icons/edge.png")));
		filterMenu.add(edgeDetectionMenuItem);
		
		helpMenu = new JMenu("Help");
		menuBar.add(helpMenu);
		
		helpMenuItem = new JMenuItem("Help");
		helpMenuItem.setIcon(new ImageIcon(FilterFrame.class.getResource("/icons/help.png")));
		helpMenu.add(helpMenuItem);
		
		aboutMenuItem = new JMenuItem("About");
		helpMenu.add(aboutMenuItem);
		
		scrollPane = new JScrollPane();
		scrollPane.setBorder(null);
		scrollPane.setBounds(10, 74, 182, 318);
		frmFilterApplication.getContentPane().add(scrollPane);
		
		photoList = new JList<String>();
		photoList.setBorder(null);
		scrollPane.setViewportView(photoList);
		
		imageLabel = new JLabel("");
		imageLabel.setBackground(Color.WHITE);
		imageLabel.setOpaque(true);
		imageLabel.setBorder(null);
		imageLabel.setBounds(202, 74, 470, 318);
		frmFilterApplication.getContentPane().add(imageLabel);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(0, 413, 682, 2);
		frmFilterApplication.getContentPane().add(separator);
		
		JLabel lblPhotos = new JLabel("Images");
		lblPhotos.setBounds(10, 55, 46, 14);
		frmFilterApplication.getContentPane().add(lblPhotos);
		
		JToolBar toolBar = new JToolBar();
		toolBar.setBackground(new Color(231,234,245));
		toolBar.setFloatable(false);
		toolBar.setBounds(0, 22, 682, 30);
		frmFilterApplication.getContentPane().add(toolBar);
		
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		toolBar.add(panel);
		
		openButton = new JButton("");
		openButton.setBounds(3, 4, 22, 22);
		openButton.setToolTipText("Load images from the directory");
		openButton.setContentAreaFilled(false);
		openButton.setBorderPainted(false);
		openButton.setIcon(new ImageIcon(FilterFrame.class.getResource("/icons/dir.png")));
		
		panel.setLayout(null);
		panel.add(openButton);
		
		grayButton = new JButton("");
		grayButton.setBounds(29, 4, 22, 22);
		grayButton.setToolTipText("Grayscale filter");
		grayButton.setContentAreaFilled(false);
		grayButton.setBorderPainted(false);
		grayButton.setIcon(new ImageIcon(FilterFrame.class.getResource("/icons/imageG.png")));
		panel.add(grayButton);
		
		thresholdButton = new JButton("");
		thresholdButton.setBounds(55, 4, 22, 22);
		thresholdButton.setToolTipText("Threshold filter");
		thresholdButton.setContentAreaFilled(false);
		thresholdButton.setBorderPainted(false);
		thresholdButton.setIcon(new ImageIcon(FilterFrame.class.getResource("/icons/imageT.png")));
		panel.add(thresholdButton);
		
		edgeButton = new JButton("");
		edgeButton.setBounds(81, 4, 22, 22);
		edgeButton.setToolTipText("Edge detection filter");
		edgeButton.setContentAreaFilled(false);
		edgeButton.setBorderPainted(false);
		edgeButton.setIcon(new ImageIcon(FilterFrame.class.getResource("/icons/edge.png")));
		panel.add(edgeButton);
		
		JLabel lblCopyrightcLucia = new JLabel("Copyright (c) Lucia Matusikova 2017");
		lblCopyrightcLucia.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		lblCopyrightcLucia.setBounds(477, 418, 195, 14);
		frmFilterApplication.getContentPane().add(lblCopyrightcLucia);
		
		/**
		 * Application actions.
		 */
		grayButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(photoListModel.size() != 0) {
					GrayScaleFrame gray = new GrayScaleFrame();
					gray.init(index, bufferedImageList, name);
					gray.frame.setVisible(true);
				} else {
					JOptionPane.showMessageDialog(null, "You didn't choose any photo directory.", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		thresholdButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(photoListModel.size() != 0) {
					ThresholdFrame threshold = new ThresholdFrame(index, bufferedImageList, name);
					threshold.frame.setVisible(true);
				} else {
					JOptionPane.showMessageDialog(null, "You didn't choose any photo directory.", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		edgeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(photoListModel.size() != 0) {
					EdgeFrame edge = new EdgeFrame(index, bufferedImageList, name);
					edge.frame.setVisible(true);
				} else {
					JOptionPane.showMessageDialog(null, "You didn't choose any photo directory.", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		openButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!photoListModel.isEmpty()) {
					photoListModel.clear();
					bufferedImageList.clear();
					name.clear();
					imageLabel.setIcon(new ImageIcon());
				}
				
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				fileChooser.showOpenDialog((Component)arg0.getSource());		
				loadPhotos();
			}
		});
		
		openMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!photoListModel.isEmpty()) {
					photoListModel.clear();
					bufferedImageList.clear();
					name.clear();
					imageLabel.setIcon(new ImageIcon());
				}
				
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				fileChooser.showOpenDialog((Component)e.getSource());		
				loadPhotos();		
			}
		});
		
		photoList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 1) {
					setPhotoInLabel();
				}
			}
		});
		
		exitMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		grayscaleMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(photoListModel.size() != 0) {
					GrayScaleFrame gray = new GrayScaleFrame();
					gray.init(index, bufferedImageList, name);
					gray.frame.setVisible(true);
				} else {
					JOptionPane.showMessageDialog(null, "You didn't choose any photo directory.", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		thresholdMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(photoListModel.size() != 0) {
					ThresholdFrame threshold = new ThresholdFrame(index, bufferedImageList, name);
					threshold.frame.setVisible(true);
				} else {
					JOptionPane.showMessageDialog(null, "You didn't choose any photo directory.", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}	
		});
		
		edgeDetectionMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(photoListModel.size() != 0) {
					EdgeFrame edge = new EdgeFrame(index, bufferedImageList, name);
					edge.frame.setVisible(true);
				} else {
					JOptionPane.showMessageDialog(null, "You didn't choose any photo directory.", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		helpMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//urobit
			}
		});
		
		aboutMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//urobit
			}
		});
		
		}
	
	private void loadPhotos() {
		int index = 0;
		chooserFile = fileChooser.getSelectedFile();
		
		if(chooserFile != null) {
			File directory = new File(chooserFile.getPath());
			setPhotoDirPath(chooserFile.getPath());
			
			for (String file : directory.list()) {
				if (file.contains(".jpg") || file.contains(".JPG") || file.contains(".PNG") || file.contains(".png")) {
					photoListModel.add(index, file);
					index++;
					
					try {
						bufferedImage = ImageIO.read(new File(directory+File.separator+file));
						bufferedImageList.add(bufferedImage);
						name.add(file);
					} catch (IOException e) {
						System.out.println("Image cannot be load.");
					}	
				}
			}
			photoList.setModel(photoListModel);
		}
	}
	
	private void setPhotoInLabel() {
		index = photoList.getSelectedIndex();

		ListModel<String> model = photoList.getModel();
		String photoName = model.getElementAt(index);

		photoPath = getPhotoDirPath() + File.separator + photoName;
		File imageFile = new File(photoPath);

		try {
			labelImage = ImageIO.read(imageFile);
		} catch (IOException e) {
			System.out.println("Image cannot be load.");
		}

		ImageIcon image = new ImageIcon(labelImage);
		Image img = image.getImage().getScaledInstance(imageLabel.getWidth(), imageLabel.getHeight(),
				Image.SCALE_SMOOTH);
		imageLabel.setIcon(new ImageIcon(img));
	}
	
	// Variables declaration - do not modify
	private JMenuBar menuBar;
	private JMenu fileMenu;
	private JMenuItem openMenuItem;
	private JMenuItem exitMenuItem;
	private JMenu filterMenu;
	private JMenuItem grayscaleMenuItem;
	private JMenuItem thresholdMenuItem;
	private JMenuItem edgeDetectionMenuItem;
	private JMenu helpMenu;
	private JMenuItem helpMenuItem;
	private JMenuItem aboutMenuItem;
	private JScrollPane scrollPane;
	private JList<String> photoList;
	private JLabel imageLabel;
	private JButton openButton;
	private JButton grayButton;
	private JButton thresholdButton;
	private JButton edgeButton;
}

