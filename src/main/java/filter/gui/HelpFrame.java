package filter.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.IOException;
import java.net.URL;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

public class HelpFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4102118683134344931L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HelpFrame frame = new HelpFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public HelpFrame() {
		setResizable(false);
		setTitle("Help");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 500, 390);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 494, 362);
		contentPane.add(scrollPane);
		JEditorPane editorPane = new JEditorPane();
		
		editorPane.setEditable(false);
		URL helpURL = HelpFrame.class.getResource("/help.html");
		
		try {

		    editorPane.setPage(helpURL);
	
		} catch (IOException e) {
		    System.err.println("Attempted to read a bad URL: " + helpURL);
		}
		
		scrollPane.setViewportView(editorPane);
		
	}

}
