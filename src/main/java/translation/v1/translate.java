package translation.v1;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.JTextPane;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;
import edu.stanford.nlp.util.Pair;

public class translate
{
	private static String api_key = "AIzaSyCUf6Kvyn7becMOt3SnWDYRMSwqyAv28wo";
	
	public static List<Pair> process_text(String text)
	{
		List<Pair> data = new ArrayList<Pair>(); 

		// creates a StanfordCoreNLP object, with POS tagging, lemmatization, NER, parsing, and coreference resolution 
		Properties props = new Properties();
		props.put("annotators", "tokenize, ssplit, pos, parse, lemma, truecase");
		props.put("tokenize.language", "ar");
		props.put("segment.model","edu/stanford/nlp/models/segmenter/arabic/arabic-segmenter-atb+bn+arztrain.ser.gz");
		props.put("pos.model", "edu/stanford/nlp/models/pos-tagger/arabic/arabic.tagger");
		//props.put("parse.model","edu/stanford/nlp/models/lexparser/arabicFactored.ser.gz");

		StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

		// create an empty Annotation just with the given text
		Annotation document = new Annotation(text);

		// run all Annotators on this text
		pipeline.annotate(document);

		// these are all the sentences in this document
		// a CoreMap is essentially a Map that uses class objects as keys and has values with custom types
		List<CoreMap> sentences = document.get(SentencesAnnotation.class);

		for(CoreMap sentence: sentences) 
		{
			// traversing the words in the current sentence
			// a CoreLabel is a CoreMap with additional token-specific methods
			System.out.println(sentence.toString());
			for (CoreLabel token: sentence.get(TokensAnnotation.class)) 
			{
				// this is the text of the token
				String word = token.get(TextAnnotation.class);
				// this is the POS tag of the token
				String pos = token.get(PartOfSpeechAnnotation.class);

				Pair pair = new Pair(word,pos);

				data.add(pair);
				// this is the NER label of the token
				String ne = token.get(NamedEntityTagAnnotation.class);

				System.out.println("word: " + word + " pos: " + pos + " ne:" + ne);
			}

		}

		return data;
	}

