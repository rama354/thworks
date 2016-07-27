package main;

import java.util.HashMap;
import java.util.Map;

/*
 * This entity stores the unit prices of different metals based on input
 * @author asus
 *
 */
public class MetalPriceChart {
	
	private Map<String , Double> priceMap;
	
	public MetalPriceChart(){
		priceMap = new HashMap<String, Double>();
	}

	public void addMetalPrice(String metal, double value)
	{
		priceMap.put(metal, value);
	}
	
	public double getMetalPrice(String metal)
	{
		return priceMap.get(metal);
	}
}
