package translation.forms;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

import translation.v2.translate;

import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;


public class MainFrame extends JFrame 
{

	private JPanel contentPane;
	private JTextPane textPane;
	private JTextPane textPane_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) 
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try 
				{
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainFrame() 
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1600, 800);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JButton btnNewButton = new JButton("Choose text file to translate");
		btnNewButton.setBounds(788, 254, 201, 23);
		btnNewButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				try
				{
					JFileChooser jf = new JFileChooser();
					int ans = jf.showOpenDialog(null);

					if(ans == jf.APPROVE_OPTION)
					{
						File file = jf.getSelectedFile();

						String content = new String(Files.readAllBytes(Paths.get(file.getPath())));
						textPane.setText(content);
					}
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
				}

			}
		});
		contentPane.setLayout(null);
		contentPane.add(btnNewButton);

		JLabel lblTextToTranslate = new JLabel("Text to translate");
		lblTextToTranslate.setBounds(428, 3, 128, 23);
		lblTextToTranslate.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		contentPane.add(lblTextToTranslate);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 37, 1364, 183);
		contentPane.add(scrollPane);
		
		textPane = new JTextPane();
		textPane.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		textPane.setText("\u0642\u062F \u0634\u0627\u0631\u0643 \u0627\u0644\u062A\u0644\u0627\u0645\u064A\u0630 \u062A\u062C\u0631\u0628\u0629 \u0645\u0631\u062D\u0629.\r\n\u0642\u062F \u062A\u0644\u0642\u0649 \u0627\u0644\u0623\u0633\u0627\u062A\u0630\u0629 \u062A\u062F\u0631\u064A\u0628\u0627 \u0639\u0645\u0644\u064A\u0627.\r\n\u0642\u062F \u0646\u0638\u0645\u062A \u0627\u0644\u0641\u062A\u064A\u0627\u062A \u0645\u062D\u0627\u0636\u0631\u0629 \u0639\u0627\u0645\u0629.\r\n\u0642\u062F \u062D\u0642\u0642\u062A \u0627\u0644\u0641\u0631\u0642 \u0641\u0648\u0632\u0627 \u0645\u0630\u0647\u0644\u0627.\r\n\u0642\u062F \u062D\u0642\u0642 \u0627\u0644\u0645\u0634\u0627\u0631\u0643\u0648\u0646 \u0641\u0648\u0632\u0627 \u0645\u0630\u0647\u0644\u0627.\r\n\u062A\u062C\u062F\u0631 \u0627\u0644\u0625\u0634\u0627\u0631\u0629 \u0623\u0646 \u0639\u062F\u062F \u0627\u0644\u0646\u0627\u0632\u062D\u064A\u0646 \u0627\u0644\u0633\u0648\u0631\u064A\u064A\u0646 \u0627\u0644\u0645\u0633\u062C\u0644\u064A\u0646 \u0641\u064A \u0644\u0628\u0646\u0627\u0646 \u0641\u064A \u0627\u0644\u0639\u0627\u0645\u064A\u0646 2014 \u0648 2015 \u0642\u062F \u062A\u0639\u062F\u0651\u0649 \u0645\u0644\u064A\u0648\u0646 \u0646\u0627\u0632\u062D \u0645\u0627 \u064A\u0634\u0643\u0651\u0644 \u062D\u0648\u0627\u0644\u064A 25 % \u0645\u0646 \u0633\u0643\u0627\u0646 \u0644\u0628\u0646\u0627\u0646\u060C \u0648\u0647\u0648 \u064A\u064F\u0639\u062A\u0628\u0631 \u0623\u0643\u0628\u0631 \u0639\u062F\u062F \u0645\u0646 \u0627\u0644\u0646\u0627\u0632\u062D\u064A\u0646 \u0644\u0643\u0644\u0651 \u0641\u0631\u062F \u0645\u0646 \u0633\u0643\u0627\u0646 \u0623\u064A\u0651 \u0628\u0644\u062F \u0641\u064A \u0627\u0644\u0639\u0627\u0644\u0645.");
		scrollPane.setViewportView(textPane);
		textPane.setBorder(new LineBorder(new Color(192, 192, 192), 4));
		
		JLabel lblTranslatedText = new JLabel("Translated Text");
		lblTranslatedText.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblTranslatedText.setBounds(559, 340, 104, 23);
		contentPane.add(lblTranslatedText);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 374, 1364, 161);
		contentPane.add(scrollPane_1);
		
		textPane_1 = new JTextPane();
		scrollPane_1.setViewportView(textPane_1);
		textPane_1.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		textPane_1.setBorder(new LineBorder(Color.LIGHT_GRAY, 4));
		
		JButton btnTranslate = new JButton("translate");
		btnTranslate.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				String translated_text = translate.translateText(textPane.getText());
				textPane_1.setText(translated_text);
			}
		});
		btnTranslate.setBounds(683, 581, 89, 23);
		contentPane.add(btnTranslate);
		
		JButton btnNewButton_1 = new JButton("Add Rule");
		btnNewButton_1.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				
				AddRule ar = new AddRule();
				ar.setVisible(true);
				
			}
		});
		btnNewButton_1.setBounds(642, 254, 104, 23);
		contentPane.add(btnNewButton_1);
		
		JButton btnAddWordTranslation = new JButton("Add word Translation");
		btnAddWordTranslation.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				AddWordTranslation awt = new AddWordTranslation();
				awt.setVisible(true);
				
			}
		});
		btnAddWordTranslation.setBounds(428, 254, 161, 23);
		contentPane.add(btnAddWordTranslation);
		
		JButton btnNewButton_2 = new JButton("add data");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AddData form = new AddData();
				form.setVisible(true);
				
			}
		});
		btnNewButton_2.setBounds(253, 254, 89, 23);
		contentPane.add(btnNewButton_2);
		
		JButton btnAddPos = new JButton("add POS");
		btnAddPos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddPOS fr = new AddPOS();
				fr.setVisible(true);
			}
		});
		btnAddPos.setBounds(132, 254, 89, 23);
		contentPane.add(btnAddPos);
	}
}
