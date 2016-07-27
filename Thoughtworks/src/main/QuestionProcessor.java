package main;

import java.util.regex.Matcher;


/*
 * This class has two methods
 * a) processHowMuch() processes the 'how much is......' question & generates the o/p
 * b) processHowMany() process the 'how many Credits is...' question & generates o/p
 * @author asus
 *
 */
public class QuestionProcessor implements Processor {
	
	private NumeralTranslator numTranslator;	
	private MetalPriceChart mpChart;
	
	
	public QuestionProcessor(NumeralTranslator numTranslator,MetalPriceChart mpChart){
		this.numTranslator=numTranslator;
		this.mpChart=mpChart;
	}
	
	@Override
	public void process(String text,InputRegex regexType) {
		 
		if(regexType == InputRegex.HOW_MANY)
			processHowMany(text,regexType);

		if(regexType == InputRegex.HOW_MUCH)
			processHowMuch(text,regexType);
		
	}

	
	
	/**
	 * processes the question 'how much is.....' e.g. how much is pish tegj glob glob ?
	 * @param text
	 * @param regexHowMuch
	 */
	private void processHowMuch(String text,InputRegex regexHowMuch)
	{
		Matcher m = regexHowMuch.getPattern().matcher(text);

		m.matches();
		String [] galaxyNumbers = m.group(1).split("\\s");
		
		String romanNumerals = numTranslator.translateToRoman(galaxyNumbers);
		if(romanNumerals==null)
		{
			GalaxyLogger.errorInp();
			return;
		}
		
		int number = numTranslator.translateToArabic(romanNumerals);
		
		 if(number != -1 )
			GalaxyLogger.howMuchOutput(m.group(1), number);	 
		
		 //return
	}
	
	/**
	 * process question "how many ([a-zA-Z]\\w+) is ((?:\\w+ )+)([A-Z]\\w+) \\?$"
	 * e.g. how many Credits is glob prok Silver ?;
	 * @param line
	 * @return
	 */
	private void processHowMany(String text,InputRegex regexHowMany)
	{
		
		Matcher m = regexHowMany.getPattern().matcher(text);
		m.matches();
		
		String [] galaxyNumbers = m.group(2).split("\\s");
		String metalName = m.group(3);
		
		String romanNumerals = numTranslator.translateToRoman(galaxyNumbers);
		
		if(romanNumerals==null)
		{
			GalaxyLogger.error("Question contains unknown Galaxy number.Moving to next question!!!!!");
			return;
		}
		
		int metalUnits  = numTranslator.translateToArabic(romanNumerals);
		
		if(metalUnits != -1)
		{
			double totCredits = metalUnits * mpChart.getMetalPrice(metalName); 	
			GalaxyLogger.howManyOutput(m.group(2), totCredits, metalName);
			
		}
		
		//return
	}
}
