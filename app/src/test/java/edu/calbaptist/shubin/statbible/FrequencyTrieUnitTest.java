package edu.calbaptist.shubin.statbible;

import edu.calbaptist.shubin.statbible.tools.FrequencyTrie;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit testing for the FrequencyTrie, to make sure it constructs
 * and navigates the trie correctly.
 */
public class FrequencyTrieUnitTest {

    @Test
    public void countWord_isCorrect() throws Exception {
        // Arrange
        String text1 = "This is some text that is a thing.";
        String text2 = "This is this and that is that this.";
        String[] stopwords = {""};
        FrequencyTrie trie1 = new FrequencyTrie(text1, stopwords);
        FrequencyTrie trie2 = new FrequencyTrie(text2, stopwords);
        final int expected1_is = 2;
        final int expected1_this = 1;
        final int expected1_thing = 1;
        final int expected2_this = 3;
        final int expected2_that = 2;
        final int expected2_th = 5;

        // Act
        final int actual1_is = trie1.countWord("is");
        final int actual1_this = trie1.countWord("this");
        final int actual1_thing = trie1.countWord("thing");
        final int actual2_this = trie2.countWord("this");
        final int actual2_that = trie2.countWord("that");
        final int actual2_th = trie2.countWord("th");

        // Assert
        assertEquals("FAILED for 'is' in trie1.", expected1_is, actual1_is);
        assertEquals("FAILED for 'this' in trie1.", expected1_this, actual1_this);
        assertEquals("FAILED for 'thing' in trie1.", expected1_thing, actual1_thing);
        assertEquals("FAILED for 'this' in trie2.", expected2_this, actual2_this);
        assertEquals("FAILED for 'that' in trie2.", expected2_that, actual2_that);
        assertEquals("FAILED for 'th' in trie2.", expected2_th, actual2_th);
    }

    @Test
    public void countParts_isCorrect() throws Exception {
        // Arrange
        String text = "This is some text that is a thing.";
        String[] stopwords = {""};
        FrequencyTrie trie = new FrequencyTrie(text, stopwords);
        final String search = "this";
        final int[] expected = {4, 3, 2, 1};

        // Act
        final int[] actual = trie.countParts(search);

        // Assert
        assertEquals("FAILED, arrays are not same length.", expected.length, actual.length);
        assertEquals("FAILED, first index different.", expected[0], actual[0]);
        assertEquals("FAILED, second index different.", expected[1], actual[1]);
        assertEquals("FAILED, third index different.", expected[2], actual[2]);
        assertEquals("FAILED, fourth index different.", expected[3], actual[3]);
    }

    @Test
    public void stopwords_worksCorrectly() throws Exception {
        // Arrange
        String text = "This is some text";
        String[] stopwords = {"is"};
        final int expected = 0;
        FrequencyTrie trie = new FrequencyTrie(text, stopwords);

        // Act
        final int actual = trie.score("is");

        // Assert
        assertEquals("FAILED, stopword not accounted for in score.", expected, actual);
    }
}
