package org.example;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Trie trie = new Trie();

        trie.insert("casa");
        trie.insert("casa");
        trie.insert("carro");
        trie.insert("carne");
        trie.insert("camino");
        trie.insert("carro");
*/ hoal
        System.out.println(trie.search("carro")); // true
        System.out.println(trie.search("carta")); // false

        System.out.println(trie.startsWith("car")); // true

        System.out.println(trie.autocomplete("ca"));
        System.out.println(trie.autocomplete("ca", 2));
    }
}