package translation.v2;

import java.util.List;

import edu.stanford.nlp.util.Pair;
import translation.newpos.PartOfSpeech;
import translation.v2.translate;

//class contains all the translation rules
public class rules 
{
	public static String rule9(List<Pair> data) 
	{
		String translated_sentence = "", temp = "", translated_word1 = "";
		System.out.println("rule 9 is activated.....");
		{
			Pair record1 = data.get( 0 );
			String word1 = (String)record1.first;
			String word1_pos = (String)record1.second;
			translated_word1 = translate.translate_word(word1,word1_pos,1);
			translated_sentence += translated_word1 + " ";
		}
		
		{
			Pair record1 = data.get( 7 );
			String word1 = (String)record1.first;
			String word1_pos = (String)record1.second;
			translated_word1 = translate.translate_word(word1,word1_pos,1);
			translated_sentence += translated_word1 + " ";
		}
		
		{
			translated_sentence += "both years" + " ";
		}
		
		{
			Pair record1 = data.get( 9 );
			String word1 = (String)record1.first;
			String word1_pos = (String)record1.second;
			translated_word1 = translate.translate_word(word1,word1_pos,1);
			translated_sentence += translated_word1 + " ";
		}
		
		{
			Pair record1 = data.get( 10 );
			String word1 = (String)record1.first;
			String word1_pos = (String)record1.second;
			translated_word1 = translate.translate_word(word1,word1_pos,1);
			translated_sentence += translated_word1 + " ";
		}
		
		{
			Pair record1 = data.get( 11 );
			String word1 = (String)record1.first;
			String word1_pos = (String)record1.second;
			translated_word1 = translate.translate_word(word1,word1_pos,1);
			translated_sentence += translated_word1 + ", ";
		}
		
		{
			Pair record1 = data.get( 6 );
			String word1 = (String)record1.first;
			String word1_pos = (String)record1.second;
			translated_word1 = translate.translate_word(word1,word1_pos,1);
			translated_sentence += translated_word1 + " ";
		}
		
		{
			Pair record1 = data.get( 13 );
			String word1 = (String)record1.first;
			String word1_pos = (String)record1.second;
			translated_word1 = translate.translate_word(word1,word1_pos,1);
			translated_sentence += translated_word1 + " ";
		}
		
		{
			Pair record1 = data.get( 14 );
			String word1 = (String)record1.first;
			String word1_pos = (String)record1.second;
			translated_word1 = translate.translate_word(word1,word1_pos,1);
			translated_sentence += "1 "+translated_word1 + " ";
		}
		
		{
			
		}
		System.out.println(translated_sentence);
		return translated_sentence;
	}
	
