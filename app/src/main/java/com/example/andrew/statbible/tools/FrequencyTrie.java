package com.example.andrew.statbible.tools;

/**
 * Created by andrew on 11/10/17.
 */

public class FrequencyTrie {

    private final static char ROOT = '^';

    private FTNode root;

    public FrequencyTrie(String source) {
        root = new FTNode();
        for (String word : source.split(" ")) {
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
        // TODO
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

        /*
         * Method adds character c as a child of this node if a child
         * node does not already exist for character c. If the later is
         * the case, the existing node has its count incremented.
         *
         * Method returns the new (or incremented) node.
         */
        public FTNode add(char c) {

        }

        /*
         * Method adds character c as the neighbor of this node in the same
         * manner and with the same stipulations as the add(char c) method.
         */
        public FTNode stack(char c) {

        }

        // TODO
    }
}
