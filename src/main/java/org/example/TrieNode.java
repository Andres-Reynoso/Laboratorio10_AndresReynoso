package org.example;

class TrieNode {
    TrieNode[] children;
    boolean isEndOfWord;
    int frequency;
    long timestamp;

    public TrieNode() {
        children = new TrieNode[26]; // solo letras a-z
        isEndOfWord = false;
        frequency = 0;
        timestamp = System.nanoTime();
    }
}