package data_structures;

import java.util.concurrent.ThreadLocalRandom;

import org.junit.jupiter.api.Test;

import data_structures.trees.Trie;

class TrieTest {

	Trie trie;

	private void initTrie() {
		trie = new Trie();
	}

	@Test
	void testAddWord() {
//		System.out.println(Integer.toBinaryString(0~0);
		System.out.println(Integer.toBinaryString(2147483647) );
		System.out.println(Integer.toBinaryString( -1  ) );
		System.out.println(~-3 + 1);
		initTrie();
		trie.addWord("alos");

		String[] words = new String[140000];
		for (int i = 0; i < 140000; i++) {
			words[i] = randomWordGenerator();
		}

		for (int i = 0; i < 140000; i++) {
			if (!trie.isWordPresent(words[i]))
				trie.addWord(words[i]);
		}
		
		for (int i = 0; i < 140000; i++) {
			if (!trie.isWordPresent(words[i]))
				System.out.println("ERROR");
		}
	}

	private String randomWordGenerator() {
		int length = ThreadLocalRandom.current().nextInt(1, 15);
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < length; i++) {
			char charr = (char) ThreadLocalRandom.current().nextInt(97, 122);
			builder.append(charr);
		}
		return builder.toString();
	}

}
