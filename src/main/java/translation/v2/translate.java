package translation.v2;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;
import edu.stanford.nlp.util.Pair;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import translation.newpos.PartOfSpeech;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

public class translate 
{

	public static void main(String[] args) 
	{

	}

	//translate a given text
	public static String translateText(String text)
	{
		List<Pair> data = new ArrayList<Pair>(); 
		String translated_sentence = "";
		String pattern = "{";
		String temp = "";
		int i=0;
		String pos = "";
		try
		{
			//prepare properties of nlp
			Properties props = new Properties();
			//set the anotators used
			props.put("annotators", "tokenize, ssplit, pos, parse");
			//set the language of the tokenizer
			props.put("tokenize.language", "ar");
			//set the model used for segmentation
			props.put("segment.model","edu/stanford/nlp/models/segmenter/arabic/arabic-segmenter-atb+bn+arztrain.ser.gz");
			props.put("ssplit.boundaryTokenRegex", "[.]|[!?]+|[!\\u061F]+");
			//set the model of the pos
			props.put("pos.model", "edu/stanford/nlp/models/pos-tagger/arabic/arabic.tagger");

			//prepare the pipeline based on the properties defined above
			StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

			//process the text
			Annotation annotation =  pipeline.process(text);

			//use the sentence annotation on text
			List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);
			
            //loop over sentences
			for(CoreMap sentence: sentences) 
			{

				System.out.println(sentence.toString());
				//loop ver tokens (words)
				for (CoreLabel token: sentence.get(TokensAnnotation.class)) 
				{
					//get the word
					String word = token.originalText();
                    temp = word;

                    //check if the word begins with "ال"
					if(word.startsWith("ال"))
					{
						//get the pos of the word before cutting it
						pos = PartOfSpeech.get_POS(word, sentence.toString());

						//if pos starts with "اسم" get the pos of the original word
						if(pos.startsWith("اسم"))
						{
							temp = word.substring(2,word.length());	
							pos = PartOfSpeech.get_POS(temp, sentence.toString());
						}
					}
					//if sentence didn't end yet
					else if(!word.equals("."))
					{
						//temp = word;
						pos = PartOfSpeech.get_POS(word, sentence.toString());
					}
					System.out.println("word: " + temp + " pos: " + pos);					

					//concatenate pos of each word if not end of sentence
					if(! word.equals("."))
					{
						pattern += pos + ",";

						Pair pair = new Pair(word,pos);

						data.add(pair);
					}
				}

				//special case
				if(sentence.toString().startsWith("تجدر الإشارة أن")) {
					data.remove(0);
					data.remove(0);
					Pair pair = new Pair("تجدر الإشارة أن","عبارة");
					data.set(0, pair);
				}
				i++;
				//replace "تجدر,الإشارة,أن" with "عبارة" in pattern string and close it
				pattern = pattern.replaceFirst("تجدر,الإشارة,أن","عبارة");
				pattern = pattern.substring(0, pattern.length() - 1);
				pattern += "}";

				System.out.println(pattern);

				//apply rules on sentence based on the extracted pattern
				translated_sentence += apply_rules(data,pattern) + "\n";
				pattern = "{";
				data.clear();
			}

			System.out.println("number of sentences : "+ i);

		}
		catch(Exception ex)
		{
			System.out.println("error");
			ex.printStackTrace();
		}
		return translated_sentence;
	}

	//search for the rule based on the pattern
	private static String apply_rules(List<Pair> data,String pattern)
	{
		String translated_sentence = "";
		
		if(pattern.equals("{ل,اسم نكرةغير معدود غير عاقل,اسم جمع نكرة معدود غير عاقل,أداة تعداد,حرف عطف,ضمير منفصل مذكر مفرد,أداة نفي,أداة حصر,اسم معرّف معدود غير عاقل,حرف جرّ,مصدر مؤول,اسم نكرة معدود,صفة نكرة}")) translated_sentence = rules.rule2(data);
		else if (pattern.equals("{لقد,فعل ماض,اسم معرّف غير عاقل,اسم جمع معدود غير عاقل,صفة,حرف جرّ,اسم معرف غير عاقل غير معدود,صفة معرفة,ظرف زمان,اسم معرف معدود غير عاقل,صفة معرفة}"))  translated_sentence = rules.rule3(data);
		else if (pattern.equals("{قد,فعل ماض مجهول ,اسم مفرد معدود غير عاقل ,صفة,حرف جرّ,اسم معدود غير عاقل معرّف بالمضاف,اسم نكرةغير معدود,صفة}")) translated_sentence = rules.rule4(data);
		else if (pattern.equals("{قد,فعل ماض,اسم معدود جمع عاقل نكرة,حرف جرّ,اسم غير عاقل معرّف مفرد,صفة معرّف مفرد}") || pattern.equals("{قد,فعل ماض,اسم معدود جمع عاقل نكرة,حرف جرّ,اسم غير عاقل نكرة مفرد,صفة معرّف مفرد}")) translated_sentence = rules.rule5(data);
		else if (pattern.equals("{قد,فعل ماض,اسم معدود جمع عاقل نكرة,حرف جرّ,اسم غير عاقل نكرة مفرد}")) translated_sentence = rules.rule6(data);
		else if (pattern.equals("{قد,فعل ماض,اسم معدود جمع عاقل نكرة,حرف جرّ,اسم غير عاقل معرّف مفرد}")) translated_sentence = rules.rule7(data);
		else if (pattern.equals("{قد,فعل ماض,اسم معدود جمع عاقل نكرة,اسم غير عاقل نكرة مفرد,صفة نكرة مفرد}") || pattern.equals("{قد,فعل ماض,اسم معدود جمع غير عاقل نكرة,اسم غير عاقل نكرة مفرد,صفة نكرة مفرد}")) 
		{	
			translated_sentence = rules.rule8(data);
		}
		else if(pattern.equals("{تجدر,الإشارة,أن,اسم معدود مفرد غير عاقل,اسم نكرة جمع معدود عاقل,صفة معرفة جمع تدل على جنسية,اسم معدود عاقل جمع نكرة,حرف جرّ,اسم بلد,حرف جرّ,اسم نكرة مثنى غير عاقل يدلّ على سنة,سنة,حرف عطف,سنة,قد,فعل ماض مفرد,اسم جمع معدود غير عاقل يدلّ على عدد,اسم مفرد معدود عاقل,ما,فعل مضارع مفرد,كلمة تفيد العدد التقريبي,عدد,نسبة,حرف جرّ,اسم جمع نكرة معدود عاقل,اسم بلد,،,حرف عطف,ضمير منفصل مذكر مفرد,فعل مضارع مجهول مفرد,من أسماء التفضيل,اسم معدود مفرد غير عاقل,حرف جرّ,اسم نكرة جمع معدود عاقل,ل,كلّ,فرد,حرف جرّ,اسم جمع نكرة معدود عاقل,أيّ,بلد,حرف جرّ,اسم نكرة غير عاقل}")){  translated_sentence = rules.rule9(data);
		System.out.println(translated_sentence);
		//translated_sentence = "It should be noted that in both years 2014 and 2015, Lebanon accounted over 1 million registered persons, which constituted around 25 per cent of the Lebanese population and represented the world’s highest number of refugees per inhabitant.";
		}
		return translated_sentence;
	}

	//translate the word based on the pos and meaning type
	public static String translate_word(String word,String pos,int meaning_nb) 
	{
		String translated_word = "",query = "";
		try
		{
			String url = "jdbc:sqlserver://localhost;databaseName=translation;integratedSecurity=true";
			Connection con = DriverManager.getConnection(url);



			if(meaning_nb == 1) query = "select english_word1 from ArabicEnglishDic where arabic_word = '"+word+"'  and   pos = '" +pos+ "'";
			else if (meaning_nb == 2) query = "select english_word2 from ArabicEnglishDic where arabic_word = '"+word+"'  and   pos = '" +pos+ "'";

			//System.out.println(query);
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(query);
			if(rs.next())
			{
				translated_word = rs.getString(1);
				//System.out.println(translated_word);
				return translated_word;
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return translated_word;
	}

	//convert verb to past perfect from excel sheet
	public static String convert_to_pastPerfect(String element)
	{
		try
		{
			FileInputStream file = new FileInputStream(new File("C:\\Users\\Sleimٍan\\workspace\\translation\\dictionary\\english verbs  tenses.xlsx"));

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

					Cell c2 = row.getCell(3);
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

	//convert verb to present perfect from excel sheet
	public static String convert_to_presentPerfect(String element)
	{
		try
		{
			FileInputStream file = new FileInputStream(new File("C:\\Users\\Sleimٍan\\workspace\\translation\\dictionary\\english verbs  tenses.xlsx"));

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

	//check is a word starts with vowel
	public static boolean start_with_vowel(String word)
	{
		if(word.startsWith("a") || word.startsWith("A") || word.startsWith("o") || word.startsWith("O") || word.startsWith("u") || word.startsWith("U") || word.startsWith("e") || word.startsWith("E") || word.startsWith("i") || word.startsWith("I"))
		{
			return true;
		}
		else return false;
	}

}
