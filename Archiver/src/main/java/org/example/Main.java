package org.example;

import java.io.*;
import static java.lang.System.out;

// Архиватор
public class Main {

    public static void main(String[] args) throws IOException {
        try {
            System.setOut(new PrintStream(out, true, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        var inputFilePath = "C:\\WORK\\Programming\\Java" +
                "\\Fundamentals of coding and encryption theory\\Archiver\\exemp_init.txt";

        // Чтение текста из файла
        BufferedReader reader = new BufferedReader(new FileReader(inputFilePath));
        StringBuilder inputText = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            inputText.append(line);
        }
        out.println();

        // Использование RLE
        RLE rle = new RLE();
        String rleEncoded = rle.encode(inputText.toString());
        String rleDecoded = rle.decode(rleEncoded);
        out.println(rleEncoded + "\n\n");
        out.println(rleDecoded);
        out.println("\nArchiver RLE created!\n\n");

        // Использование Хаффмана
        Huffman huffman = new Huffman();
        String huffmanEncoded = huffman.encode(inputText.toString());
        String huffmanDecoded = huffman.decode(huffmanEncoded);
        out.println(huffman.getStringTable() + "\n");
        out.println(huffmanEncoded + "\n\n");
        out.println(huffmanDecoded);
        out.println("\nArchiver Huffman created!\n\n");


        float ratioComHuff = (float) huffmanEncoded.length()
                / ((float) huffmanDecoded.getBytes().length * 8);
        float ratioEffHuff = huffman.calculateEntropy(huffmanDecoded)
                / huffman.calculateAverageCodeLength();
        float ratioComRLE = (float) rleEncoded.length()
                / (float) rleDecoded.length();
        float ratio = (float) huffmanEncoded.length()
                / ((float) rleEncoded.getBytes().length * 8);
        out.println("Compression ratio Huffman: " + ratioComHuff);
        out.println("Relative efficiency ratio Huffman: " + ratioEffHuff + "\n");
        out.println("Compression ratio RLE: " + ratioComRLE + "\n");
        out.println("Ratio: " + ratio);
    }
}