	//store words to database
	public static void store_words( List<Pair> data, JTextPane textPane_1 )
	{
		PreparedStatement ps = null;

		Vector<String> word_pos = new Vector<String>();
		Vector<String> pattern_list = new Vector<String>();


		String pattern = "{";

		try
		{
			String url = "jdbc:sqlserver://localhost;databaseName=translation;integratedSecurity=true";
			Connection con = DriverManager.getConnection(url);


			String query = "INSERT INTO Data (word,type,translation) VALUES (?, ?, ?)";
			ps = con.prepareStatement(query);  

			//loop over pairs (word,POS of word)
			for (Pair record : data) 
			{
				String word = (String) record.first;
				String word_type = (String) record.second;
				ps.setString(1, word);
				ps.setString(2, word_type);

				//if the word was a puntuation then we reached the end of sentence
				if(word_type.equals("PUNC"))
				{
					ps.setString(3, word);

					pattern = pattern.substring(0, pattern.length() - 1);

					pattern = pattern + "}";

					System.out.println("pattern: "+pattern);
					String translated_sentence = translate_sentence(word_pos,pattern);
					textPane_1.setText(translated_sentence);
					word_pos.clear();
				}		
				//translate the word based on its pos
				else
				{
					String translated_text = translate_word(word,word_type);

					word_pos.add(translated_text);
					pattern = pattern + word_type + ",";

					ps.setString(3, translated_text);
				}

				ps.addBatch();
			}
			//save to db
			ps.executeBatch();

		} 
		catch (Exception e)
		{

			e.printStackTrace();
		}
	}
	//translate each sentence based on its pattern
	private static String translate_sentence(Vector word_pos, String pattern) 
	{
		String rule = "";
		String translated_sentence = "";

		Vector<String> sentence_list = new Vector<String>();
		try
		{
			//get the relative english pattern based on its arabic pattern
			rule = discover_rule(pattern);

			//translate using the relative pattern
			sentence_list = apply_rule(rule,word_pos);

			//check for exception rules after main translation
			check_for_exceptions(sentence_list);

			for(int i=0 ; i < sentence_list.size() ; i++)
			{
				translated_sentence += sentence_list.get(i) + " ";
			}

			return translated_sentence;

		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return null;
	}


	//translate using the rule extracted from db
	private static Vector apply_rule(String rule, Vector word_pos)
	{

		Vector<String> sentence_list = new Vector<String>();

		StringTokenizer tokenizer = new StringTokenizer(rule, ",");


		while (tokenizer.hasMoreTokens())
		{
			int index = 0;
			String token = tokenizer.nextToken();

			if( token.contains("(") && token.contains(")")) index = Integer.parseInt(token.substring(token.indexOf("(")+1, token.indexOf(")")));

			if(token.startsWith("'") && token.endsWith("'"))
			{
				String word = token.replaceAll("'", "");
				//translated_sentence = translated_sentence + word + " ";

				sentence_list.add(word);
			}
			else 
			{
				if(token.contains("simple past"))
				{
					//translated_sentence = translated_sentence + find_word(token.replaceAll(";simple past", ""),word_pos, "simple past") + " ";
					String past_word = convert_to_past( (String) word_pos.get(index) );
					//translated_sentence = translated_sentence + past_word + " ";

					sentence_list.add(past_word);
				}

				else if(token.contains("present perfect"))
				{
					String past_word = convert_to_present_perfect( (String) word_pos.get(index) );

					sentence_list.add(past_word);
				}

				else 
				{
					String word = (String) word_pos.get(index);
					sentence_list.add(word);
				}
			}

		}
		return sentence_list;
	}

	//convert verbs to tenses from excel file
	private static String convert_to_present_perfect(String element) 
	{
		try
		{
			FileInputStream file = new FileInputStream(new File("C:\\Users\\Sleimòan\\workspace\\translation\\dictionary\\english verbs  tenses.xlsx"));

			//Create Workbook instance holding reference to .xlsx file
			XSSFWorkbook workbook = new XSSFWorkbook(file);

			//Get first/desired sheet from the workbook
			XSSFSheet sheet = workbook.getSheetAt(0);

			//Iterate through each rows one by one
			Iterator<Row> rowIterator = sheet.iterator();
			while (rowIterator.hasNext()) 
			{
				Row row = rowIterator.next();
				Cell c = row.getCell(1);

				if(c.getRichStringCellValue().getString().equals(element))
				{

					Cell c2 = row.getCell(6);

					//System.out.println(c2.getRichStringCellValue());
					return c2.getRichStringCellValue().getString();
				}
			}
			file.close();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return null;
	}


	private static void check_for_exceptions(Vector<String> sentence_list) 
	{
		check_for_vowels(sentence_list);
	}

	//check for vowels condition in each word of the sentence
	private static void check_for_vowels(Vector<String> sentence_list) 
	{
		for(int i = 0 ; i < sentence_list.size() - 1 ; i++)
		{
			String first_word = sentence_list.get(i);
			String second_word = sentence_list.get(i + 1);
			if(  first_word.equals("a")  &&   StartsWithVowel(second_word))
			{
				sentence_list.setElementAt("an", i);
			}

		}

	}

	//check if the word starts with vowel
	private static boolean StartsWithVowel(String second_word) 
	{
		if(second_word.startsWith("i") || second_word.startsWith("e") || second_word.startsWith("a") || second_word.startsWith("o") || second_word.startsWith("u"))  
			return true;
		else return false;
	}

	//get the rule from db based on the pattern extracted from sentence
	private static String discover_rule(String pattern) 
	{
		String rule = "";
		String url = "jdbc:sqlserver://localhost;databaseName=translation;integratedSecurity=true";
		try 
		{
			Connection con = DriverManager.getConnection(url);


			String query = "SELECT [rule] FROM [translation].[dbo].[rules] where pattern = '"+pattern+"'";

			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(query);
			if(rs.next())
			{
				rule = rs.getString(1);
				System.out.println("Rule: "+rule);
			}

			rule = rule.substring(rule.indexOf("{") + 1, rule.indexOf("}"));
		} 
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return rule;
	}

	//convert verbs to past from excel sheet
	private static String convert_to_past(String element)
	{
		try
		{
			FileInputStream file = new FileInputStream(new File("C:\\Users\\Sleimòan\\workspace\\translation\\dictionary\\english verbs  tenses.xlsx"));

			//Create Workbook instance holding reference to .xlsx file
			XSSFWorkbook workbook = new XSSFWorkbook(file);

			//Get first/desired sheet from the workbook
			XSSFSheet sheet = workbook.getSheetAt(0);

			//Iterate through each rows one by one
			Iterator<Row> rowIterator = sheet.iterator();
			while (rowIterator.hasNext()) 
			{
				Row row = rowIterator.next();
				Cell c = row.getCell(1);

				if(c.getRichStringCellValue().getString().equals(element))
				{

					Cell c2 = row.getCell(2);

					//System.out.println(c2.getRichStringCellValue());
					return c2.getRichStringCellValue().getString();
				}
			}
			file.close();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return null;
	}

	//translate word from db
	private static String translate_word(String word,String pos) 
	{
		String translated_word = "";
		try
		{
			String url = "jdbc:sqlserver://localhost;databaseName=translation;integratedSecurity=true";
			Connection con = DriverManager.getConnection(url);



			String query = "select english_word1 from ArabicEnglishDic where arabic_word = '"+word+"'  and   pos = '" +pos+ "'";

			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(query);
			if(rs.next())
			{
				translated_word = rs.getString(1);
                System.out.println(translated_word);
				return translated_word;
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return translated_word;
	}
}
