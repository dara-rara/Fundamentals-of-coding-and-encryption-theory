package org.example;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

class HuffmanNode implements Comparable<HuffmanNode> {
    int frequency;
    char character;
    HuffmanNode left, right;

    public HuffmanNode(char character, int frequency) {
        this.character = character;
        this.frequency = frequency;
    }

    public int compareTo(HuffmanNode node) {
        return Integer.compare(this.frequency, node.frequency);
    }
}

public class Huffman {
    private Map<Character, String> huffmanCodeMap = new HashMap<>();
    private Map<Character, Integer> frequencyMap = new HashMap<>();

    private void buildHuffmanTree(String text) {
        //частотный словарь
        for (char c : text.toCharArray()) {
            frequencyMap.put(c, frequencyMap.getOrDefault(c, 0) + 1);
        }

        //приорететная очередь
        PriorityQueue<HuffmanNode> priorityQueue = new PriorityQueue<>();
        for (Map.Entry<Character, Integer> entry : frequencyMap.entrySet()) {
            priorityQueue.add(new HuffmanNode(entry.getKey(), entry.getValue()));
        }

        //дерево
        while (priorityQueue.size() > 1) {
            HuffmanNode left = priorityQueue.poll();
            HuffmanNode right = priorityQueue.poll();
            HuffmanNode parent = new HuffmanNode('\0', left.frequency + right.frequency);
            parent.left = left;
            parent.right = right;
            priorityQueue.add(parent);
        }
        generateCodes(priorityQueue.peek(), "");
    }

    private void generateCodes(HuffmanNode node, String code) {
        if (node != null) {
            if (node.left == null && node.right == null) {
                huffmanCodeMap.put(node.character, code);
            }
            generateCodes(node.left, code + "0");
            generateCodes(node.right, code + "1");
        }
    }

    //Кодирование
    public String encode(String text) {
        buildHuffmanTree(text);
        StringBuilder encodedString = new StringBuilder();
        for (char c : text.toCharArray()) {
            encodedString.append(huffmanCodeMap.get(c));
        }
        return encodedString.toString();
    }

    //Декодирование
    public String decode(String encodedString) {
        StringBuilder decodedString = new StringBuilder();
        HuffmanNode node = buildDecodeTree(huffmanCodeMap);
        HuffmanNode current = node;

        for (char bit : encodedString.toCharArray()) {
            current = (bit == '0') ? current.left : current.right;

            if (current.left == null && current.right == null) {
                decodedString.append(current.character);
                current = node;
            }
        }
        return decodedString.toString();
    }

    private HuffmanNode buildDecodeTree(Map<Character, String> huffmanCodeMap) {
        HuffmanNode root = new HuffmanNode('\0', 0);
        for (Map.Entry<Character, String> entry : huffmanCodeMap.entrySet()) {
            String code = entry.getValue();
            HuffmanNode current = root;
            for (char bit : code.toCharArray()) {
                if (bit == '0') {
                    if (current.left == null) {
                        current.left = new HuffmanNode('\0', 0);
                    }
                    current = current.left;
                } else {
                    if (current.right == null) {
                        current.right = new HuffmanNode('\0', 0);
                    }
                    current = current.right;
                }
            }
            current.character = entry.getKey();
        }
        return root;
    }

    public float calculateEntropy(String input) {
        int totalChars = input.length();
        double entropy = 0.0;

        // Вычисление энтропии
        for (Map.Entry<Character, Integer> entry : frequencyMap.entrySet()) {
            var probability = (float) entry.getValue() / totalChars;
            entropy -= probability * (Math.log(probability) / Math.log(2)); // log2 пропорциональна
        }

        return (float)entropy;
    }

    public float calculateAverageCodeLength() {
        int totalLength = 0;
        int numberOfKeys = huffmanCodeMap.size();

        for (String value : huffmanCodeMap.values()) {
            totalLength += value.length();
        }

        return (float) totalLength / numberOfKeys;
    }

    public String getStringTable() {
        var sb = new StringBuilder();
        for (Map.Entry<Character, String> entry : huffmanCodeMap.entrySet()) {
            sb.append(entry.getKey() + ": " + entry.getValue() + "; ");
        }
        return sb.toString();
    }
}