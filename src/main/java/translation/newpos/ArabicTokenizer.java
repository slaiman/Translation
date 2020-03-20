package translation.newpos;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class ArabicTokenizer
{

	String sentence = "ŞÏ ÑğßğÖğ ãÔÇÑßæä İí ÇáÈáÏÉ";
	ArrayList<String> tokens = new ArrayList<String>();

	public ArabicTokenizer(String sentence)
	{
		
		this.sentence = sentence;
	}
	
	//tokenize a sentence
	public void process()
	{
		StringTokenizer st = new StringTokenizer(sentence, " ");
		
		while(st.hasMoreTokens())
		{
			tokens.add(st.nextToken());
		}	
	}

	//return tokens
	public ArrayList getToken()
	{
		return tokens;
	}
	
}
