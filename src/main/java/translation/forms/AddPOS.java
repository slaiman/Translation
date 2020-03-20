package translation.forms;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import translation.db.DBConnection;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;

public class AddPOS extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JComboBox comboBox;
	private ArrayList<String> pos = new ArrayList<String>();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddPOS frame = new AddPOS();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	//get all pos from db
    public void GetPOS()
    {
		try
		{
			String url = DBConnection.ConectionString;
			Connection con = DriverManager.getConnection(url);

			String query = "select pos from [translation].[dbo].[POS]";
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(query);
		    pos.add("");
			while(rs.next())
			{
				pos.add(rs.getString(1));
			}
			DefaultComboBoxModel model = new DefaultComboBoxModel(pos.toArray());
			comboBox.setModel(model);
			rs.close();
			st.close();
			con.close();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
    }
	/**
	 * Create the frame.
	 */
	public AddPOS() {
		//GetPOS();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 856, 459);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblWord = new JLabel("Word");
		lblWord.setBounds(58, 43, 46, 14);
		contentPane.add(lblWord);
		
		textField = new JTextField();
		textField.setBounds(126, 40, 225, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblPos = new JLabel("POS");
		lblPos.setBounds(58, 86, 46, 14);
		contentPane.add(lblPos);
		
		comboBox = new JComboBox();
		GetPOS();
		AutoCompleteDecorator.decorate(comboBox);
		comboBox.setEditable(true);
		comboBox.setBounds(360, 83, 236, 20);
		contentPane.add(comboBox);
		
		JButton btnAdd = new JButton("add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try
				{
					//db url
					String url = DBConnection.ConectionString;
					Connection con = DriverManager.getConnection(url);

					String query = "insert into [translation].[dbo].[POS] values(?,?)";

					PreparedStatement ps = con.prepareStatement(query);

					//add word to query
					ps.setString(1, textField.getText());
					//add pos to query
					ps.setString(2, textField_1.getText().isEmpty() ? comboBox.getEditor().getItem().toString():textField_1.getText());
					
					ps.addBatch();
					ps.executeBatch();
					
					JOptionPane.showMessageDialog(null, "POS inserted Successfully");
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
				}
			}
		});
		btnAdd.setBounds(126, 160, 89, 23);
		contentPane.add(btnAdd);
		
		textField_1 = new JTextField();
		textField_1.setBounds(126, 83, 225, 20);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
	}
}
