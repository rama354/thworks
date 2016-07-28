package test;

import java.util.List;
import java.util.Map;

import main.InputRegex;
import main.MetalPriceCalculator;
import main.MetalPriceChart;
import main.NumeralTranslator;
import main.Processor;
import main.QuestionProcessor;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class MerchantGalaxyGuideTest {

	private static NumeralTranslator numTranslator;
	private static MetalPriceCalculator mpCalculator;
	private static QuestionProcessor qProcessor;

	@BeforeClass
	public void beforeClass() {
		numTranslator = new NumeralTranslator();
		mpCalculator = new MetalPriceCalculator(numTranslator);
		qProcessor = new QuestionProcessor(numTranslator,
				mpCalculator.getMpChart());
	}

	@Test(dataProvider = "galaxynumerals")
	public void testNumTranslatorProcess(String[] inpArr, InputRegex regex) {

		Map<String, String> testMap = numTranslator.getGalaxyNumerals()
				.getTranslationChart();
		for (String text : inpArr)
			numTranslator.process(text, regex);

		Assert.assertEquals(testMap.get("glob"), "I");
		Assert.assertEquals(testMap.get("prok"), "V");
		Assert.assertNull(testMap.get("pishis"));
		Assert.assertNull(testMap.get("tegj"));
	}

	@DataProvider(name = "galaxynumerals")
	public Object[][] galaxynumeralsDP() {
		return new Object[][] { new Object[] {
				new String[] { "glob is I", "prok is V", "pishis X",
						"tegj is A" }, InputRegex.GLXY_NUM }

		};
	}

	@Test(dataProvider = "validGlxy", dependsOnMethods = { "testNumTranslatorProcess" })
	public void testValidRomanTranslation(String[] gNumerals) {
		Assert.assertNotNull(numTranslator.translateToRoman(gNumerals));
	}

	@DataProvider(name = "validGlxy")
	public Object[][] validRomanDP() {
		return new Object[][] {
				new Object[] { new String[] { "glob", "prok" } },
				new Object[] { new String[] { "glob", "glob" } }
		// new Object[] { new String[]{"blob"}}
		};
	}

	@Test(dataProvider = "inValidGlxy", dependsOnMethods = { "testNumTranslatorProcess" })
	public void testInValidRomanTranslation(String[] gNumerals) {
		Assert.assertNull(numTranslator.translateToRoman(gNumerals));
	}

	@DataProvider(name = "inValidGlxy")
	public Object[][] invalidRomanDP() {
		return new Object[][] {
				// new Object[] { new String[]{"glob", "prok" }},
				new Object[] { new String[] { "prok", "pish" } },
				new Object[] { new String[] { "blob" } } };
	}

	@Test(dataProvider = "validRomanNumeral", dependsOnMethods = { "testNumTranslatorProcess" })
	public void testValidArabicTranslation(String romanNumeral, int expected) {
		Assert.assertEquals(numTranslator.translateToArabic(romanNumeral),
				expected);
	}

	@DataProvider(name = "validRomanNumeral")
	public Object[][] validRomanNumeralDP() {
		return new Object[][] { new Object[] { "II", 2 },
				new Object[] { "IV", 4 }, new Object[] { "III", 3 },
				new Object[] { "VI", 6 }, new Object[] { "VIV", 9 },
				new Object[] { "VIII", 8 },
				new Object[] { "MCMIII", 1903 }};
	}

	@Test(dataProvider = "invalidRomanNumeral", dependsOnMethods = { "testNumTranslatorProcess" })
	public void testInvalidArabicTranslation(String romanNumeral) {
		Assert.assertEquals(numTranslator.translateToArabic(romanNumeral), -1);
	}

	@DataProvider(name = "invalidRomanNumeral")
	public Object[][] invalidRomanNumeralDP() {
		return new Object[][] { new Object[] { "IL" }, new Object[] { "VV" },
				new Object[] { "IIII" }, new Object[] { "IIIVI" },
				new Object[] { "VIIII" } };
	}

	@Test(dataProvider = "validMetalprices", dependsOnMethods = { "testNumTranslatorProcess" })
	public void testValidMPCalculator(String text, String metal,
			InputRegex regex, double expected) {
		mpCalculator.process(text, regex);
		MetalPriceChart mpChart = mpCalculator.getMpChart();

		Assert.assertEquals(mpChart.getMetalPrice(metal), expected);

	}

	@DataProvider(name = "validMetalprices")
	public Object[][] validmetalPricesDP() {
		return new Object[][] {
				new Object[] { "glob prok Gold is 57800 Credits", "Gold",
						InputRegex.METAL_PRICE, 14450.0 },
				new Object[] { "glob glob Silver is 34 Credits", "Silver",
						InputRegex.METAL_PRICE, 17.0 }

		};
	}

	@Test(dataProvider = "invalidMetalprices", dependsOnMethods = { "testNumTranslatorProcess" })
	public void testInValidMPCalculator(String text, String metal,
			InputRegex regex) {
		mpCalculator.process(text, regex);
		MetalPriceChart mpChart = mpCalculator.getMpChart();

		Assert.assertNull(mpChart.getMetalPrice(metal));
	}

	@DataProvider(name = "invalidMetalprices")
	public Object[][] invalidmetalPricesDP() {
		return new Object[][] {
				new Object[] { "pish pish Iron is 3910 Credits", "Iron",
						InputRegex.METAL_PRICE },
				new Object[] { "pish pikachu Iron is 3910 Credits", "Iron",
						InputRegex.METAL_PRICE }};
	}

	@Test(dataProvider = "validQuestions", dependsOnMethods = { "testValidMPCalculator" })
	public void testValidQProcessor(String text, InputRegex regex) {
		qProcessor.process(text, regex);
		// Assert.assertNotNull(mpChart.getMetalPrice());
	}

	@DataProvider(name = "validQuestions")
	public Object[][] validQuestionsDP() {
		return new Object[][] {
				new Object[] { "how much is prok glob glob ?",
						InputRegex.HOW_MUCH },
				new Object[] { "how many Credits is glob prok Silver ?",
						InputRegex.HOW_MANY },
				new Object[] { "how many Credits is glob prok Gold ?",
						InputRegex.HOW_MANY },
				new Object[] { "how much is prok glob glob prok ?",
						InputRegex.HOW_MUCH } };
	}

	@Test(dataProvider = "invalidQuestions", dependsOnMethods = { "testValidMPCalculator" })
	public void testInValidQProcessor(String text, InputRegex regex) {
		qProcessor.process(text, regex);
		// Assert.assert(, GalaxyLogger.errorInp());
	}

	@DataProvider(name = "invalidQuestions")
	public Object[][] invalidQuestionsDP() {
		return new Object[][] {
				new Object[] { "how much is pish tegj glob glob ?",
						InputRegex.HOW_MUCH },
				new Object[] { "how much is prok glob glob glob glob ?",
						InputRegex.HOW_MUCH },
				new Object[] { "how many Credits is glob prok Iron ?",
						InputRegex.HOW_MANY } };
	}
	
	
	@Test(dataProvider = "regexmismatch", dependsOnMethods = { "testNumTranslatorProcess","testValidMPCalculator" }, expectedExceptions = IllegalStateException.class)
	public void testRgxMismatchProcessor(Processor processor,String text, InputRegex regex) {
		processor.process(text, regex);
	}
	
	/*@Test(dataProvider = "regexmismatch", dependsOnMethods = {  }, expectedExceptions = IllegalStateException.class)
	public void testRgxMismatchCalculator(String text, InputRegex regex) {
		mpCalculator.process(text, regex);
	}
*/

	@DataProvider(name = "regexmismatch")
	public Object[][] exceptionsDP() {
		return new Object[][] {
				new Object[] { mpCalculator,"pish pish Iron is 3910 Rupay",
						InputRegex.METAL_PRICE },
						
				new Object[] { mpCalculator,"pish pish Iron is 39Rupay",
								InputRegex.METAL_PRICE },
				new Object[] { qProcessor,"how many Credits is glob prokSilver ?",
						InputRegex.HOW_MANY },
				new Object[] {qProcessor,
						"how much wood could a woodchuck chuck if a woodchuck could chuck wood ?",
						InputRegex.HOW_MANY } };

	}

	@AfterMethod
	public void cleanSlate() {
		List<Integer> arabicList=numTranslator.getRomanNumerals().getSignedArabicList();
		if (arabicList!=null)
			arabicList.clear();
	}
}