	public static String rule8(List<Pair> data) 
	{
		String translated_sentence = "", temp = "", translated_word1 = "";
		System.out.println("rule 8 is activated.....");
		{
			Pair record1 = data.get( 2 );
			String word1 = (String)record1.first;
			String word1_pos = (String)record1.second;

			if(word1.startsWith("«·") && word1_pos.startsWith("«”„"))
			{

				temp = word1.substring(2,word1.length());
				translated_sentence += "the ";
			}
			else temp = word1;

			translated_word1 = translate.translate_word(temp,word1_pos,1);
			translated_sentence += translated_word1 + " ";
		}

		{
			Pair record1 = data.get( 1 );
			String word1 = (String)record1.first;
			String word1_pos = (String)record1.second;

			if(word1.startsWith("«·") && word1_pos.startsWith("«”„"))
			{
				temp = word1.substring(2,word1.length());
				translated_sentence += "the ";
			}
			else temp = word1;
            
			String word2 = (String) data.get(3).first;
			if(word1.equals("‘«—ﬂ") && !word2.startsWith("«·")) translated_word1 = translate.translate_word(temp,word1_pos,2);
			else if(word1.equals("Õﬁﬁ") && (word2.startsWith("›Ì") || word2.startsWith("ÕÌ«·"))) translated_word1 = translate.translate_word(temp,word1_pos,2);
			else if(word1.equals("‘«—ﬂ") && data.contains(new Pair(" Ã—»…","«”„ €Ì— ⁄«ﬁ· ‰ﬂ—… „›—œ"))) translated_word1 = translate.translate_word(temp,word1_pos,2);
			
			else translated_word1 = translate.translate_word(temp,word1_pos,1);
			translated_sentence += translated_word1 + " ";
		}

		{
			Pair record1 = data.get( 4 );
			String word1 = (String)record1.first;
			String word1_pos = (String)record1.second;

			if(word1.startsWith("«·") && word1_pos.startsWith("«”„"))
			{
				temp = word1.substring(2,word1.length());
				translated_sentence += "the ";
			}
			else temp = word1;

			translated_word1 = translate.translate_word(temp,word1_pos,1);
			
			if(translate.start_with_vowel(translated_word1)) translated_sentence += "an " + translated_word1 + " ";
			else translated_sentence += "a " + translated_word1 + " ";
		}

		{
			Pair record1 = data.get( 3 );
			String word1 = (String)record1.first;
			String word1_pos = (String)record1.second;

			if(word1.startsWith("«·") && word1_pos.startsWith("«”„"))
			{
				temp = word1.substring(2,word1.length());
				translated_sentence += "the ";
			}
			else temp = word1;

			translated_word1 = translate.translate_word(temp,word1_pos,1);
			translated_sentence += translated_word1 + " ";
		}

		return translated_sentence;

	}


	public static String rule7(List<Pair> data) 
	{
		String translated_sentence = "",temp = "";
		System.out.println("rule 7 is activated.....");
		{
			Pair record1 = data.get( 2 );
			String word1 = (String)record1.first;
			String word1_pos = (String)record1.second;

			if(word1.startsWith("«·") && word1_pos.startsWith("«”„"))
			{

				temp = word1.substring(2,word1.length());
			}

			String translated_word1 = translate.translate_word(temp,word1_pos,1);
			translated_sentence += "the " + translated_word1 + " ";
		}

		{
			Pair record1 = data.get( 1 );
			String word1 = (String)record1.first;
			String word1_pos = (String)record1.second;

			if(word1.startsWith("«·") && word1_pos.startsWith("«”„"))
			{

				temp = word1.substring(2,word1.length());
			}


			String translated_word1 = translate.translate_word(word1,word1_pos,1);
			translated_sentence += translated_word1 + " ";
		}

		{
			Pair record1 = data.get( 3 );
			String word1 = (String)record1.first;
			String word1_pos = (String)record1.second;

			if(word1.startsWith("«·") && word1_pos.startsWith("«”„"))
			{

				temp = word1.substring(2,word1.length());
			}


			String translated_word1 = translate.translate_word(word1,word1_pos,1);
			translated_sentence += translated_word1 + " ";
		}

		{
			Pair record1 = data.get( 4 );
			String word1 = (String)record1.first;
			String word1_pos = (String)record1.second;

			if(word1.startsWith("«·") && word1_pos.startsWith("«”„"))
			{

				temp = word1.substring(2,word1.length());
			}


			String translated_word1 = translate.translate_word(word1,word1_pos,1);
			translated_sentence += translated_word1 + " ";
		}

		return translated_sentence;
	}


