package algorithms.yandex;

import java.util.Set;
import java.util.TreeSet;

public class Brackets {

	private static String[] getAllRearrangements(String str) {
		String[] result = null;
		if (str.length() == 1) {
			result = new String[1];
			result[0] = str;
			return result;
		}
		String currChar = str.substring(0, 1);
		String[] prev = getAllRearrangements(str.substring(1));
		result = generateNextArray(currChar, prev);
		return result;
	}

	private static String[] generateNextArray(String currCharacterAsString, String[] prevArr) {
		char currCharacter = currCharacterAsString.charAt(0);
		int size = factorial(prevArr[0].length() + 1);
		String[] result = new String[size];
		int pointer = 0;
		for (int i = 0; i < prevArr.length; i++) {
			StringBuilder builder = new StringBuilder(prevArr[i]);
			for (int j = 0; j <= builder.length(); j++) {
				String temp = builder.insert(j, currCharacter).toString();
				result[pointer] = temp;
				builder.deleteCharAt(j);
				pointer++;
			}
		}
		return result;
	}

	private static boolean validation(String str) {
		char open = '(';
		int observer = 0;
		char[] chars = str.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			if (observer < 0)
				return false;
			if (chars[i] == open)
				observer++;
			else
				observer--;
		}
		if (observer != 0)
			return false;
		return true;
	}

	private static int factorial(int n) {

		int result = 1;
		for (int i = n; i > 1; i--) {
			result *= i;
		}
		return result;
	}

	public static void main(String[] args) {
		String[] strings = getAllRearrangements("((((()))))");
		Set<String> result = new TreeSet<String>();
		for(String str : strings) {
			if(validation(str)) {
				if(!result.contains(str)) {
					result.add(str);
					System.out.println(str);
				}
			}
		}
		System.out.println(result.size());
	}

}
