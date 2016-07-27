package main;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;


/*
 * This class is the entry point & diverts incoming input lines to different
 * processors based on regex matches of the input. If no regex match is found,
 * prints appropriate message.
 * @author asus
 *
 */
public class Parser {
	private Scanner sc;
	private NumeralTranslator numTranslator;
	private MetalPriceCalculator mpCalculator;
	private QuestionProcessor qProcessor;
	private Map<InputRegex,Processor> regexMap;
	
	public Parser(Scanner sc){
		this.sc=sc;
		this.numTranslator =  new NumeralTranslator();
		this.mpCalculator = new MetalPriceCalculator(numTranslator);
		this.qProcessor = new QuestionProcessor(numTranslator,mpCalculator.getMpChart());
	}
	
	//initialise input regexes to corresponding processors
	public void init(){
		regexMap=new HashMap<InputRegex,Processor>();
		regexMap.put(InputRegex.GLXY_NUM, numTranslator);
		regexMap.put(InputRegex.METAL_PRICE, mpCalculator);
		regexMap.put(InputRegex.HOW_MANY, qProcessor);
		regexMap.put(InputRegex.HOW_MUCH, qProcessor);
	}
	
	public void parse()
	{
		init();
		while(sc.hasNext()){
			String input=sc.nextLine();
			if (input.length()>0)
			{
				boolean matchFound=false;
				for (InputRegex regex:InputRegex.values())
				{
					Matcher matcher=regex.getPattern().matcher(input);
					if (matcher.matches()){
						matchFound=true;
						regexMap.get(regex).process(input,regex);
						break;
					}		
				}
				if (!matchFound)
					GalaxyLogger.noIdeaMsg();
			}	
		}
	
		sc.close();
		
	}
	
}
