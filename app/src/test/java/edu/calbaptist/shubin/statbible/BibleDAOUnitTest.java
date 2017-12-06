package edu.calbaptist.shubin.statbible;

import edu.calbaptist.shubin.statbible.tools.BibleDAO;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit testing for the BibleDAO.
 */

public class BibleDAOUnitTest {

    private static final String INVALID_REFERENCE_MESSAGE = "INVALID REFERENCE";



    @Test
    public void basicTestOf_BibleDAO_getVerse() throws Exception {
        // Arrange
        BibleDAO mark = new BibleDAO("Mark", null);
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
        assertEquals("FAILED on basic getVerse test (reference).",
                expected_reference, actual_reference);
        assertEquals("FAILED on basic getVerse test (passage).",
                expected_passage, actual_passage);
    }

    @Test
    public void testInvalidVerseIn_BibleDAO_getVerse() throws Exception {
        // Arrange
        BibleDAO james = new BibleDAO("James", null);
        final int chapter = 2;
        final int verse = 93;
        final String expected_reference = "James 2:93";
        final String expected_passage = INVALID_REFERENCE_MESSAGE;

        // Act
        final String[] actual = james.getVerse(chapter, verse);

        // Assert
        assertEquals("FAILED on test of invalid verse in getVerse (reference).",
                expected_reference, actual[0]);
        assertEquals("FAILED on test of invalid verse in getVerse (passage).",
                expected_passage, actual[1]);
    }

    @Test
    public void testInvalidChapterIn_BibleDAO_getVerse() throws Exception {
        // Arrange
        BibleDAO james = new BibleDAO("James", null);
        final int chapter = 9;
        final int verse = 1;
        final String expected_reference = "James 9:1";
        final String expected_passage = INVALID_REFERENCE_MESSAGE;

        // Act
        final String[] actual = james.getVerse(chapter, verse);

        // Assert
        assertEquals("FAILED on test of invalid chapter in getVerse (reference).",
                expected_reference, actual[0]);
        assertEquals("FAILED on test of invalid chapter in getVerse (passage).",
                expected_passage, actual[1]);
    }

    @Test
    public void testInvalidBookUsing_BibleDAO_getBookName() throws Exception {
        // Arrange
        BibleDAO notJames = new BibleDAO("james", null);
        final String defaultBook = "Mark";  // expected

        // Act
        final String actualBook = notJames.getBookName();

        // Assert
        assertEquals("FAILED on test of invalid book using getBookName.", defaultBook, actualBook);
    }

    @Test
    public void testValidBookUsing_BibleDAO_getBookName() throws Exception {
        // Arrange
        BibleDAO james = new BibleDAO("James", null);
        final String expected = "James";  // expected

        // Act
        final String actual = james.getBookName();

        // Assert
        assertEquals("FAILED on test of valid book using getBookName.", expected, actual);
    }

    @Test
    public void basicTestOf_BibleDAO_getRange() throws Exception {
        // Arrange
        BibleDAO mark = new BibleDAO("mark", null);
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
        assertEquals("FAILED on basic getRange test (reference).",
                expected_reference, actual_reference);
        assertEquals("FAILED on basic getRange test (passage).",
                expected_passage, actual_passage);
    }

    @Test
    public void testInvalidStartVerseIn_BibleDAO_getRange() throws Exception {
        // Arrange
        BibleDAO james = new BibleDAO("James", null);
        final int startChapter = 2;
        final int startVerse = 93;
        final int endChapter = 3;
        final int endVerse = 3;
        final String expected_reference = "James 2:93 - 3:3";
        final String expected_passage = INVALID_REFERENCE_MESSAGE;

        // Act
        final String[] actual = james.getRange(startChapter, startVerse, endChapter, endVerse);

        // Assert
        assertEquals("FAILED on test of invalid start verse in getRange (reference).",
                expected_reference, actual[0]);
        assertEquals("FAILED on test of invalid start verse in getRange (passage).",
                expected_passage, actual[1]);
    }

    @Test
    public void testInvalidEndVerseIn_BibleDAO_getRange() throws Exception {
        // Arrange
        BibleDAO james = new BibleDAO("James", null);
        final int startChapter = 2;
        final int startVerse = 2;
        final int endChapter = 3;
        final int endVerse = 99;
        final String expected_reference = "James 2:2 - 3:99";
        final String expected_passage = INVALID_REFERENCE_MESSAGE;

        // Act
        final String[] actual = james.getRange(startChapter, startVerse, endChapter, endVerse);

        // Assert
        assertEquals("FAILED on test of invalid end verse in getRange (reference).",
                expected_reference, actual[0]);
        assertEquals("FAILED on test of invalid end verse in getRange (passage).",
                expected_passage, actual[1]);
    }

