package com.jk;

public class BF {
	public static int indexOf(String text, String pattern) {
		if (text == null || pattern == null) return -1;
		char[] textChars = text.toCharArray();
		if (textChars.length == 0) return -1;
		char[] patternChars = pattern.toCharArray();
		if (patternChars.length == 0) return -1;
		if (textChars.length < pattern.length()) return - 1;
		
		for (int textIndex = 0; textIndex <= textChars.length - patternChars.length; textIndex++) {
			int patternIndex = 0;
			for (; patternIndex < patternChars.length; patternIndex++) {
				if (textChars[textIndex + patternIndex] != patternChars[patternIndex]) break;
			}
			if (patternIndex == patternChars.length) return textIndex;
		}
		return -1;
	} 
}
