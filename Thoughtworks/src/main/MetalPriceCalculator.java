package main;

import java.util.regex.Matcher;


/*
 * This class does the calculation of a single metal unit based on the input
 * @author asus
 *
 */
public class MetalPriceCalculator implements Processor {

	private NumeralTranslator numTranslator;
	private MetalPriceChart mpChart;
	
	public MetalPriceCalculator(NumeralTranslator numTranslator){
		mpChart=new MetalPriceChart();
		this.numTranslator=numTranslator;
	}
	
	@Override
	public void process(String text,InputRegex regexMetalPrice) {
		
		Matcher m = regexMetalPrice.getPattern().matcher(text);
		m.matches();
		
		String [] galacticNumbers = m.group(1).split("\\s");
		String metalName = m.group(2);
		int credits = Integer.parseInt(m.group(3));
		
		String romanNumerals = numTranslator.translateToRoman(galacticNumbers);
		
		if (romanNumerals==null)
		{
			GalaxyLogger.errorInp();
			return;
		}
		
		double divisor = numTranslator.translateToArabic(romanNumerals);
		
		double unitValue = credits/divisor;
		
		mpChart.addMetalPrice(metalName, unitValue);
		
	}

	public NumeralTranslator getNumTranslator() {
		return numTranslator;
	}

	public MetalPriceChart getMpChart() {
		return mpChart;
	}

	
	
}