	public static String rule6(List<Pair> data)
	{
		String translated_sentence = "", temp = "";
		System.out.println("rule 6 is activated.....");
		{
			Pair record1 = data.get( 2 );
			String word1 = (String)record1.first;
			String word1_pos = (String)record1.second;

			if(word1.startsWith("«·") && word1_pos.startsWith("«”„"))
			{

				temp = word1.substring(2,word1.length());
				translated_sentence += "the ";
			}
			else temp = word1;

			String translated_word1 = translate.translate_word(temp,word1_pos,1);
			translated_sentence += translated_word1 + " ";
		}

		{
			Pair record1 = data.get( 1 );
			String word1 = (String)record1.first;
			String word1_pos = (String)record1.second;

			if(word1.startsWith("«·") && word1_pos.startsWith("«”„"))
			{

				temp = word1.substring(2,word1.length());
			}
			else temp = word1;

			String translated_word1 = translate.translate_word(temp,word1_pos,1);
			translated_sentence += translated_word1 + " ";
		}

		{
			Pair record1 = data.get( 3 );
			String word1 = (String)record1.first;
			String word1_pos = (String)record1.second;

			if(word1.startsWith("«·") && word1_pos.startsWith("«”„"))
			{

				temp = word1.substring(2,word1.length());
			}
			else temp = word1;

			String translated_word1 = translate.translate_word(temp,word1_pos,1);
			translated_sentence += translated_word1 + " the ";
		}

		{
			Pair record1 = data.get( 4 );
			String word1 = (String)record1.first;
			String word1_pos = (String)record1.second;

			if(word1.startsWith("«·") && word1_pos.startsWith("«”„"))
			{

				temp = word1.substring(2,word1.length());
			}
			else temp = word1;

			String translated_word1 = translate.translate_word(temp,word1_pos,1);
			translated_sentence += translated_word1 + " ";
		}

		return translated_sentence;
	}

	public static String rule5(List<Pair> data) 
	{
		String translated_sentence = "", temp = "";
		System.out.println("rule 5 is activated.....");
		{
			Pair record1 = data.get( 2 );
			String word1 = (String)record1.first;
			String word1_pos = (String)record1.second;

			if(word1.startsWith("«·") && word1_pos.startsWith("«”„"))
			{

				temp = word1.substring(2,word1.length());
				translated_sentence += "the ";
			}
			else temp = word1;


			String translated_word1 = translate.translate_word(temp,word1_pos,1);
			translated_sentence += translated_word1 + " ";
		}

		{
			Pair record1 = data.get( 1 );
			String word1 = (String)record1.first;
			String word1_pos = (String)record1.second;

			if(word1.startsWith("«·") && word1_pos.startsWith("«”„"))
			{

				temp = word1.substring(2,word1.length());
			}
			else temp = word1;

			String translated_word1 = translate.translate_word(temp,word1_pos,1);
			translated_sentence += translated_word1 + " ";
		}

		{
			Pair record1 = data.get( 3 );
			String word1 = (String)record1.first;
			String word1_pos = (String)record1.second;

			if(word1.startsWith("«·") && word1_pos.startsWith("«”„"))
			{

				temp = word1.substring(2,word1.length());
			}
			else temp = word1;

			String translated_word1 = translate.translate_word(temp,word1_pos,1);
			translated_sentence += translated_word1 + " ";
		}

		{
			Pair record1 = data.get( 5 );
			String word1 = (String)record1.first;
			String word1_pos = (String)record1.second;

			if(word1.startsWith("«·") && word1_pos.startsWith("«”„"))
			{

				temp = word1.substring(2,word1.length());
			}
			else temp = word1;

			String translated_word1 = translate.translate_word(temp,word1_pos,1);
			translated_sentence += "the " + translated_word1 + " ";
		}

		{
			Pair record1 = data.get( 4 );
			String word1 = (String)record1.first;
			String word1_pos = (String)record1.second;

			if(word1.startsWith("«·") && word1_pos.startsWith("«”„"))
			{

				temp = word1.substring(2,word1.length());
			}
			else temp = word1;

			String translated_word1 = translate.translate_word(temp,word1_pos,1);
			translated_sentence += translated_word1 + " ";
		}

		return translated_sentence;
	}
	
