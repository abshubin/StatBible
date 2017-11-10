package com.example.andrew.statbible.tools;

/**
 * Created by andrew on 11/10/17.
 */

public class FrequencyTrie {

    private final static char ROOT = '^';

    private FTNode root;

    public FrequencyTrie(String source) {
        root = new FTNode();
        for (String word : source.toLowerCase().split(" ")) {
            this.add(word);
        }
    }

    public int countWord(String word) {
        // TODO
        return 0;
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
            FTNode result = this.search(child, c);
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
         * Method adds character c as the neighbor of this node in the same
         * manner and with the same stipulations as the add(char c) method.
         *
         * I'm not sure if I'll need this...implemented it just in case. For
         * now, I'm making it private so I don't use it by accident if I
         * don't need to.
         */
        private FTNode stack(char c) {
            if (!Character.isLetter(c)) return this; // i.e. nothing happens.
            FTNode result = this.search(this, c);
            if (result == null) {
                result = new FTNode(c);
                if (next == null) {
                    next = result;
                } else {
                    result.next = next;
                    next = result;
                }
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
            return this.search(child, c);
        }

        public int getCount() {
            return count;
        }

        public char getLetter() {
            return letter;
        }

        /*
         * Searches neighbors starting with 'start' for 'letter'.
         */
        private FTNode search(FTNode start, char letter) {
            FTNode current = start;
            while (current != null) {
                if (current.letter == letter) {
                    return current;
                }
                current = current.next;
            }
            return null; // If matching node not found.
        }
    }
}
