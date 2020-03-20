package translation.newpos;

import translation.newpos.ArabicDiacritics;

import java.util.Hashtable;

//class that contains conjunction rules based on diacritics
public class ArabicConjugation {
	
	public static Hashtable<Integer, String> fa3ala = new Hashtable<Integer, String>();
	public static Hashtable<Integer, String> fa3ola = new Hashtable<Integer, String>();
	public static Hashtable<Integer, String> fa3ila = new Hashtable<Integer, String>();
	
	public static Hashtable<Integer, String> fa33ala = new Hashtable<Integer, String>();
	
	public static Hashtable<String, Hashtable<Integer, String>> conjugations = new Hashtable<String, Hashtable<Integer, String>>();
	public static Hashtable<String, String> POS = new Hashtable<String, String>();

	//define the base conjunction and add them to hashtable
    public ArabicConjugation()
    {
    	fa3ala.put(1, ArabicDiacritics.fatha);
    	fa3ala.put(3, ArabicDiacritics.fatha);
    	fa3ala.put(5, ArabicDiacritics.fatha);
    	
    	fa3ola.put(1, ArabicDiacritics.fatha);
    	fa3ola.put(3, ArabicDiacritics.damma);
    	fa3ola.put(5, ArabicDiacritics.fatha);
    	
    	fa3ila.put(1, ArabicDiacritics.fatha);
    	fa3ila.put(3, ArabicDiacritics.kasra);
    	fa3ila.put(5, ArabicDiacritics.fatha);
    	
    	fa33ala.put(1, ArabicDiacritics.fatha);
    	fa33ala.put(3, ArabicDiacritics.shaddah);
    	fa33ala.put(4, ArabicDiacritics.fatha);
    	fa33ala.put(6, ArabicDiacritics.fatha);
    	
    	conjugations.put("fa3ala", fa3ala);
    	conjugations.put("fa3ola", fa3ola);
    	conjugations.put("fa3ila", fa3ila);
    	conjugations.put("fa33ala", fa33ala);
    	
    	loadPOS();
    }

    //load pos based on conjunction
    private void loadPOS()
    {
    	POS.put("fa3ala", "›⁄· „«÷Ì „Ã—œ");
    	POS.put("fa3ola", "›⁄· „«÷Ì „Ã—œ");
    	POS.put("fa3ila", "›⁄· „«÷Ì „Ã—œ");
    	POS.put("fa33ala", "›⁄· „«÷Ì „“Ìœ");
    }

    //get pos of word based on its conjunction
    public String GetPOSfromConjugator(String Conjugation)
    {
    	return POS.get(Conjugation);
    }

    //extract the conjunction of a given word
    public String GetConjugation(String word)
    {
    	String pattern = "";
    	
    	pattern = FetchConjugationList(word);
    	
    	return pattern;
    }
    
    //fetch the conjunction of the word and return if exist
    private String FetchConjugationList(String word)
    {
    	String Conjugation = "";
		Object[] conjugationList = (Object[]) conjugations.keySet().toArray();
    	
    	for(int i=0;i<conjugationList.length;i++)
    	{
    		Conjugation = (String)conjugationList[i];
    		if(Check(conjugations.get(Conjugation),word)) 
    		{
    			break;
    		}
    	}
    	
    	return Conjugation;
    }

    //check if a word contains it's conjunction in hashtable
    private Boolean Check(Hashtable<Integer, String> ArabicForm,String word)
    {
    	int flag = 1;
    	Object[] Indexes = (Object[]) ArabicForm.keySet().toArray();
    	
    	for(int i=0;i<Indexes.length;i++)
    	{
    		Integer index = (Integer)Indexes[i];
    		String diacritics = ArabicForm.get(index);
    		if( ! word.substring(index, index+1).equals(diacritics) )
    		{
    			flag = 0;
    			break;
    		}
    	}
    	
    	if(flag == 0) return false;
    	else return true;
    	
    }
    
}