	public static String rule4(List<Pair> data) 
	{
		String translated_sentence = "";
		System.out.println("rule 4 is activated.....");
		{
			Pair record1 = data.get( data.size() - 1 );
			String word1 = (String)record1.first;
			String word1_pos = (String)record1.second;

			String translated_word1 = translate.translate_word(word1,word1_pos,1);
			translated_sentence += translated_word1 + " ";
		}

		{
			Pair record1 = data.get( data.size() - 2 );
			String word1 = (String)record1.first;
			String word1_pos = (String)record1.second;

			String translated_word1 = translate.translate_word(word1,word1_pos,1);
			translated_sentence += translated_word1 + " ";
		}

		{
			Pair record1 = data.get( 3 );
			String word1 = (String)record1.first;
			String word1_pos = (String)record1.second;

			String translated_word1 = translate.translate_word(word1,word1_pos,1);
			if(translated_word1.endsWith("le")) translated_word1 = translated_word1.substring(0, translated_word1.length() - 2);
			translated_sentence += "has " + translated_word1 + "ly ";
		}

		{
			Pair record1 = data.get( 2 );
			String word1 = (String)record1.first;
			String word1_pos = (String)record1.second;

			String verb_form = PartOfSpeech.get_base_form(word1);

			String translated_word1 = translate.translate_word( verb_form , "›⁄·",1 );

			translated_word1 = translate.convert_to_pastPerfect(translated_word1);

			translated_sentence += translated_word1 + " ";
		}

		return translated_sentence;
	}
	
