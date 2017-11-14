package com.example.andrew.statbible.tools;

/**
 * Created by andrew on 11/10/17.
 */

public class FrequencyTrie {

    private final static char ROOT = '^';

    private FTNode root;

    private FrequencyTrie() {
        // Should not be used.
    }

    public FrequencyTrie(String source) {
        root = new FTNode();
        for (String word : source.toLowerCase().split(" ")) {
            this.add(word);
        }
    }

    public int countWord(String word) {
        // Clean up the word...
        String cleanWord = "";
        for (char c : word.toLowerCase().toCharArray()) {
            if (Character.isLetter(c)) {
                cleanWord += c;
            }
        }

        // Find the correct node...
        int count = 0;
        int index = 0;
        String partial = "";
        FTNode current = root;
        while (current != null) {
            if (current != root) {
                partial += current.getLetter();
            }
            if (cleanWord == partial) {
                count = current.getCount();
                break;
            }
            current = current.find(cleanWord.charAt(index));
        }
        return count;
    }

    public int[] countParts(String word) {
        int[] counts = new int[word.length()];
        // TODO
        return counts;
    }

    private void add(String word) {
        char[] letters = word.toCharArray();
        FTNode current = root;
        for (char letter : letters) {
            current = current.add(letter);
        }
    }

    // =========== FOR USE WITH UNIT TESTS ===========

    public void TEST_OUT(String word) {
        //p("Testing for '" + word + "'");
        FTNode current = root;
        p("Level 2 (t)");
        current = current.child;
        while (current.getLetter() != 't') {
            current = current.next;
        }
        current = current.child;
        while (current != null) {
            System.out.println(current);
            current = current.next;
        }
    }

    private void p(String message) {
        System.out.println(message);
    }

    // ======== END TEST CODE =======================

    private class FTNode {

        private char letter;
        private int count;

        private FTNode child;
        private FTNode next;

        public FTNode() {
            letter = ROOT;
            count = 0;
        }

        public FTNode(char c) {
            letter = c;
            count = 1;
        }

        /*
         * Method adds character c as a child of this node if a child
         * node does not already exist for character c. If the later is
         * the case, the existing node has its count incremented.
         *
         * Method returns the new (or incremented) node.
         */
        public FTNode add(char c) {
            if (!Character.isLetter(c)) return this; // i.e. nothing happens.
            if (child == null) {
                child = new FTNode(c);
                return child;
            }
            FTNode result = this.search(c);
            if (result == null) {
                result = new FTNode(c);
                // Now insert it into the Trie...
                result.next = child;
                child = result;
            } else {
                result.count++;
            }
            return result;
        }

        /*
         * Searches immediate children for node with character c.
         *
         * Returns node if found, returns null otherwise.
         */
        public FTNode find(char c) {
            if (child == null) return null;
            return this.search(c);
        }

        public int getCount() {
            return count;
        }

        public char getLetter() {
            return letter;
        }

        /*
         * Searches children for 'letter'.
         *
         * Returns node of letter if found, otherwise null.
         */
        private FTNode search(char letter) {
            FTNode current = this.child;
            while (current != null) {
                if (current.getLetter() == letter) {
                    break;
                }
                current = current.next;
            }
            return current;
        }

        @Override
        public String toString() {
            return "[" + letter + "," + count + "]";
        }
    }
}
