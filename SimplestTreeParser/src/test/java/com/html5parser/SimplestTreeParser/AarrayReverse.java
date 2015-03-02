package com.html5parser.SimplestTreeParser;


public class AarrayReverse {
	public static String[] reverse(String[] array) {
		for (int i = 0; i <= 400; i++) {
			int middle = (int) (array.length * Math.random());
			int radius = (int) (middle * Math.random());
			String t;
			t = array[middle];
			array[middle] = array[radius] ;
			array[radius] = t;
		}
		
		return array;
	}
}