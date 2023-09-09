package project1;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;

import java.util.*;
import java.util.List;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class SplitPDF extends JFrame {

	private JPanel contentPane;
	private JTextField textField1;
	private JTextField textField;
	private String pathToSourceFile = "";
    private String pathToSaveFiles = "";
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SplitPDF frame = new SplitPDF();
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
	public SplitPDF() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 705, 382);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("File Path");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel.setBounds(10, 68, 113, 28);
		contentPane.add(lblNewLabel);
		
		textField1 = new JTextField();
		textField1.setBounds(133, 66, 360, 37);
		contentPane.add(textField1);
		textField1.setColumns(10);
		
		JLabel label2 = new JLabel("PDF Splitter");
		label2.setHorizontalAlignment(SwingConstants.CENTER);
		label2.setFont(new Font("Tahoma", Font.BOLD, 14));
		label2.setBounds(237, 10, 193, 35);
		contentPane.add(label2);
		
		JButton btnNewButton = new JButton("Browse");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {//Create a file chooser
                final JFileChooser fileChooser = new JFileChooser();
                //Filter only pdf files can be selected
                fileChooser.setFileFilter(new FileNameExtensionFilter(".pdf", "pdf"));
                //Disable choose all type files
                fileChooser.setAcceptAllFileFilterUsed(false);

                //In response to a button click:
                int returnVal = fileChooser.showOpenDialog(fileChooser);
                if (returnVal == JFileChooser.APPROVE_OPTION){
                    File file = fileChooser.getSelectedFile();
                    //Path to source (selected) file.
                    pathToSourceFile = file.getAbsoluteFile().toString();
                    //Set text to open file text field
                    textField1.setText(pathToSourceFile);
			}
			}});
		JButton splitbutton = new JButton("SPLIT");
		 splitbutton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                try {
	                    Splitter splitter = new Splitter();
	                    PDFTextStripperByArea stripper = new PDFTextStripperByArea();
	                    PDFTextStripper textStripper = new PDFTextStripper();

	                    stripper.setSortByPosition(true);

	                    File file = new File(pathToSourceFile);
	                    // Load pdf file
	                    PDDocument document = PDDocument.load(file);

	                    // Split the pages of a PDF document
	                    List<PDDocument> Pages = splitter.split(document);

	                    // Saving pdf splits by number(default) or by text from pdf file
	                    int i = 0;
	                    for (PDDocument pdf : Pages) {
	                        //Get text from pdf
	                        //String pdfText = textStripper.getText(pdf);
	                        // Split by whitespace
	                        //String[] linesInPdf = pdfText.split("\\r?\\n");

	                        //Save pdf
	                        pdf.save(pathToSaveFiles +i+".pdf");
	                        System.out.println("Saved in "+pathToSaveFiles+i+".pdf");
	                        i ++;
	                    }
	                    JOptionPane.showMessageDialog(contentPane, "The pdf file was successfully splitted");
	                    document.close();
	                } catch (IOException ex) {
	                    JOptionPane.showMessageDialog(contentPane, "No pdf file was selected");
	                    ex.printStackTrace();
	                }
	            }
	        });
		 
		 
				
		 JButton savebutton = new JButton("Save Files To");
		 savebutton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                //Create a file chooser
	                final JFileChooser fileChooser = new JFileChooser();
	                //Filter, only pdf files
	                //fileChooser.setFileFilter(new FileNameExtensionFilter(".pdf", "pdf"));
	                //Disable all type files
	                fileChooser.setAcceptAllFileFilterUsed(true);
	                //Only directory can be selected to save the splited files
	                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

	                int returnVal = fileChooser.showSaveDialog(fileChooser);
	                if (returnVal == JFileChooser.APPROVE_OPTION){
	                    File file = fileChooser.getSelectedFile();
	                    //Path to save directory
	                    pathToSaveFiles = file.getAbsoluteFile()+"/";
	                    textField.setText(pathToSaveFiles);
	                }
	            }
	        });

		 
		 
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnNewButton.setBounds(520, 68, 113, 35);
		contentPane.add(btnNewButton);
		
		JLabel lblSaveFilesTo = new JLabel("Save Files To");
		lblSaveFilesTo.setHorizontalAlignment(SwingConstants.CENTER);
		lblSaveFilesTo.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblSaveFilesTo.setBounds(10, 170, 113, 28);
		contentPane.add(lblSaveFilesTo);
		
		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(133, 161, 360, 37);
		contentPane.add(textField);
		
		
		
		
		savebutton.setFont(new Font("Tahoma", Font.BOLD, 14));
		savebutton.setBounds(520, 167, 147, 35);
		contentPane.add(savebutton);
		
		
		splitbutton.setFont(new Font("Tahoma", Font.BOLD, 14));
		splitbutton.setBounds(122, 242, 113, 35);
		contentPane.add(splitbutton);
		
		JButton btnExit = new JButton("EXIT");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnExit.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnExit.setBounds(431, 251, 113, 35);
		contentPane.add(btnExit);
	}
}

