package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * This is an entity class that stores translated(arabic) roman character.contains helper method
 * for translation of a Roman numeral to Arabic counterpart.
 * @author asus
 *
 */
public class RomanNumerals {

	private Map<Character, Integer> translation;
	private List<Integer> signedArabicList;

	/**
	 * initializes the roman-arabic translation
	 */
	public RomanNumerals() {
		translation = new HashMap<Character, Integer>();
		translation.put('I', 1);
		translation.put('V', 5);
		translation.put('X', 10);
		translation.put('L', 50);
		translation.put('C', 100);
		translation.put('D', 500);
		translation.put('M', 1000);
		signedArabicList = new ArrayList<Integer>();

	}

	public List<Integer> getSignedArabicList() {
		return signedArabicList;
	}

	public void setSignedArabicList(List<Integer> signedArabicList) {
		this.signedArabicList = signedArabicList;
	}

	/**
	 * Checks the validity of the roman numeral against the defined repetion &
	 * subtraction rules
	 * 
	 * @param romanNumeral
	 * @return
	 */
	public boolean isValid(String romanNumeral) {
		// repeation rules
		if (breaksRepeatitionRule(romanNumeral))
			return false;

		createSignedArabicList(romanNumeral);

		// The numeral fails the subtraction rules!!!
		if (signedArabicList.size() != romanNumeral.length())
			return false;

		return true;

	}

	private boolean breaksRepeatitionRule(String romanNumeral) {
		for (RepRuleRegex rr : RepRuleRegex.values()) {
			Matcher m = rr.pattern.matcher(romanNumeral);
			if (m.matches()) {
				GalaxyLogger.error("Numeral "+romanNumeral+" is breaking Repetion Rule!!!!");
				return true;
			}
		}
		return false;
	}

	/**
	 * creates a list of corresponding signed arabic number for each
	 * alphanumeric character in the roman numeral subject to subtraction rules
	 * 
	 * @param rNumeral
	 */
	private void createSignedArabicList(String rNumeral) {
		int signCount = 0;
		int arabicVal, nxtArabicVal;
		for (int i = 0; i < rNumeral.length() - 1; i++) 
		{
			arabicVal = getArabicValue(rNumeral.charAt(i));
			nxtArabicVal = getArabicValue(rNumeral.charAt(i + 1));
			
			// Only one small-value symbol may be subtracted from any large-value symbol.
			if (arabicVal < nxtArabicVal) {
				if (signCount > 0)
					return;

				arabicVal = -arabicVal;
				signCount++;
			}

			// "V", "L", and "D" can never be subtracted.
			if (arabicVal == -getArabicValue('V') || arabicVal == -getArabicValue('L') || arabicVal == -getArabicValue('D')) {
				GalaxyLogger.error("V, L, and D can never be subtracted!!!!");
				return;
			}

			if (breaksSubtractionRule(arabicVal, nxtArabicVal))
				return;
			// nxtArabicVal = Math.abs(signedArabicList.get(i+1));

			signedArabicList.add(arabicVal);
		}

		// adding the last roman character in numeral
		signedArabicList
				.add(getArabicValue(rNumeral.charAt(rNumeral.length() - 1)));
	}

	
	/**
	 * Subtraction rules for roman numeral
	 * @param arabicVal
	 * @param nxtArabicVal
	 * @return
	 */
	private boolean breaksSubtractionRule(int arabicVal, int nxtArabicVal) 
	{
		switch (arabicVal) 
		{
		case -1: // "I" can be subtracted from "V" and "X" only
			if (nxtArabicVal != getArabicValue('V')
					&& nxtArabicVal != getArabicValue('X')) {
				GalaxyLogger.error("I can be subtracted from V and X only.");
				return true;
			}
			break;

		case -10:// "X" can be subtracted from "L" and "C" only.
			if (nxtArabicVal != getArabicValue('L')
					&& nxtArabicVal != getArabicValue('C')) {
				GalaxyLogger.error("X can be subtracted from L and C only!!!!");
				return true;
			}
			break;

		case -100:// "C" can be subtracted from "D" and "M" only
			if (nxtArabicVal != getArabicValue('D')
					&& nxtArabicVal != getArabicValue('M')) {
				GalaxyLogger.error("C can be subtracted from D and M only!!!!");
				return true;
			}
			break;

		}

		return false;
	}

	
	private int getArabicValue(char roman) {
		return translation.get(roman);
	}

	/**
	 * regex for the repetion rules
	 * 
	 * @author asus
	 * 
	 */
	enum RepRuleRegex {
		RR1("\\w?(III(V?)I+)"), RR2("\\w?(XXX(L|C?)X+)"), RR3("\\w?(CCC(D|M?)C+)"), RR4("\\w?(MMMM+)"), RR5(
				"\\w?(DD+)"), RR6("\\w?(LL+)"), RR7("\\w?(VV+)");

		String regex;
		Pattern pattern;

		RepRuleRegex(String regex) {
			this.regex = regex;
			pattern = Pattern.compile(regex);
		}
	}
}