    @Test
    public void testInvalidStartChapterIn_BibleDAO_getRange() throws Exception {
        // Arrange
        BibleDAO james = new BibleDAO("James", null);
        final int startChapter = 0;
        final int startVerse = 2;
        final int endChapter = 3;
        final int endVerse = 5;
        final String expected_reference = "James 0:2 - 3:5";
        final String expected_passage = INVALID_REFERENCE_MESSAGE;

        // Act
        final String[] actual = james.getRange(startChapter, startVerse, endChapter, endVerse);

        // Assert
        assertEquals("FAILED on test of invalid start chapter in getRange (reference).",
                expected_reference, actual[0]);
        assertEquals("FAILED on test of invalid start chapter in getRange (passage).",
                expected_passage, actual[1]);
    }

    @Test
    public void testInvalidEndChapterIn_BibleDAO_getRange() throws Exception {
        // Arrange
        BibleDAO james = new BibleDAO("James", null);
        final int startChapter = 2;
        final int startVerse = 2;
        final int endChapter = 40;
        final int endVerse = 5;
        final String expected_reference = "James 2:2 - 40:5";
        final String expected_passage = INVALID_REFERENCE_MESSAGE;

        // Act
        final String[] actual = james.getRange(startChapter, startVerse, endChapter, endVerse);

        // Assert
        assertEquals("FAILED on test of invalid end chapter in getRange (reference).",
                expected_reference, actual[0]);
        assertEquals("FAILED on test of invalid end chapter in getRange (passage).",
                expected_passage, actual[1]);
    }

    @Test
    public void testChaptersMisorderedIn_BibleDAO_getRange() throws Exception {
        // Arrange
        BibleDAO james = new BibleDAO("James", null);
        final int startChapter = 2;
        final int startVerse = 2;
        final int endChapter = 1;
        final int endVerse = 5;
        final String expected_reference = "James 2:2 - 1:5";
        final String expected_passage = INVALID_REFERENCE_MESSAGE;

        // Act
        final String[] actual = james.getRange(startChapter, startVerse, endChapter, endVerse);

        // Assert
        assertEquals("FAILED on test of chapters misordered in getRange (reference).",
                expected_reference, actual[0]);
        assertEquals("FAILED on test of chapters misordered in getRange (passage).",
                expected_passage, actual[1]);
    }

    @Test
    public void testVersesMisorderedIn_BibleDAO_getRange() throws Exception {
        // Arrange
        BibleDAO james = new BibleDAO("James", null);
        final int startChapter = 2;
        final int startVerse = 2;
        final int endChapter = 2;
        final int endVerse = 1;
        final String expected_reference = "James 2:2-1";
        final String expected_passage = INVALID_REFERENCE_MESSAGE;

        // Act
        final String[] actual = james.getRange(startChapter, startVerse, endChapter, endVerse);

        // Assert
        assertEquals("FAILED on test of verses misordered in getRange (reference).",
                expected_reference, actual[0]);
        assertEquals("FAILED on test of verses misordered in getRange (passage).",
                expected_passage, actual[1]);
    }

    @Test
    public void testVerseCountingIn_BibleDAO_getVerseCount() throws Exception {
        // Arrange
        BibleDAO psalms = new BibleDAO("Psalms", null);
        final int shortChapter = 117;
        final int shortVerseCount = 2;
        final int midChapter = 118;
        final int midVerseCount = 29;
        final int longChapter = 119;
        final int longVerseCount = 176;

        // Act
        final int shortCount = psalms.getVerseCount(shortChapter);
        final int midCount = psalms.getVerseCount(midChapter);
        final int longCount = psalms.getVerseCount(longChapter);

        // Assert
        assertEquals("FAILED on short verse count.", shortVerseCount, shortCount);
        assertEquals("FAILED on mid verse count.", midVerseCount, midCount);
        assertEquals("FAILED on long verse count.", longVerseCount, longCount);
    }

    @Test
    public void testChapterCounterIn_BibleDAO_getChapterCount() throws Exception {
        // Arrange
        BibleDAO philemon = new BibleDAO("Philemon", null);
        BibleDAO psalms = new BibleDAO("Psalms", null);
        final int philemonLength = 1;
        final int psalmsLength = 150;

        // Act
        final int philemonCount = philemon.getChapterCount();
        final int psalmsCount = psalms.getChapterCount();

        // Assert
        assertEquals("FAILED on counting Philemon's chapters.", philemonLength, philemonCount);
        assertEquals("FAILED on counting the Psalms.", psalmsLength, psalmsCount);
    }
}
