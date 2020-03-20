package translation.forms;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AddWordTranslation extends JFrame 
{

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JComboBox comboBox;

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
					AddWordTranslation frame = new AddWordTranslation();
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
	public AddWordTranslation()
	{
		setTitle("Add POS");
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				
				try
				{
					String url = "jdbc:sqlserver://localhost;databaseName=translation;integratedSecurity=true";
					Connection con = DriverManager.getConnection(url);

					String query = "select DISTINCT pos from [translation].[dbo].[POS]";

					Statement st = con.createStatement();
					ResultSet rs =  st.executeQuery(query);
					while(rs.next())
					{
					  comboBox.addItem(rs.getString(1));	
					}
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
				}
				
				
			}
		});
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 751, 365);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblArabicWord = new JLabel("Arabic Word");
		lblArabicWord.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		lblArabicWord.setBounds(34, 33, 103, 14);
		contentPane.add(lblArabicWord);
		
		JLabel lblEnglishWord = new JLabel("English Word");
		lblEnglishWord.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		lblEnglishWord.setBounds(34, 132, 103, 22);
		contentPane.add(lblEnglishWord);
		
		textField = new JTextField();
		textField.setBounds(219, 32, 195, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(219, 134, 195, 20);
		contentPane.add(textField_1);
		
		JButton btnAddTranslation = new JButton("add translation");
		btnAddTranslation.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				try
				{
					String url = "jdbc:sqlserver://localhost;databaseName=translation;integratedSecurity=true";
					Connection con = DriverManager.getConnection(url);

                    String pos = (String)comboBox.getSelectedItem();
                    String arabic_word = textField.getText();

					String query = "insert into [translation].[dbo].[ArabicEnglishDic] (arabic_word,pos,english_word1,word_domain) values('"+arabic_word+"','"+pos+"','"+textField_1.getText()+"','"+textField_2.getText()+"')";

					Statement st = con.createStatement();
					st.execute(query);
					
					query = "select pos from [dbo].[POS] where pos = '"+pos+"'";
					ResultSet rs = st.executeQuery(query);
					if(!rs.next())
					{
						query = "insert into [dbo].[POS] (word,pos) values('"+arabic_word+"','"+pos+"')";
						st.execute(query);
					}
					JOptionPane.showMessageDialog(null, "Word Translation inserted Successfully");
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
				}
				
			}
		});
		btnAddTranslation.setBounds(72, 271, 139, 23);
		contentPane.add(btnAddTranslation);
		
		JLabel lblPos = new JLabel("POS");
		lblPos.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		lblPos.setBounds(34, 200, 103, 22);
		contentPane.add(lblPos);
		
		JLabel lblFormOfThe = new JLabel("Form of Arabic Verb");
		lblFormOfThe.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		lblFormOfThe.setBounds(34, 84, 159, 14);
		contentPane.add(lblFormOfThe);
		
		textField_2 = new JTextField();
		textField_2.setBounds(219, 83, 195, 20);
		contentPane.add(textField_2);
		textField_2.setColumns(10);
		
		comboBox = new JComboBox();
		comboBox.setEditable(true);
		comboBox.addKeyListener(new KeyAdapter() 
		{
	
			@Override
			public void keyPressed(KeyEvent e)
			{
				System.out.println("okok");
				System.out.println(e.getKeyChar());
			}
			@Override
			public void keyTyped(KeyEvent e)
			{
				System.out.println("okok");
				System.out.println(e.getKeyChar());
			}
			@Override
			public void keyReleased(KeyEvent e) 
			{
				System.out.println("okok");
				System.out.println(e.getKeyChar());
			}
		});
		
		comboBox.setBounds(219, 203, 195, 20);
		contentPane.add(comboBox);
		
		
	}
}
