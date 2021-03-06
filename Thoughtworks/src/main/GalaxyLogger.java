package main;



public class GalaxyLogger{
	
	private static final String WHITESPACE=" ";
	private static final String CURRENCY="Credits";
	

	public static void info(String infoMsg){
		System.out.println(infoMsg);
	}

	public static void error(String errorMsg)
	{	
		System.err.println(errorMsg);
	}
	
	public static void howManyOutput(String galaxyNum,double number,String metalname){
		StringBuilder sb=new StringBuilder().append(galaxyNum)
							.append(WHITESPACE).append(metalname)
							.append(WHITESPACE).append(number)
							.append(WHITESPACE).append(CURRENCY);
		
		System.out.println(sb.toString());
	}
	
	public static void howMuchOutput(String galaxyNum,int number)
	{
		StringBuilder sb=new StringBuilder().append(galaxyNum)
				.append("is").append(WHITESPACE).append(number);
		
		
		System.out.println(sb.toString());
	}

	public static void noIdeaMsg() {
		System.out.println("I have no idea what you are talking about");
		
	}

	public static void inValidGXNumMsg(String gNum) {
		System.out.println(gNum +" is an invalid Galactic Numeral!!!!!");
		
	}

	public static void errorInp(String inp) {
		System.out.println("<"+inp+"> is Incorrect input!!!!....Proceeding to next input");
		
	}

	public static void inValidRMNumMsg(String rNum) {
		System.out.println(rNum +" is an invalid Roman Numeral!!!!!");
		
	}
	
	public static void nonCommodity(String metal) {
		System.out.println(metal +" has not be defined as a Commodity.No price AVAILABLE!!!");
		
	}
}
