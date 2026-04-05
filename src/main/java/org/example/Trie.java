package org.example;

import java.util.*;

public class Trie {
    private TrieNode root;

    public Trie() {
        root = new TrieNode();
    }

    public void insert(String word) {
        TrieNode current = root;
        for (char c : word.toCharArray()) {
            int index = c - 'a';
            if (current.children[index] == null) {
                current.children[index] = new TrieNode();
            }
            current = current.children[index];
        }
        current.isEndOfWord = true;
        current.frequency++;
    }


    public boolean search(String word) {
        return searchHelper(word, 0, root);
    }

    private boolean searchHelper(String word, int pos, TrieNode node) {
        if (node == null) return false;

        if (pos == word.length()) {
            return node.isEndOfWord;
        }

        char c = word.charAt(pos);

        if (c == '.') {
            for (int i = 0; i < 26; i++) {
                if (node.children[i] != null) {
                    if (searchHelper(word, pos + 1, node.children[i])) {
                        return true;
                    }
                }
            }
            return false;
        } else {
            int index = c - 'a';
            return searchHelper(word, pos + 1, node.children[index]);
        }
    }

    public boolean startsWith(String prefix) {
        return findNode(prefix) != null;
    }

    private TrieNode findNode(String prefix) {
        TrieNode current = root;
        for (char c : prefix.toCharArray()) {
            int index = c - 'a';
            if (current.children[index] == null) {
                return null;
            }
            current = current.children[index];
        }
        return current;
    }


    public void preorder() {
        preorderHelper(root, "");
    }

    private void preorderHelper(TrieNode node, String word) {
        if (node == null) return;

        if (node.isEndOfWord) {
            System.out.println(word);
        }

        for (int i = 0; i < 26; i++) {
            if (node.children[i] != null) {
                preorderHelper(node.children[i], word + (char)(i + 'a'));
            }
        }
    }


    public void postorder() {
        postorderHelper(root, "");
    }

    private void postorderHelper(TrieNode node, String word) {
        if (node == null) return;

        for (int i = 0; i < 26; i++) {
            if (node.children[i] != null) {
                postorderHelper(node.children[i], word + (char)(i + 'a'));
            }
        }

        if (node.isEndOfWord) {
            System.out.println(word);
        }
    }


    public void bfs() {
        Queue<Pair> queue = new LinkedList<>();
        queue.add(new Pair(root, ""));

        while (!queue.isEmpty()) {
            Pair current = queue.poll();
            TrieNode node = current.node;
            String word = current.word;

            if (node.isEndOfWord) {
                System.out.println(word);
            }

            for (int i = 0; i < 26; i++) {
                if (node.children[i] != null) {
                    queue.add(new Pair(node.children[i], word + (char)(i + 'a')));
                }
            }
        }
    }


    public List<String> autocomplete(String prefix) {
        List<String> result = new ArrayList<>();
        TrieNode node = findNode(prefix);
        if (node == null) return result;

        autocompleteHelper(node, prefix, result);
        return result;
    }

    private void autocompleteHelper(TrieNode node, String word, List<String> result) {
        if (node == null) return;

        if (node.isEndOfWord) {
            result.add(word);
        }

        for (int i = 0; i < 26; i++) {
            if (node.children[i] != null) {
                autocompleteHelper(node.children[i], word + (char)(i + 'a'), result);
            }
        }
    }


    public List<String> autocomplete(String prefix, int k) {
        List<WordData> words = new ArrayList<>();
        TrieNode node = findNode(prefix);
        if (node == null) return new ArrayList<>();

        collectWords(node, prefix, words);

        words.sort((a, b) -> {
            if (b.frequency != a.frequency) {
                return b.frequency - a.frequency;
            }
            return Long.compare(a.timestamp, b.timestamp);
        });

        List<String> result = new ArrayList<>();
        for (int i = 0; i < Math.min(k, words.size()); i++) {
            result.add(words.get(i).word);
        }
        return result;
    }

    private void collectWords(TrieNode node, String word, List<WordData> words) {
        if (node == null) return;

        if (node.isEndOfWord) {
            words.add(new WordData(word, node.frequency, node.timestamp));
        }

        for (int i = 0; i < 26; i++) {
            if (node.children[i] != null) {
                collectWords(node.children[i], word + (char)(i + 'a'), words);
            }
        }
    }
}