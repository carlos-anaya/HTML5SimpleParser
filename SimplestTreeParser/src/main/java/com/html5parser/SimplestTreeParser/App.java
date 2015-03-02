package com.html5parser.SimplestTreeParser;

import java.util.ArrayList;
import java.util.List;

import javax.management.RuntimeErrorException;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		System.out.println("Hello World!");
	}

	public static final int add(String numbers) {
		int result = 0;
		String separator = "";
		String[] numbersArray;

		if (numbers.startsWith("//")) {
			separator = numbers.split("n")[0];
			numbers = numbers.replace(separator, "");
			separator = separator.substring(2);
		} else
			separator = ",";
		numbersArray = numbers.split(separator + "|n");
		// if (numbersArray.length > 2) {
		// throw new RuntimeException(
		// "Up to 2 numbers separated by comma (,) are allowed");
		// } else {
		List<Integer> negativeNumbers = new ArrayList<Integer>();
		for (String number : numbersArray) {
			if (!number.isEmpty()) {
				int val = Integer.parseInt(number); // If it is not a number,
													// parseInt will throw an
													// exception
				if (val < 0)
					negativeNumbers.add(val);
				
				if (val > 1000)
					continue;
				
				result += Integer.parseInt(number);
			}
		}
		// }
		if (negativeNumbers.size() > 0)
			throw new RuntimeErrorException(null,
					"Negative numbers not allowed: "
							+ negativeNumbers.toString());
		return result;
	}
}
