package sk.ics.upjs.matusikova.gui;

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

public class FilterFrame {

	private JFrame frame;
	private JFileChooser fileChooser;
    private File chooserFile;
	private DefaultListModel<String> photoListModel;
	private BufferedImage bufferedImage;
	private String photoPath;
	private String photoDirPath;
	private List<BufferedImage> bufferedImageList;
	private List<String> name;
    
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FilterFrame window = new FilterFrame();
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
		frame = new JFrame();
		frame.setBounds(100, 100, 725, 498);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 709, 21);
		frame.getContentPane().add(menuBar);
		
		fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		
		openMenuItem = new JMenuItem("Open");
		fileMenu.add(openMenuItem);
		
		saveMenuItem = new JMenuItem("Save");
		fileMenu.add(saveMenuItem);
		
		saveAsMenuItem = new JMenuItem("Save as...");
		fileMenu.add(saveAsMenuItem);
		
		exitMenuItem = new JMenuItem("Exit");
		fileMenu.add(exitMenuItem);
		
		filterMenu = new JMenu("Filter");
		menuBar.add(filterMenu);
		
		grayscaleMenuItem = new JMenuItem("GrayScale");
		filterMenu.add(grayscaleMenuItem);
		
		thresholdMenuItem = new JMenuItem("Threshold");
		filterMenu.add(thresholdMenuItem);
		
		edgeDetectionMenuItem = new JMenuItem("Edge detection");
		filterMenu.add(edgeDetectionMenuItem);
		
		helpMenu = new JMenu("Help");
		menuBar.add(helpMenu);
		
		helpMenuItem = new JMenuItem("Help");
		helpMenu.add(helpMenuItem);
		
		aboutMenuItem = new JMenuItem("About");
		helpMenu.add(aboutMenuItem);
		
		scrollPane = new JScrollPane();
		scrollPane.setBorder(null);
		scrollPane.setBounds(10, 51, 182, 359);
		frame.getContentPane().add(scrollPane);
		
		photoList = new JList<String>();
		photoList.setBorder(null);
		scrollPane.setViewportView(photoList);
		
		imageLabel = new JLabel("");
		imageLabel.setBackground(Color.WHITE);
		imageLabel.setOpaque(true);
		imageLabel.setBorder(null);
		imageLabel.setBounds(218, 51, 481, 359);
		frame.getContentPane().add(imageLabel);
		
		/**
		 * Application actions.
		 */
		
		openMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
		
		saveMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//urobit
			}
		});
		
		saveAsMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				fileChooser.showOpenDialog((Component)e.getSource());
			    //dokoncit  
			}
		});
		
		exitMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		grayscaleMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//urobit
			}
		});
		
		thresholdMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ThresholdFrame threshold = new ThresholdFrame(bufferedImage, bufferedImageList, name);		
				threshold.frame.setVisible(true);
			}
		});
		
		edgeDetectionMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//urobit
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
				if (file.contains(".jpg") || file.contains(".JPG") || file.contains(".PNG")
						|| file.contains(".jpeg") || file.contains(".png")) {
					photoListModel.add(index, file);
					index++;
					
					try {
						bufferedImage = ImageIO.read(new File(file));
						bufferedImageList.add(bufferedImage);
					} catch (IOException e) {
						System.out.println("Image cannot be load.");
					}	
				}
			}
			photoList.setModel(photoListModel);
		}
	}
	
	private void setPhotoInLabel() {
		int index = photoList.getSelectedIndex();

		ListModel<String> model = photoList.getModel();
		String photoName = model.getElementAt(index);

		photoPath = getPhotoDirPath() + File.separator + photoName;
		File imageFile = new File(photoPath);

		try {
			bufferedImage = ImageIO.read(imageFile);
		} catch (IOException e) {
			System.out.println("Image cannot be load.");
		}

		ImageIcon image = new ImageIcon(bufferedImage);
		Image img = image.getImage().getScaledInstance(imageLabel.getWidth(), imageLabel.getHeight(),
				Image.SCALE_SMOOTH);
		imageLabel.setIcon(new ImageIcon(img));
	}
	
	// Variables declaration - do not modify
	private JMenuBar menuBar;
	private JMenu fileMenu;
	private JMenuItem openMenuItem;
	private JMenuItem saveMenuItem;
	private JMenuItem saveAsMenuItem;
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
	// End of variables declaration    
}

