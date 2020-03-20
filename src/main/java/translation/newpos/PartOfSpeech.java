package translation.newpos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PartOfSpeech 
{

	public static void main(String[] args) 
	{
	
	}

	//get pos of a word found in this sentence
	public static String get_POS(String word, String sentence)
	{
		String word_pos = "";
		String url = "jdbc:sqlserver://localhost:1433;databaseName=translation;integratedSecurity=true";
		try 
		{
			//register sql driver
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			Connection con = DriverManager.getConnection(url);

			String query = "SELECT [pos] FROM [translation].[dbo].[POS] where word = '"+word+"'";

			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(query);
			//if pos is found in db
			if(rs.next())
			{
				word_pos = rs.getString(1);
			}
			//search in the arabicconjugation class
			else 
			{/*
				ArabicConjugation ARCon = new ArabicConjugation();
				String Conjugation = ARCon.GetConjugation(word);
				if(Conjugation.isEmpty()) return word;
				else
				{
					return ARCon.GetPOSfromConjugator(Conjugation);
				}*/
				word_pos = word;
			}
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return word_pos;
	}

	//get the base form of word from db
	public static String get_base_form(String word)
	{
		
		String verb_base_form = "";
		String url = "jdbc:sqlserver://localhost;databaseName=translation;integratedSecurity=true";
		try 
		{
			Connection con = DriverManager.getConnection(url);

			String query = "SELECT [base_form] FROM [translation].[dbo].[Base_Form] where verb = '"+word+"'";

			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(query);
			if(rs.next())
			{
				verb_base_form = rs.getString(1);
				return verb_base_form;
			}
		} 
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return word;
	}
	

}
