package com.html5parser.SimplestTreeParser;

import org.junit.Assert;
import org.junit.Test;

public class AppTest {

	/*
	 * Requirement 1: The method can take 0, 1 or 2 numbers separated by comma
	 * (,).
	 */

	// This was removed in order to allow more than 2 numbers
	// @Test(expected = RuntimeException.class)
	// public final void whenMoreThan2NumbersAreUsedThenExceptionIsThrown() {
	// App.add("1,2,3");
	// }
	@Test
	public final void when2NumbersAreUsedThenNoExceptionIsThrown() {
		App.add("1,2");
		Assert.assertTrue(true);
	}

	@Test(expected = RuntimeException.class)
	public final void whenNonNumberIsUsedThenExceptionIsThrown() {
		App.add("1,X");
	}

	/*
	 * Requirement 2: For an empty string the method will return 0
	 */
	@Test
	public final void whenEmptyStringIsUsedThenReturnValueIs0() {
		Assert.assertEquals(0, App.add(""));
	}

	/*
	 * Requirement 3: Method will return their sum of numbers
	 */

	@Test
	public final void whenOneNumberIsUsedThenReturnValueIsThatSameNumber() {
		Assert.assertEquals(3, App.add("3"));
	}

	@Test
	public final void whenTwoNumbersAreUsedThenReturnValueIsTheirSum() {
		Assert.assertEquals(3 + 6, App.add("3,6"));
	}

	/*
	 * Requirement 4: Allow the Add method to handle an unknown amount of
	 * numbers
	 */
	@Test
	public final void whenManyNumbersAreUsedThenReturnValueIsTheirSum() {
		Assert.assertEquals(3 + 6 + 9 + 1 + 7, App.add("3,6,9,1,7"));
	}

	/*
	 * Requirement 5: Allow the Add method to handle new lines between numbers
	 * (instead of commas).
	 */
	@Test
	public final void whenNewLineIsUsedBetweenNumbersThenReturnValuesAreTheirSums() {
		Assert.assertEquals(3 + 6 + 15, App.add("3,6n15"));
	}

	/*
	 * Requirement 6: Support different delimiters
	 * 
	 * To change a delimiter, the beginning of the string will contain a
	 * separate line that looks like this: “//[delimiter]\n[numbers…]” for
	 * example “//;\n1;2″ should take 1 and 2 as parameters and return 3 where
	 * the default delimiter is ‘;’ .
	 */
	@Test
	public final void whenDelimiterIsSpecifiedThenItIsUsedToSeparateNumbers() {
		Assert.assertEquals(3 + 6 + 15, App.add("//;n3;6;15"));
	}

	/*
	 * Requirement 7: Negative numbers will throw an exception
	 * 
	 * Calling Add with a negative number will throw an exception “negatives not
	 * allowed” – and the negative that was passed. If there are multiple
	 * negatives, show all of them in the exception message.
	 */
	@Test(expected = RuntimeException.class)
	public final void whenNegativeNumberIsUsedThenRuntimeExceptionIsThrown() {
		App.add("3,-6,15,18,46,33");
	}

	@Test
	public final void whenNegativeNumbersAreUsedThenRuntimeExceptionIsThrown() {
		RuntimeException exception = null;
		try {
			App.add("3,-6,15,-18,46,33");
		} catch (RuntimeException e) {
			exception = e;
		}
		Assert.assertNotNull(exception);
		Assert.assertEquals("Negative numbers not allowed: [-6, -18]",
				exception.getMessage());
	}

	/*
	 * Requirement 8: Numbers bigger than 1000 should be ignored
	 */
	@Test
	public final void whenNumberGreaterThan1000IsUsedThenItIsIgnored() {
		Assert.assertEquals(3 + 6, App.add("//;n3;6;1500"));
	}

	/*
	 * Requirement 9: Delimiters can be of any length
	 * 
	 * Following format should be used: “//[delimiter]\n”. Example:
	 * “//[—]\n1—2—3″ should return 6
	 */
	@Test
	public final void whenDelimiterOfMoreThanOneCharacterIsSpecifiedThenItIsUsedToSeparateNumbers() {
		Assert.assertEquals(3 + 6 + 15, App.add("//---n3---6---15"));
	}
	
	/*
	 * Requirement 10: Allow multiple delimiters
	 * 
	 * Following format should be used: “//[delim1][delim2]\n”. Example
	 * “//[-][%]\n1-2%3″ should return 6.
	 */
	
	/*
	 * Requirement 11: Make sure you can also handle multiple delimiters with length longer than one char
	 */
	
}
