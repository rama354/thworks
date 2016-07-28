package main;

import java.util.HashMap;
import java.util.Map;

/*
 * This entity class stores translated Galaxy numerals
 * @author asus
 *
 */
public class GalaxyNumerals {

	private Map<String, String> translation;
	
	public GalaxyNumerals(){
		translation=new HashMap<String,String>();
	}

	public void addNumeral(String galactic,String roman) {
		
		if( translation.containsKey(galactic))
			GalaxyLogger.info("Translation already available for "+galactic+"!!!!!!");
		else
			translation.put(galactic, roman);
		
	}
	
	
	public boolean isValid(String galactic)
	{
		return translation.containsKey(galactic);
			
	}
	
	public String getRomanValue(String galactic){
		return translation.get(galactic);
	}

	public Map<String, String> getTranslationChart() {
		return translation;
	}

	public void setTranslationChart(Map<String, String> translation) {
		this.translation = translation;
	}
	
	
}
