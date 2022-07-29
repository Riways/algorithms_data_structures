package algorithms;

public class HashFoo {

	static final int hash(Object key) {
		int h;
		int hashcode = key.hashCode();
		System.out.println("hashCode: " + hashcode);
		String binaryRep = addZeroes(Integer.toBinaryString(hashcode));
		System.out.println("Binary hashCode: " +binaryRep);
		int mask = hashcode >>> 16;
		String binaryRepOfMask = addZeroes(Integer.toBinaryString(mask));
		System.out.println("Binary mask:     " + binaryRepOfMask);
		String binaryRepOfHashcodeAfterXOR = addZeroes(Integer.toBinaryString(hashcode^mask ));
		System.out.println("Binary result:   " + binaryRepOfHashcodeAfterXOR);
		return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
	}

	public static void main(String[] args) {
		String str = "dddadadada";
		System.out.println("result as decimal: " + hash(str));
	}

	static String addZeroes(String binaryString) {
		String zero = "0";
		while (binaryString.length() < 32) {
			binaryString = zero.concat(binaryString);
		}
		return binaryString;
	}
}
