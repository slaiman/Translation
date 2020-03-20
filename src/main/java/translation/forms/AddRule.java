package translation.forms;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import java.awt.Font;

public class AddRule extends JFrame 
{

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;

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
					AddRule frame = new AddRule();
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
	public AddRule() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 848, 506);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblPattern = new JLabel("Pattern");
		lblPattern.setBounds(29, 21, 46, 14);
		contentPane.add(lblPattern);
		
		JLabel lblRule = new JLabel("Rule");
		lblRule.setBounds(29, 83, 46, 14);
		contentPane.add(lblRule);
		
		textField = new JTextField();
		textField.setBounds(85, 18, 589, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(85, 80, 589, 20);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		JButton btnAddRule = new JButton("Add Rule");
		btnAddRule.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				
				try
				{
					//db url
					String url = "jdbc:sqlserver://localhost;databaseName=translation;integratedSecurity=true";
					Connection con = DriverManager.getConnection(url);

					String query = "insert into [translation].[dbo].[rules] values(?,?)";

					PreparedStatement ps = con.prepareStatement(query);

					//add arabic rule to query
					ps.setString(1, textField.getText());

					//add english rule to query
					ps.setString(2, textField_1.getText());
					
					ps.addBatch();
					ps.executeBatch();
					
					JOptionPane.showMessageDialog(null, "Rule inserted Successfully");
					
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
				}
			}
		});
		btnAddRule.setBounds(85, 152, 89, 23);
		contentPane.add(btnAddRule);
		
		JTextArea txtrHintthePattern = new JTextArea();
		txtrHintthePattern.setFont(new Font("Courier New", Font.PLAIN, 16));
		txtrHintthePattern.setText("Hint:\r\n\r\n-the pattern must be of the form : {pattern1,pattern2,pattern3,......}\r\n\r\n-the rule must be of the form: {word1;tense,word2;tense,.......;........}\r\n");
		txtrHintthePattern.setBounds(0, 242, 832, 196);
		contentPane.add(txtrHintthePattern);
	}
}
