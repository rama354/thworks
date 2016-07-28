package main;

import java.util.List;
import java.util.regex.Matcher;


/*
 * This class handles the translation of galaxy numbers to roman
 * and roman to arabic
 * @author asus
 *
 */
public class NumeralTranslator implements Processor {

	private GalaxyNumerals galaxyNumerals;
	private RomanNumerals romanNumerals;
	
	public NumeralTranslator(){
		galaxyNumerals=new GalaxyNumerals();
		romanNumerals=new RomanNumerals();
	}
	
	@Override
	public void process(String text,InputRegex regexNum) {
		Matcher m = regexNum.getPattern().matcher(text);
		if (m.matches())
			galaxyNumerals.addNumeral(m.group(1),m.group(2));
		
	}

	public String translateToRoman(String [] gNumerals)
	{
		StringBuilder romanNumeral = new StringBuilder(gNumerals.length);
		
		for(String gNum : gNumerals)
		{
			if (!galaxyNumerals.isValid(gNum))
			{
				GalaxyLogger.inValidGXNumMsg(gNum);
				return null;
			}
			romanNumeral.append(galaxyNumerals.getRomanValue(gNum));
		}
		
		return romanNumeral.toString();
		
		
	}


	public int translateToArabic(String romanNumeral)
	{
		
		if(romanNumerals.isValid(romanNumeral))
		{
			int actualArabicNumber=0;
			List<Integer> arabicList=romanNumerals.getSignedArabicList();
			if(arabicList.size() > 0)
				for(int i : arabicList)
					actualArabicNumber += i;
				
			return actualArabicNumber;
		}
		GalaxyLogger.inValidRMNumMsg(romanNumeral);
		return -1;
	}

	public GalaxyNumerals getGalaxyNumerals() {
		return galaxyNumerals;
	}

	public RomanNumerals getRomanNumerals() {
		return romanNumerals;
	}
	
	
	
}