	public static String rule3(List<Pair> data)
	{
		String translated_sentence = "";
		System.out.println("rule 3 is activated.....");
		{
			Pair record1 = data.get( 2 );
			String word1 = (String)record1.first;
			String word1_pos = (String)record1.second;

			String translated_word1 = translate.translate_word(word1,word1_pos,1);
			translated_sentence = translated_sentence + translated_word1 + " ";
		}

		{
			Pair record1 = data.get( 1 );
			String word1 = (String)record1.first;
			String word1_pos = (String)record1.second;

			String translated_word1 = translate.translate_word(word1,word1_pos,1);

			translated_word1 = translate.convert_to_presentPerfect(translated_word1);
			translated_sentence = translated_sentence + translated_word1 + " ";
		}

		{
			Pair record1 = data.get( 4 );
			String word1 = (String)record1.first;
			String word1_pos = (String)record1.second;

			String translated_word1 = translate.translate_word(word1,word1_pos,1);	

			translated_sentence = translated_sentence + translated_word1 + " ";
		}

		{
			Pair record1 = data.get( 3 );
			String word1 = (String)record1.first;
			String word1_pos = (String)record1.second;

			String translated_word1 = translate.translate_word(word1,word1_pos,1);	

			translated_sentence = translated_sentence + translated_word1 + " ";
		}

		{
			Pair record1 = data.get( 5 );
			String word1 = (String)record1.first;
			String word1_pos = (String)record1.second;

			String translated_word1 = translate.translate_word(word1,word1_pos,1);	

			translated_sentence = translated_sentence + translated_word1 + " ";
		}

		{
			Pair record1 = data.get( 7 );
			String word1 = (String)record1.first;
			String word1_pos = (String)record1.second;

			String translated_word1 = translate.translate_word(word1,word1_pos,1);	

			translated_sentence = translated_sentence + translated_word1 + " ";
		}

		{
			Pair record1 = data.get( 6 );
			String word1 = (String)record1.first;
			String word1_pos = (String)record1.second;

			String translated_word1 = translate.translate_word(word1,word1_pos,1);	

			translated_sentence = translated_sentence + translated_word1 + " ";

		}

		{
			Pair record1 = data.get( 8 );
			String word1 = (String)record1.first;
			String word1_pos = (String)record1.second;

			String translated_word1 = translate.translate_word(word1,word1_pos,1);	

			translated_sentence = translated_sentence + translated_word1 + " ";
		}

		{
			Pair record1 = data.get( 10 );
			String word1 = (String)record1.first;
			String word1_pos = (String)record1.second;

			String translated_word1 = translate.translate_word(word1,word1_pos,1);	

			translated_sentence = translated_sentence + translated_word1 + " ";
		}

		{
			Pair record1 = data.get( 9 );
			String word1 = (String)record1.first;
			String word1_pos = (String)record1.second;

			String translated_word1 = translate.translate_word(word1,word1_pos,1);	

			translated_sentence = translated_sentence + translated_word1 + " ";
		}


		return translated_sentence;
	}

	
	public static String rule2(List<Pair> data)
	{
		String translated_sentence = "";
		System.out.println("rule 2 is activated.....");

		{
			Pair record1 = data.get( 1 );
			String word1 = (String)record1.first;
			String word1_pos = (String)record1.second;

			String translated_word1 = translate.translate_word(word1,word1_pos,1);
			translated_sentence = translated_sentence + translated_word1 + " has ";
		}

		{
			Pair record1 = data.get( 2 );
			String word1 = (String)record1.first;
			String word1_pos = (String)record1.second;

			Pair record2 = data.get( 3 );
			String word2 = (String)record2.first;
			String word2_pos = (String)record2.second;

			String translated_word1 = translate.translate_word(word1,word1_pos,1);	
			String translated_word2 = translate.translate_word(word2,word2_pos,1);	
			translated_sentence = translated_sentence + translated_word2 + " " + translated_word1 + " ";
		}

		{
			Pair record1 = data.get( 4 );
			String word1 = (String)record1.first;
			String word1_pos = (String)record1.second;

			String translated_word1 = translate.translate_word(word1,word1_pos,1);	

			translated_sentence = translated_sentence + translated_word1 + " ";
		}

		{
			Pair record1 = data.get( 5 );
			String word1 = (String)record1.first;
			String word1_pos = (String)record1.second;

			String translated_word1 = translate.translate_word(word1,word1_pos,1);	

			translated_sentence = translated_sentence + translated_word1 + " is ";
		}

		{
			Pair record1 = data.get( 6 );
			String word1 = (String)record1.first;
			String word1_pos = (String)record1.second;

			String translated_word1 = translate.translate_word(word1,word1_pos,1);	

			translated_sentence = translated_sentence + translated_word1 + " ";
		}

		{
			Pair record1 = data.get( 7 );
			String word1 = (String)record1.first;
			String word1_pos = (String)record1.second;

			String translated_word1 = translate.translate_word(word1,word1_pos,1);	

			translated_sentence = translated_sentence + translated_word1 + " ";

		}

		{
			Pair record1 = data.get( 8 );
			String word1 = (String)record1.first;
			String word1_pos = (String)record1.second;

			String translated_word1 = translate.translate_word(word1,word1_pos,1);	

			translated_sentence = translated_sentence + "the " + translated_word1 + " ";
		}

		{
			Pair record1 = data.get( 9 );
			String word1 = (String)record1.first;
			String word1_pos = (String)record1.second;

			translated_sentence = translated_sentence + "to ";
		}

		{
			Pair record1 = data.get( 10 );
			String word1 = (String)record1.first;
			String word1_pos = (String)record1.second;

			Pair record2 = data.get( 11 );
			String word2 = (String)record2.first;
			String word2_pos = (String)record2.second;

			String verb_form = PartOfSpeech.get_base_form(word1);

			String translated_word1 = translate.translate_word( verb_form , "›⁄·",1 );
			String translated_word2 = translate.translate_word( word2 , word2_pos,1 );

			String indefinite_article = "";

			Pair record3 = data.get( 12 );
			String word3 = (String)record3.first;
			String word3_pos = (String)record3.second;
			String translated_word3 = translate.translate_word(word3,word3_pos,1);

			if(translated_word3.startsWith("a") || translated_word3.startsWith("o") || translated_word3.startsWith("u") || translated_word3.startsWith("e") || translated_word3.startsWith("i")) indefinite_article = "an";
			else indefinite_article = "a";

			translated_sentence = translated_sentence + translated_word1 + " " + indefinite_article + " " + translated_word3 + " " + translated_word2;
		}

		return translated_sentence;
	}

}
