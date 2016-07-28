package main;

import java.util.regex.Pattern;


public enum InputRegex {
	
	GLXY_NUM("^([a-z]+) is ([I|V|X|L|C|D|M])$"),
	METAL_PRICE("((?:[a-z]+ )+)([A-Z]\\w+) is (\\d+) Credits"),
	HOW_MANY("^how many Credits is ((?:\\w+ )+)([A-Z]\\w+) \\?$"),
	HOW_MUCH("^how much is ((?:\\w+[^0-9] )+)\\?$");
	
	private final String regex;
	private Pattern pattern;
	
	InputRegex(final String regex){
		this.regex=regex;
		pattern=Pattern.compile(regex);
	}


	@Override
    public String toString() {
        return regex;
    }

	public Pattern getPattern() {
		return pattern;
	}
	
}


