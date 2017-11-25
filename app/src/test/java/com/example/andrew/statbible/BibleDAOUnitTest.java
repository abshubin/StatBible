package com.example.andrew.statbible;

import com.example.andrew.statbible.tools.BibleDAO;
import com.example.andrew.statbible.tools.FrequencyTrie;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit testing for the BibleDAO.
 */
public class BibleDAOUnitTest {

    @Test
    public void basicTestOf_BibleDAO_getVerse() throws Exception {
        // Arrange
        BibleDAO mark = new BibleDAO("mark");
        final int chapter = 2;
        final int verse = 17;
        final String expected_reference = "Mark 2:17";
        final String expected_passage = "When Jesus heard it, he saith unto them," +
                " They that are whole have no need of the physician, but they" +
                " that are sick: I came not to call the righteous, but sinners" +
                " to repentance.";

        // Act
        final String[] actual = mark.getVerse(2, 17);
        final String actual_reference = actual[0];
        final String actual_passage = actual[1];

        // Assert
        assertEquals("FAILED on basic getVerse test (reference).", expected_reference, actual_reference);
        assertEquals("FAILED on basic getVerse test (passage).", expected_passage, actual_passage);
    }

    @Test
    public void basicTestOf_BibleDAO_getRange() throws Exception {
        // Arrange
        BibleDAO mark = new BibleDAO("mark");
        final int startChapter = 3;
        final int endChapter = 4;
        final int startVerse = 33;
        final int endVerse = 2;
        final String expected_reference = "Mark 3:33 - 4:2";
        final String expected_passage = "And he answered them, saying, Who is my mother, or my" +
                " brethren? And he looked round about on them which sat about him, and" +
                " said, Behold my mother and my brethren! For whosoever shall do the" +
                " will of God, the same is my brother, and my sister, and mother. And" +
                " he began again to teach by the sea side: and there was gathered unto" +
                " him a great multitude, so that he entered into a ship, and sat in the" +
                " sea; and the whole multitude was by the sea on the land. And he" +
                " taught them many things by parables, and said unto them in his doctrine,";

        // Act
        final String[] actual = mark.getRange(startChapter, startVerse, endChapter, endVerse);
        final String actual_reference = actual[0];
        final String actual_passage = actual[1];

        // Assert
        assertEquals("FAILED on basic getRange test (reference).", expected_reference, actual_reference);
        assertEquals("FAILED on basic getRange test (passage).", expected_passage, actual_passage);
    }
}
