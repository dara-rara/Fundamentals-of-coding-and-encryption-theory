package org.example;

import java.io.*;
import java.util.Random;

import static java.lang.System.out;

// Криптография
public class Main {

    public static void main(String[] args) throws IOException {
        try {
            System.setOut(new PrintStream(out, true, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        byte[] key = new byte[32]; //ключ
        byte[] nonce = new byte[12]; //одноразовые ключи
        int counter = 1; //начальный счетчик

        Random random = new Random();
        // Заполнение массива случайными значениями
        random.nextBytes(key);
        random.nextBytes(nonce);

        ChaCha20 chacha20 = new ChaCha20(key, nonce, counter);
        byte[] plaintext = readFile();

        byte[] encrypted = chacha20.encrypt(plaintext);
        byte[] decrypted = chacha20.decrypt(encrypted);


        var enFilePath = "C:\\WORK\\Programming\\Java" +
                "\\Fundamentals of coding and encryption theory\\Chipher\\init-en.txt";
        var decFilePath = "C:\\WORK\\Programming\\Java" +
                "\\Fundamentals of coding and encryption theory\\Chipher\\init-dec.txt";

        writeFile(enFilePath, new String(encrypted));
        writeFile(decFilePath, new String(decrypted));
    }

    // Метод для преобразования массива байтов в строку в шестнадцатеричном представлении
    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

    private static byte[] readFile() throws IOException {
        var inputFilePath = "C:\\WORK\\Programming\\Java" +
                "\\Fundamentals of coding and encryption theory\\Chipher\\init.txt";

        // Чтение текста из файла
        BufferedReader reader = new BufferedReader(new FileReader(inputFilePath));
        StringBuilder inputText = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            inputText.append(line);
        }

        return inputText.toString().getBytes();
    }

    private static void writeFile(String fileName, String text) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
        writer.write(text);

        writer.close();
    }
}