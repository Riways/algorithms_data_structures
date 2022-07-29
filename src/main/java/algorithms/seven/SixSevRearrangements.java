package algorithms.seven;

/*
 * разработайте алгоритм для вывода всех возможных перестановок символов в строке (для
простоты считайте, что все символы встречаются только один раз).
 */
public class SixSevRearrangements {
	
	private static String[] getAllRearrangements(String str) {
		String[] result = null;
		if(str.length() == 1) {
			result = new String[1];
			result[0] = str;
			return result;
		}
		String currChar = str.substring(0,1);
		String[] prev = getAllRearrangements(str.substring(1));
		result = generateNextArray(currChar, prev);
		return result;
	}
	
	private  static String[] generateNextArray(String currCharacterAsString, String[] prevArr) {
		
		char currCharacter = currCharacterAsString.charAt(0);
		int size = factorial(prevArr[0].length()+1);
		String[] result = new String[size];
		int pointer = 0;
		for(int i = 0; i < prevArr.length; i++) {
			StringBuilder builder = new StringBuilder(prevArr[i]);
			for(int j = 0; j <= builder.length(); j++) {
				String temp = builder.insert(j, currCharacter).toString();
				result[pointer] = temp;
//				System.out.print(currCharacterAsString +  " : ");
//				System.out.println(temp);
				builder.deleteCharAt(j);
				pointer++;
			}
		}
		return result;
	}
	
	private static int factorial(int n) {
		
		int result = 1;
		for(int i = n; i>1; i--) {
			result *= i;
		}
		System.out.println(n + " factorial: " + result);
		return result;
	}
	
	public static void main(String[] args) {
		String[] strings = getAllRearrangements("abcdefd");
		System.out.println(strings.length);
	}
	
}
